package com.company.dashboard.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.company.dashboard.dto.CompanyDto;
import com.company.dashboard.entity.Company;
import com.company.dashboard.entity.User;
import com.company.dashboard.mapper.CompanyMapper;
import com.company.dashboard.repository.CompanyRepository;

/**
 * Sits between a controller and repository. A business logic layer for managing Company.
 */
@Service
public class CompanyService {

  private static final Logger logger = Logger.getLogger(CompanyService.class);

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private CompanyMapper companyMapper;

  @Autowired
  private UserService userService;

  /**
   * Returns a list of all companies.
   * 
   * @return A List of company.
   */
  public List<CompanyDto> all() {
    logger.info("Getting all companies");
    List<CompanyDto> dtos = companyMapper.get(companyRepository.findAll());
    logger.info("Companies is db: " + dtos.size());
    return dtos;
  };

  /**
   * Gets a company using by id. Throws IllegalArgumentException if id is null.
   * 
   * @param id Id of the company.
   * @return A Company or null if it doesn't exists.
   */
  public CompanyDto get(Long id) {
    Assert.notNull(id, "A company id is required.");
    logger.info("Fetching company with id " + id);
    Company company = companyRepository.findOne(id);
    if (company == null) {
      return null;
    } else {
      return companyMapper.get(company);
    }
  }

  /**
   * Saves a new company.
   * 
   * @param dto Company to be saved.
   * @return Dto of the saved company.
   */
  @Transactional
  public CompanyDto add(CompanyDto dto) {
    logger.info("Adding a company: " + dto.toString());
    Company company = companyMapper.get(dto);
    company = companyRepository.save(company);
    logger.info("Company Added id: " + company.getId());
    return companyMapper.get(company);
  }

  /**
   * Deletes a company.
   * 
   * @param id Id of the company to be deleted.
   */
  @Transactional
  public void delete(Long id) {
    logger.info("Deleting a company having id: " + id);
    companyRepository.delete(id);
    logger.info("Deleted company having id: " + id);
  }

  /**
   * Updates a company. Doesn't update the owners.
   * 
   * @param dto Company to be updated.
   * @return Updated company.
   */
  @Transactional
  public CompanyDto update(CompanyDto dto) {
    logger.info("Update company id " + dto.getId());
    Company company = companyRepository.findOne(dto.getId());
    if (company == null) {
      return null;
    }
    return companyMapper.get(companyRepository.save(companyMapper.copy(dto, company)));
  }

  /**
   * Removes a company owner.
   * 
   * @param ownerId Id of user to be removed.
   * @param companyId Id of company to be updated.
   * @return Updated Company.
   */
  public CompanyDto removeOwner(Long ownerId, Long companyId) {
    Company company = companyRepository.findOne(companyId);
    if (company == null) {
      return null;
    }
    Iterator<User> ownerIterator = company.getOwners().iterator();
    while (ownerIterator.hasNext()) {
      User owner = ownerIterator.next();
      if (owner.getId().equals(ownerId)) {
        ownerIterator.remove();
      }
    }
    return companyMapper.get(companyRepository.save(company));
  }

  /**
   * Adds an owner to a company.
   * 
   * @param ownerId Id of user to be added.
   * @param companyId Id of the company to be updated.
   * @return Updated company.
   */
  public CompanyDto addOwner(Long ownerId, Long companyId) {
    Assert.notNull(companyId, "A company Id is required while adding an owner.");
    Assert.notNull(ownerId, "User Id cannot be null while adding an owner.");
    logger.info("Adding owners to company :" + companyId);
    User user = userService.getUser(ownerId);
    if (user == null) {
      logger.error(String.format("User with id %d doesn't exists.", ownerId));
      return null;
    }
    Company company = companyRepository.findOne(companyId);
    if (company == null) {
      logger.error(String.format("Company with id %d doesn't exists.", companyId));
      return null;
    }
    Set<User> owners = company.getOwners();
    boolean userExists = false;
    for (User owner : owners) {
      if (owner.getId().equals(ownerId)) {
        userExists = true;
        break;
      }
    }
    if (!userExists) {
      owners.add(user);
    }
    return companyMapper.get((Company) companyRepository.save(company));
  }
}
