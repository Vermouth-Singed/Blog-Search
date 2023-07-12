package com.search.blog.controller;

import com.search.blog.dto.BlogSearchDTO;
import com.search.blog.model.ApiResult;
import com.search.blog.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "블로그 검색", description = "블로그 검색 API")
@RestController
@RequestMapping("/search/blog")
public class StatisticsController {
    @Autowired
    StatisticsService statisticsService;

    @GetMapping("/statistics")
    @Operation(summary = "많이 조회한 검색어 통계", description = "검색어 기록 많은 순으로 10개만 조회")
    public ApiResult statistics() throws Exception {
        return ApiResult.OK(statisticsService.statistics());
    }

    @PostMapping
    @Operation(summary = "블로그 조회", description = "블로그 검색 후 검색어 이력 저장")
    public ApiResult search(@RequestBody BlogSearchDTO blogSearchDTO) throws Exception {
//        http post localhost:8080/search/blog query=집짓기 sort=recency page=1 size=1
        return ApiResult.OK(statisticsService.search(
                blogSearchDTO.getQuery(),
                blogSearchDTO.getSort(),
                blogSearchDTO.getPage(),
                blogSearchDTO.getSize()
        ));
    }
}
