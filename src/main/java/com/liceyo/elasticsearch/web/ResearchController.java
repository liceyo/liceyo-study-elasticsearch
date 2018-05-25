package com.liceyo.elasticsearch.web;

import com.liceyo.elasticsearch.pojo.Research;
import com.liceyo.elasticsearch.service.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liceyo
 * @version 2018/5/25
 */
@RestController
@RequestMapping("/research")
public class ResearchController extends BaseController<Research>{

    private final ResearchService researchService;

    @Autowired
    public ResearchController(ResearchService researchService) {
        this.researchService = researchService;
        super.baseService=researchService;
    }
}
