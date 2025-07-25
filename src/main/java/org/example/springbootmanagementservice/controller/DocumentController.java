package org.example.springbootmanagementservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.springbootmanagementservice.dto.DocumentResponseDto;
import org.example.springbootmanagementservice.dto.DocumentUploadRequest;
import org.example.springbootmanagementservice.entity.Document;
import org.example.springbootmanagementservice.service.DocumentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(
            @RequestParam("title") String title,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("uploadDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate uploadDate,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        DocumentUploadRequest request = new DocumentUploadRequest();
        request.setTitle(title);
        request.setCategoryName(categoryName);
        request.setUploadDate(uploadDate);
        request.setFile(file);

        Document saved = documentService.uploadDocument(request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponseDto>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocumentsDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponseDto> getDocumentById(@PathVariable Long id) {
        DocumentResponseDto dto = documentService.getDocumentDtoById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
