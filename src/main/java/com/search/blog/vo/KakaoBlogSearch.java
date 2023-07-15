package com.search.blog.vo;

import com.search.blog.enums.BlogSearchKeywordEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class KakaoBlogSearch {
    @Value("${blog.search.kakao.url}")
    private String url;

    @Value("${blog.search.kakao.api-key}")
    private String apiKey;

    private HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return headers;
    }

    public Map<String, Object> list(String query, String sort, Integer page, Integer size) throws Exception {
        HttpEntity<String> httpEntity = new HttpEntity<>(this.httpHeaders());

        if (sort == null || !BlogSearchKeywordEnum.RECENCY.code().equals(sort)) {
            sort = BlogSearchKeywordEnum.ACCURACY.code();
        }

        if (page == null || page.intValue() < 1 || page.intValue() > 50) {
            page = 1;
        }

        if (size == null || size.intValue() < 1 || size.intValue() > 50) {
            size = 10;
        }

        URI targetUrl = UriComponentsBuilder
                .fromUriString(url)
                .queryParam(BlogSearchKeywordEnum.QUERY.code(), query)
                .queryParam(BlogSearchKeywordEnum.SORT.code(), sort)
                .queryParam(BlogSearchKeywordEnum.PAGE.code(), page)
                .queryParam(BlogSearchKeywordEnum.SIZE.code(), size)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        Map<String, Object> returnData = new RestTemplate().exchange(targetUrl, HttpMethod.GET, httpEntity, Map.class).getBody();

        Map<String, Object> result = new HashMap<>();
        result.put(BlogSearchKeywordEnum.LIST.code(), (ArrayList<Map<String, Object>>)(returnData.get("documents")));
        result.put(BlogSearchKeywordEnum.SORT.code(), sort);
        result.put(BlogSearchKeywordEnum.PAGE.code(), page);
        result.put(BlogSearchKeywordEnum.SIZE.code(), size);

        Map<String, Object> metaData = (Map<String, Object>)returnData.get(BlogSearchKeywordEnum.META.code());

        Integer pageableCount = Integer.parseInt(metaData.get("pageable_count").toString());

        result.put("lastPage", pageableCount > 50 ? 50 : pageableCount);

        return result;
    }
}
