package com.scheduler.cameldispatcher;

import java.util.Map;

public record PayloadRecord(
        String type, // QUERY|RAW
        String operation,
        Map<String, Object> params,
        String body) {

}
