package com.company.dashboard.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.dashboard.dto.CompanyDto;
import com.company.dashboard.dto.UserDto;
import com.company.dashboard.entity.Company;
import com.company.dashboard.entity.User;
import com.company.dashboard.mapper.UserMapper;
import com.company.dashboard.repository.UserRepository;

@Service
public class UserService {

  private static final Logger logger = Logger.getLogger(UserService.class);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserMapper userMapper;

  public List<UserDto> all() {
    logger.info("Fetching all users");
    List<UserDto> users = userMapper.get((List<User>) userRepository.findAll());
    logger.info("Users in db: " + users.size());
    return users;
  };

  public UserDto get(Long id) {
    logger.info("Fetching user with id: " + id);
    UserDto user = userMapper.get(userRepository.findOne(id));
    logger.info("User fetced " + user);
    return user;
  }

  protected User getUser(Long id) {
    return userRepository.findOne(id);
  }

  @Transactional
  public UserDto add(UserDto dto) {
    logger.info("Adding a User: " + dto.toString());
    User user = userMapper.get(dto);
    user = userRepository.save(user);
    logger.info("User Added id: " + user.getId());
    return userMapper.get(user);
  }

  @Transactional
  public void delete(Long id) {
    logger.info("Deleting a user having id: " + id);
    userRepository.delete(id);
    logger.info("Deleted user having id: " + id);
  }

  @Transactional
  public UserDto update(UserDto dto) {
    logger.info("Update user id " + dto.getId());
    User company = userRepository.findOne(dto.getId());
    if (company == null) {
      return null;
    }
    return userMapper.get(userRepository.save(userMapper.copy(dto, company)));
  }

  @Transactional
  public void enable(Long id) {
    logger.info("Enable user id " + id);
    userRepository.updateUserStatus(id);
  }
}
