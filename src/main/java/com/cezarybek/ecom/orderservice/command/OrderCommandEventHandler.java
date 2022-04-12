package com.cezarybek.ecom.orderservice.command;

import com.cezarybek.ecom.orderservice.command.events.OrderCreatedEvent;
import com.cezarybek.ecom.orderservice.command.events.ProductAddedToCartEvent;
import com.cezarybek.ecom.orderservice.core.OrderStatus;
import com.cezarybek.ecom.orderservice.core.data.entity.OrderEntity;
import com.cezarybek.ecom.orderservice.core.data.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderCommandEventHandler {
    private final OrderRepository orderRepository;

    @EventHandler
    public void handle(OrderCreatedEvent event){
        OrderEntity orderEntity = new OrderEntity(
                event.getOrderId(),
                event.getUserId(),
                event.getOrderStatus().toString(),
                event.getProducts()
        );
        orderRepository.save(orderEntity);
    }

    @EventHandler
    public void handle(ProductAddedToCartEvent event){
        OrderEntity orderEntity = orderRepository.getOrderEntityByOrderId(event.getOrderId());
        orderEntity.getProducts().add(event.getProduct());
        orderRepository.save(orderEntity);
    }
}
