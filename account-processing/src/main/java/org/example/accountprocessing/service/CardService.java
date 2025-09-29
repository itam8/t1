package org.example.accountprocessing.service;

import lombok.AllArgsConstructor;
import org.example.accountprocessing.dto.ClientProductDto;
import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Card;
import org.example.accountprocessing.model.Status;
import org.example.accountprocessing.repository.CardRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final AccountService accountService;

    public Card create(ClientProductDto clientProductDto) {
        Account account = accountService.findByClientIdAndProductId(clientProductDto);
        Long accountId = account.getId();
        if (accountService.isAccountClosed(accountId)) {
            throw new IllegalStateException("Счёт с таким id заблокирован");
        }

        return cardRepository.save(
                new Card(null, accountId, generateCardId(accountId), "Visa", Status.OPENED)
        );
    }

    private String generateCardId(Long accountId) {
        return String.valueOf(10000000000000001L + accountId).substring(1);
    }
}
