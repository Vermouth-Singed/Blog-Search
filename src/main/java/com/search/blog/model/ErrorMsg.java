package com.search.blog.model;

public enum ErrorMsg {
    PARAM_ERROR("파라미터 오류입니다."),
    UNPREDICTABLE_ERROR("예상치못한 오류입니다.");

    private String msg;

    private ErrorMsg(String msg){
        this.msg = msg;
    }

    public String msg(){
        return msg;
    }
}
