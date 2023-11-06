package com.scheduler.cameldispatcher.repository.scheduler;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.scheduler.cameldispatcher.Job;

public interface JobRepo extends MongoRepository<Job, String> {

}
