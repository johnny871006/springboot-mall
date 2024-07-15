package org.example.springbootmall.service.impl;

import org.example.springbootmall.dao.OrderDao;
import org.example.springbootmall.dao.ProductDao;
import org.example.springbootmall.dto.BuyItem;
import org.example.springbootmall.dto.CreateOrderRequest;
import org.example.springbootmall.model.OrderItem;
import org.example.springbootmall.model.Product;
import org.example.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        // 計算價錢
        int totalAmount = 0;

        // 轉換BuyItem to OrderItem
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getByProductId(buyItem.getProductId());

            int amount = product.getPrice() * buyItem.getQuantity();

            totalAmount = totalAmount + amount;

            // 轉換BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        // 創建訂單
        Integer orderId = orderDao.createOrder(userId,totalAmount);

        orderDao.createOrderItem(orderId,orderItemList);


        return orderId;
    }
}
