package org.example.clientprocessing.service;

import org.example.clientprocessing.model.Role;
import org.example.clientprocessing.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @InjectMocks
    private RoleService roleService;
    @Mock
    private RoleRepository roleRepository;

    @Test
    void findByName_withValidName() {
        Role role = new Role();
        Optional<Role> optionalRole = Optional.of(role);

        Mockito.when(roleRepository.findByName(role.getName())).thenReturn(optionalRole);

        Assertions.assertEquals(role, roleService.findByName(role.getName()));
    }

    @Test
    void findByName_withInvalidName() {
        Role role = new Role();
        Optional<Role> optionalRole = Optional.empty();

        Mockito.when(roleRepository.findByName(role.getName())).thenReturn(optionalRole);

        Assertions.assertThrows(IllegalStateException.class, () -> roleService.findByName(role.getName()));
    }
}
