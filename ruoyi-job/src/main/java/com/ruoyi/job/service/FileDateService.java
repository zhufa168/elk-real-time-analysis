package com.ruoyi.job.service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.elasticsearch.service.impl.ElasticServiceImpl;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class FileDateService {

    private final static String FILTER_KEY = "filter:time";

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private ElasticServiceImpl elasticService;

    @XxlJob("filterJobHandler")
    public void filterLog(){
        //参数获取时间
        String jobParam = XxlJobHelper.getJobParam();

        Date date = DateUtils.parseDate(jobParam);

        Boolean aBoolean = redisTemplate.hasKey(FILTER_KEY);
        String dateTime = null;
        if(aBoolean) {
            dateTime = redisTemplate.opsForValue().get(FILTER_KEY);
        }else {
            redisTemplate.opsForValue().set(FILTER_KEY, DateUtils.getTime(date));
        }
        String index = "winlog_"+DateUtils.dateTime(DateUtils.parseDate(dateTime))+"_*";

        String startTime = DateUtils.stringTimetoUTC(dateTime);

        String endTime = DateUtils.dateCalcTimeMil(dateTime,5);

        List list = elasticService.queryIndexsDataList(index, startTime, endTime);
        System.out.println(list.size());


    }

}
