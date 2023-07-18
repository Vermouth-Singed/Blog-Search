package com.search.blog.enums;

public enum BlogSearchKeywordEnum {
    RECENCY("recency"),
    ACCURACY("accuracy"),
    QUERY("query"),
    SORT("sort"),
    PAGE("page"),
    SIZE("size"),
    LIST("list"),
    META("meta"),
    TOTAL("total"),
    SIM("sim"),
    DATE("date");

    private String code;

    private BlogSearchKeywordEnum(String code){
        this.code = code;
    }

    public String code(){
        return code;
    }
}
