package com.fakebilly.monet.prometheus.util;

import cn.hutool.core.collection.CollectionUtil;

import java.lang.management.ManagementFactory;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OSUtil
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class OSUtil {

    private static volatile String OS_NAME;
    private static volatile String HOST_NAME;
    private static volatile List<String> IPV4_LIST;
    private static volatile int PROCESS_NO = 0;

    public static String getOsName() {
        if (OS_NAME == null) {
            OS_NAME = System.getProperty("os.name");
        }
        return OS_NAME;
    }

    public static String getHostName() {
        if (HOST_NAME == null) {
            try {
                InetAddress host = InetAddress.getLocalHost();
                HOST_NAME = host.getHostName();
            } catch (UnknownHostException e) {
                HOST_NAME = "unknown";
            }
        }
        return HOST_NAME;
    }

    public static List<String> getAllIPV4() {
        if (IPV4_LIST == null) {
            IPV4_LIST = new LinkedList<>();
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = networkInterfaces.nextElement();
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress address = inetAddresses.nextElement();
                        if (address instanceof Inet4Address) {
                            String addressStr = address.getHostAddress();
                            addressStr = "localhost".equals(addressStr) ? "127.0.0.1" : addressStr;
                            if (!IPV4_LIST.contains(addressStr)) {
                                IPV4_LIST.add(addressStr);
                            }
                        }
                    }
                }
            } catch (SocketException e) {
            }
        }
        return IPV4_LIST;
    }

    public static List<String> getAllIPV4ExcludeLocal() {
        final List<String> allIPV4 = getAllIPV4();
        if (CollectionUtil.isNotEmpty(allIPV4)) {
            List<String> allIPV4ExcludeLocal = allIPV4.stream().filter(e -> !e.equals("127.0.0.1")).collect(Collectors.toList());
            return CollectionUtil.isNotEmpty(allIPV4ExcludeLocal) ? allIPV4ExcludeLocal : Collections.emptyList();
        } else {
            return Collections.emptyList();
        }
    }

    public static String getIPV4() {
        final List<String> allIPV4 = getAllIPV4();
        if (CollectionUtil.isNotEmpty(allIPV4)) {
            return allIPV4.get(0);
        } else {
            return "";
        }
    }

    public static String getIPV4ExcludeLocal() {
        final List<String> allIPV4ExcludeLocal = getAllIPV4ExcludeLocal();
        if (CollectionUtil.isNotEmpty(allIPV4ExcludeLocal)) {
            return allIPV4ExcludeLocal.get(0);
        } else {
            return "";
        }
    }

    public static int getProcessNo() {
        if (PROCESS_NO == 0) {
            try {
                PROCESS_NO = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
            } catch (Exception e) {
                PROCESS_NO = -1;
            }
        }
        return PROCESS_NO;
    }

}
