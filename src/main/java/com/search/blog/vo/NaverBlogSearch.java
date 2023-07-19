package com.search.blog.vo;

import com.search.blog.enums.BlogSearchKeywordEnum;
import com.search.blog.util.DateUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class NaverBlogSearch {
    @Value("${blog.search.naver.url}")
    private String apiURL;

    @Value("${blog.search.naver.clientId}")
    private String clientId;

    @Value("${blog.search.naver.clientSecret}")
    private String clientSecret;

    private Map<String, String> requestHeaders () {
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        return requestHeaders ;
    }

    public Map<String, Object> list(String text, String sort, Integer start, Integer display) throws Exception {
        String paramSort = sort;

        if (!BlogSearchKeywordEnum.DATE.code().equals(paramSort)) {
            paramSort = BlogSearchKeywordEnum.SIM.code();
        }

        try {
            text = URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        if (start == null || start.intValue() < 1 || start.intValue() > 50) {
            start = 1;
        }

        if (display == null || display.intValue() < 1 || display.intValue() > 50) {
            display = 10;
        }

        String jsonStr = get(String.format(apiURL + "?query=%s&start=%d&display=%d&sort=%s", text, start, display, paramSort), requestHeaders());

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(jsonStr);
        JSONObject jsonObj = (JSONObject) obj;

        Map<String, Object> result = new HashMap<>();
        result.put(
            BlogSearchKeywordEnum.LIST.code(),
            ((ArrayList<Map<String, Object>>)(jsonObj.get("items"))).stream().map(item -> {
                Map<String, Object> map = new HashMap<>();
                map.put("blogname", item.get("bloggername"));
                map.put("contents", item.get("description"));
                map.put("datetime", DateUtil.convertStrToDttmStr((String)item.get("postdate")));
                map.put("thumbnail", "");
                map.put("title", item.get("title"));
                map.put("url", item.get("link"));

                return map;
            })
        );
        result.put(BlogSearchKeywordEnum.SORT.code(), sort);
        result.put(BlogSearchKeywordEnum.PAGE.code(), start);
        result.put(BlogSearchKeywordEnum.SIZE.code(), display);

        obj = jsonObj.get(BlogSearchKeywordEnum.TOTAL.code());

        int total = obj != null ? Integer.parseInt(obj.toString()) : 0;
        int totalPage = (total % display == 0 ? 0 : 1) + total/display;

        result.put("lastPage", totalPage > 50 ? 50 : totalPage);

        return result;
    }

    private String get(String apiUrl, Map<String, String> requestHeaders) throws Exception {
        HttpURLConnection con = connect(apiUrl);

        try {
            con.setRequestMethod("GET");

            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl) throws Exception {
        try {
            URL url = new URL(apiUrl);

            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private String readBody(InputStream body) throws Exception {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;

            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
