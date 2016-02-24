package com.company.dashboard.repository;

import org.springframework.data.repository.CrudRepository;

import com.company.dashboard.entity.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {
}
