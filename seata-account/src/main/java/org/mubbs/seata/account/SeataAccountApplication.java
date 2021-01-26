package org.mubbs.seata.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.mubbs.seata.account.dao")
@SpringBootApplication
public class SeataAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataAccountApplication.class, args);
    }

}
