package org.example.clientprocessing.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "blacklist_registries")
public class BlacklistRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;
    @Column(name = "document_id")
    private String documentId;
    @Column(name = "blacklisted_at")
    private String blacklistedAt;
    private String reason;
    @Column(name = "blacklist_expiration_date")
    private LocalDate blacklistExpirationDate;
}
