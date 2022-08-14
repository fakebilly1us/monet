package com.fakebilly.monet.mq.utils;

import org.apache.rocketmq.common.UtilAll;

/**
 * GroupNameUtil
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class GroupNameUtil {

    /**
     * 生成 producerGroupName
     * 相同业务的编为一组
     * @param group
     * @param topic
     * @return
     */
    public static String getGroupName(String group, String topic) {
        StringBuffer sb = new StringBuffer();
        sb.append(group)
                .append('_')
                .append(topic);
        return sb.toString();
    }

    /**
     * 生成唯一性 producerId/consumerId
     * @param group
     * @return
     */
    public static String buildInstanceName(String group) {
        StringBuffer sb = new StringBuffer();
        sb.append(UtilAll.getPid())
                .append('%')
                .append(group)
                .append('%')
                .append(System.nanoTime());
        return sb.toString();
    }
}
