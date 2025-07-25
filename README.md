"# Spring Boot Management Service"
echo "# Spring Boot Management Service

This is a Spring Boot-based document upload and management service. It provides REST APIs to upload PDF files, manage categories, and store metadata like title, upload date, and file location.

## ✨ Features
- Upload PDF documents
- Associate documents with categories
- Validate file types and required fields
- Store metadata and files locally
- REST API with proper request/response handling

## 📦 Technologies Used
- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- Validation (JSR-380)
- Lombok

## ▶️ How to Run
1. Clone the repository
2. Open in IntelliJ IDEA or any preferred IDE
3. Run \`SpringBootManagementServiceApplication.java\`

\`\`\`bash
./mvnw spring-boot:run
\`\`\`

## 📁 API Endpoints
- POST /api/documents/upload: Upload a document
- GET /api/documents: Get all documents
- GET /api/categories: Get all categories
- POST /api/categories: Add a new category

## ⚠️ Notes
- Files are stored locally.
- Only .pdf files are allowed for upload.
- Make sure the category exists before uploading.