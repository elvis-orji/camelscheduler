package com.scheduler.cameldispatcher.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class DBProperties {

    private MongoProperties userdb = new MongoProperties();
    private MongoProperties schedulerdb = new MongoProperties();

}
