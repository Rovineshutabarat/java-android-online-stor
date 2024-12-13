package com.online.store.server.repositories;

import com.online.store.server.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Boolean existsByName(String name);
}
