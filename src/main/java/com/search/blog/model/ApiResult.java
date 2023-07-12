package com.search.blog.model;

import com.search.blog.enums.ApiResultEnum;
import lombok.Getter;

import java.util.Map;

@Getter
public class ApiResult {
    private final Map<String, Object> data;
    private final Boolean success;
    private final String msg;

    private ApiResult(Map<String, Object> data) {
        this.success = data.get(ApiResultEnum.MESSAGE.code()) == null;
        this.msg = this.success ? ApiResultEnum.SUCCESS.code() : data.get(ApiResultEnum.MESSAGE.code()).toString();
        this.data = data;
    }

    public static ApiResult OK(Map<String, Object> data){
        return new ApiResult(data);
    }
}
