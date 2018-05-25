package com.liceyo.elasticsearch.web;

import com.liceyo.elasticsearch.pojo.AnalysisResult;
import com.liceyo.elasticsearch.service.BaseService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liceyo
 * @version 2018/5/25
 */
public class BaseController<T> {

    protected BaseService<T> baseService;

    @RequestMapping("/search")
    public AnalysisResult<T> search(String query,
                                    @RequestParam(defaultValue = "1")Integer page,
                                    @RequestParam(defaultValue = "10")Integer limit){
        return baseService.search(query, page, limit);
    }

    @RequestMapping("/{id}")
    public T get(@PathVariable String id){
        return baseService.getById(id);
    }
}
