package com.master.system.client;

import com.master.common.entity.Result;
import com.master.domain.company.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuxx
 * @version 1.0
 * @date 2020/11/20 11:33
 * @className CompanyFeignClient
 * @description TODO
 */
@FeignClient("master-company")
public interface CompanyFeignClient {

    /**
     * 调用微服务的接口
     */
    @RequestMapping(value = "/company/department/search" , method = RequestMethod.POST)
    Department findByCode(@RequestParam("code") String code, @RequestParam("companyId") String companyId);

    @RequestMapping(value = "/company/{id}" , method = RequestMethod.GET)
    Result findCompanyById(@PathVariable(value="id") String id);
}
