package com.search.blog.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="blog_search_cnt")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogSearchCnt {
    @Id
    private String query;

    private Long cnt;
}
