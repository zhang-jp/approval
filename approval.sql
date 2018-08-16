/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.21 : Database - approval
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '后台管理用户ID',
  `user_name` varchar(50) NOT NULL COMMENT '后台管理用户姓名',
  `user_account` varchar(50) NOT NULL COMMENT '后台管理用户账号',
  `user_password` varchar(100) NOT NULL COMMENT '后台管理用户密码',
  `user_email` varchar(100) NOT NULL DEFAULT '' COMMENT '后台管理用户邮箱',
  `user_telphone` varchar(50) NOT NULL DEFAULT '' COMMENT '后台管理用户电话',
  `user_status` tinyint(4) NOT NULL COMMENT '后台管理用户状态默认11表示正常0表示停用',
  `user_department` bigint(20) NOT NULL COMMENT '用户部门',
  `user_position` bigint(20) NOT NULL COMMENT '用户职位',
  `salt` varchar(50) NOT NULL COMMENT '密码加密',
  `creation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `last_updated_by` bigint(20) NOT NULL COMMENT '最后更新人',
  `enabled_flag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '可用状态，默认为1，可用；0为不可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='后台管理用户表';

/*Data for the table `user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
