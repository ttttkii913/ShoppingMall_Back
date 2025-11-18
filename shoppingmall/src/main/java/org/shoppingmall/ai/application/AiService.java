package org.shoppingmall.ai.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.ai.api.dto.request.AiReqDto;
import org.shoppingmall.ai.api.dto.response.AiResDto;
import org.shoppingmall.aiweather.application.WeatherService;
import org.shoppingmall.aiweather.mapper.WeatherMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=%s";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private final WeatherService weatherService;

    public AiResDto getQuestion(AiReqDto request) {
        try {
            String prompt;

            if ("dailyLook".equals(request.category())) {
                String mappedAddress = WeatherMapper.toEnglish(request.question());
                String weatherInfo = weatherService.getWeather(mappedAddress);

                prompt = "다음은 사용자가 요청한 지역의 실제 날씨입니다: \n"
                        + weatherInfo + "\n"
                        + "위 지역의 온도 정보를 알려주고, 그 날씨에 맞는 데일리룩을 간단히 추천해줘.";
            } else {
                prompt = switch (request.category()) {
                    case "productInquiry" -> "상품 '" + request.question() + "'에 대한 상세 정보를 간단하게 알려줘.";
                    case "delivery" -> "주문번호 '" + request.question() + "' 배송 상태를 알려줘.";
                    case "styleTip" -> "주말 데이트 코디 간단하게 추천해줘.";
                    case "event" -> "이번 주 할인/이벤트 상품 알려줘.";
                    default -> "사용자 질문: " + request.question();
                };
            }

            String body = mapper.writeValueAsString(
                    Map.of("contents",
                            List.of(Map.of("parts", List.of(Map.of("text", prompt))))));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            String url = String.format(GEMINI_API_URL, apiKey);
            String responseBody = restTemplate.postForObject(url, entity, String.class);

            JsonNode candidates = mapper.readTree(responseBody).path("candidates");
            String answer = candidates.isArray() && candidates.size() > 0
                    ? candidates.get(0).path("content").path("parts").get(0).path("text").asText()
                    : "AI 응답 없음";

            return new AiResDto(answer);

        } catch (Exception e) {
            e.printStackTrace();
            return new AiResDto("AI 호출 실패: " + e.getMessage());
        }
    }
}
