package com.scheduler.cameldispatcher;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties({ "_id", "_class", "lastRun", "nextRun" })
@Document(collection = "schedulers")
public class Scheduler {

    @Id
    private String id;
    private String service;
    private String uri;
    private PayloadRecord payload;
    private String scheduleType; // ON_DEMAND, INFINITE, CUSTOM
    private LocalDateTime lastRun = LocalDateTime.now();
    private LocalDateTime nextRun = LocalDateTime.now();
    private Boolean active = true;
    private Integer scheduleCount = 0;
    private Integer scheduleLimit = 0;
    private String status; // CREATED, RE_SCHEDULED, COMPLETED

}
