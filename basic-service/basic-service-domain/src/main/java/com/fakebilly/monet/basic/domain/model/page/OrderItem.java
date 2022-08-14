package com.fakebilly.monet.basic.domain.model.page;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderItem
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Data
@Accessors(chain = true)
@ToString
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 需要进行排序的字段
     */
    private String column;

    /**
     * 是否正序排列，默认 true
     */
    private boolean asc = true;

    public static OrderItem asc(String column) {
        return build(column, true);
    }

    public static OrderItem desc(String column) {
        return build(column, false);
    }

    public static List<OrderItem> ascs(String... columns) {
        return Arrays.stream(columns).map(OrderItem::asc).collect(Collectors.toList());
    }

    public static List<OrderItem> descs(String... columns) {
        return Arrays.stream(columns).map(OrderItem::desc).collect(Collectors.toList());
    }

    private static OrderItem build(String column, boolean asc) {
        OrderItem item = new OrderItem();
        item.setColumn(column);
        item.setAsc(asc);
        return item;
    }
}
