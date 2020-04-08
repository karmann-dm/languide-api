package com.karmanno.languagelearningappapi.controller;

import com.karmanno.languagelearningappapi.domain.Language;
import com.karmanno.languagelearningappapi.dto.AddLiteralRequest;
import com.karmanno.languagelearningappapi.dto.ApiResponse;
import com.karmanno.languagelearningappapi.security.CurrentUser;
import com.karmanno.languagelearningappapi.security.UserPrincipal;
import com.karmanno.languagelearningappapi.service.LiteralService;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("literal")
@RequiredArgsConstructor
public class LiteralController {
    private final LiteralService literalService;

    @PostMapping
    public ResponseEntity<ApiResponse> addLiteral(@RequestBody @Valid AddLiteralRequest request,
                                                  @CurrentUser UserPrincipal user) {
        return ResponseEntity.ok(
                Try.of(() -> ApiResponse.success(
                        literalService.add(
                                user.getId(),
                                Language.valueOf(request.getSourceLang()),
                                Language.valueOf(request.getDestLang()),
                                request.getWord(),
                                request.getTranslation()
                        )
                )).onFailure(e -> ApiResponse.error(e.getMessage())).get()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllLiterals(
            @RequestParam("lang") String language,
            @CurrentUser UserPrincipal user
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(literalService.getAll(user.getId(), language))
        );
    }
}
