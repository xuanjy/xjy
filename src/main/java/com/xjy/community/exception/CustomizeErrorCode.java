package com.xjy.community.exception;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/15 16:43
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001, "问题不存在"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "请登录后重试！"),
    SYS_ERROR(2004, "服务器出现问题了,请稍后重试"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "评论不存在"),
    CONTENT_IS_EMPTY(2007, "输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008, "不允许读他人信息"),
    NOTIFICATION_NOT_FOUND(2009, "消息不见了"),
    ;


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
