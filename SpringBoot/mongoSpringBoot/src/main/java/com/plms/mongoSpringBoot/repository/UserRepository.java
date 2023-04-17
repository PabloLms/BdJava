package com.plms.mongoSpringBoot.repository;

import com.plms.mongoSpringBoot.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

  Optional<User> findFirstByLastName(final String lastName);

  List<User> findByLastName(final String lastName);

  Long deleteByAge(final Integer age);

  Long deleteByAgeAndLastName(final Integer age, final String lastName);

  Long deleteByAgeAndLastNameAndName(final Integer age, final String lastName, final String name);
}
