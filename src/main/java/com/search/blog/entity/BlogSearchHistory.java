package com.search.blog.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name="blog_search_history")
@Getter
@Setter
@Builder
@DynamicInsert
public class BlogSearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private Character source;

    private String query;

    private LocalDateTime regDttm;

    private Long cntQuery;
}
