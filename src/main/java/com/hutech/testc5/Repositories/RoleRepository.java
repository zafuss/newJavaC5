package com.hutech.testc5.Repositories;


import com.hutech.testc5.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, String> {
    @Query("SELECT r FROM Role r where r.role_name=?1 ")
    Role findOneByName(String name);
}