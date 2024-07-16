package org.example.springbootmall.service;

import org.example.springbootmall.dto.CreateOrderRequest;
import org.example.springbootmall.dto.OrderQueryParams;
import org.example.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
