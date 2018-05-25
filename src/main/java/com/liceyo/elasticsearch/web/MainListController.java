package com.liceyo.elasticsearch.web;

import com.liceyo.elasticsearch.pojo.Subject;
import com.liceyo.elasticsearch.service.MainListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liceyo
 * @version 2018/5/25
 */
@RestController
@RequestMapping("/mainList")
public class MainListController extends BaseController<Subject>{

    private final MainListService mainListService;
    @Autowired
    public MainListController(MainListService mainListService) {
        this.mainListService = mainListService;
        super.baseService=mainListService;
    }
}
