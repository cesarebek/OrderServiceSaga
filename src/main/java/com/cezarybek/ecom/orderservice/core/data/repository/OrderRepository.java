package com.cezarybek.ecom.orderservice.core.data.repository;

import com.cezarybek.ecom.orderservice.core.OrderStatus;
import com.cezarybek.ecom.orderservice.core.data.entity.OrderEntity;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    Optional<OrderEntity> findOrderEntityByUserIdAndOrderStatus(String userId, String orderStatus);
    OrderEntity getOrderEntityByOrderId(String orderId);
}
