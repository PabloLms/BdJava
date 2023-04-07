package com.plms.mongoSpringBoot.controller;

import org.springframework.http.ResponseEntity;

abstract class AbstractMongoController<T, ID> {

  abstract ResponseEntity<Object> getAll();

  abstract ResponseEntity<Object> get(ID id);

  abstract ResponseEntity<Object> create(T entity);

  abstract ResponseEntity<Object> update(T entity, ID id);

  abstract ResponseEntity<Object> delete(ID id);
}
