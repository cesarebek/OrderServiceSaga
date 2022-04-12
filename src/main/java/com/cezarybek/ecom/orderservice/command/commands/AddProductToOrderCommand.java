package com.cezarybek.ecom.orderservice.command.commands;

import com.cezarybek.ecom.orderservice.model.Product;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class AddProductToOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;
    private final Product product;
}
