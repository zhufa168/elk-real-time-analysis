package com.ruoyi.job.service;

import com.ruoyi.common.constant.RedisKeyConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.elasticsearch.service.impl.ElasticServiceImpl;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class FileDateService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private ElasticServiceImpl elasticService;

    @XxlJob("filterJobHandler")
    public void filterLog(){

        Date date = new Date();
        Boolean aBoolean = redisTemplate.hasKey(RedisKeyConstants.FILTER_KEY);
        String nowTimeStr =  DateUtils.getTime(date);
        String nowTimeUTC = DateUtils.stringTimetoUTC(nowTimeStr);
        String endTimeStr = "";
        String endTimeUTC = "";

        if(aBoolean) {
            endTimeStr = redisTemplate.opsForValue().get(RedisKeyConstants.FILTER_KEY);
            endTimeUTC = DateUtils.stringTimetoUTC(endTimeStr);
        }else {
            endTimeStr = nowTimeStr;
            endTimeUTC = DateUtils.dateCalcTimeMil(endTimeStr,-5);
            redisTemplate.opsForValue().set(RedisKeyConstants.FILTER_KEY, nowTimeStr);
        }
        log.info(DateUtils.getTime()+": winlog数据第一次过滤开始！");
        //时间段跨天处理
        if(DateUtils.differentDays(DateUtils.parseDate(endTimeStr),date)){
            //第一段处理
            String last_time = DateUtils.getDate(endTimeStr)+" 23:59:59";
            String oldIndex = RedisKeyConstants.PRE_INDEX +DateUtils.getDate(endTimeStr)+"_*";
            oldIndex = oldIndex.replace("-",".");
            boolean oldFlag = elasticService.queryIndexsDataList(oldIndex, endTimeUTC, DateUtils.stringTimetoUTC(last_time));

            //第二段处理
            String newStartTime = DateUtils.getDate()+ " 00:00:01";
            String newIndex = RedisKeyConstants.PRE_INDEX +DateUtils.getDate()+"_*";
            newIndex = newIndex.replace("-",".");

            boolean newFlag = elasticService.queryIndexsDataList(newIndex, DateUtils.stringTimetoUTC(newStartTime), nowTimeUTC);
            if(oldFlag && newFlag) redisTemplate.opsForValue().set(RedisKeyConstants.FILTER_KEY, nowTimeStr);
        }else{
            String index = RedisKeyConstants.PRE_INDEX +DateUtils.dateTime(DateUtils.parseDate(nowTimeStr))+"_*";
            index = index.replace("-",".");
            log.info("index:"+index + "  startTime："+endTimeUTC + " endTime："+nowTimeUTC);
            boolean flag = elasticService.queryIndexsDataList(index, endTimeUTC, nowTimeUTC);
            if(flag) redisTemplate.opsForValue().set(RedisKeyConstants.FILTER_KEY, nowTimeStr);
        }
        log.info(DateUtils.getTime()+": winlog数据第一次过滤完成！");
    }

    @XxlJob("alertJobHandler")
    public void alertJob(){
        Date date = new Date();
        String beforeDate = "";
        if(redisTemplate.hasKey(RedisKeyConstants.ALART_BEFORE_DATE)){
            beforeDate = redisTemplate.opsForValue().get(RedisKeyConstants.ALART_BEFORE_DATE);
        }else{
            beforeDate = DateUtils.getDate();
            redisTemplate.opsForValue().set(RedisKeyConstants.ALART_BEFORE_DATE,beforeDate);
        }
        log.error(DateUtils.getTime()+":winlog告警生成开始！！！");
        //判断是否同一天
        if(DateUtils.differentDays(DateUtils.parseDate(beforeDate),date)){
            this.createAlart(beforeDate);
        }
        this.createAlart(DateUtils.getDate());
        log.info(DateUtils.getTime()+":winlog告警生成成功！！！");

    }

    /**
     * 生成告警
     * @param DateStr
     */
    private void createAlart(String DateStr){
        Set<String> keys = redisTemplate.keys(RedisKeyConstants.ELASTIC_DATE + DateStr + ":*");
        keys.forEach(k ->{
            String[] split = k.split(RedisKeyConstants.ELASTIC_DATE +DateStr + ":");
            String indexName = split[1];
            String ipkey = RedisKeyConstants.ALERT_DATE +DateStr+":"+indexName;
            Boolean hasKey = redisTemplate.hasKey(ipkey);
            long minTime,maxTime;
            if(hasKey){
                String startTime = redisTemplate.opsForValue().get(ipkey);
                minTime = DateUtils.parseDate(startTime).getTime();
                maxTime =  DateUtils.dateCalcTimeStamp(DateUtils.parseDate(startTime), 30);
                if(maxTime> new Date().getTime()) return;
            }else {
                Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet().rangeWithScores(k, 0, 0);
                if(typedTuples.size()>0){
                    ZSetOperations.TypedTuple<String> next = typedTuples.iterator().next();
                    minTime = next.getScore().longValue();
                    Date mindate = new Date(minTime);
                    maxTime=DateUtils.dateCalcTimeStamp(mindate, 30);
                    if(maxTime> new Date().getTime()) return;
                }else{
                    return;
                }
            }
            long pageCount = 0;
            long size = 1000;
            long count = redisTemplate.opsForZSet().count(k, minTime, maxTime);
            if(count>0){
                if(count%size>0){
                    pageCount = (count/size)+1;
                }else{
                    pageCount = (count/size);
                }
            }
            for(int i = 0 ;i<=pageCount;i++){
                List<String> alertList = new ArrayList();
                long offset = i*size;
                Set<ZSetOperations.TypedTuple<String>> typedTuples1 = redisTemplate.opsForZSet().rangeByScoreWithScores(k, minTime, maxTime,offset,size);
                typedTuples1.forEach(kv -> alertList.add(kv.getValue()));
                if(alertList.size() > 0) {
                    try {
                        //elasticService.searchESdateByUUID(indexName, alertList);
                        elasticService.createAlert(alertList);
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.error(DateUtils.getTime()+":winlog告警生成失败！！！",e.getMessage());
                    }
                }
            }
            if(maxTime>0)redisTemplate.opsForValue().set(ipkey,DateUtils.getTime(new Date(maxTime)),12*3600, TimeUnit.SECONDS);
        });
    }

}
