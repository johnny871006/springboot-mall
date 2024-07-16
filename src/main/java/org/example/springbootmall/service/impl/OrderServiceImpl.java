package org.example.springbootmall.service.impl;

import org.example.springbootmall.dao.OrderDao;
import org.example.springbootmall.dao.ProductDao;
import org.example.springbootmall.dao.UserDao;
import org.example.springbootmall.dto.BuyItem;
import org.example.springbootmall.dto.CreateOrderRequest;
import org.example.springbootmall.model.Order;
import org.example.springbootmall.model.OrderItem;
import org.example.springbootmall.model.Product;
import org.example.springbootmall.model.User;
import org.example.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Order getOrderById(Integer orderId) {

        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemById(orderId);

        order.setOrderItemList(orderItemList);

        return order;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        // 檢查User是否存在
        User user = userDao.getUserById(userId);

        if(user == null){
            logger.warn("此userId {} 沒有註冊，請先註冊!",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 計算價錢
        int totalAmount = 0;

        // 轉換BuyItem to OrderItem
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getByProductId(buyItem.getProductId());

            if(product == null){
                logger.warn("沒有此商品Id {}",buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }else if ( product.getStock() < buyItem.getQuantity()){
                logger.warn("此商品 {} 庫存不足! ", product.getProductName());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            productDao.updateStock(product.getProductId(),product.getStock() - buyItem.getQuantity());

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
