package com.xjy.community.dto;

import lombok.Data;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/6 15:35
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
