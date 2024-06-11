package com.example.profitsoftunit5.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@ToString
@Document(indexName="notifications-2024")
public class Email {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String subject;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Text)
    private List<String> recipients;

    @Field(type = FieldType.Text)
    private String status;

    @Field(type = FieldType.Text)
    private String errorMessage;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private Instant lastAttemptTime;

    @Field(type = FieldType.Integer)
    private int attemptCount;

}
