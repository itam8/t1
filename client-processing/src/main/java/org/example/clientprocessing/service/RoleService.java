package org.example.clientprocessing.service;

import lombok.AllArgsConstructor;
import org.example.clientprocessing.model.Role;
import org.example.clientprocessing.model.RoleEnum;
import org.example.clientprocessing.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findByName(RoleEnum roleEnum) {
        Optional<Role> optionalRole = roleRepository.findByName(roleEnum);
        if (optionalRole.isEmpty()) {
            throw new IllegalStateException("Такой роли не существует");
        }

        return optionalRole.get();
    }
}
