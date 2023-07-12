package com.search.blog.service;

import com.search.blog.entity.BlogSearchCnt;
import com.search.blog.entity.BlogSearchHistory;
import com.search.blog.enums.ApiResultEnum;
import com.search.blog.model.ErrorMsg;
import com.search.blog.repository.BlogSearchCntJpa;
import com.search.blog.repository.BlogSearchCntQuerydsl;
import com.search.blog.repository.BlogSearchHistoryJpa;
import com.search.blog.repository.BlogSearchHistoryQuerydsl;
import com.search.blog.vo.KakaoBlogSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StatisticsService {
    @Autowired
    BlogSearchCntQuerydsl blogSearchCntQuerydsl;

    @Autowired
    BlogSearchHistoryQuerydsl blogSearchHistoryQuerydsl;

    @Autowired
    BlogSearchCntJpa blogSearchCntJpa;

    @Autowired
    BlogSearchHistoryJpa blogSearchHistoryJpa;

    public Map<String, Object> statistics() {
        Map<String, Object> result = new HashMap<>();

        try {
            result.put("list", blogSearchCntQuerydsl.statistics());
        } catch(Exception e) {
            result.put(ApiResultEnum.MESSAGE.code(), ErrorMsg.UNPREDICTABLE_ERROR.msg());
        }

        return result;
    }

    public Map<String, Object> search(String query, String sort, Integer page, Integer size) {
        Map<String, Object> result = new HashMap<>();

        if (query == null) {
            result.put(ApiResultEnum.MESSAGE.code(), ErrorMsg.PARAM_ERROR.msg());

            return result;
        }

        try {
            result = new KakaoBlogSearch().list(query, sort, page, size);

            blogSearchHistoryJpa.save(BlogSearchHistory.builder().query(query).build());
        } catch(Exception e01) {
            result.put(ApiResultEnum.MESSAGE.code(), ErrorMsg.UNPREDICTABLE_ERROR.msg());

//            try {
//                blogSearchHistoryJpa.save(BlogSearchHistory.builder().source(BlogSearchHistoryEnum.NAVER.code()).query(query).build());
//            } catch(Exception e) {
//                result.put(ApiResultEnum.MESSAGE.code(), ErrorMsg.UNPREDICTABLE_ERROR.msg());
//            }
        }

        return result;
    }

    public void stackStatisticsData() {
        blogSearchHistoryQuerydsl.stackData().stream().forEach(blogSearchHistory -> {
            Optional<BlogSearchCnt> optional = blogSearchCntJpa.findById(blogSearchHistory.getQuery());

            if (optional.isPresent()) {
                BlogSearchCnt blogSearchCnt = optional.get();
                blogSearchCnt.setCnt(blogSearchCnt.getCnt() + blogSearchHistory.getCntQuery());

                blogSearchCntJpa.save(blogSearchCnt);
            } else {
                blogSearchCntJpa.save(
                    BlogSearchCnt.builder()
                        .query(blogSearchHistory.getQuery())
                        .cnt(blogSearchHistory.getCntQuery())
                        .build()
                );
            }
        });
    }
}
