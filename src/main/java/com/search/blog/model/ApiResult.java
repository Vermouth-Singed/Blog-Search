package com.search.blog.model;

import com.search.blog.enums.ApiResultEnum;
import lombok.Getter;

import java.util.HashMap;
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

    public static ApiResult OK(Object object){
        if (object instanceof Map) {
            return new ApiResult((Map<String, Object>)object);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("data", object);

        return new ApiResult(data);
    }
}
