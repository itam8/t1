package org.example.accountprocessing.repository;

import org.example.accountprocessing.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByCardIdAndTimestampBetween(Long cardId, LocalDateTime timestampAfter, LocalDateTime timestampBefore);
}
