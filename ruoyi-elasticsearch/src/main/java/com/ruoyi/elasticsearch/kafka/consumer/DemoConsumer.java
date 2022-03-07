package com.ruoyi.elasticsearch.kafka.consumer;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.elasticsearch.domain.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Jayden cxp
 * date 2022-02-24
 */
@Component
@Slf4j
public class DemoConsumer {

//    @KafkaListener(topics = "kafka_topic")
    public void topic_test(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            SystemLog systemLog = JSONObject.parseObject(msg.toString(),SystemLog.class);
            log.info("win_kafka 消费了： Topic:" + topic + ",Message:" + systemLog);
            ack.acknowledge();
        }
    }
}
