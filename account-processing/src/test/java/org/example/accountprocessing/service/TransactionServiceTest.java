package org.example.accountprocessing.service;

import org.example.accountprocessing.model.Transaction;
import org.example.accountprocessing.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private TransactionRepository transactionRepository;

    @Test
    void findAllByCardIdAndTimestampBetween() {
        long cardId = 1L;
        LocalDateTime timestampAfter = LocalDateTime.now();
        LocalDateTime timestampBefore = LocalDateTime.now();
        List<Transaction> transactions = Collections.emptyList();

        Mockito.when(transactionRepository.findAllByCardIdAndTimestampBetween(cardId, timestampAfter, timestampBefore))
                .thenReturn(transactions);

        Assertions.assertEquals(
                transactions,
                transactionService.findAllByCardIdAndTimestampBetween(cardId, timestampAfter, timestampBefore)
        );
    }
}
