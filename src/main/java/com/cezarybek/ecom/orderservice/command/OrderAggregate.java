package com.cezarybek.ecom.orderservice.command;

import com.cezarybek.ecom.orderservice.command.commands.AddProductToOrderCommand;
import com.cezarybek.ecom.orderservice.command.commands.CreateOrderCommand;
import com.cezarybek.ecom.orderservice.command.events.OrderCreatedEvent;
import com.cezarybek.ecom.orderservice.command.events.ProductAddedToCartEvent;
import com.cezarybek.ecom.orderservice.core.OrderStatus;
import com.cezarybek.ecom.orderservice.model.Product;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private String userId;
    private String orderStatus;
    private List<Product> products;

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command){
        OrderCreatedEvent event = new OrderCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event){
        this.orderId = event.getOrderId();
        this.userId = event.getUserId();
        this.orderStatus = event.getOrderStatus();
        this.products = event.getProducts();
    }

    @CommandHandler
    public void handle(AddProductToOrderCommand command){
        ProductAddedToCartEvent event = new ProductAddedToCartEvent(
                command.getOrderId(),
                command.getProduct()
        );
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ProductAddedToCartEvent event){
        this.products.stream().map(p -> {
            if(event.getProduct().getProductId().equals(p.getProductId())) {
            p.setQuantity(p.getQuantity() + event.getProduct().getQuantity());
        }
            return p;
        });
    }
}
