# Seata
## request
- http://localhost:9102/order/create?points=100&userName=aabb&goods=coco&count=200
- http://localhost:9102/order/create?points=100&userName=aabb&goods=coco&count=1

## Sql
### order
```sql
CREATE DATABASE /*!32312 IF NOT EXISTS*/`seata_order` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `seata_order`;

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) DEFAULT NULL,
  `points` int(11) DEFAULT NULL,
  `goods` varchar(32) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
```
### storage
```sql
CREATE DATABASE /*!32312 IF NOT EXISTS*/`seata_storage` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `seata_storage`;

/*Table structure for table `storage` */

DROP TABLE IF EXISTS `storage`;

CREATE TABLE `storage` (
  `storage_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods` varchar(32) DEFAULT NULL,
  `storage_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`storage_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `storage` */

insert  into `storage`(`storage_id`,`goods`,`storage_count`) values 
(1,'coco',100);
```
### account
```sql
CREATE DATABASE /*!32312 IF NOT EXISTS*/`seata_account` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `seata_account`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) DEFAULT NULL,
  `points` int(11) DEFAULT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `account` */

insert  into `account`(`account_id`,`user_name`,`points`) values 
(1,'aabb',100);
```
## 单机 Seata
### 每个项目加入依赖
```xml
<!-- Seata -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
</dependency>
```
### 配置代理数据源
**虽说 seata0.9.0之后版本自带数据源，但是不配怎么都运行不起来**，使用的是 mybatis plus，mybatis代理数据源需要额外配置SqlSessionFactory
```java
package org.mubbs.seata.order.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableConfigurationProperties({MybatisPlusProperties.class})
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean
    public DataSourceProxy dataSourceProxy(DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSourceProxy dataSourceProxy,
//                                                       MybatisPlusProperties mybatisProperties) {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSourceProxy);
//
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            Resource[] mapperLocaltions = resolver.getResources(mybatisProperties.getMapperLocations()[0]);
//            bean.setMapperLocations(mapperLocaltions);
//
//            if (StringUtils.isNotBlank(mybatisProperties.getConfigLocation())) {
//                Resource[] resources = resolver.getResources(mybatisProperties.getConfigLocation());
//                bean.setConfigLocation(resources[0]);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bean;
//    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSourceProxy dataSourceProxy) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSourceProxy);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] mapperLocaltions = resolver.getResources("classpath*:/mapper/**/*.xml");
            factoryBean.setMapperLocations(mapperLocaltions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return factoryBean.getObject();
    }

}
```
### application
```yaml
server:
  port: 9102
spring:
  datasource:
    url: jdbc:mysql://8.131.62.249:11306/seata_order?useSSL=true&useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
  cloud:
    nacos:
      discovery:
        server-addr: 47.106.251.53:8848
        namespace: 93346eb0-444c-47c0-8e12-ccba0070e06b
    alibaba:
      seata:
        # 这个是在 resources 目录里 file.conf 的 service.vgroup_mapping 的值决定，具体官方md有更加详细的说明
        tx-service-group: my_test_tx_group
  application:
    name: seataOrder
```
### file.conf & registry.conf
- file.conf
```text
transport {
  # tcp udt unix-domain-socket
  type = "TCP"
  #NIO NATIVE
  server = "NIO"
  #enable heartbeat
  heartbeat = true
  #thread factory for netty
  thread-factory {
    boss-thread-prefix = "NettyBoss"
    worker-thread-prefix = "NettyServerNIOWorker"
    server-executor-thread-prefix = "NettyServerBizHandler"
    share-boss-worker = false
    client-selector-thread-prefix = "NettyClientSelector"
    client-selector-thread-size = 1
    client-worker-thread-prefix = "NettyClientWorkerThread"
    # netty boss thread size,will not be used for UDT
    boss-thread-size = 1
    #auto default pin or 8
    worker-thread-size = 8
  }
  shutdown {
    # when destroy server, wait seconds
    wait = 3
  }
  serialization = "seata"
  compressor = "none"
}
service {
  #vgroup->rgroup
  vgroup_mapping.my_test_tx_group = "default"
  #only support single node
  default.grouplist = "127.0.0.1:8091"
  #degrade current not support
  enableDegrade = false
  #disable
  disable = false
  #unit ms,s,m,h,d represents milliseconds, seconds, minutes, hours, days, default permanent
  max.commit.retry.timeout = "-1"
  max.rollback.retry.timeout = "-1"
}

client {
  async.commit.buffer.limit = 10000
  lock {
    retry.internal = 10
    retry.times = 30
  }
  report.retry.count = 5
}

## transaction log store
store {
  ## store mode: file、db
  mode = "file"

  ## file store
  file {
    dir = "sessionStore"

    # branch session size , if exceeded first try compress lockkey, still exceeded throws exceptions
    max-branch-session-size = 16384
    # globe session size , if exceeded throws exceptions
    max-global-session-size = 512
    # file buffer size , if exceeded allocate new buffer
    file-write-buffer-cache-size = 16384
    # when recover batch read size
    session.reload.read_size = 100
    # async, sync
    flush-disk-mode = async
  }

  ## database store
  db {
    ## the implement of javax.sql.DataSource, such as DruidDataSource(druid)/BasicDataSource(dbcp) etc.
    datasource = "dbcp"
    ## mysql/oracle/h2/oceanbase etc.
    db-type = "mysql"
    url = "jdbc:mysql://127.0.0.1:3306/seata"
    user = "mysql"
    password = "mysql"
    min-conn = 1
    max-conn = 3
    global.table = "global_table"
    branch.table = "branch_table"
    lock-table = "lock_table"
    query-limit = 100
  }
}
lock {
  ## the lock store mode: local、remote
  mode = "remote"

  local {
    ## store locks in user's database
  }

  remote {
    ## store locks in the seata's server
  }
}
recovery {
  committing-retry-delay = 30
  asyn-committing-retry-delay = 30
  rollbacking-retry-delay = 30
  timeout-retry-delay = 30
}

transaction {
  undo.data.validation = true
  undo.log.serialization = "jackson"
}

## metrics settings
metrics {
  enabled = false
  registry-type = "compact"
  # multi exporters use comma divided
  exporter-list = "prometheus"
  exporter-prometheus-port = 9898
}
```
- registry.conf
```text
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  type = "file"

  nacos {
    serverAddr = "localhost"
    namespace = "public"
    cluster = "default"
  }
  eureka {
    serviceUrl = "http://localhost:8761/eureka"
    application = "default"
    weight = "1"
  }
  redis {
    serverAddr = "localhost:6379"
    db = "0"
  }
  zk {
    cluster = "default"
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  consul {
    cluster = "default"
    serverAddr = "127.0.0.1:8500"
  }
  etcd3 {
    cluster = "default"
    serverAddr = "http://localhost:2379"
  }
  sofa {
    serverAddr = "127.0.0.1:9603"
    application = "default"
    region = "DEFAULT_ZONE"
    datacenter = "DefaultDataCenter"
    cluster = "default"
    group = "SEATA_GROUP"
    addressWaitTime = "3000"
  }
  file {
    name = "file.conf"
  }
}

config {
  # file、nacos 、apollo、zk、consul、etcd3
  type = "file"

  nacos {
    serverAddr = "localhost"
    namespace = "public"
    cluster = "default"
  }
  consul {
    serverAddr = "127.0.0.1:8500"
  }
  apollo {
    app.id = "seata-server"
    apollo.meta = "http://192.168.1.204:8801"
  }
  zk {
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  etcd3 {
    serverAddr = "http://localhost:2379"
  }
  file {
    name = "file.conf"
  }
}
```
### Seata Server
使用的是1.4.0版本，直接下载完解压至 seata-server-1.3.0\seata\bin 目录下运行脚本
- windows：双击 seata-server.bat
- linux/mac：./seata-server.sh
不需要更改配置，本次是单机seata
### 业务
在需要进行 分布式事务的业务逻辑代码处 添加 `@GlobalTransactional` 注解
```java
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private StorageClient storageClient;

    @GlobalTransactional(rollbackFor = Exception.class)
    public boolean createOrder(Integer points, String userName, String goods, Integer count) {
        try {
            accountClient.addPoints(points * count, userName);
            storageClient.decStorage(goods, count);
            orderMapper.insert(Order.builder()
                    .goods(goods)
                    .money(points * count)
                    .points(points * count)
                    .quantity(count)
                    .userName(userName)
                    .build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
```

## 高可用 配置
### 修改 server 包下的文件
- registry.conf（./seata/conf/registry.conf）
```text
registry {
  # 修改为nacos
  type = "nacos"

  nacos {
    application = "seata-server"
    serverAddr = "47.106.251.53:8848"
    group = "SEATA_GROUP"
    namespace = "93346eb0-444c-47c0-8e12-ccba0070e06b"
    cluster = "default"
    username = "nacos"
    password = "nacoseasycase"
  }
  file {
    name = "file.conf"
  }
}

config {
  # 修改为nacos
  type = "nacos"

  nacos {
    serverAddr = "47.106.251.53:8848"
    namespace = "93346eb0-444c-47c0-8e12-ccba0070e06b"
    group = "SEATA_GROUP"
    username = "nacos"
    password = "nacoseasycase"
  }
}

```
- file.conf
```text
## transaction log store, only used in seata-server
store {
  ## 修改为db模式
  mode = "db"

  ## database store property
  db {
    ## the implement of javax.sql.DataSource, such as DruidDataSource(druid)/BasicDataSource(dbcp)/HikariDataSource(hikari) etc.
    datasource = "druid"
    ## mysql/oracle/postgresql/h2/oceanbase etc.
    dbType = "mysql"
    driverClassName = "com.mysql.cj.jdbc.Driver"
    url = "jdbc:mysql://8.131.62.249:11306/seata?useSSL=true&useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8"
    user = "root"
    password = "123456"
    minConn = 5
    maxConn = 30
    globalTable = "global_table"
    branchTable = "branch_table"
    lockTable = "lock_table"
    queryLimit = 100
    maxWait = 5000
  }
}
```
### 创建 seata 库
```sql
-- -------------------------------- The script used when storeMode is 'db' --------------------------------
-- the table to store GlobalSession data
CREATE TABLE IF NOT EXISTS `global_table`
(
    `xid`                       VARCHAR(128) NOT NULL,
    `transaction_id`            BIGINT,
    `status`                    TINYINT      NOT NULL,
    `application_id`            VARCHAR(32),
    `transaction_service_group` VARCHAR(32),
    `transaction_name`          VARCHAR(128),
    `timeout`                   INT,
    `begin_time`                BIGINT,
    `application_data`          VARCHAR(2000),
    `gmt_create`                DATETIME,
    `gmt_modified`              DATETIME,
    PRIMARY KEY (`xid`),
    KEY `idx_gmt_modified_status` (`gmt_modified`, `status`),
    KEY `idx_transaction_id` (`transaction_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- the table to store BranchSession data
CREATE TABLE IF NOT EXISTS `branch_table`
(
    `branch_id`         BIGINT       NOT NULL,
    `xid`               VARCHAR(128) NOT NULL,
    `transaction_id`    BIGINT,
    `resource_group_id` VARCHAR(32),
    `resource_id`       VARCHAR(256),
    `branch_type`       VARCHAR(8),
    `status`            TINYINT,
    `client_id`         VARCHAR(64),
    `application_data`  VARCHAR(2000),
    `gmt_create`        DATETIME(6),
    `gmt_modified`      DATETIME(6),
    PRIMARY KEY (`branch_id`),
    KEY `idx_xid` (`xid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- the table to store lock data
CREATE TABLE IF NOT EXISTS `lock_table`
(
    `row_key`        VARCHAR(128) NOT NULL,
    `xid`            VARCHAR(96),
    `transaction_id` BIGINT,
    `branch_id`      BIGINT       NOT NULL,
    `resource_id`    VARCHAR(256),
    `table_name`     VARCHAR(32),
    `pk`             VARCHAR(36),
    `gmt_create`     DATETIME,
    `gmt_modified`   DATETIME,
    PRIMARY KEY (`row_key`),
    KEY `idx_branch_id` (`branch_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
```
### 配置 nacos 的配置
- 下载 seata 的源码包 [Github](https://github.com/seata/seata)，配置 /script/config-center/config.txt 文件。
```text
service.vgroupMapping.my_test_tx_group=default
store.mode=db|redis
-----db-----
store.db.datasource=druid
store.db.dbType=mysql
store.db.driverClassName=com.mysql.jdbc.Driver
store.db.url=jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true
store.db.user=root
store.db.password=123456
----redis----
store.redis.host=127.0.0.1
store.redis.port=6379
store.redis.maxConn=10
store.redis.minConn=1
store.redis.database=0
store.redis.password=null
store.redis.queryLimit=100
```
- 接着执行 nacos/nacos-config.sh
`./nacos-config.sh -h nacos的地址 -p nacos端口 -g 组 -t 命名空间 -u 账号 -w 密码`，等待脚本执行完毕，发现 nacos 配置中心已有一大串配置就行了。
### Link
[官方文档](http://seata.io/zh-cn/docs/ops/deploy-ha.html)