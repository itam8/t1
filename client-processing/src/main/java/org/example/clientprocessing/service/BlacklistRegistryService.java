package org.example.clientprocessing.service;

import lombok.AllArgsConstructor;
import org.example.clientprocessing.model.BlacklistRegistry;
import org.example.clientprocessing.model.Role;
import org.example.clientprocessing.model.RoleEnum;
import org.example.clientprocessing.model.User;
import org.example.clientprocessing.repository.BlacklistRegistryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BlacklistRegistryService {
    private final BlacklistRegistryRepository blacklistRegistryRepository;
    private final UserService userService;
    private final RoleService roleService;

    public boolean isInBlacklist(String documentId) {
        Optional<BlacklistRegistry> blacklistRegistry = blacklistRegistryRepository.findByDocumentId(documentId);
        return blacklistRegistry.isPresent();
    }

    @Transactional
    public BlacklistRegistry create(BlacklistRegistry blacklistRegistry) {
        setBlockClientRoleForUser(blacklistRegistry.getDocumentId());
        return blacklistRegistryRepository.save(blacklistRegistry);
    }

    private void setBlockClientRoleForUser(String documentId) {
        User user = userService.findByDocumentId(documentId);
        Role role = roleService.findByName(RoleEnum.ROLE_BLOCKED_CLIENT);
        user.getRoles().add(role);
    }
}
