package com.codearena.submission_service.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.HashMap;

import com.codearena.submission_service.dto.SubmissionRequest;
import com.codearena.submission_service.dto.Judge0Response;

@Component
public class Judge0Client {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String JUDGE0_URL = "https://judge0-ce.p.rapidapi.com/submissions?base64_encoded=false&wait=true";
    private final String API_KEY = "your-rapidapi-key";

    public Judge0Response execute(SubmissionRequest submission) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-RapidAPI-Key", API_KEY);
        headers.set("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com");

        Map<String, Object> body = new HashMap<>();
        body.put("source_code", submission.getCode());
        body.put("language_id", getLanguageId(submission.getLanguage()));
        body.put("stdin", ""); // Optional
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(JUDGE0_URL, entity, Map.class);
        Map<String, Object> responseBody = response.getBody();

        return new Judge0Response(
                (String) ((Map) responseBody.get("status")).get("description"),
                (Double) responseBody.get("time"),
                (Integer) responseBody.get("memory"),
                (String) responseBody.get("stdout"),
                (String) responseBody.get("stderr")
        );
    }

    private int getLanguageId(String lang) {
        return switch (lang.toLowerCase()) {
            case "cpp" -> 54;
            case "java" -> 62;
            case "python" -> 71;
            default -> 62;
        };
    }
}

