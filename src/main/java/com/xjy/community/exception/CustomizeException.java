package com.xjy.community.exception;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/15 16:29
 */
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public CustomizeException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
