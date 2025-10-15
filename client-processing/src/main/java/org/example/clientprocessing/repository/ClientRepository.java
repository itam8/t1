package org.example.clientprocessing.repository;

import org.example.clientprocessing.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = "SELECT COALESCE(MAX(SUBSTRING(c.client_id, 5)::integer), 0) FROM clients c", nativeQuery = true)
    int findMaxSequenceNumber();

    boolean existsByDocumentId(String documentId);
}
