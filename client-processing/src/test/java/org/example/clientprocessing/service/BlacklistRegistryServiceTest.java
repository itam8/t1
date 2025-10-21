package org.example.clientprocessing.service;

import org.example.clientprocessing.model.BlacklistRegistry;
import org.example.clientprocessing.model.Role;
import org.example.clientprocessing.model.RoleEnum;
import org.example.clientprocessing.model.User;
import org.example.clientprocessing.repository.BlacklistRegistryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BlacklistRegistryServiceTest {
    @InjectMocks
    private BlacklistRegistryService blacklistRegistryService;
    @Mock
    private BlacklistRegistryRepository blacklistRegistryRepository;
    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;

    @Test
    void isInBlacklist_withPresent() {
        String documentId = "11";
        Optional<BlacklistRegistry> blacklistRegistry = Optional.of(new BlacklistRegistry());

        Mockito.when(blacklistRegistryRepository.findByDocumentId(documentId)).thenReturn(blacklistRegistry);

        Assertions.assertTrue(blacklistRegistryService.isInBlacklist(documentId));
    }

    @Test
    void isInBlacklist_withEmpty() {
        String documentId = "111";
        Optional<BlacklistRegistry> blacklistRegistry = Optional.empty();

        Mockito.when(blacklistRegistryRepository.findByDocumentId(documentId)).thenReturn(blacklistRegistry);

        Assertions.assertFalse(blacklistRegistryService.isInBlacklist(documentId));
    }

    @Test
    void create() {
        String documentId = "11";
        BlacklistRegistry blacklistRegistry = new BlacklistRegistry();
        blacklistRegistry.setDocumentId(documentId);

        Mockito.when(userService.findByDocumentId(documentId)).thenReturn(new User());
        Mockito.when(roleService.findByName(RoleEnum.ROLE_BLOCKED_CLIENT)).thenReturn(new Role());

        blacklistRegistryService.create(blacklistRegistry);

        Mockito.verify(blacklistRegistryRepository, Mockito.times(1)).save(blacklistRegistry);
    }
}
