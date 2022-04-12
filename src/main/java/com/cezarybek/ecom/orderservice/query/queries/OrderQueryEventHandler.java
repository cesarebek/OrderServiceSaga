package com.cezarybek.ecom.orderservice.query.queries;

import com.cezarybek.ecom.orderservice.core.data.entity.OrderEntity;
import com.cezarybek.ecom.orderservice.core.data.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderQueryEventHandler {

    private final OrderRepository orderRepository;

    @QueryHandler
    Optional<OrderEntity> handle(UserOrderByStatusQuery query){
        return orderRepository.findOrderEntityByUserIdAndOrderStatus(query.getUserId(),query.getOrderStatus());
    }
}
