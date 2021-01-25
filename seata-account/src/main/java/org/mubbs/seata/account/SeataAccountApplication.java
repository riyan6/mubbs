package org.mubbs.seata.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("org.mubbs.seata.account.dao")
@SpringBootApplication
public class SeataAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataAccountApplication.class, args);
    }

}
