package com.search.blog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DateTimeTests {
    String yyyymmddRegex = "\\d{8}";
    String yyyymmddTRegex = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";

    @Test
    @DisplayName("연월일 테스트")
    void 연월일_테스트() {
        assertTrue(Pattern.matches(yyyymmddRegex, "20230719"));
    }

    @Test
    @DisplayName("연월일시분초 테스트")
    void 연월일시분초_테스트() {
        assertTrue(Pattern.matches(yyyymmddTRegex, "2023-07-18T23:05:47.000+09:00".substring(0, 19)));
    }
}
