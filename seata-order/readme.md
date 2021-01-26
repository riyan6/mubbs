## request
- http://localhost:9102/order/create?points=100&userName=aabb&goods=coco&count=200
- http://localhost:9102/order/create?points=100&userName=aabb&goods=coco&count=1

## Sql
### order
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