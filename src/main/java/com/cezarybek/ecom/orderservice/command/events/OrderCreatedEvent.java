package com.cezarybek.ecom.orderservice.command.events;

import com.cezarybek.ecom.orderservice.core.OrderStatus;
import com.cezarybek.ecom.orderservice.model.Product;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderCreatedEvent {
    @TargetAggregateIdentifier
    private String orderId;
    private String userId;
    private String orderStatus;
    private List<Product> products;
}
