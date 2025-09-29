package org.example.accountprocessing.repository;

import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByIdAndStatus(Long id, Status status);

    Account findByClientIdAndProductId(Long clientId, Long productId);
}
