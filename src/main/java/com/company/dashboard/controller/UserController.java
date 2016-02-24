package com.company.dashboard.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.company.dashboard.dto.UserDto;
import com.company.dashboard.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

  private static final Logger logger = Logger.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @ResponseBody
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public List<UserDto> all() {
    logger.info("Received a request to fetch all Users.");
    return userService.all();
  }

  @ResponseBody
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public UserDto get(@PathVariable Long id) {
    logger.info("Received a request to fetch User " + id);
    if (id == null || id.equals(0)) {
      logger.info("Invalid company id provided. " + id);
      throw new UserNotFoundException();
    }
    UserDto user = userService.get(id);
    if (user == null) {
      logger.info("User does not exists. " + id);
      throw new UserNotFoundException();
    }
    logger.info("Found the user with id: " + id);
    return user;
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(method = RequestMethod.POST)
  public void add(@RequestBody UserDto dto) {
    logger.info("Received a request to save a companies " + dto);
    if (dto == null || dto.getId() != null) {
      logger.info("Either provided user is null or the user.getId is not null");
      throw new InvalidUserProvided();
    }
    userService.add(dto);
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(method = RequestMethod.PUT)
  public void update(@RequestBody UserDto dto) {
    logger.info("Received a request to save a user " + dto);
    if (dto == null) {
      logger.info("Provided user is null");
      throw new InvalidUserProvided();
    }
    UserDto updatedUser = userService.update(dto);
    if (updatedUser == null) {
      logger.info("user doesn't exits.");
      throw new UserNotFoundException();
    }
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public void remove(@PathVariable Long id) {
    logger.info("Received a request to delete a user " + id);
    if (id == null) {
      logger.info("User id cannot be null for deletion.");
      throw new InvalidUserProvided();
    }
    userService.delete(id);
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "enable/{id}", method = RequestMethod.PUT)
  public void enableUser(@PathVariable Long id) {
    logger.info("Received a request to enable a user " + id);
    if (id == null) {
      logger.info("User id cannot be null for deletion.");
      throw new InvalidUserProvided();
    }
    userService.enable(id);
  }
}


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Such User exists.")
class UserNotFoundException extends RuntimeException {
}


@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid user provided.")
class InvalidUserProvided extends RuntimeException {
}
