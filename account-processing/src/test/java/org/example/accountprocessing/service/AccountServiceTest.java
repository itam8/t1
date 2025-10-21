package org.example.accountprocessing.service;

import org.example.accountprocessing.dto.ClientCardDto;
import org.example.accountprocessing.dto.ClientProductDto;
import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Status;
import org.example.accountprocessing.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;

    @Test
    void create() {
        ClientProductDto clientProductDto = new ClientProductDto();
        Account account = new Account(
                null,
                clientProductDto.getClientId(),
                clientProductDto.getProductId(),
                null,
                0.0,
                false,
                false,
                Status.OPENED
        );

        Mockito.when(accountRepository.save(account)).thenReturn(account);

        Assertions.assertEquals(account, accountService.create(clientProductDto));
    }

    @Test
    void findById_withValidId() {
        Account account = new Account();
        Optional<Account> optionalAccount = Optional.of(account);

        Mockito.when(accountRepository.findById(account.getId())).thenReturn(optionalAccount);

        Assertions.assertEquals(account, accountService.findById(account.getId()));
    }

    @Test
    void findById_withInvalidId() {
        Account account = new Account();
        Optional<Account> optionalAccount = Optional.empty();

        Mockito.when(accountRepository.findById(account.getId())).thenReturn(optionalAccount);

        Assertions.assertThrows(IllegalStateException.class, () -> accountService.findById(account.getId()));
    }

    @Test
    void findByClientIdAndProductId() {
        ClientCardDto clientCardDto = new ClientCardDto();
        Account account = new Account();

        Mockito.when(accountRepository.findByClientIdAndProductId(clientCardDto.getClientId(), clientCardDto.getProductId()))
                .thenReturn(account);

        Assertions.assertEquals(account, accountService.findByClientIdAndProductId(clientCardDto));
    }

    @Test
    void isAccountClosed_returnTrue() {
        long id = 1L;

        Mockito.when(accountRepository.existsByIdAndStatus(id, Status.CLOSED)).thenReturn(true);

        Assertions.assertTrue(accountService.isAccountClosed(id));
    }

    @Test
    void isAccountClosed_returnFalse() {
        long id = 1L;

        Mockito.when(accountRepository.existsByIdAndStatus(id, Status.CLOSED)).thenReturn(false);

        Assertions.assertFalse(accountService.isAccountClosed(id));
    }
}
