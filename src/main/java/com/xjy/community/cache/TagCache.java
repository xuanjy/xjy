package com.xjy.community.cache;

import com.xjy.community.dto.TagDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/23 12:45
 */
public class TagCache {
    public static List<TagDTO> get() {
        ArrayList<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("css", "html", "java", "node", "python", "javascript", "php", "c", "c++", "golang", "shell", "swift", "c#", "sass", "ruby", "bash", "typescript", "scala", "rust", ".net"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts", "geuwighew", "gfewg", "gewg", "grww"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "nosql", "oracle", "mongodb", "redis", "sql", "sqlite"));
        tagDTOS.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "github", "idea", "pycharm", "redis", "sql", "sqlite", "yii", "ruby-on-rails", "tornado", "koa", "struts", "geuwighew", "gfewg", "gewg", "grww"));
        tagDTOS.add(tool);

        return tagDTOS;
    }

    public static String filterInvalid(String tags) {
        String[] split = tags.split(",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
