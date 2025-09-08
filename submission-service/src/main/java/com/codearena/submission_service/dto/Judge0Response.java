package com.codearena.submission_service.dto;

public class Judge0Response {

    private String status;
    private Double time;
    private Integer memory;
    private String stdout;
    private String stderr;

    // No-arg constructor needed by frameworks
    public Judge0Response() {}

    // All-args constructor
    public Judge0Response(String status, Double time, Integer memory, String stdout, String stderr) {
        this.status = status;
        this.time = time;
        this.memory = memory;
        this.stdout = stdout;
        this.stderr = stderr;
    }

    // Getters and setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getTime() { return time; }
    public void setTime(Double time) { this.time = time; }

    public Integer getMemory() { return memory; }
    public void setMemory(Integer memory) { this.memory = memory; }

    public String getStdout() { return stdout; }
    public void setStdout(String stdout) { this.stdout = stdout; }

    public String getStderr() { return stderr; }
    public void setStderr(String stderr) { this.stderr = stderr; }
}
