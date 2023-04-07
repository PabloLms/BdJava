package com.plms.mongoSpringBoot.controller;

import com.plms.mongoSpringBoot.dto.User;
import com.plms.mongoSpringBoot.handler.ResponseHandle;
import com.plms.mongoSpringBoot.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractMongoController<User, String> {

  @Autowired
  private UserRepository userRepository;


  @RequestMapping(method = RequestMethod.GET)
  @Override
  public ResponseEntity<Object> getAll() {
    try {
      List<User> users = userRepository.findAll();
      return ResponseHandle.generateResponse("Success", HttpStatus.OK, users);
    } catch (Exception ex) {
      return ResponseHandle.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
  @Override
  public ResponseEntity<Object> get(@PathVariable String userId) {
    try {
      Optional<User> userOptional = userRepository.findById(userId);
      return userOptional.map(
              user -> ResponseHandle.generateResponse("Success", HttpStatus.OK, user))
          .orElseGet(
              () -> ResponseHandle.generateResponse("Not found user", HttpStatus.NOT_FOUND, null));
    } catch (Exception ex) {
      return ResponseHandle.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @Override
  public ResponseEntity<Object> create(@RequestBody User user) {
    try {
      User userSave = userRepository.save(user);
      return ResponseHandle.generateResponse("user created", HttpStatus.OK, userSave);
    } catch (Exception ex) {
      return ResponseHandle.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }

  @RequestMapping(value = "/update/{userId}", method = RequestMethod.PUT)
  @Override
  public ResponseEntity<Object> update(@RequestBody User user, @PathVariable String userId) {
    try {
      Optional<User> optionalUser = userRepository.findById(userId);
      if (optionalUser.isPresent()) {
        User userUpdate = optionalUser.get();
        userUpdate.setName(user.getName());
        userUpdate.setLastName(user.getLastName());
        userUpdate.setAge(user.getAge());
        User userSave = userRepository.save(userUpdate);
        return ResponseHandle.generateResponse("user updated", HttpStatus.OK, userSave);
      } else {
        return ResponseHandle.generateResponse("user not found", HttpStatus.MULTI_STATUS, null);
      }
    } catch (Exception ex) {
      return ResponseHandle.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }

  @RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
  @Override
  public ResponseEntity<Object> delete(@PathVariable String userId) {
    try {
      userRepository.deleteById(userId);
      return ResponseHandle.generateResponse("Delete SuccessFully", HttpStatus.OK, null);
    } catch (Exception ex) {
      return ResponseHandle.generateResponse(ex.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }
}
