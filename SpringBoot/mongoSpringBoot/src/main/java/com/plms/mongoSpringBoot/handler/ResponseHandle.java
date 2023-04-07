package com.plms.mongoSpringBoot.handler;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandle {

  public static final String MESSAGE = "message";
  public static final String STATUS = "status";
  public static final String DATA = "data";

  public static ResponseEntity<Object> generateResponse(String message, HttpStatus httpStatus,
      Object object) {
    final Map<String, Object> map = new HashMap<>();
    map.put(MESSAGE, message);
    map.put(STATUS, httpStatus.value());
    map.put(DATA, object);
    return new ResponseEntity<>(map, httpStatus);
  }

}
