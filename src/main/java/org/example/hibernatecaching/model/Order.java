package org.example.hibernatecaching.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.example.hibernatecaching.model.compositekey.OrderCompositKeyWithId;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@IdClass(OrderCompositKeyWithId.class)
@Table(name = "order_dumy", schema = "public")
@Entity
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "orderCache")
@Cacheable
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderId")
public class Order {

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if(product != null) {
          products.add(product);
          product.getOrders().add(this);
        }
    }

}
