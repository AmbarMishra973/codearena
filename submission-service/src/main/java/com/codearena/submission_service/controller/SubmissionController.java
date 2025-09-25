package com.codearena.submission_service.controller;

import com.codearena.submission_service.dto.SubmissionRequest;
import com.codearena.submission_service.dto.SubmissionResponse;
import com.codearena.submission_service.service.SubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping("/api/submissions/{contestId}")
    public ResponseEntity<SubmissionResponse> submitCode(
            @PathVariable Long contestId,
            @RequestHeader("userId") String userId,
            @RequestBody SubmissionRequest submissionRequest) {

        // Set contestId and userId in the request object
        submissionRequest.setContestId(contestId);
        submissionRequest.setUserId(Long.parseLong(userId));  // Convert userId to Long, if necessary

        SubmissionResponse response = submissionService.submitCode(submissionRequest);
        return ResponseEntity.ok(response);
    }
}
