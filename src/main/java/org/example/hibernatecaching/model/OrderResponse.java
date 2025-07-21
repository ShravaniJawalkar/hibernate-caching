package org.example.hibernatecaching.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("order_date")
    private LocalDateTime orderDate;
    @JsonProperty("user")
    private String userName;
    @JsonProperty("products")
    private List<ProductResponse> products = new ArrayList<>();
}
