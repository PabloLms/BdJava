package com.plms.mongoSpringBoot.controller;

import com.plms.mongoSpringBoot.dto.UserDto;
import com.plms.mongoSpringBoot.entities.User;
import com.plms.mongoSpringBoot.handler.ResponseHandle;
import com.plms.mongoSpringBoot.mapper.UserMapper;
import com.plms.mongoSpringBoot.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractMongoController<User, String> {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserMapper userMapper;

//  @Autowired
//  private MongoTemplate mongoTemplate;


  @RequestMapping(method = RequestMethod.GET)
  @Override
  public ResponseEntity<Object> getAll() {
    try {
      final List<User> users = this.userRepository.findAll();
      return ResponseHandle.generateResponseGet(users);
    } catch (final Exception ex) {
      return ResponseHandle.generateResponseException(ex.getMessage());
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/paginate")
  public ResponseEntity<Page<User>> getPaginate(
      @RequestParam(required = false, name = "index") final Integer optionalIndex,
      @RequestParam(required = false, name = "max") final Integer optionalMax) {
    final int index = Optional.ofNullable(optionalIndex).orElse(0);
    final int max = Optional.ofNullable(optionalMax).orElse(2);
    final HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("indexDefault", "0");
    httpHeaders.set("maxDefault", "2");
    return ResponseEntity.ok().headers(httpHeaders)
        .body(this.userRepository.findAll(PageRequest.of(index, max)));
  }

  @RequestMapping(method = RequestMethod.GET, value = "/paginate/content")
  public List<User> getPaginateContent(
      @RequestParam(required = false, name = "index") final Integer optionalIndex,
      @RequestParam(required = false, name = "max") final Integer optionalMax) {
    final int index = Optional.ofNullable(optionalIndex).orElse(0);
    final int max = Optional.ofNullable(optionalMax).orElse(2);
    return this.userRepository.findAll(PageRequest.of(index, max)).getContent();
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
  @Override
  public ResponseEntity<Object> get(@PathVariable final String userId) {
    try {
      final Optional<User> userOptional = this.userRepository.findById(userId);
      return userOptional.map(ResponseHandle::generateResponseGet)
          .orElseGet(ResponseHandle::generateResponseNotFound);
    } catch (final Exception ex) {
      return ResponseHandle.generateResponseException(ex.getMessage());
    }
  }

  @RequestMapping(value = "/findLastName", method = RequestMethod.GET)
  public ResponseEntity<Object> getLastName(@RequestParam final String lastName) {
    try {
      final Optional<User> userOptional = this.userRepository.findFirstByLastName(lastName);
      return userOptional.map(ResponseHandle::generateResponseGet)
          .orElseGet(ResponseHandle::generateResponseNotFound);
    } catch (final Exception ex) {
      return ResponseHandle.generateResponseException(ex.getMessage());
    }
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @Override
  public ResponseEntity<Object> create(@RequestBody final User user) {
    try {
      final User userSave = this.userRepository.save(user);
      return ResponseHandle.generateResponseCreate(userSave);
    } catch (final Exception ex) {
      return ResponseHandle.generateResponseException(ex.getMessage());
    }
  }

  @RequestMapping(value = "/update/{userId}", method = RequestMethod.PUT)
//  @Override
  public ResponseEntity<Object> update(@RequestBody final UserDto userDto,
      @PathVariable final String userId) {
    try {
      final Optional<User> optionalUser = this.userRepository.findById(userId);
      return optionalUser.map(user -> {
        this.userMapper.update(userDto, user);
        final User userSave = this.userRepository.save(user);
        return ResponseHandle.generateResponseUpdate(userSave);
      }).orElseGet(ResponseHandle::generateResponseNotFound);
    } catch (final Exception ex) {
      return ResponseHandle.generateResponseException(ex.getMessage());
    }
  }

  @RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
  @Override
  public ResponseEntity<Object> delete(@PathVariable final String userId) {
    try {
      this.userRepository.deleteById(userId);
      return ResponseHandle.generateResponseDelete(User.class);
    } catch (final Exception ex) {
      return ResponseHandle.generateResponseException(ex.getMessage());
    }
  }

  @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
  public ResponseEntity<Object> deleteParams(@RequestParam(name = "age") final Integer age) {
    try {
      final Long countUsers = this.userRepository.deleteByAge(age);
      return ResponseHandle.generateResponse(
          StringUtils.join("Numbers deleted of users: ", countUsers),
          HttpStatus.OK, null);
    } catch (final Exception ex) {
      return ResponseHandle.generateResponseException(ex.getMessage());
    }
  }
}
