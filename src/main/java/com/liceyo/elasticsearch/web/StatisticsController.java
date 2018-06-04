package com.liceyo.elasticsearch.web;

import com.liceyo.elasticsearch.analysis.aggs.result.BaseAggResult;
import com.liceyo.elasticsearch.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liceyo
 * @version 2018/6/4
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping("/hit")
    public BaseAggResult hit(){
        return statisticsService.hitAgg();
    }

    @RequestMapping("/hit/{type}")
    public BaseAggResult hit(@PathVariable Integer type){
        return statisticsService.hitAgg(type);
    }
}
