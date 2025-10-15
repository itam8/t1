package org.example.clientprocessing.service;

import lombok.AllArgsConstructor;
import org.example.clientprocessing.dto.ClientDto;
import org.example.clientprocessing.dto.RegisterDto;
import org.example.clientprocessing.model.Client;
import org.example.clientprocessing.model.User;
import org.example.clientprocessing.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final UserService userService;
    private final BlacklistRegistryService blacklistRegistryService;

    @Transactional
    public RegisterDto create(RegisterDto registerDto) {
        User user = userService.create(new User(
                registerDto.getLogin(),
                registerDto.getPassword(),
                registerDto.getEmail()
        ));

        Client client = new Client(
                null,
                generateClientId(registerDto.getRegionNumber(), registerDto.getDivisionNumber()),
                user.getId(),
                registerDto.getFirstName(),
                registerDto.getMiddleName(),
                registerDto.getLastName(),
                registerDto.getDateOfBirth(),
                registerDto.getDocumentType(),
                registerDto.getDocumentId(),
                registerDto.getDocumentPrefix(),
                registerDto.getDocumentSuffix()
        );

        if (isValidClient(client)) {
            clientRepository.save(client);
        }

        return registerDto;
    }

    private String generateClientId(String regionNumber, String divisionNumber) {
        return regionNumber
                + divisionNumber
                + String.valueOf(100000001 + clientRepository.findMaxSequenceNumber()).substring(1);
    }

    private boolean isValidClient(Client client) {
        if (blacklistRegistryService.isInBlacklist(client.getDocumentId())) {
            throw new IllegalStateException("Документ в чёрном списке");
        }
        if (clientRepository.existsByDocumentId(client.getDocumentId())) {
            throw new IllegalStateException("Клиент с таким документом уже существует");
        }
        if (client.getClientId().length() != 12) {
            throw new IllegalStateException("Неверный clientId");
        }

        return true;
    }

    public ClientDto findById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            throw new IllegalStateException("Клиент с таким id не существует");
        }
        Client client = optionalClient.get();

        return new ClientDto(client.getFirstName(), client.getMiddleName(), client.getLastName(), client.getDocumentId());
    }
}
