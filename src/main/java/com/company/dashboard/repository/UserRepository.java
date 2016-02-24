package com.company.dashboard.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.company.dashboard.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
  public User findByLoginId(String username);

  @Modifying
  @Transactional
  public void updateUserStatus(@Param("userId") Long userId);
}
