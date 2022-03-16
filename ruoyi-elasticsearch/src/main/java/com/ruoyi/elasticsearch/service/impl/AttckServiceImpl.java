package com.ruoyi.elasticsearch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.elasticsearch.domain.model.AttCkFeature;
import com.ruoyi.elasticsearch.domain.vo.FeatureData;
import com.ruoyi.elasticsearch.mapper.AttckFeatureMapper;
import com.ruoyi.elasticsearch.service.IAttckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("attckService")
public class AttckServiceImpl implements IAttckService {

    @Autowired
    private  AttckFeatureMapper attckFeatureMapper;

    private static List<AttCkFeature> attckFeatureList =  null;

    @Override
    public List<AttCkFeature> selectAttckFeatureList() {
        if(attckFeatureList == null) attckFeatureList = attckFeatureMapper.selectAttckFeatureList();
        return attckFeatureList;
    }

    /**
     * 将所有规则变成or关系
     * @return map
     */
    public List<Map<String,String>> createOrRulesList(){
        //List<AttckFeature> attckFeatures = selectAttckFeatureList();

        List<Map<String,String>> featureDataList = new ArrayList<>();
        String json = "{\"list\":[{\"data\":{\"fieldName\":\"process.name\",\"value\":\"System\",\"operator\":\"NOTIN\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"file.extension\",\"value\":\"new\",\"operator\":\"NOTIN\"},\"operate\":\"OR\"}],\"operate\":\"OR\"}";
        String json1 = "{\"list\":[{\"data\":{\"fieldName\":\"eventType\",\"value\":\"login\",\"operator\":\"EQ\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"result\",\"value\":\"success\",\"operator\":\"EQ\"},\"operate\":\"AND\"}],\"operate\":\"AND\"}";
        String json2 = "{\"list\":[{\"list\":[{\"data\":{\"fieldName\":\"targetName\",\"value\":\"333\",\"operator\":\"EQ\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"targetName\",\"value\":\"333\",\"operator\":\"EQ\"},\"operate\":\"AND\"}],\"operate\":\"AND\"},{\"list\":[{\"data\":{\"fieldName\":\"targetName\",\"value\":\"333\",\"operator\":\"EQ\"},\"operate\":\"AND\"},{\"data\":{\"fieldName\":\"targetName\",\"value\":\"444\",\"operator\":\"EQ\"},\"operate\":\"AND\"}],\"operate\":\"OR\"}],\"operate\":\"AND\"}";
        FeatureData featureData1 = JSONObject.parseObject(json, FeatureData.class);
        FeatureData featureData2 = JSONObject.parseObject(json1, FeatureData.class);
        FeatureData featureData3 = JSONObject.parseObject(json2, FeatureData.class);
        Map map1 = new HashMap();
        Map map2 = new HashMap();
        Map map3 = new HashMap();
        map1.put("rule",json);
        map1.put("attckType","TT001");
        map2.put("rule",json1);
        map2.put("attckType","TT002");
        map3.put("rule",json2);
        map3.put("attckType","TT003");
        featureDataList.add(map1);
        featureDataList.add(map2);
        featureDataList.add(map3);
        return featureDataList;
    }

    /**
     *
     * @return
     */
    public List<FeatureData> getfeatureDataList(){

        List<FeatureData> featureDataList = new ArrayList<>();
        List<Map<String, String>> orRulesList = createOrRulesList();
        orRulesList.forEach(e->{
            Iterator<Map.Entry<String, String>> iterator = e.entrySet().iterator();
            if(iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                if(next.getKey().equals("rule")) featureDataList.add(JSONObject.parseObject(next.getValue(),FeatureData.class));
            }
        });
        return featureDataList;
    }

}
