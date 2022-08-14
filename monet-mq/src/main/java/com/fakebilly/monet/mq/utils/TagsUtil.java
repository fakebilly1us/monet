package com.fakebilly.monet.mq.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * TagsUtil
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class TagsUtil {

    /**
     * 将多个 tag 组合成 "TagA||TagB||TagC" 格式
     * @param tags
     * @param isTags2GroupId
     * @return
     */
    public static String formatTag(Set<String> tags, boolean isTags2GroupId) {
        String tag = "*";
        if (null == tags || tags.size() == 0) {
            return tag;
        } else if (tags.size() == 1) {
            tag = tags.iterator().next();
        } else {
            // 处理 HashSet 的无序导致应用重启之后 consumer 的 groupName 发生变化而重复消费
            if (isTags2GroupId && tags instanceof HashSet) {
                tags = new TreeSet<>(tags);
            }
            StringBuffer sb = new StringBuffer();
            for (String s : tags) {
                sb.append(s).append("||");
            }
            if (sb.indexOf("||") > 0) {
                sb.delete(sb.length() - 2, sb.length());
            }
            tag = sb.toString();
        }
        return tag;

    }

}
