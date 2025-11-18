package org.shoppingmall.aiweather.mapper;

import java.util.Map;

// 한글 - 영어 변환
public class WeatherMapper {
    private static final Map<String, String> weatherMap = Map.ofEntries(
            Map.entry("종로구", "Jongno-gu"),
            Map.entry("중구", "Jung-gu"),
            Map.entry("용산구", "Yongsan-gu"),
            Map.entry("성동구", "Seongdong-gu"),
            Map.entry("광진구", "Gwangjin-gu"),
            Map.entry("동대문구", "Dongdaemun-gu"),
            Map.entry("중랑구", "Jungnang-gu"),
            Map.entry("성북구", "Seongbuk-gu"),
            Map.entry("강북구", "Gangbuk-gu"),
            Map.entry("도봉구", "Dobong-gu"),
            Map.entry("노원구", "Nowon-gu"),
            Map.entry("은평구", "Eunpyeong-gu"),
            Map.entry("서대문구", "Seodaemun-gu"),
            Map.entry("마포구", "Mapo-gu"),
            Map.entry("양천구", "Yangcheon-gu"),
            Map.entry("강서구", "Gangseo-gu"),
            Map.entry("구로구", "Guro-gu"),
            Map.entry("금천구", "Geumcheon-gu"),
            Map.entry("영등포구", "Yeongdeungpo-gu"),
            Map.entry("동작구", "Dongjak-gu"),
            Map.entry("관악구", "Gwanak-gu"),
            Map.entry("서초구", "Seocho-gu"),
            Map.entry("강남구", "Gangnam-gu"),
            Map.entry("송파구", "Songpa-gu"),
            Map.entry("강동구", "Gangdong-gu")
    );

    public static String toEnglish(String koreanDistrict) {
        return weatherMap.getOrDefault(koreanDistrict, koreanDistrict);
    }
}
