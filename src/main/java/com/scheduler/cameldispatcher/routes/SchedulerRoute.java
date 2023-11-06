package com.scheduler.cameldispatcher.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mongodb.MongoDbConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import java.time.LocalDateTime;

import com.mongodb.client.model.Filters;
import com.scheduler.cameldispatcher.Scheduler;
import com.scheduler.cameldispatcher.SchedulerService;

@Component
public class SchedulerRoute extends RouteBuilder {

        private final SchedulerService schedulerService;

        public SchedulerRoute(SchedulerService schedulerService) {
                this.schedulerService = schedulerService;
        }

        @Override
        public void configure() throws Exception {
                // getContext().setTracing(true);

                // from("timer:first-timer?period=30000") // every 60 seconds
                // .routeId("dispatcherRoute")
                // .setHeader(MongoDbConstants.CRITERIA, constant(
                // // Filters.lte("nextRun", LocalDateTime.now()),
                // Filters.and(
                // List.of(Filters.lte("nextRun", LocalDateTime.now()),
                // Filters.eq("active", true),
                // Filters.eq("status", "CREATED")))))
                // .to("mongodb://mongoSchedulerDbClient?database={{spring.data.mongodb.schedulerdb.database}}&collection=schedulers&createCollection=false&operation=findAll&outputType=MongoIterable")
                // .process(schedulerService)
                // .to("direct: updateSchedulerStatus")
                // .log("-> Scheduler Processing Completed.");

                from("timer:first-timer?period=30000") // every 60 seconds
                                .routeId("dispatcherRoute")
                                .setHeader(MongoDbConstants.CRITERIA, constant(
                                                // Filters.lte("nextRun", LocalDateTime.now()),
                                                Filters.and(
                                                                List.of(Filters.lte("nextRun", LocalDateTime.now()),
                                                                                Filters.eq("active", true),
                                                                                Filters.eq("status", "CREATED")))))
                                .to("mongodb://mongoSchedulerDbClient?database={{spring.data.mongodb.schedulerdb.database}}&collection=schedulers&createCollection=false&operation=findAll&outputType=MongoIterable")
                                .split(body())
                                .log("${body.get('payload').get('type')}")
                                .choice()
                                .when(simple("${body.get('payload').get('type')} == 'QUERY'"))
                                .to("direct:queryHandler")
                                .otherwise()
                                .to("direct:rawHandler")
                                .end()
                                // .process(schedulerService)
                                // .to("direct: updateSchedulerStatus")
                                .log("-> Scheduler Processing Completed.");

                from("direct:queryHandler")
                                .routeId("queryHandler")
                                .bean(schedulerService, "setHeader")
                                .log("------")
                                .toD("mongodb:mongoUserDbClient?${body.get('payload').get('operation')}")
                                // .to("mongodb://mongoUserDbClient?database=User&collection=uzers&operation=findAll")
                                .log("${body}");

                from("direct:rawHandler")
                                .routeId("rawHandler")
                                .setHeader("body", body())
                                .log("${body}")
                                .setHeader(MongoDbConstants.CRITERIA, constant(Filters.empty()))
                                .to("mongodb://mongoUserDbClient?database=User&collection=uzers&operation=findAll")
                                .log("${body}")
                                .bean(schedulerService, "myMethod");

                from("direct:updateSchedulerStatus")
                                .routeId("schedulerUpdateRoute")
                                .split(body())
                                .setBody(simple("{ $set: { status: 'COMPLETED' } }"))
                                .to("mongodb:mongoSchedulerDbClient?database={{spring.data.mongodb.schedulerdb.database}}&collection=schedulers&operation=update")
                                .log("-> Updating Scheduler Completed.");

        }

}
