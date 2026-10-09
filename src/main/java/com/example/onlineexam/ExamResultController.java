package com.example.onlineexam;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/results")
public class ExamResultController {

    private final ExamResultRepository repository;

    public ExamResultController(ExamResultRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> saveResult(@RequestBody ExamResult result) {
        if (result.getStudentName() == null ||
            result.getStudentEmail() == null ||
            result.getCategory() == null ||
            result.getDifficulty() == null ||
            result.getScore() < 0 ||
            result.getTotalQuestions() <= 0 ||
            result.getScore() > result.getTotalQuestions()) {

            return ResponseEntity.badRequest().body("Invalid exam result");
        }

        result.setSubmittedAt(null);
        ExamResult saved = repository.save(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public List<ExamResult> getAllResults() {
        return repository.findAll();
    }
}