package com.company.dashboard.init;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.company.dashboard.entity.Company;
import com.company.dashboard.entity.User;
import com.company.dashboard.service.CompanyService;
import com.company.dashboard.service.UserService;

/**
 * This is a initializing bean that inserts test data in the database.
 */
@Component
public class SampleDataInitializer {

  private static final Logger logger = Logger.getLogger(SampleDataInitializer.class);

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private UserService userService;

  @Autowired
  private CompanyService companyService;

  /**
   * Inserts test data.
   */
  public void init() {
    logger.info("Initializing sample data.");
    Session session = sessionFactory.openSession();
    User userOne =
        new User("user1@company.com", new BCryptPasswordEncoder().encode("user1"), "User One", true);
    User userTwo =
        new User("user2@company.com", new BCryptPasswordEncoder().encode("user2"), "User Two", true);
    if (CollectionUtils.isEmpty(userService.all())) {
      Transaction transaction = session.beginTransaction();
      logger.info("Adding user data.");
      Long id = (Long) session.save(userOne);
      userOne.setId(id);
      Long idTwo = (Long) session.save(userTwo);
      userTwo.setId(idTwo);
      transaction.commit();

      transaction = session.beginTransaction();
      logger.info("Adding company data.");
      Set<User> companyUser = new HashSet<User>();
      companyUser.add(userOne);
      companyUser.add(userTwo);
      session.save(new Company("Company One", "address one", "abc", "testCompany1@company.com",
          "1234", companyUser, "test city"));
      transaction.commit();
    }
  }

}
