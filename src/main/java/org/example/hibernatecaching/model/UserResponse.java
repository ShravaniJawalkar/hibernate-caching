package org.example.hibernatecaching.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("address")
    private AddressRequest address = new AddressRequest();
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("orders")
    private List<OrderRequest> orders = new ArrayList<>();
}
