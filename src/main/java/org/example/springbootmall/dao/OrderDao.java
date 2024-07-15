package org.example.springbootmall.dao;

import org.example.springbootmall.model.Order;
import org.example.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemById(Integer orderId);

    Integer createOrder(Integer userId,Integer totalAmount);

    void createOrderItem(Integer orderId, List<OrderItem> orderItemList);
}
