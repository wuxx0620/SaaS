package com.master.system.dao;

import com.master.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author wuxx
 * @version 1.0
 * @date 2020/11/16 10:50
 * @className RoleDao
 * @description TODO
 */
public interface RoleDao extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {
}
