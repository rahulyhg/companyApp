package com.company.dashboard.security;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.company.dashboard.entity.User;
import com.company.dashboard.repository.UserRepository;

/**
 * UserDetails service that reads the user credentials from the database, using a JPA repository.
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

  private static final Logger LOGGER = Logger.getLogger(SecurityUserDetailsService.class);

  @Autowired
  private UserRepository userRepository;

  /**
   * Throws illegalArgumentException if username is empty. Fetches the user using the loginid. If
   * user not found then throws UsernameNotFoundException. If User exists but is inactive then
   * throws UsernameNotFoundException. Extract the Role from User entity and create a list of
   * authorities
   * 
   * @return An instance of AuthenticatedUser extracted from User Entity.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Assert.hasText(username, "Username cannot be empty.");
    User user = userRepository.findByLoginId(username);

    if (user == null) {
      String message = "User not found" + username;
      LOGGER.info(message);
      throw new UsernameNotFoundException(message);
    } else if (BooleanUtils.isFalse(user.getIsActive())) {
      String message = "User is inactive" + username;
      LOGGER.info(message);
      throw new UsernameNotFoundException(message);
    }

    LOGGER.info("Found user in database: " + user);
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ADMINISTRATOR"));


    return new AuthenticatedUser(user.getLoginId(), user.getPassword(), user.getIsActive(),
        authorities, user.getId());
  }

}
