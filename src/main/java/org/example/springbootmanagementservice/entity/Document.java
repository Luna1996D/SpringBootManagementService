package org.example.springbootmanagementservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate uploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private String filePath;
}
