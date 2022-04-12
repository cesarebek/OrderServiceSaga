package com.cezarybek.ecom.orderservice.query.queries;

import com.cezarybek.ecom.orderservice.core.OrderStatus;
import lombok.Data;

@Data
public class UserOrderByStatusQuery {
    private final String userId;
    private final String orderStatus;
}
