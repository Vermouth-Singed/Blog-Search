package com.search.blog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.search.blog.entity.BlogSearchCnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.search.blog.entity.QBlogSearchCnt.blogSearchCnt;

@Repository
public class BlogSearchCntQuerydsl {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public List<BlogSearchCnt> statistics() {
        return jpaQueryFactory
                .selectFrom(blogSearchCnt)
                .orderBy(blogSearchCnt.cnt.desc())
                .limit(10)
                .fetch();
    }
}
