package com.xjy.community.controller;

import com.xjy.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/25 11:27
 */
@Controller
public class FileController {
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload() {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/images/my.png");
        return fileDTO;
    }
}
