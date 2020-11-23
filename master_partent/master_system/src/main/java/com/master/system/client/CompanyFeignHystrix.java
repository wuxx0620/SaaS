package com.master.system.client;

import com.master.common.entity.Result;
import com.master.common.entity.ResultCode;
import com.master.domain.company.Department;
import org.springframework.stereotype.Component;

/**
 * @author wuxx
 * @version 1.0
 * @date 2020/11/23 14:44
 * @className CompanyFeignHystrix
 * @description TODO
 */

@Component
public class CompanyFeignHystrix implements CompanyFeignClient {


    @Override
    public Department findByCode(String code, String companyId) {
        return new Department();
    }

    @Override
    public Result findCompanyById(String id) {
        return new Result(ResultCode.FAIL,"hahahah");
    }
}
