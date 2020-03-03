package com.xjy.community.controller;

import com.xjy.community.dto.NotificationDTO;
import com.xjy.community.dto.PageDTO;
import com.xjy.community.dto.QuestionDTO;
import com.xjy.community.pojo.Notification;
import com.xjy.community.pojo.User;
import com.xjy.community.service.NotificationService;
import com.xjy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/12 15:05
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PageDTO pageDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination", pageDTO);
        } else if ("replies".equals(action)) {
            PageDTO pageDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section", "replies");
            model.addAttribute("pagination", pageDTO);
            model.addAttribute("sectionName", "最新回复");
        }
        return "profile";
    }
}