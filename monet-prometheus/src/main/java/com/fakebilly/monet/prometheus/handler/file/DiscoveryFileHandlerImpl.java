package com.fakebilly.monet.prometheus.handler.file;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fakebilly.monet.prometheus.config.MonetMonitorConfigInfo;
import com.fakebilly.monet.prometheus.enums.DiscoveryTypeEnum;
import com.fakebilly.monet.prometheus.handler.DiscoveryHandler;
import com.fakebilly.monet.prometheus.util.OSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * DiscoveryFileHandlerImpl
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Component
public class DiscoveryFileHandlerImpl implements DiscoveryHandler {

    private static final Logger logger = LoggerFactory.getLogger(DiscoveryFileHandlerImpl.class);

    private static final int BYTE_BUFFER_CAPACITY = 1024;

    private static final String DISCOVERY_PATH_DEFAULT = "";
    private static final String DISCOVERY_FILE_NAME_DEFAULT = "prometheus-discovery.json";

    private static final String PROMETHEUS_JOB_NAME_DEFAULT = "application_file_discovery";

    private static String host = "";

    @Autowired
    private MonetMonitorConfigInfo monetMonitorConfigInfo;

    @Override
    public String getType() {
        return DiscoveryTypeEnum.FILE.getCode();
    }

    @Override
    public void discovery() {
        dealDiscovery();
    }

    private void dealDiscovery() {
        FileChannel channel = null;
        FileLock lock = null;
        try {
            String ipv4 = OSUtil.getIPV4ExcludeLocal();
            if (StrUtil.isBlank(ipv4)) {
                logger.warn("DiscoveryHandler deal file cannot find ipv4");
                return;
            }
            String discoveryPath = StrUtil.isBlank(monetMonitorConfigInfo.getDiscoveryFilePath()) ? DISCOVERY_PATH_DEFAULT : monetMonitorConfigInfo.getDiscoveryFilePath();
            Path path = Paths.get(discoveryPath, monetMonitorConfigInfo.getApplicationName() + "-" + DISCOVERY_FILE_NAME_DEFAULT);
            boolean exists = Files.exists(path);
            if (!exists) {
                Files.createFile(path);
            }
            channel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.READ);
            ByteBuffer byteBuffer = ByteBuffer.allocate(BYTE_BUFFER_CAPACITY);
            long size = channel.size() - 1 < 0 ? 0 : channel.size() - 1;
            lock = channel.tryLock(0, size, Boolean.TRUE);
            if (null == lock || !lock.isValid()) {
                return;
            }
            StringBuffer content = new StringBuffer();
            while (channel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(byteBuffer);
                content.append(charBuffer);
                byteBuffer.clear();
            }
            host = ipv4 + ":" + monetMonitorConfigInfo.getServerPort();
            List<FileSdConfig> fileSdConfigs = convert(content.toString());
            boolean write = true;
            if (CollectionUtil.isEmpty(fileSdConfigs)) {
                fileSdConfigs = new ArrayList<>();
                FileSdConfig fileSdConfig = buildConfig();
                fileSdConfigs.add(fileSdConfig);
            } else {
                boolean addConfig = true;
                for (FileSdConfig fileSdConfig : fileSdConfigs) {
                    Labels labels = fileSdConfig.getLabels();
                    String applicationName = labels.getApplicationName();
                    if (applicationName.equals(monetMonitorConfigInfo.getApplicationName())) {
                        addConfig = false;
                        List<String> targets = fileSdConfig.getTargets();
                        if (!targets.contains(host)) {
                            targets.add(host);
                        } else {
                            write = false;
                        }
                    }
                }
                if (addConfig) {
                    fileSdConfigs.add(buildConfig());
                }
            }
            if (!write) {
                return;
            }
            String convert = convert(fileSdConfigs);
            if (StrUtil.isBlank(convert)) {
                return;
            }
            byte[] convertBytes = convert.getBytes(StandardCharsets.UTF_8);
            channel.truncate(0);
            for (int i = 0; i < convertBytes.length; ) {
                byteBuffer.put(convertBytes, i, Math.min(convertBytes.length - i, byteBuffer.limit() - byteBuffer.position()));
                byteBuffer.flip();
                i += channel.write(byteBuffer);
                byteBuffer.compact();
            }
            channel.force(Boolean.TRUE);
            lock.release();
        } catch (Throwable t) {
            logger.warn("DiscoveryHandler deal file failed ", t);
        } finally {
            if (null != channel) {
                try {
                    channel.close();
                } catch (IOException e) {
                    logger.warn("DiscoveryHandler deal file channel close failed ", e);
                }
            }
            if (null != lock) {
                try {
                    if (lock.isValid()) {
                        lock.release();
                    }
                } catch (IOException e) {
                    logger.warn("DiscoveryHandler deal file lock close failed ", e);
                }
            }
        }
    }

    private List<FileSdConfig> convert(String content) {
        if (StrUtil.isBlank(content)) {
            return null;
        }
        return JSON.parseArray(content, FileSdConfig.class);
    }

    private String convert(List<FileSdConfig> list) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return JSON.toJSONString(list);
    }

    private FileSdConfig buildConfig() {
        FileSdConfig fileSdConfig = new FileSdConfig();
        fileSdConfig.setTargets(CollectionUtil.newArrayList(host));
        Labels labels = new Labels();
        labels.setApplicationName(monetMonitorConfigInfo.getApplicationName());
        labels.setJob(PROMETHEUS_JOB_NAME_DEFAULT);
        fileSdConfig.setLabels(labels);
        return fileSdConfig;
    }


}
