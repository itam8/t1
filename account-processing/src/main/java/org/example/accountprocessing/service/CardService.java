package org.example.accountprocessing.service;

import lombok.AllArgsConstructor;
import org.example.accountprocessing.dto.ClientCardDto;
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

    public Card create(ClientCardDto clientCardDto) {
        Account account = accountService.findByClientIdAndProductId(clientCardDto);
        Long accountId = account.getId();
        if (accountService.isAccountClosed(accountId)) {
            throw new IllegalStateException("Счёт с таким id заблокирован");
        }

        return cardRepository.save(
                new Card(null, accountId, generateCardId(accountId), clientCardDto.getPaymentSystem(), Status.OPENED)
        );
    }

    private String generateCardId(Long accountId) {
        return String.valueOf(10000000000000001L + accountId).substring(1);
    }
}
