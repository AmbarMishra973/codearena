package com.codearena.submission_service.service;

import com.codearena.submission_service.dto.SubmissionRequest;
import com.codearena.submission_service.dto.Judge0Response;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.HashMap;

@Component
public class Judge0Client {

    private RestTemplate restTemplate = new RestTemplate();  // Default RestTemplate
    private final String JUDGE0_URL = "https://judge0-ce.p.rapidapi.com/submissions?base64_encoded=false&wait=true";
    private final String API_KEY = "your-rapidapi-key";

    public Judge0Response execute(SubmissionRequest submission) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-RapidAPI-Key", API_KEY);
        headers.set("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com");

        // Prepare the request body
        Map<String, Object> body = new HashMap<>();
        body.put("source_code", submission.getCode());
        body.put("language_id", getLanguageId(submission.getLanguage()));
        body.put("stdin", ""); // Optional, can be customized
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        // Send the request and get the response
        ResponseEntity<Map> response = restTemplate.postForEntity(JUDGE0_URL, entity, Map.class);
        Map<String, Object> responseBody = response.getBody();

        // Map the response to Judge0Response
        return new Judge0Response(
                (String) ((Map) responseBody.get("status")).get("description"),
                (Double) responseBody.get("time"),
                (Integer) responseBody.get("memory"),
                (String) responseBody.get("stdout"),
                (String) responseBody.get("stderr")
        );
    }

    // Method to convert language to its ID (as per Judge0 API)
    private int getLanguageId(String lang) {
        return switch (lang.toLowerCase()) {
            case "python" -> 71;
            case "java" -> 62;
            case "cpp" -> 50;
            default -> 0;  // Default language ID (or throw exception if invalid)
        };
    }

    // Getter for RestTemplate (if needed for testing)
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
