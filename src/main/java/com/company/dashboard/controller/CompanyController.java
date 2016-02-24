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

@Controller
@RequestMapping("/company")
public class CompanyController {

  private static final Logger logger = Logger.getLogger(CompanyController.class);

  @Autowired
  private CompanyService companyService;

  @ResponseBody
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public List<CompanyDto> all() {
    logger.info("Received a request to fetch all companies.");
    return companyService.all();
  }

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

  @ResponseBody
  @ExceptionHandler({IllegalArgumentException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String runtimeExceptionHandler(HttpServletRequest req, Exception exception) {
    logger.error("Exception occured", exception);
    return "Invalid request provided.";
  }

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
