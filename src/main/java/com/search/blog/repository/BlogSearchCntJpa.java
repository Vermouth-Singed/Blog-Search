package com.search.blog.repository;

import com.search.blog.entity.BlogSearchCnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogSearchCntJpa extends JpaRepository<BlogSearchCnt, String> {
}
