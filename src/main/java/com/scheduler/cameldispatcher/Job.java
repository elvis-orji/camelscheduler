package com.scheduler.cameldispatcher;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "jobs")
@Builder
public class Job {

    @Id
    private String id;
    private String status; // SCHEDULED, FAILED, COMPLETED
    private String uri;
    private String payload;
    private String schedulerId;

}
