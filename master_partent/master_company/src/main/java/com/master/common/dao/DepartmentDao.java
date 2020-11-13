package com.master.common.dao;

import com.master.domain.company.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author wuxx
 * @version 1.0
 * @date 2020/11/13 17:15
 * @className DepartmentDao
 * @description TODO
 */
public interface DepartmentDao extends JpaRepository<Department, String>, JpaSpecificationExecutor<Department> {
    Department findByCodeAndCompanyId(String code, String companyId);
}
