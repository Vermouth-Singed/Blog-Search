package com.search.blog.repository;

import com.search.blog.entity.BlogSearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogSearchHistoryJpa extends JpaRepository<BlogSearchHistory, Long> {
}
