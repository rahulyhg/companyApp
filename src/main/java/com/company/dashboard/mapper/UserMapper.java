package com.company.dashboard.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.stereotype.Component;

import com.company.dashboard.dto.UserDto;
import com.company.dashboard.entity.User;

@Component
public class UserMapper {

  public UserDto get(User user) {
    UserDto dto = new UserDto();
    dto.setId(user.getId());
    dto.setUsername(user.getUsername());
    dto.setLoginId(user.getLoginId());
    dto.setActive(user.getIsActive());
    dto.setCreatedOn(user.getCreatedOn());
    dto.setUpdatedOn(user.getUpdatedOn());
    return dto;
  }

  public User get(UserDto dto) {
    User user = new User(dto.getLoginId(), dto.getPassword(), dto.getUsername());
    if (dto.getId() != null && dto.getId() > 0) {
      user.setId(dto.getId());
    }
    user.setIsActive(dto.isActive());
    return user;
  }

  public List<UserDto> get(Iterable<User> users) {
    List<UserDto> dtos = new ArrayList<UserDto>();
    for (User user : users) {
      dtos.add(get(user));
    }
    return dtos;
  }

  public List<User> getUsers(Iterable<UserDto> dtos) {
    List<User> users = new ArrayList<User>();
    for (UserDto dto : dtos) {
      users.add(get(dto));
    }
    return users;
  }

  public User copy(UserDto src, User dest) {
    dest.setUsername(src.getUsername());
    dest.setLoginId(src.getLoginId());
    dest.setIsActive(BooleanUtils.isTrue(src.isActive()));
    return dest;
  }
}
