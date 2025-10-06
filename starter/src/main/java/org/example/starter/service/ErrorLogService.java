package org.example.starter.service;

import lombok.AllArgsConstructor;
import org.example.starter.dto.ErrorLogDto;
import org.example.starter.repository.ErrorLogRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ErrorLogService {
    private final ErrorLogRepository errorLogRepository;

    public void create(ErrorLogDto errorLogDto) {
        errorLogRepository.save(errorLogDto);
    }
}
