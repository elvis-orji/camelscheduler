package com.scheduler.cameldispatcher;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.mongodb.MongoDbConstants;
import org.bson.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.scheduler.cameldispatcher.repository.scheduler.JobRepo;
import com.scheduler.cameldispatcher.repository.user.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SchedulerService implements Processor {

    private final JobRepo jobRepo;
    private final UserRepo userRepo;

    public SchedulerService(JobRepo jobRepo, UserRepo userRepo) {
        this.jobRepo = jobRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        // handle exceptions
        MongoIterable<Document> results = exchange.getIn().getBody(MongoIterable.class);

        results.forEach(doc -> {
            try {
                Scheduler scheduler = new ObjectMapper().readValue(doc.toJson(), Scheduler.class);
                if (scheduler.getPayload().type().equals("QUERY")) {
                    userRepo.findByAccountAuthenticatedTrue().forEach(user -> {
                        jobRepo.save(Job.builder().uri(scheduler.getUri()).payload(scheduler.getPayload().body())
                                .status("SCHEDULED")
                                .schedulerId(doc.get("_id").toString()).build());
                    });
                } else {
                    jobRepo.save(Job.builder().uri(scheduler.getUri()).payload(scheduler.getPayload().body())
                            .status("SCHEDULED")
                            .schedulerId(doc.get("_id").toString()).build());
                }
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }
        });
    }

    public void myMethod(Exchange exchange) throws Exception {

        log.info("-> Exchange: {}", exchange.getIn().getBody());
        log.info("-> Exchange Header: {}", exchange.getIn().getHeader("body"));
    }

    public void setHeader(Exchange exchange) throws Exception {

        Scheduler scheduler = new ObjectMapper().readValue(exchange.getIn().getBody(Document.class).toJson(),
                Scheduler.class);

        for (String key : scheduler.getPayload().params().keySet()) {
            exchange.getIn().setHeader(MongoDbConstants.CRITERIA,
                    Filters.eq(key.replace('_', '.'), scheduler.getPayload().params().get(key)));
        }

    }
}
