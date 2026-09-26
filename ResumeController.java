package com.example.resumeanalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "*")
public class ResumeController {

    @Autowired
    private FileParserService fileParserService;

    @Autowired
    private AIService aiService;

    @PostMapping("/analyze")
    public ResponseEntity<String> analyze(@RequestParam("file") MultipartFile file) {
        try {
            String extractedText = fileParserService.extractText(file);
            String result = aiService.analyzeResume(extractedText);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}