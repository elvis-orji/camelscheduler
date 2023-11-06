package com.scheduler.cameldispatcher.repository.scheduler;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.scheduler.cameldispatcher.Scheduler;

public interface SchedulerRepo extends MongoRepository<Scheduler, String> {

}
