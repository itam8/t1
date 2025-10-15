package org.example.clientprocessing.repository;

import org.example.clientprocessing.model.Role;
import org.example.clientprocessing.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
