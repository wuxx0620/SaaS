package com.master.common;

import com.master.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

/**
 * @author wuxx
 * @version 1.0
 * @date 2020/11/13 10:53
 * @className com.eternal.wuxx.CompanyApplication
 * @description TODO
 */

@SpringBootApplication(scanBasePackages = "com.master")
@EntityScan(value = "com.master")
public class CompanyApplication {


    public static void main(String[] args) {
        SpringApplication.run(CompanyApplication.class);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }
}
