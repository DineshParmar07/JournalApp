package com.ntorq.journalApp.repository;

import com.ntorq.journalApp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User getUserByUserName(String userName);
}
