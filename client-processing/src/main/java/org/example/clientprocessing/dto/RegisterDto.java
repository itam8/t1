package org.example.clientprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.clientprocessing.model.DocumentType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String login;
    private String password;
    private String email;
    private String regionNumber;
    private String divisionNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private DocumentType documentType;
    private String documentId;
    private String documentPrefix;
    private String documentSuffix;
}
