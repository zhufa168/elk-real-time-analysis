package com.ruoyi.elasticsearch.util;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.elasticsearch.domain.vo.FeatureData;
import com.ruoyi.elasticsearch.domain.vo.FieldData;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Jayden cxp
 * date 2022-03-11
 */
public class TreeJson {




    public static void main(String[] args) {
        /*String json = "{\"list\":[{\"data\":{\"fieldName\":\"sourceIp\",\"value\":\"ASSERTIP\",\"operator\":\"NOTIN\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"targetIp\",\"value\":\"ASSERTIP\",\"operator\":\"NOTIN\"},\"operate\":\"OR\"}],\"operate\":\"OR\"}";
        String json1 = "{\"list\":[{\"data\":{\"fieldName\":\"eventType\",\"value\":\"login\",\"operator\":\"EQ\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"result\",\"value\":\"success\",\"operator\":\"EQ\"},\"operate\":\"AND\"}],\"operate\":\"AND\"}";
        String json2 = "{\"list\":[{\"list\":[{\"data\":{\"fieldName\":\"targetName\",\"value\":\"333\",\"operator\":\"EQ\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"targetName\",\"value\":\"333\",\"operator\":\"EQ\"},\"operate\":\"AND\"}],\"operate\":\"AND\"},{\"list\":[{\"data\":{\"fieldName\":\"targetName\",\"value\":\"333\",\"operator\":\"EQ\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"targetName\",\"value\":\"444\",\"operator\":\"EQ\"},\"operate\":\"AND\"}],\"operate\":\"OR\"}],\"operate\":\"AND\"}";
        FeatureData featureData = JSONObject.parseObject(json2, FeatureData.class);
        System.out.println(featureData);*/

        List<FeatureData> featureDataList = new ArrayList<>();
        String json = "{\"list\":[{\"data\":{\"fieldName\":\"process.name\",\"value\":\"winlogbeat.exe\",\"operator\":\"NOTIN\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"file.extension\",\"value\":\"new\",\"operator\":\"NOTIN\"},\"operate\":\"OR\"}],\"operate\":\"OR\"}";
        String json1 = "{\"list\":[{\"data\":{\"fieldName\":\"eventType\",\"value\":\"login\",\"operator\":\"EQ\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"result\",\"value\":\"success\",\"operator\":\"EQ\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"result\",\"value\":\"success\",\"operator\":\"EQ\"},\"operate\":\"AND\"}],\"operate\":\"AND\"}";
        String json2 = "{\"list\":[{\"list\":[{\"data\":{\"fieldName\":\"process.name\",\"value\":\"ChsIME.exe\",\"operator\":\"EQ\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"targetNameS\",\"value\":\"333\",\"operator\":\"EQ\"},\"operate\":\"AND\"}],\"operate\":\"AND\"},{\"list\":[{\"data\":{\"fieldName\":\"targetName1\",\"value\":\"333\",\"operator\":\"EQ\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"targetName\",\"value\":\"444\",\"operator\":\"EQ\"},\"operate\":\"AND\"}],\"operate\":\"OR\"}],\"operate\":\"AND\"}";
        FeatureData featureData1 = JSONObject.parseObject(json2, FeatureData.class);
        //FeatureData featureData2 = JSONObject.parseObject(json1, FeatureData.class);
        //FeatureData featureData3 = JSONObject.parseObject(json2, FeatureData.class);
        featureDataList.add(featureData1);
        //featureDataList.add(featureData2);
        //featureDataList.add(featureData3);
        //file.extensiononggu
       /* Map map = new HashMap();
        map.put("targetName","444");
        map.put("targetName1","333");
        map.put("targetNameT","333");
        map.put("targetNameS","333");*/

    }




}
