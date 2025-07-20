package org.example.hibernatecaching.model.compositekey;

import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@IdClass(OrderCompositKeyWithId.class)
public class OrderCompositKeyWithId implements Serializable {

    private Long orderId;
    private LocalDateTime orderDate;
}
