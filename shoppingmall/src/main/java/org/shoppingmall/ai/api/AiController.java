package org.shoppingmall.ai.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.ai.api.dto.request.AiReqDto;
import org.shoppingmall.ai.api.dto.response.AiResDto;
import org.shoppingmall.ai.application.AiService;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.error.SuccessCode;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
@Tag(name = "AI ChatBot API", description = "AI 챗봇 API")

public class AiController {

    private final AiService aiService;

    @Operation(summary = "AI 챗봇", description = "AI 챗봇으로 지정한 카테고리 내에서 답변 가능합니다.")
    @PostMapping
    public ApiResponseTemplate<AiResDto> getQuestion(@RequestBody AiReqDto aiReqDto) {
        AiResDto aiResDto = aiService.getQuestion(aiReqDto);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, aiResDto);
    }
}
