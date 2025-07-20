package org.example.hibernatecaching.repository;

import org.example.hibernatecaching.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
