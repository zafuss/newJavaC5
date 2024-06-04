package com.hutech.tests3.Repositories;

import com.hutech.tests3.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {
}