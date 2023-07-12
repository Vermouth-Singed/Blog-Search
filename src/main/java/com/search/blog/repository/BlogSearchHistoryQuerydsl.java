package com.search.blog.repository;


import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.search.blog.entity.BlogSearchHistory;
import com.search.blog.util.OrderByNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.search.blog.entity.QBlogSearchHistory.blogSearchHistory;

@Repository
public class BlogSearchHistoryQuerydsl {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public List<BlogSearchHistory> stackData() {
        List<Tuple> list = jpaQueryFactory
                .select(blogSearchHistory.query, blogSearchHistory.query.count())
                .from(blogSearchHistory)
                .where(blogSearchHistory.regDttm.after(LocalDateTime.now().minusSeconds(10)))
                .groupBy(blogSearchHistory.query)
                .orderBy(OrderByNull.DEFAULT)
                .fetch();

        if (list == null) {
            return new ArrayList<>();
        }

        return list.stream().map(tuple -> {
            return BlogSearchHistory.builder()
                    .query(tuple.get(0, String.class))
                    .cntQuery(tuple.get(1, Long.class))
                    .build();
            })
            .collect(Collectors.toList());
    }
}
