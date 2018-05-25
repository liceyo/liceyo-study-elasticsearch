package com.liceyo.elasticsearch.analysis.score;

import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lichy
 * @version 2018/2/28
 * @desc 这里定义了一些评分function
 */
public class ScoreFunction {

    /**
     * 研究文献时间衰减function
     */
    public static ScoreFunctionBuilder researchDateDecayFunction(){
        return ScoreFunctionBuilders.exponentialDecayFunction("news_pub_time", System.currentTimeMillis(), "30d", "1d", 0.9);
    }


    /**
     * 资讯模块时间衰减function
     */
    public static ScoreFunctionBuilder articleDateDecayFunction(){
        return ScoreFunctionBuilders.exponentialDecayFunction("scr_pub_time", System.currentTimeMillis(), "60d", "1d", 0.95);
    }

    /**
     * 计算越近越好权重
     *
     * @param decay
     * @param origin
     * @param scale
     * @param offset
     * @return
     */
    private static double exp(double decay, long origin, long scale, long offset, long current) {
        if (origin - current <= offset) {
            return 1d;
        } else {
            double pow = Math.pow(decay, (double) -1 / scale);
            return Math.pow(pow, -(origin - offset - current));
        }
    }

    /**
     * 计算时间越近越好
     *
     * @param decay
     * @param scale
     * @param offset
     * @param date
     * @param format
     * @return
     * @throws ParseException
     */
    public static double exp(double decay, long scale, long offset, String date, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date originDate = new Date();
        Date currentDate = dateFormat.parse(date);
        long origin = originDate.getTime() / (24 * 3600 * 1000);
        long current = currentDate.getTime() / (24 * 3600 * 1000);
        return exp(decay, origin, scale, offset, current);
    }

    /**
     * 计算欢迎度
     *
     * @param num
     * @return
     */
    public static double factor(Double num) {
        if (num == null) {
            num = 0d;
        }
        return Math.log(2 + num);
    }

//    public static void main(String[] args) throws ParseException {
//        System.out.println(exp(0.9, 360, 7, "2010-12-18", "yyyy-MM-dd"));
//        System.out.println(factor(5d) * factor(null));
//    }
}
