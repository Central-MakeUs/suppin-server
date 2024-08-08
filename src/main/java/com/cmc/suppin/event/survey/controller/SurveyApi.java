package com.cmc.suppin.event.survey.controller;

import com.cmc.suppin.event.survey.controller.dto.SurveyRequestDTO;
import com.cmc.suppin.event.survey.controller.dto.SurveyResponseDTO;
import com.cmc.suppin.event.survey.service.SurveyService;
import com.cmc.suppin.global.response.ApiResponse;
import com.cmc.suppin.global.security.reslover.Account;
import com.cmc.suppin.global.security.reslover.CurrentAccount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "Event-Survey", description = "Survey 관련 API")
@RequestMapping("/api/v1/survey")
public class SurveyApi {

    private final SurveyService surveyService;

    @PostMapping("/create")
    @Operation(summary = "설문지 생성 API", description = "QuestionType(Enum): SUBJECTIVE(주관식), SINGLE_CHOICE(객관식(단일 선택)), MULTIPLE_CHOICE(객관식(복수 선택))")
    public ResponseEntity<ApiResponse<SurveyResponseDTO.SurveyCreateResponse>> createSurvey(@RequestBody @Valid SurveyRequestDTO.SurveyCreateDTO request, @CurrentAccount Account account) {
        SurveyResponseDTO.SurveyCreateResponse response = surveyService.createSurvey(request, account.userId());
        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @GetMapping("/{surveyId}")
    @Operation(summary = "설문지 조회 API", description = "Request: 설문지 ID, Response: 설문지 정보 <br><br>" +
            "SUBJEVTIVE: 주관식, SINGLE_CHOICE: 객관식(단일 선택), MULTIPLE_CHOICE: 객관식(복수 선택)")
    public ResponseEntity<ApiResponse<SurveyResponseDTO.SurveyResultDTO>> getSurvey(@PathVariable Long surveyId) {
        SurveyResponseDTO.SurveyResultDTO response = surveyService.getSurvey(surveyId);
        return ResponseEntity.ok(ApiResponse.of(response));
    }
}
