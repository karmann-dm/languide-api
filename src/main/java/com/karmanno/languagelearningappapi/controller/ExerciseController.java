package com.karmanno.languagelearningappapi.controller;

import com.karmanno.languagelearningappapi.dto.AddExerciseRequest;
import com.karmanno.languagelearningappapi.dto.ApiResponse;
import com.karmanno.languagelearningappapi.security.CurrentUser;
import com.karmanno.languagelearningappapi.security.UserPrincipal;
import com.karmanno.languagelearningappapi.service.ExerciseService;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("exercise")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity<ApiResponse> addExercise(@RequestBody @Valid AddExerciseRequest request) {
        return ResponseEntity.ok(
                Try.of(() -> ApiResponse.success(exerciseService.add(
                        request.getDescription(),
                        request.getTemplate()
                ))).onFailure(e -> ApiResponse.error(e.getMessage())).get()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getExercises() {
        return ResponseEntity.ok(
                Try.of(() -> ApiResponse.success(exerciseService.getAll()))
                    .onFailure(e -> ApiResponse.error(e.getMessage())).get()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getExercise(@PathVariable Integer id,
                                                   @CurrentUser UserPrincipal user) {
        return ResponseEntity.ok(
                Try.of(() -> ApiResponse.success(exerciseService.getById(id, user.getId())))
                    .onFailure(e -> ApiResponse.error(e.getMessage())).get()
        );
    }
}
