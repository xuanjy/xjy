package com.xjy.community.dto;

import lombok.Data;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/16 14:54
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
