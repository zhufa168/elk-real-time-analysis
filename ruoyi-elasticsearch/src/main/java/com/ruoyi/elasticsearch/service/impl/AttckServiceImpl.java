package com.ruoyi.elasticsearch.service.impl;

import com.ruoyi.elasticsearch.domain.AttckFeature;
import com.ruoyi.elasticsearch.mapper.AttckFeatureMapper;
import com.ruoyi.elasticsearch.service.IAttckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("attckService")
public class AttckServiceImpl implements IAttckService {

    @Autowired
    private  AttckFeatureMapper attckFeatureMapper;

    private static List<AttckFeature> attckFeatureList =  null;

    @Override
    public List<AttckFeature> selectAttckFeatureList() {
        if(attckFeatureList == null) attckFeatureList = attckFeatureMapper.selectAttckFeatureList();
        return attckFeatureList;
    }

    /**
     * 将所有规则变成or关系
     * @return map
     */
    public Map<String,String> createOrRules(){
        List<AttckFeature> attckFeatures = selectAttckFeatureList();
        Map map = new HashMap();

        //todo 解析规则
        /*attckFeatures.forEach(future ->{

        });*/
        map.put("process.name","lsass.exe");
        map.put("process.pid","424");
        map.put("process.entity_id","1111");
        return map;
    }



}
