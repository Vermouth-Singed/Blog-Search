package com.search.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogSearchDTO {
    private String query;
    private String sort;
    private Integer page;
    private Integer size;
}
