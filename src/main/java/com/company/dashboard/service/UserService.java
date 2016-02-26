package com.company.dashboard.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.dashboard.dto.UserDto;
import com.company.dashboard.entity.User;
import com.company.dashboard.mapper.UserMapper;
import com.company.dashboard.repository.UserRepository;

/**
 * Business logic layer for managing user.
 */
@Service
public class UserService {

  private static final Logger logger = Logger.getLogger(UserService.class);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserMapper userMapper;

  /**
   * List all users.
   * 
   * @return list of users.
   */
  public List<UserDto> all() {
    logger.info("Fetching all users");
    List<UserDto> users = userMapper.get((List<User>) userRepository.findAll());
    logger.info("Users in db: " + users.size());
    return users;
  };

  /**
   * Gets a user for a given id.
   * 
   * @param id Id of a user.
   * @return Fetched user or null.
   */
  public UserDto get(Long id) {
    logger.info("Fetching user with id: " + id);
    UserDto user = userMapper.get(userRepository.findOne(id));
    logger.info("User fetced " + user);
    return user;
  }

  protected User getUser(Long id) {
    return userRepository.findOne(id);
  }

  /**
   * Saves a new user.
   * 
   * @param dto User to be saved.
   * @return Saved User.
   */
  @Transactional
  public UserDto add(UserDto dto) {
    logger.info("Adding a User: " + dto.toString());
    User user = userMapper.get(dto);
    user = userRepository.save(user);
    logger.info("User Added id: " + user.getId());
    return userMapper.get(user);
  }

  /**
   * Soft deletes a user.
   * 
   * @param id Id of the user to be deleted.
   */
  @Transactional
  public void delete(Long id) {
    logger.info("Deleting a user having id: " + id);
    userRepository.delete(id);
    logger.info("Deleted user having id: " + id);
  }

  /**
   * Updates a User.
   * 
   * @param dto User to be updated.
   * @return An updated user.
   */
  @Transactional
  public UserDto update(UserDto dto) {
    logger.info("Update user id " + dto.getId());
    User company = userRepository.findOne(dto.getId());
    if (company == null) {
      return null;
    }
    return userMapper.get(userRepository.save(userMapper.copy(dto, company)));
  }

  /**
   * Enables a previously soft deleted user.
   * 
   * @param id Id of user to enabled.
   */
  @Transactional
  public void enable(Long id) {
    logger.info("Enable user id " + id);
    userRepository.updateUserStatus(id);
  }
}
