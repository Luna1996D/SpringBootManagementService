package org.example.springbootmanagementservice.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PdfService {

    public void createPdf(String filePath, String title, String content) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph("Title: " + title));
            document.add(new Paragraph("Content: " + content));
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("PDF creation failed", e);
        }
    }
}
