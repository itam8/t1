package org.example.clientprocessing.service;

import org.example.clientprocessing.dto.ClientDto;
import org.example.clientprocessing.dto.RegisterDto;
import org.example.clientprocessing.model.Client;
import org.example.clientprocessing.model.User;
import org.example.clientprocessing.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private UserService userService;
    @Mock
    private BlacklistRegistryService blacklistRegistryService;
    private RegisterDto registerDto;
    private Client client;
    private User user;

    @BeforeEach
    void setUp() {
        registerDto = new RegisterDto();
        registerDto.setRegionNumber("11");
        registerDto.setDivisionNumber("11");
        client = new Client();
        client.setClientId("111100000001");
        user = new User();
    }

    @Test
    void create_withValidClientId() {
        Mockito.when(userService.create(user)).thenReturn(user);
        Mockito.when(blacklistRegistryService.isInBlacklist(client.getDocumentId())).thenReturn(false);
        Mockito.when(clientRepository.existsByDocumentId(client.getDocumentId())).thenReturn(false);

        clientService.create(registerDto);

        Mockito.verify(userService, Mockito.times(1)).create(user);
        Mockito.verify(clientRepository, Mockito.times(1)).save(client);
    }

    @Test
    void create_withBlacklist() {
        Mockito.when(userService.create(user)).thenReturn(user);
        Mockito.when(blacklistRegistryService.isInBlacklist(client.getDocumentId())).thenReturn(true);

        Assertions.assertThrows(IllegalStateException.class, () -> clientService.create(registerDto));
    }

    @Test
    void create_withInvalidDocumentId() {
        Mockito.when(userService.create(user)).thenReturn(user);
        Mockito.when(blacklistRegistryService.isInBlacklist(client.getDocumentId())).thenReturn(false);
        Mockito.when(clientRepository.existsByDocumentId(client.getDocumentId())).thenReturn(true);

        Assertions.assertThrows(IllegalStateException.class, () -> clientService.create(registerDto));
    }

    @Test
    void create_withInvalidClientId() {
        registerDto.setDivisionNumber("111");
        Mockito.when(userService.create(user)).thenReturn(user);
        Mockito.when(blacklistRegistryService.isInBlacklist(client.getDocumentId())).thenReturn(false);
        Mockito.when(clientRepository.existsByDocumentId(client.getDocumentId())).thenReturn(false);

        Assertions.assertThrows(IllegalStateException.class, () -> clientService.create(registerDto));
    }

    @Test
    void findById_withValidId() {
        long id = 1L;
        Optional<Client> optionalClient = Optional.of(new Client());
        ClientDto clientDto = new ClientDto();

        Mockito.when(clientRepository.findById(id)).thenReturn(optionalClient);

        Assertions.assertEquals(clientDto, clientService.findById(id));
    }

    @Test
    void findById_withInvalidId() {
        long id = 1L;
        Optional<Client> optionalClient = Optional.empty();

        Mockito.when(clientRepository.findById(id)).thenReturn(optionalClient);

        Assertions.assertThrows(IllegalStateException.class, () -> clientService.findById(id));
    }
}
