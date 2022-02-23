package com.tora.service;

import com.tora.domain.Order;
import com.tora.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final Repository<Order, Long> orderRepository;

    @Autowired
    public OrderService(Repository<Order, Long> orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public void deleteOrderById(long id) {
        orderRepository.deleteById(id);
    }

    public Iterable<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
