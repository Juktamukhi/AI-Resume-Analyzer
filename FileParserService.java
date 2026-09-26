package com.example.resumeanalyzer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class FileParserService {

    public String extractText(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename().toLowerCase();

        if (filename.endsWith(".pdf")) {
            try (InputStream is = file.getInputStream(); PDDocument doc = PDDocument.load(is)) {
                return new PDFTextStripper().getText(doc);
            }
        } else if (filename.endsWith(".docx")) {
            try (InputStream is = file.getInputStream(); 
                 XWPFDocument doc = new XWPFDocument(is);
                 XWPFWordExtractor extractor = new XWPFWordExtractor(doc)) {
                return extractor.getText();
            }
        } else if (filename.endsWith(".txt")) {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
        } else {
            throw new IllegalArgumentException("Unsupported file type! Upload PDF, DOCX, or TXT.");
        }
    }
}