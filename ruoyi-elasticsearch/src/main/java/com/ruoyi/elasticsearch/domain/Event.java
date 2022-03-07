package com.ruoyi.elasticsearch.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class Event {
    private String code;
    private String provider;
    @JsonFormat(shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd HH:mm:ss",
        timezone = "GMT+8")
    private LocalDateTime created;
    private String kind;
    private String module;
    private String action;
    private String type;
    private String category;
}
