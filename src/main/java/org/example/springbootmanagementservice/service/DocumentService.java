package org.example.springbootmanagementservice.service;

import org.example.springbootmanagementservice.dto.DocumentResponseDto;
import org.example.springbootmanagementservice.dto.DocumentUploadRequest;
import org.example.springbootmanagementservice.entity.Document;
import org.example.springbootmanagementservice.entity.Category;
import org.example.springbootmanagementservice.repository.CategoryRepository;
import org.example.springbootmanagementservice.repository.DocumentRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final CategoryRepository categoryRepository;
    private final PdfService pdfService;


    private final Path uploadDir = Paths.get(System.getProperty("user.dir"), "uploads");

    public DocumentService(DocumentRepository documentRepository,
                           CategoryRepository categoryRepository,
                           PdfService pdfService) {
        this.documentRepository = documentRepository;
        this.categoryRepository = categoryRepository;
        this.pdfService = pdfService;
    }

    public Document uploadDocument(DocumentUploadRequest request) throws IOException {
        Category category = categoryRepository.findByName(request.getCategoryName())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(request.getCategoryName());
                    newCategory.setDescription("Default description");
                    return categoryRepository.save(newCategory);
                });


        Files.createDirectories(uploadDir);

        if (!Files.isWritable(uploadDir)) {
            throw new RuntimeException("Upload directory is not writable: " + uploadDir.toString());
        }


        MultipartFile file = request.getFile();
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(fileName);

        file.transferTo(filePath.toFile());

        Document document = new Document();
        document.setTitle(request.getTitle());
        document.setCategory(category);
        document.setUploadDate(request.getUploadDate());
        document.setFilePath(filePath.toString());

        Document saved = documentRepository.save(document);


        String safeFileName = request.getTitle().replaceAll("[^a-zA-Z0-9\\-_]", "_") + ".pdf";
        String pdfPath = uploadDir.resolve(safeFileName).toString();

        pdfService.createPdf(pdfPath, request.getTitle(), "Category: " + category.getName());

        return saved;
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Resource getDocumentFile(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        try {
            Path path = Paths.get(document.getFilePath());
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found", e);
        }
    }

    public void deleteDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        try {
            Files.deleteIfExists(Paths.get(document.getFilePath()));
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file", e);
        }
        documentRepository.deleteById(id);
    }
    public DocumentResponseDto mapToDto(Document document) {
        String downloadUrl = "/api/documents/" + document.getId() + "/download";
        return new DocumentResponseDto(
                document.getId(),
                document.getTitle(),
                document.getUploadDate(),
                document.getCategory(),
                downloadUrl
        );
    }


    public List<DocumentResponseDto> getAllDocumentsDto() {
        return documentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public DocumentResponseDto getDocumentDtoById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        return mapToDto(document);
    }
}
