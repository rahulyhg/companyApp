package com.company.dashboard.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.company.dashboard.entity.User;

/**
 * Spring CRUD repository for managing User.
 */
public interface UserRepository extends CrudRepository<User, Long> {
  public User findByLoginId(String username);

  /**
   * Updates the status of a user. Sets active flag to true. Executes the named query:
   * updateUserStatus on User entity.
   * 
   * @param userId Id of user to be updated.
   */
  @Modifying
  @Transactional
  public void updateUserStatus(@Param("userId") Long userId);
}
