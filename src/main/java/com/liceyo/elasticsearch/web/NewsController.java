package com.liceyo.elasticsearch.web;

import com.liceyo.elasticsearch.pojo.News;
import com.liceyo.elasticsearch.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liceyo
 * @version 2018/5/25
 */
@RestController
@RequestMapping("/news")
public class NewsController extends BaseController<News>{

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
        super.baseService=newsService;
    }
}
