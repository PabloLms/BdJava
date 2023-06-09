package com.plms.mongoSpringBoot.mapper;

import com.plms.mongoSpringBoot.dto.UserDto;
import com.plms.mongoSpringBoot.entities.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void update(final UserDto userDto, @MappingTarget final User user);
}
