package com.master.system;

import com.master.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

/**
 * @author wuxx
 * @version 1.0
 * @date 2020/11/16 10:39
 * @className SystemApplication
 * @description TODO
 */
@SpringBootApplication(scanBasePackages = "com.master")
@EntityScan("com.master")
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }
}
