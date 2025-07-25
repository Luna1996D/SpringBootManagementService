package org.example.springbootmanagementservice.dto;

import lombok.Data;
import org.example.springbootmanagementservice.entity.Category;

import java.time.LocalDate;

@Data
public class DocumentResponseDto {
    private Long id;
    private String title;
    private LocalDate uploadDate;
    private Category category;
    private String downloadUrl;

    public DocumentResponseDto(Long id, String title, LocalDate uploadDate, Category category, String downloadUrl) {
        this.id = id;
        this.title = title;
        this.uploadDate = uploadDate;
        this.category = category;
        this.downloadUrl = downloadUrl;
    }
}
