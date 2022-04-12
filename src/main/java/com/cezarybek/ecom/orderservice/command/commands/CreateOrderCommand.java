package com.cezarybek.ecom.orderservice.command.commands;

import com.cezarybek.ecom.orderservice.core.OrderStatus;
import com.cezarybek.ecom.orderservice.model.Product;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CreateOrderCommand {
    private final String orderId;
    private final String userId;
    private final String orderStatus;
    private final List<Product> products = new ArrayList<>();
}
