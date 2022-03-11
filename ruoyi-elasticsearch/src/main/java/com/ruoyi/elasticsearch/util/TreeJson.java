package com.ruoyi.elasticsearch.util;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.elasticsearch.domain.vo.FeatureData;

import java.util.Arrays;

/**
 * @author Jayden cxp
 * date 2022-03-11
 */
public class TreeJson {

    public static void main(String[] args) {
        String json = "{\"list\":[{\"data\":{\"fieldName\":\"sourceIp\",\"value\":\"ASSERTIP\",\"operator\":\"NOTIN\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"targetIp\",\"value\":\"ASSERTIP\",\"operator\":\"NOTIN\"},\"operate\":\"OR\"}],\"operate\":\"OR\"}";
        String json1 = "{\"list\":[{\"data\":{\"fieldName\":\"eventType\",\"value\":\"login\",\"operator\":\"EQ\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"result\",\"value\":\"success\",\"operator\":\"EQ\"},\"operate\":\"AND\"}],\"operate\":\"AND\"}";
        String json2 = "{\"list\":[{\"list\":[{\"data\":{\"fieldName\":\"targetName\",\"value\":\"333\",\"operator\":\"EQ\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"targetName\",\"value\":\"333\",\"operator\":\"EQ\"},\"operate\":\"AND\"}],\"operate\":\"AND\"},{\"list\":[{\"data\":{\"fieldName\":\"targetName\",\"value\":\"333\",\"operator\":\"EQ\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"targetName\",\"value\":\"444\",\"operator\":\"EQ\"},\"operate\":\"AND\"}],\"operate\":\"OR\"}],\"operate\":\"AND\"}";
        FeatureData featureData = JSONObject.parseObject(json2, FeatureData.class);
        System.out.println(featureData);
    }
}
