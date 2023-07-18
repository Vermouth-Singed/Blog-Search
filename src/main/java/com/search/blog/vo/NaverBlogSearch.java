package com.search.blog.vo;

import com.search.blog.enums.BlogSearchKeywordEnum;
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
        result.put(BlogSearchKeywordEnum.LIST.code(), (ArrayList<Map<String, Object>>)(jsonObj.get("items")));

//        {
//            "bloggername":"EBS"
//            "description":"70대, 죽기 전에 <b>집짓기<\/b> 칠십 넘어 집 짓다간 10년 더 늙고, 발인 날짜 나온다는 주변의 만류. 하지만... 아무도 몰래 종이에다 설계도를 그리던 경원 씨는 결국 집을 짓기 위해 용기를 냈습니다. 주변 사람들은... ",
//                "postdate":"20230605",
//                "link":"https:\/\/blog.naver.com\/ebsstory\/223120827151",
//                "thumbnail": "",
//                "title":"나만의 <b>집 짓기<\/b>에 도전한... 탐구 집 &lt;나의 건축 일지&gt; 미리보기",
//                "bloggerlink":"blog.naver.com\/ebsstory",
//
//        }
//
//        {
//            "blogname": "단단이아빠",
//                "contents": "또한 <b>집</b>에서 가족과 함께 생활하기 위한 최소한의 위생관리도 스스로 해결하지 못하기 시 작했다. 하루는 화장실에 들어간 엄마가 한 시간 가까이 밖에 나오지 않았는데 아무리 문을 두드려도 알아들을 수 없는 소리만  외칠 뿐 밖으로 나오지 않았다. 기다림에 지친 난 문고리를 부숴 화장실 안으로 들어갔는데 눈앞에...",
//                "datetime": "2023-07-18T23:05:47.000+09:00",
//                "thumbnail": "",
//                "title": "10년간의 헤어짐 - Cahpter 5 - 퇴행의 자각",
//                "url": "https://brunch.co.kr/@64b25d9202064e3/11"
//        }
        result.put(BlogSearchKeywordEnum.SORT.code(), sort);
        result.put(BlogSearchKeywordEnum.PAGE.code(), start);
        result.put(BlogSearchKeywordEnum.SIZE.code(), display);

        obj = jsonObj.get(BlogSearchKeywordEnum.TOTAL.code());

        int total = Integer.parseInt(obj.toString());
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
