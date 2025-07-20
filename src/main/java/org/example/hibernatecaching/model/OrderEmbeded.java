package org.example.hibernatecaching.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.hibernatecaching.model.compositekey.OrderCompositeKeyWithEmbedded;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "order_embedded",
        schema = "public")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "orderCache")
@Data
@Cacheable
public class OrderEmbeded {
    @EmbeddedId
    private OrderCompositeKeyWithEmbedded orderCompositKeyWithId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "price", nullable = false)
    private Double price;
}
