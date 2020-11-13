package com.master.common.controller;


import com.master.common.entity.Result;
import com.master.common.entity.ResultCode;
import com.master.common.service.CompanyService;
import com.master.common.service.DepartmentService;
import com.master.domain.company.Company;
import com.master.domain.company.Department;
import com.master.domain.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author wuxx
 * @version 1.0
 * @date 2020/11/13 17:30
 * @className DepartmentController
 * @description TODO
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/company")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;

//    /**
//     * 保存
//     */
//    @RequestMapping(value = "/department", method = RequestMethod.POST)
//    public Result save(@RequestBody Department department) {
//        //1.设置保存的企业id
//        /**
//         * 企业id：目前使用固定值1，以后会解决
//         */
//        department.setCompanyId(companyId);
//        //2.调用service完成保存企业
//        departmentService.save(department);
//        //3.构造返回结果
//        return new Result(ResultCode.SUCCESS);
//    }
//
//    /**
//     * 查询企业的部门列表
//     * 指定企业id
//     */
//    @RequestMapping(value = "/department", method = RequestMethod.GET)
//    public Result findAll() {
//        //1.指定企业id
//        Company company = companyService.findById(companyId);
//        //2.完成查询
//        List<Department> list = departmentService.findAll(companyId);
//        //3.构造返回结果
//        DeptListResult deptListResult = new DeptListResult(company, list);
//        return new Result(ResultCode.SUCCESS, deptListResult);
//    }

    /**
     * 根据ID查询department
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        Department department = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS, department);
    }

    /**
     * 修改Department
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody Department department) {
        //1.设置修改的部门id
        department.setId(id);
        //2.调用service更新
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id) {
        departmentService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据部门编码和公司id查询部门
     */
    @RequestMapping(value = "/department/search", method = RequestMethod.POST)
    public Department findByCode(@RequestParam("code") String code, @RequestParam("companyId") String companyId) {
        return departmentService.findByCode(code, companyId);
    }
}
