package com.search.blog.controller;

import com.search.blog.model.ApiResult;
import com.search.blog.util.Jasypt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "jasypt 테스트", description = "jasypt 테스트 API")
@RestController
@RequestMapping("/jasypt")
public class JasyptController {
    @Autowired
    Jasypt jasypt;

    @Value("${jasypt.key}")
    private String jasyptKey;

    @GetMapping("/encrypt/{text}")
    @Operation(summary = "jasypt 암호화", description = "jasypt 암호화 텍스트 조회")
    public ApiResult encrypt(@PathVariable String text) {
//        http get localhost:8080/jasypt/encrypt/asdf
        return ApiResult.OK(jasypt.encrypt(text));
    }

    @GetMapping("/key")
    @Operation(summary = "jasypt 키", description = "jasypt 키 텍스트 조회")
    public ApiResult key() {
//        http get localhost:8080/jasypt/key
        return ApiResult.OK(jasyptKey);
    }
}
