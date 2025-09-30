package org.example.accountprocessing.service;

import lombok.AllArgsConstructor;
import org.example.accountprocessing.model.Transaction;
import org.example.accountprocessing.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public List<Transaction> findAllByCardIdAndTimestampBetween(Long cardId, LocalDateTime timestampAfter, LocalDateTime timestampBefore) {
        return transactionRepository.findAllByCardIdAndTimestampBetween(cardId, timestampAfter, timestampBefore);
    }
}
