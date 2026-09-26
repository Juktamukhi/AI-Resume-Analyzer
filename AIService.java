package com.example.resumeanalyzer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.*;

@Service
public class AIService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String analyzeResume(String resumeText) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash-lite:generateContent?key=" + apiKey;

        String prompt = "You are an HR Screener. Analyze this candidate resume and summarize key strengths, extracted technical and soft skills, experience summary, and areas of improvement.\\n\\n" +
                        "Resume: " + resumeText.replaceAll("\"", "'");

        String requestBody = "{\"contents\": [{\"parts\": [{\"text\": \"" + prompt.replaceAll("\n", " ") + "\"}]}]}";

        // Configure network connection settings
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000); // 10 seconds wait to connect
        factory.setReadTimeout(20000);    // 20 seconds wait for response

        RestTemplate restTemplate = new RestTemplate(factory);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            return response.getBody();
        } catch (Exception e) {
            return "Network Error: Could not reach Google AI servers. Please check your internet/hotspot connection. Details: " + e.getMessage();
        }
    }
}