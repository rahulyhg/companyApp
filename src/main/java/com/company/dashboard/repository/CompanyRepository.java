package com.company.dashboard.repository;

import org.springframework.data.repository.CrudRepository;

import com.company.dashboard.entity.Company;

/**
 * Spring CRUD repository for managing Company.
 */
public interface CompanyRepository extends CrudRepository<Company, Long> {
}
