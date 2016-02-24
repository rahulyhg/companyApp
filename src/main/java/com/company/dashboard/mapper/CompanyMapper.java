package com.company.dashboard.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.company.dashboard.dto.CompanyDto;
import com.company.dashboard.dto.UserDto;
import com.company.dashboard.entity.Company;
import com.company.dashboard.entity.User;

@Component
public class CompanyMapper {

  private static final Logger logger = Logger.getLogger(CompanyMapper.class);

  @Autowired
  private UserMapper userMapper;

  public CompanyDto get(Company company) {
    Assert.notNull(company, "Company is required for mapping.");
    CompanyDto dto = new CompanyDto();
    dto.setId(company.getId());
    dto.setName(company.getName());
    dto.setAddress(company.getAddress());
    dto.setCity(company.getCity());
    dto.setCountry(company.getCountry());
    dto.setEmail(company.getEmail());
    dto.setPhone(company.getPhone());
    if (!CollectionUtils.isEmpty(company.getOwners())) {
      dto.setOwners(new HashSet<UserDto>(userMapper.get(company.getOwners())));
    }
    dto.setCreatedOn(company.getCreatedOn());
    dto.setUpdatedOn(company.getUpdatedOn());
    return dto;
  }

  public Company get(CompanyDto dto) {
    Assert.notNull(dto, "Company Dto is required for mapping.");
    Company company = new Company();
    if (dto.getId() != null) {
      company.setId(dto.getId());
    }
    company.setName(dto.getName());
    company.setAddress(dto.getAddress());
    company.setCity(dto.getCity());
    company.setCountry(dto.getCountry());
    company.setEmail(dto.getEmail());
    company.setPhone(dto.getPhone());
    if (!CollectionUtils.isEmpty(dto.getOwners())) {
      company.setOwners(new HashSet<User>(userMapper.getUsers(dto.getOwners())));
    }
    return company;
  }

  /**
   * Copies source dto to destination entity. Doesn't copy the owner information into destination.
   * 
   * @param src To be copied company.
   * @param dest Company to be updated.
   * @return An updated Company entity.
   */
  public Company copy(CompanyDto src, Company dest) {
    dest.setName(src.getName());
    dest.setAddress(src.getAddress());
    dest.setCity(src.getCity());
    dest.setCountry(src.getCountry());
    dest.setEmail(src.getEmail());
    dest.setPhone(src.getPhone());
    return dest;
  }

  public List<CompanyDto> get(Iterable<Company> companies) {
    List<CompanyDto> dtos = new ArrayList<CompanyDto>();
    for (Company company : companies) {
      if (company == null) {
        logger.info("Skipping company while mapping company entities to dtos, since it is null");
        continue;
      }
      dtos.add(get(company));
    }
    return dtos;
  }
}
