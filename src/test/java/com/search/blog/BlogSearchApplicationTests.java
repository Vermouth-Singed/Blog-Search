package com.search.blog;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.search.blog.entity.BlogSearchCnt;
import com.search.blog.entity.BlogSearchHistory;
import com.search.blog.entity.QBlogSearchCnt;
import com.search.blog.entity.QBlogSearchHistory;
import com.search.blog.util.OrderByNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.search.blog.entity.QBlogSearchCnt.blogSearchCnt;
import static com.search.blog.entity.QBlogSearchHistory.blogSearchHistory;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BlogSearchApplicationTests {
	@Autowired
	EntityManager em;

	@Test
	@DisplayName("querydsl 테스트")
	void querydsl() {
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

		assertTrue(Optional.ofNullable(jpaQueryFactory.selectFrom(blogSearchCnt).limit(1).fetchOne()).isPresent());
	}

	@Test
	@DisplayName("group by 테스트")
	void groupBy() {
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

		List<Tuple> list = jpaQueryFactory
				.select(blogSearchHistory.query, blogSearchHistory.query.count())
				.from(blogSearchHistory)
				.groupBy(blogSearchHistory.query)
				.orderBy(OrderByNull.DEFAULT)
				.fetch();

		assertTrue(Optional.ofNullable(list).isPresent());
	}
}
