package com.liceyo.elasticsearch.web;

import com.liceyo.elasticsearch.analysis.completion.AutoCompletion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liceyo
 * @version 2018/6/6
 */
@RestController
@RequestMapping("/autoCompletion")
public class AutoCompletionController {
    /**
     * 获取自动补全
     * @return
     */
    @RequestMapping("/get")
    public List<String> autoCompletion(String query){
        return AutoCompletion.autoCompletion(query);
    }
}
