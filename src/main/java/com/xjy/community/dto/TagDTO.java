package com.xjy.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/23 12:45
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
