package org.example.hibernatecaching.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_dumy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "productCache")
@Cacheable
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "product_order",
            joinColumns = {
                    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "order_id", referencedColumnName = "order_id"),
                    @JoinColumn(name = "order_date", referencedColumnName = "order_date")
            })
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        if (order != null) {
            orders.add(order);
//            order.getProducts().add(this); // Ensure bidirectional relationship
        }
    }
}

