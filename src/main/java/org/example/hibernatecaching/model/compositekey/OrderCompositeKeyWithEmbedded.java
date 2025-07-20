package org.example.hibernatecaching.model.compositekey;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderCompositeKeyWithEmbedded implements Serializable {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "order_seq", sequenceName = "order_sequence", allocationSize = 6, initialValue = 1)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "order_date", unique = true, nullable = false, updatable = false)
    private LocalDateTime orderDate;
}
