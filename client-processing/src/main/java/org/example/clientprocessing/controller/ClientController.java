package org.example.clientprocessing.controller;

import lombok.AllArgsConstructor;
import org.example.clientprocessing.dto.ClientDto;
import org.example.clientprocessing.dto.RegisterDto;
import org.example.clientprocessing.service.ClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public RegisterDto create(@RequestBody RegisterDto registerDto) {
        return clientService.create(registerDto);
    }

    @GetMapping
    public ClientDto findById(@RequestParam Long id) {
        return clientService.findById(id);
    }
}
