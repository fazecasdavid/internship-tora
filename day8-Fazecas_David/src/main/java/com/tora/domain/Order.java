package com.tora.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order implements Comparable<Order> {
    private final int id;
    private final int price;
    private final int quantity;

    @Override
    public int compareTo(Order o) {
        return Integer.compare(price, o.price);
    }
}
