package org.mubbs.seata.storage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.mubbs.seata.storage.dao")
@SpringBootApplication
public class SeataStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataStorageApplication.class, args);
    }

}
