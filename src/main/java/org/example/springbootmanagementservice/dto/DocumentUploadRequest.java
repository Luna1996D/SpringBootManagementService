package org.example.springbootmanagementservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUploadRequest {
    @NotBlank(message = "Title must not be empty")
    private String title;

    @NotBlank(message = "Category name must not be empty")
    private String categoryName;

    @NotNull(message = "Upload date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate uploadDate;

    @NotNull(message = "File is required")
    private MultipartFile file;
}
