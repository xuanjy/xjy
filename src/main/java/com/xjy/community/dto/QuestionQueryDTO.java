package com.xjy.community.dto;

import lombok.Data;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/25 13:43
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
