package org.example.creditprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private String documentId;
}
