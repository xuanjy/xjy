package com.xjy.community.controller;

import com.xjy.community.dto.PageDTO;
import com.xjy.community.dto.QuestionDTO;
import com.xjy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 黄粱一梦
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search) {
        PageDTO<QuestionDTO> pagination = questionService.list(search, page, size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search",search);
        return "index";
    }
}