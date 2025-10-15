package org.example.clientprocessing.repository;

import org.example.clientprocessing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);

    @Query(value = "SELECT u.id, u.login, u.password, u.email FROM users u " +
            "JOIN clients c ON u.id = c.user_id " +
            "JOIN blacklist_registries b ON c.document_id = b.document_id " +
            "WHERE b.document_id = :documentId", nativeQuery = true)
    User findByDocumentId(String documentId);
}
