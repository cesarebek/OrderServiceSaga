package com.cezarybek.ecom.orderservice.command.rest;

import com.cezarybek.ecom.orderservice.command.commands.AddProductToOrderCommand;
import com.cezarybek.ecom.orderservice.command.commands.CreateOrderCommand;
import com.cezarybek.ecom.orderservice.core.OrderStatus;
import com.cezarybek.ecom.orderservice.core.data.entity.OrderEntity;
import com.cezarybek.ecom.orderservice.core.data.repository.OrderRepository;
import com.cezarybek.ecom.orderservice.model.Product;
import com.cezarybek.ecom.orderservice.query.queries.UserOrderByStatusQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import java.util.UUID;

@RestController
public class OrderCommandController {

    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;
    private final static String userId = "121";

    public OrderCommandController(QueryGateway queryGateway, CommandGateway commandGateway) {
        this.queryGateway = queryGateway;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public void addProductToCart(@RequestBody Product product){

        UserOrderByStatusQuery query = new UserOrderByStatusQuery(userId, OrderStatus.CART.toString());

        Optional<OrderEntity> orderEntity = queryGateway.query(query, ResponseTypes.optionalInstanceOf(OrderEntity.class)).join();

        if(orderEntity.isPresent()){
            addProductToOrder(orderEntity.get().getOrderId(), product);
        }else {
            String orderId = UUID.randomUUID().toString();
            CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                    .orderId(orderId)
                    .userId(userId)
                    .orderStatus(OrderStatus.CART.toString())
                    .build();

            commandGateway.sendAndWait(createOrderCommand);

            addProductToOrder(orderId, product);
        }
    }

    private void addProductToOrder(String orderId, Product product){
        AddProductToOrderCommand addProductToOrderCommand = AddProductToOrderCommand.builder()
                .orderId(orderId)
                .product(product)
                .build();

        commandGateway.send(addProductToOrderCommand);
    }
}
