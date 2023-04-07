package com.plms.mongoSpringBoot.repository;

import com.plms.mongoSpringBoot.dto.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
