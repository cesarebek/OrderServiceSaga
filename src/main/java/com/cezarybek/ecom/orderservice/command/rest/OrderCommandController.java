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
    private final static String userId = "123";

    public OrderCommandController(QueryGateway queryGateway, CommandGateway commandGateway) {
        this.queryGateway = queryGateway;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public OrderEntity addProductToCart(@RequestBody Product product){

        UserOrderByStatusQuery query = new UserOrderByStatusQuery(userId, OrderStatus.CART.toString());

        Optional<OrderEntity> findOrder = queryGateway.query(query, ResponseTypes.optionalInstanceOf(OrderEntity.class)).join();

        OrderEntity orderEntity = null;

        if(findOrder.isPresent()){
            AddProductToOrderCommand addProductToOrderCommand = AddProductToOrderCommand.builder()
                    .orderId(findOrder.get().getOrderId())
                    .product(product)
                    .build();

            OrderEntity orderUpdated = commandGateway.sendAndWait(addProductToOrderCommand);
            orderEntity = orderUpdated;
        }else {
            CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                    .orderId(UUID.randomUUID().toString())
                    .userId(userId)
                    .orderStatus(OrderStatus.CART.toString())
                    .build();

            OrderEntity orderCreated = commandGateway.sendAndWait(createOrderCommand);

            AddProductToOrderCommand addProductToOrderCommand = AddProductToOrderCommand.builder()
                    .orderId(orderCreated.getOrderId())
                    .product(product)
                    .build();

            OrderEntity orderUpdated = commandGateway.sendAndWait(addProductToOrderCommand);

            orderEntity = orderUpdated;

        }

        return orderEntity;
    }
}
