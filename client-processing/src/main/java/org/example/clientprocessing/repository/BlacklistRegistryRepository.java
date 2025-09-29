package org.example.clientprocessing.repository;

import org.example.clientprocessing.model.BlacklistRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlacklistRegistryRepository extends JpaRepository<BlacklistRegistry, Long> {
    Optional<BlacklistRegistry> findByDocumentId(String documentId);
}
