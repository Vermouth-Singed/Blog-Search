package com.search.blog.vo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KakaoBlogSearch {
    private String url = "https://dapi.kakao.com/v2/search/blog";
    private String apiKey = "7980d53bb05df342637585c06f764ed9";

    private HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return headers;
    }

    public Map<String, Object> list(String query, String sort, Integer page, Integer size) throws Exception {
        HttpEntity<String> httpEntity = new HttpEntity<>(this.httpHeaders());

        if (sort == null || !"recency".equals(sort)) {
            sort = "accuracy";
        }

        if (page == null || page.intValue() < 1 || page.intValue() > 50) {
            page = 1;
        }

        if (size == null || size.intValue() < 1 || size.intValue() > 50) {
            size = 10;
        }

        URI targetUrl = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("query", query)
                .queryParam("sort", sort)
                .queryParam("page", page)
                .queryParam("size", size)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        Map<String, Object> returnData = new RestTemplate().exchange(targetUrl, HttpMethod.GET, httpEntity, Map.class).getBody();

        Map<String, Object> result = new HashMap<>();
        result.put("list", (ArrayList<Map<String, Object>>)(returnData.get("documents")));
        result.put("sort", sort);
        result.put("page", page);
        result.put("size", size);

        Map<String, Object> metaData = (Map<String, Object>)returnData.get("meta");

        Integer pageableCount = Integer.parseInt(metaData.get("pageable_count").toString());

        result.put("lastPage", pageableCount > 50 ? 50 : pageableCount);

        return result;
    }
}
