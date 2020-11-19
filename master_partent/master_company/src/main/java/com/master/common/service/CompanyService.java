package com.master.common.service;

import com.master.common.dao.CompanyDao;
import com.master.common.utils.IdWorker;
import com.master.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuxx
 * @version 1.0
 * @date 2020/11/13 13:51
 * @className CompanyService
 * @description TODO
 */
@Service
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private IdWorker idWorker;


    /**
     * 保存用户
     *
     * @param company
     */
    public void add(Company company) {
        String id = idWorker.nextId() + "";
        company.setId(id);
        company.setAuditState("0");
        company.setState(1);
        companyDao.save(company);
    }

    /**
     * 修改用户
     *
     * @param company
     */
    public void update(Company company) {
        companyDao.save(company);
    }

    /**
     * 删除用户
     */
    public void deleteById(String id) {
        companyDao.deleteById(id);
    }

    /**
     * 根据id查询用户
     */
    public Company findById(String id) {
        return companyDao.findById(id).get();
    }

    /**
     * 查询用户列表
     */
    public List<Company> findAll() {
        return companyDao.findAll();
    }



    public Company save(Company company) {
        return companyDao.save(company);
    }
}
