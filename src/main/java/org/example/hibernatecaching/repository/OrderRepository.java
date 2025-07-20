package org.example.hibernatecaching.repository;

import org.example.hibernatecaching.model.Order;
import org.example.hibernatecaching.model.compositekey.OrderCompositKeyWithId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, OrderCompositKeyWithId> {
}
