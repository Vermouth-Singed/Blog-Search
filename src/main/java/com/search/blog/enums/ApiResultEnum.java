package com.search.blog.enums;

public enum ApiResultEnum {
    SUCCESS("success"),
    MESSAGE("msg");

    private String code;

    private ApiResultEnum(String code){
        this.code = code;
    }

    public String code(){
        return code;
    }
}
