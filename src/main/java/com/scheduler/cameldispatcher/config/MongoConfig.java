package com.scheduler.cameldispatcher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.RequiredArgsConstructor;

import org.springframework.data.mongodb.MongoDatabaseFactory;

@Configuration
@RequiredArgsConstructor
public class MongoConfig {

    private final DBProperties mongoProperties;

    @Bean
    public MongoDatabaseFactory mongoUserDbFactory() {

        return new SimpleMongoClientDatabaseFactory(mongoProperties.getUserdb().getUri());
    }

    @Bean
    @Primary
    public MongoDatabaseFactory mongoSchedulerDbFactory() {

        return new SimpleMongoClientDatabaseFactory(mongoProperties.getSchedulerdb().getUri());
    }

    @Bean
    public MongoTemplate mongoUserDbTemplate() {

        return new MongoTemplate(mongoUserDbFactory());
    }

    @Bean
    @Primary
    public MongoTemplate mongoSchedulerDbTemplate() {

        return new MongoTemplate(mongoSchedulerDbFactory());
    }

    @Bean
    public MongoClient mongoSchedulerDbClient() {

        ConnectionString connectionString = new ConnectionString(mongoProperties.getSchedulerdb().getUri());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    @Primary
    public MongoClient mongoUserDbClient() {

        ConnectionString connectionString = new ConnectionString(mongoProperties.getUserdb().getUri());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }
}