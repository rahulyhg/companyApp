package com.company.dashboard.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.company.dashboard.dto.UserDto;
import com.company.dashboard.entity.User;

/**
 * Contains methods for mapping a User to UserDto and vice-versa.
 */
@Component
public class UserMapper {

  /**
   * Returns a UserDto from User. Throws IllegalArgumentException if company is null.
   * 
   * @param user User to be mapped.
   * @return An instance of mapped UserDto.
   */
  public UserDto get(User user) {
    Assert.notNull(user, "User is required for mapping.");
    UserDto dto = new UserDto();
    dto.setId(user.getId());
    dto.setUsername(user.getUsername());
    dto.setLoginId(user.getLoginId());
    dto.setActive(user.getIsActive());
    dto.setCreatedOn(user.getCreatedOn());
    dto.setUpdatedOn(user.getUpdatedOn());
    return dto;
  }

  /**
   * Returns a User from UserDto. Throws IllegalArgumentException if dto is null.
   * 
   * @param company UserDto to be mapped.
   * @return An instance of mapped User.
   */
  public User get(UserDto dto) {
    Assert.notNull(dto, "User Dto is required for mapping.");
    User user = new User(dto.getLoginId(), dto.getPassword(), dto.getUsername());
    if (dto.getId() != null && dto.getId() > 0) {
      user.setId(dto.getId());
    }
    user.setIsActive(dto.isActive());
    return user;
  }

  /**
   * Returns a list of mapped UserDto from a list of User.
   * 
   * @param users Users to be mapped.
   * @return A list of UserDto.
   */
  public List<UserDto> get(Iterable<User> users) {
    List<UserDto> dtos = new ArrayList<UserDto>();
    for (User user : users) {
      dtos.add(get(user));
    }
    return dtos;
  }

  /**
   * Returns a list of mapped User from a list of UserDto.
   * 
   * @param users Users to be mapped.
   * @return A list of User.
   */
  public List<User> getUsers(Iterable<UserDto> dtos) {
    List<User> users = new ArrayList<User>();
    for (UserDto dto : dtos) {
      users.add(get(dto));
    }
    return users;
  }

  /**
   * Copies a userDto to user.
   * 
   * @param src UserDto to be copied.
   * @param dest User to be copied to.
   * @return Updated destination user.
   */
  public User copy(UserDto src, User dest) {
    dest.setUsername(src.getUsername());
    dest.setLoginId(src.getLoginId());
    dest.setIsActive(BooleanUtils.isTrue(src.isActive()));
    return dest;
  }
}
