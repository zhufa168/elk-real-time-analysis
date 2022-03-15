package com.ruoyi.common.constant;

public class RedisKeyConstants {

    //日志过滤的上一次时间
    public final static String FILTER_KEY = "es:filter:time";
    //处理es 索引的前缀
    public final static String PRE_INDEX = "winlog_";
    //第一次过滤之后生成数据的key 前缀 ,完整的是加上日期和ip
    public final static String ELASTIC_DATE = "elastic:data:";
    //生成告警的key 前缀 ,完整的加上日期和ip
    public final static String ALERT_DATE = "es:alert:data:";
    //告警 生成的前一个时间，与当前时间做对比,是否在同一天
    public final static String ALART_BEFORE_DATE = "es:before:time";



}
