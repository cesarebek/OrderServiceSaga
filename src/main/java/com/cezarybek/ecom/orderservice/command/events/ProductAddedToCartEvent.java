package com.cezarybek.ecom.orderservice.command.events;

import com.cezarybek.ecom.orderservice.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class ProductAddedToCartEvent {
    @TargetAggregateIdentifier
    private String orderId;
    private Product product;
}
