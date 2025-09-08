package com.codearena.submission_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codearena.submission_service.service.SubmissionService;
import com.codearena.submission_service.dto.SubmissionRequest;
import com.codearena.submission_service.dto.SubmissionResponse;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService service;

    @PostMapping
    public ResponseEntity<SubmissionResponse> submit(@RequestBody SubmissionRequest request) {
        SubmissionResponse response = service.submitCode(request);
        return ResponseEntity.ok(response);
    }
}
