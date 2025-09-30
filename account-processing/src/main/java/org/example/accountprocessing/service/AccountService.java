package org.example.accountprocessing.service;

import lombok.AllArgsConstructor;
import org.example.accountprocessing.dto.ClientCardDto;
import org.example.accountprocessing.dto.ClientProductDto;
import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Status;
import org.example.accountprocessing.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account create(ClientProductDto clientProductDto) {
        return accountRepository.save(new Account(
                null,
                clientProductDto.getClientId(),
                clientProductDto.getProductId(),
                null,
                0.0,
                false,
                false,
                Status.OPENED
        ));
    }

    public Account findByClientIdAndProductId(ClientCardDto clientCardDto) {
        return accountRepository.findByClientIdAndProductId(clientCardDto.getClientId(), clientCardDto.getProductId());
    }

    public boolean isAccountClosed(Long id) {
        return accountRepository.existsByIdAndStatus(id, Status.CLOSED);
    }
}
