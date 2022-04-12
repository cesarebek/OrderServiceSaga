package com.cezarybek.ecom.orderservice.command.commands;

import com.cezarybek.ecom.orderservice.model.Product;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;
    private final String userId;
    private final String orderStatus;
    private final List<Product> products = new ArrayList<>();
}
