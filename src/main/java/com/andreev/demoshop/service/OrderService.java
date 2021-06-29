package com.andreev.demoshop.service;

import com.andreev.demoshop.domain.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();
    void saveOrder(Order order);
    Order getOrder(Long id);
}
