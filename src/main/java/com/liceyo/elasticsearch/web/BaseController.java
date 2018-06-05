package com.liceyo.elasticsearch.web;

import com.liceyo.elasticsearch.analysis.DataHandler;
import com.liceyo.elasticsearch.pojo.AnalysisResult;
import com.liceyo.elasticsearch.pojo.Subject;
import com.liceyo.elasticsearch.service.BaseService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 通用搜索
 * @author liceyo
 * @version 2018/5/25
 */
public class BaseController<T extends Subject> {

    BaseService<T> baseService;

    /**
     * 搜索
     * @param query 搜索词
     * @param page 页数 默认1
     * @param limit 一页条数 默认10
     * @return 搜索结果
     */
    @RequestMapping("/search")
    public AnalysisResult<T> search(String query,
                                    @RequestParam(defaultValue = "1")Integer page,
                                    @RequestParam(defaultValue = "10")Integer limit){
        return baseService.search(query, page, limit);
    }

    /**
     * 根据id获取内容
     * @param id id
     * @return 结果
     */
    @RequestMapping("/{id}")
    public T get(@PathVariable String id){
        T t = baseService.getById(id);
        if (t!=null){
            DataHandler.hitIncrease(id,t.getHitCount());
        }
        return t;
    }
}
