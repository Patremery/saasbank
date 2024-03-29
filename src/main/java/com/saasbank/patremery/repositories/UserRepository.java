package com.saasbank.patremery.repositories;

import com.saasbank.patremery.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
}
