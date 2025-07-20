package org.example.hibernatecaching.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.hibernatecaching.model.compositekey.OrderCompositKeyWithId;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.time.LocalDateTime;

@IdClass(OrderCompositKeyWithId.class)
@Table(name = "order_dumy", schema = "public")
@Entity
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "orderCache")
@Cacheable
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 6, initialValue = 1)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "price", nullable = false)
    private Double price;
    @Id
    @Column(name = "order_date", nullable = false, updatable = false)
    private LocalDateTime orderDate;


}
