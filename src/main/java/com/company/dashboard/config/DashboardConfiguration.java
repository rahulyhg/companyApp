package com.company.dashboard.config;


import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.company.dashboard.init.SampleDataInitializer;

/**
 * Integration testing specific configuration - uses the company postgres db, sets hibernate on
 * create drop mode and inserts some test data on the database.
 */
@Configuration
@Profile("dev")
@EnableTransactionManagement
@EnableJpaRepositories("com.company.dashboard.repository")
@ComponentScan({"com.company.dashboard.service", "com.company.dashboard.repository",
    "com.company.dashboard.mapper", "com.company.dashboard.provider",
    "com.company.dashboard.security"})
@EnableWebMvc
@PropertySource("classpath:application-dev.properties")
public class DashboardConfiguration {


  @Value("${db.url}")
  private String dbUrl;

  @Value("${db.user}")
  private String dbUser;

  @Value("${db.password}")
  private String dbPassword;

  @Value("${db.driverClass}")
  private String dbDriverClass;

  @Value("${hibernate.dialect}")
  private String hibernateDialect;

  /**
   * Provides an initializer for setting up application data.
   * 
   * @return SampletDataInitializer An initializer for setting up data.
   */
  @Bean(initMethod = "init")
  public SampleDataInitializer initTestData() {
    return new SampleDataInitializer();
  }

  /**
   * Provides a Datasource.
   * 
   * @return A datasource
   */
  @Bean(name = "datasource")
  public DriverManagerDataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(dbDriverClass);
    dataSource.setUrl(dbUrl);
    dataSource.setUsername(dbUser);
    dataSource.setPassword(dbPassword);
    return dataSource;
  }

  /**
   * Provides an Entity Manager Factory.
   * 
   * @param dataSource A Datasource
   * @return An entity manager.
   */
  @Bean(name = "entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      DriverManagerDataSource dataSource) {

    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
        new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setPackagesToScan(new String[] {"com.company.dashboard.entity"});
    entityManagerFactoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

    Map<String, Object> jpaProperties = new HashMap<String, Object>();
    jpaProperties.put("hibernate.hbm2ddl.auto", "update");
    jpaProperties.put("hibernate.show_sql", "false");
    jpaProperties.put("hibernate.format_sql", "true");
    jpaProperties.put("hibernate.use_sql_comments", "true");
    jpaProperties.put("hibernate.dialect", hibernateDialect);
    jpaProperties.put("hibernate.current_session_context_class", "thread");
    jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
    entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);
    return entityManagerFactoryBean;
  }

  /**
   * Provides Session Factory.
   * 
   * @param emf Entity Manager Factory.
   * @return A session factory.
   */
  @Autowired
  @Bean(name = "sessionFactory")
  public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
    HibernateJpaSessionFactoryBean factory = new HibernateJpaSessionFactoryBean();
    factory.setEntityManagerFactory(emf);
    return factory;
  }

  /**
   * Provide a TransactionManager.
   * 
   * @param entityManagerFactory An entity manager factory.
   * @param dataSource Datasource to be used.
   * @return A Transaction Manager.
   */
  @Bean(name = "transactionManager")
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory,
      DriverManagerDataSource dataSource) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    transactionManager.setDataSource(dataSource);
    return transactionManager;
  }

  /**
   * Provides a configurer to resolve ${} in value annotation.
   * 
   * @return Returns PropertySourcesPlaceholderConfigurer.
   */
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfig() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
