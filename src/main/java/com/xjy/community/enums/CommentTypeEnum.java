package com.xjy.community.enums;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/17 12:59
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType().equals(type)) {
                return true;
            }

        }
        return false;
    }

    public Integer getType(){
        return type;
    };

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
