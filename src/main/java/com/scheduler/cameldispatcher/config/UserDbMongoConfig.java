package com.scheduler.cameldispatcher.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.scheduler.cameldispatcher.repository.user", mongoTemplateRef = "mongoUserDbTemplate")
public class UserDbMongoConfig {

}
