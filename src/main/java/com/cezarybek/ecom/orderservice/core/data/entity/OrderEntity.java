package com.cezarybek.ecom.orderservice.core.data.entity;

import com.cezarybek.ecom.orderservice.core.OrderStatus;
import com.cezarybek.ecom.orderservice.model.Product;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
@TypeDef(name = "json", typeClass = JsonType.class)
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    private String orderId;
    private String userId;
    private String orderStatus;
    @Column(columnDefinition = "json")
    @Type(type = "json")
    private List<Product> products = new ArrayList<>();
}
