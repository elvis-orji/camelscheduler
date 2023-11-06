package com.scheduler.cameldispatcher;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "uzers")
public class User {

    @Id
    private String id;
    private Integer entityId;
    private String name;
    private AccountRecord account;

}
