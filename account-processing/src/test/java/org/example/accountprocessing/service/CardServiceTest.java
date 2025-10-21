package org.example.accountprocessing.service;

import org.example.accountprocessing.dto.ClientCardDto;
import org.example.accountprocessing.model.Account;
import org.example.accountprocessing.model.Card;
import org.example.accountprocessing.model.Status;
import org.example.accountprocessing.repository.CardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {
    @InjectMocks
    private CardService cardService;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private AccountService accountService;

    @Test
    void create_withOpenedAccount() {
        ClientCardDto clientCardDto = new ClientCardDto();
        Account account = new Account();
        account.setId(1L);
        Card card =
                new Card(null, account.getId(), "0000000000000002", clientCardDto.getPaymentSystem(), Status.OPENED);

        Mockito.when(accountService.findByClientIdAndProductId(clientCardDto)).thenReturn(account);
        Mockito.when(accountService.isAccountClosed(account.getId())).thenReturn(false);
        Mockito.when(cardRepository.save(card)).thenReturn(card);

        Assertions.assertEquals(card, cardService.create(clientCardDto));
    }

    @Test
    void create_withClosedAccount() {
        ClientCardDto clientCardDto = new ClientCardDto();
        Account account = new Account();
        account.setId(2L);

        Mockito.when(accountService.findByClientIdAndProductId(clientCardDto)).thenReturn(account);
        Mockito.when(accountService.isAccountClosed(account.getId())).thenReturn(true);

        Assertions.assertThrows(IllegalStateException.class, () -> cardService.create(clientCardDto));
    }
}
