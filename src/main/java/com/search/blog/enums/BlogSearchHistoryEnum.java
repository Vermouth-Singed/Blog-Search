package com.search.blog.enums;

public enum BlogSearchHistoryEnum {
    KAKAO('K'),
    NAVER('N');

    private Character code;

    private BlogSearchHistoryEnum(Character code){
        this.code = code;
    }

    public Character code(){
        return code;
    }
}
