package com.company.dashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.company.dashboard.dto.CompanyDto;
import com.company.dashboard.service.CompanyService;

/**
 * Represents the REST based controller for managing Company.
 */
@Controller
@RequestMapping("/company")
public class CompanyController {

  private static final Logger logger = Logger.getLogger(CompanyController.class);

  @Autowired
  private CompanyService companyService;

  /**
   * Returns all companies.
   * 
   * @return A list of all companies.
   */
  @ResponseBody
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public List<CompanyDto> all() {
    logger.info("Received a request to fetch all companies.");
    return companyService.all();
  }

  /**
   * Returns a company for a given id. Throws CompanyNotFoundException if id is null or 0 or no
   * company exists for a given id.
   * 
   * @param id Id of a company.
   * @return A company.
   */
  @ResponseBody
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public CompanyDto get(@PathVariable Long id) {
    logger.info("Received a request to fetch a company id: " + id);
    if (id == null || id.equals(0)) {
      logger.info("Invalid company id provided. " + id);
      throw new CompanyNotFoundException();
    }
    CompanyDto dto = companyService.get(id);
    if (dto == null) {
      logger.info("Company does not exists. " + id);
      throw new CompanyNotFoundException();
    } else {
      logger.info("Found the company with id: " + id);
      return dto;
    }
  }

  /**
   * Saves a new company. Throws InvalidCompanyProvided if id is dto is null or id is not null in
   * dto.
   * 
   * @param dto Company to be saved.
   */
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(method = RequestMethod.POST)
  public void add(@RequestBody CompanyDto dto) {
    logger.info("Received a request to save a companies " + dto);
    if (dto == null || dto.getId() != null) {
      logger.info("Either provided company is null or the company.getId is not null");
      throw new InvalidCompanyProvided();
    }
    companyService.add(dto);
  }

  /**
   * Updates an existing company. Throws InvalidCompanyProvided if dto is null or if company does
   * not exists. Doesn't update owners of a company.
   * 
   * @param dto Company to be updated.
   */
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(method = RequestMethod.PUT)
  public void update(@RequestBody CompanyDto dto) {
    logger.info("Received a request to save a companies " + dto);
    if (dto == null) {
      logger.info("Provided company is null");
      throw new InvalidCompanyProvided();
    }
    CompanyDto updatedCompany = companyService.update(dto);
    if (updatedCompany == null) {
      logger.info("Company doesn't exits.");
      throw new InvalidCompanyProvided();
    }
  }

  /**
   * Removes a company for a given id. Throws InvalidCompanyProvided exception if id is null.
   * 
   * @param id Id of a company to be removed.
   */
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public void remove(@PathVariable Long id) {
    logger.info("Received a request to delete a company " + id);
    if (id == null) {
      logger.info("Company id cannot be null for deletion.");
      throw new InvalidCompanyProvided();
    }
    companyService.delete(id);
  }

  /**
   * An exception handler for company rest calls. Currently, this handles only
   * IllegalArgumentException.
   * 
   * @param req A request.
   * @param exception Exception raised by a method.
   * @return Message for the UI.
   */
  @ResponseBody
  @ExceptionHandler({IllegalArgumentException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String runtimeExceptionHandler(HttpServletRequest req, Exception exception) {
    logger.error("Exception occured", exception);
    return "Invalid request provided.";
  }

  /**
   * Removes a owner from a company. Throws InvalidCompanyProvided if company id is null. Throws
   * InvalidUserProvided if user id is null. Throws CompanyNotFoundException if company doesn't
   * exists.
   * 
   * @param companyId Id of the company.
   * @param ownerId Id of a user.
   */
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{companyId}/{ownerId}", method = RequestMethod.DELETE)
  public void removeOwner(@PathVariable Long companyId, @PathVariable Long ownerId) {
    logger.info("Received a request to delete a user" + ownerId + " from company " + companyId);
    if (companyId == null) {
      logger.info("Company id cannot be null for deletion.");
      throw new InvalidCompanyProvided();
    }
    if (ownerId == null) {
      logger.info("Owner id cannot be null for deletion.");
      throw new InvalidUserProvided();
    }
    CompanyDto company = companyService.removeOwner(ownerId, companyId);
    if (company == null) {
      throw new CompanyNotFoundException();
    }
  }

  /**
   * Adds an owner into a company. Throws InvalidCompanyProvided if company id is null. Throws
   * InvalidUserProvided if user id is null. Throws CompanyNotFoundException if company doesn't
   * exists.
   * 
   * @param companyId Id of the company.
   * @param ownerId Id of a user.
   */
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{companyId}/{ownerId}", method = RequestMethod.PUT)
  public void addOwner(@PathVariable Long companyId, @PathVariable Long ownerId) {
    logger.info("Received a request to add a user" + ownerId + " to company " + companyId);
    if (companyId == null) {
      logger.info("Company id cannot be null for adding owner.");
      throw new InvalidCompanyProvided();
    }
    if (ownerId == null) {
      logger.info("Owner id cannot be null for adding owner.");
      throw new InvalidUserProvided();
    }
    CompanyDto company = companyService.addOwner(ownerId, companyId);
    if (company == null) {
      throw new CompanyNotFoundException();
    }
  }
}


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Such company exists.")
class CompanyNotFoundException extends RuntimeException {
}


@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid company provided.")
class InvalidCompanyProvided extends RuntimeException {
}
