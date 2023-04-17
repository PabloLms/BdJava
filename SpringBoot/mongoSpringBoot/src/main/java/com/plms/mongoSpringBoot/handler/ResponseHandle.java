package com.plms.mongoSpringBoot.handler;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandle {

  public static final String MESSAGE = "message";
  public static final String STATUS = "status";
  public static final String DATA = "data";
  public static final String MESSAGE_GET = "Success";
  public static final String MESSAGE_CREATED = " created successfully";

  public static final String MESSAGE_UPDATED = " updated successfully";

  public static final String MESSAGE_NOT_FOUND = "Not found";

  private static final String MESSAGE_DELETE = " deleted successfully";

  public static ResponseEntity<Object> generateResponse(final String message,
      final HttpStatus httpStatus, final Object object) {
    final Map<String, Object> map = new HashMap<>();
    map.put(MESSAGE, message);
    map.put(STATUS, httpStatus.value());
    map.put(DATA, object);
    return new ResponseEntity<>(map, httpStatus);
  }

  public static ResponseEntity<Object> generateResponseGet(final Object object) {
    return generateResponse(MESSAGE_GET, HttpStatus.OK, object);
  }

  public static ResponseEntity<Object> generateResponseCreate(final Object object) {
    final String message = StringUtils.join(object.getClass().getSimpleName(), MESSAGE_CREATED);
    return generateResponse(message, HttpStatus.OK, object);
  }

  public static ResponseEntity<Object> generateResponseUpdate(final Object object) {
    final String message = StringUtils.join(object.getClass().getSimpleName(), MESSAGE_UPDATED);
    return generateResponse(message, HttpStatus.OK, object);
  }

  public static ResponseEntity<Object> generateResponseDelete(final Class<?> clazz) {
    final String message = StringUtils.join(clazz.getSimpleName(), MESSAGE_DELETE);
    return generateResponse(message, HttpStatus.OK, null);
  }

  public static ResponseEntity<Object> generateResponseNotFound() {
    return generateResponse(MESSAGE_NOT_FOUND, HttpStatus.NOT_FOUND, null);
  }

  public static ResponseEntity<Object> generateResponseException(final String message) {
    return generateResponse(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
  }


}
