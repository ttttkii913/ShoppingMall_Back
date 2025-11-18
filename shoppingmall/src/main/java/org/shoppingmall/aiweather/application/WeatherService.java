package org.shoppingmall.aiweather.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.shoppingmall.common.error.ErrorCode;
import org.shoppingmall.common.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class WeatherService {

    @Value("${openweather.api.key}")
    private String weatherApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    // 주소 → 위경도
    public double[] getLatLon(String query) {
        try {
            String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = "http://api.openweathermap.org/geo/1.0/direct?q="
                    + encoded + "&limit=1&appid=" + weatherApiKey;

            String response = restTemplate.getForObject(url, String.class);
            JsonNode json = mapper.readTree(response);

            if (json.isArray() && json.size() > 0) {
                double lat = json.get(0).path("lat").asDouble();
                double lon = json.get(0).path("lon").asDouble();
                return new double[]{lat, lon};
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 날씨 조회
    public String getWeather(String address) {
        try {
            double[] coords = getLatLon(address);
            if (coords == null) {
                throw new CustomException(ErrorCode.WEATHER_NOT_FOUND_EXCEPTION
                        , ErrorCode.WEATHER_NOT_FOUND_EXCEPTION.getMessage());
            }

            double lat = coords[0];
            double lon = coords[1];

            String url = "https://api.openweathermap.org/data/2.5/weather?"
                    + "lat=" + lat
                    + "&lon=" + lon
                    + "&appid=" + weatherApiKey
                    + "&units=metric&lang=kr";

            String response = restTemplate.getForObject(url, String.class);
            JsonNode json = mapper.readTree(response);

            String description = json.path("weather").get(0).path("description").asText();
            double temp = json.path("main").path("temp").asDouble();
            double feels = json.path("main").path("feels_like").asDouble();
            int humidity = json.path("main").path("humidity").asInt();

            return String.format(
                    "%s 지역의 현재 날씨는 %s, 온도 %.1f°C, 체감 %.1f°C, 습도 %d%% 입니다.",
                    address, description, temp, feels, humidity
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "날씨 정보를 불러오는 중 오류가 발생했습니다.");
        }
    }
}
