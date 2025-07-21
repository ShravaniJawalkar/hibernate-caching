package org.example.hibernatecaching.repository;

import org.example.hibernatecaching.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUserName(String name);

    Optional<User> findByUserName(String name);
}
