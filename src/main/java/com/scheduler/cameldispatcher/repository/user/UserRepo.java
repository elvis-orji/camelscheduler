package com.scheduler.cameldispatcher.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.scheduler.cameldispatcher.User;
import java.util.List;

public interface UserRepo extends MongoRepository<User, String> {

    List<User> findByAccountAuthenticatedTrue();
}
