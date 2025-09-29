package org.example.clientprocessing.service;

import lombok.AllArgsConstructor;
import org.example.clientprocessing.model.BlacklistRegistry;
import org.example.clientprocessing.repository.BlacklistRegistryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BlacklistRegistryService {
    private final BlacklistRegistryRepository blacklistRegistryRepository;

    public boolean isInBlacklist(String documentId) {
        Optional<BlacklistRegistry> blacklistRegistry = blacklistRegistryRepository.findByDocumentId(documentId);
        return blacklistRegistry.isPresent();
    }
}
