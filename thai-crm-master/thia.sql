/*
SQLyog Ultimate v11.24 (64 bit)
MySQL - 5.6.20 : Database - thai
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`thai` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `thai`;

/*Table structure for table `channel` */

DROP TABLE IF EXISTS `channel`;

CREATE TABLE `channel` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `token` varchar(50) NOT NULL COMMENT '接口下单标识',
  `operator_id` int(11) NOT NULL COMMENT '运营人员',
  `operator_name` varchar(50) NOT NULL,
  `status` int(2) unsigned NOT NULL DEFAULT '0' COMMENT '0=正常, 1=停用',
  `order_count` int(4) NOT NULL DEFAULT '0',
  `amount` double(12,2) unsigned NOT NULL DEFAULT '0.00',
  `goods_count` int(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `channel` */

insert  into `channel`(`id`,`name`,`token`,`operator_id`,`operator_name`,`status`,`order_count`,`amount`,`goods_count`) values (2,'taobao','61ab279c07a758c9e64f40fec837e4',1,'管理员',0,0,0.00,0);

/*Table structure for table `channel_goods_inv` */

DROP TABLE IF EXISTS `channel_goods_inv`;

CREATE TABLE `channel_goods_inv` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `channel_id` int(11) NOT NULL COMMENT '渠道id',
  `allocated_amount` float NOT NULL COMMENT '分配的库存',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `channel_goods_inv` */

/*Table structure for table `channel_merchant_inv` */

DROP TABLE IF EXISTS `channel_merchant_inv`;

CREATE TABLE `channel_merchant_inv` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) NOT NULL COMMENT '商品id',
  `channel_id` int(11) NOT NULL COMMENT '渠道id',
  `allocated_amount` float NOT NULL COMMENT '分配的库存',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `channel_merchant_inv` */

insert  into `channel_merchant_inv`(`id`,`merchant_id`,`channel_id`,`allocated_amount`) values (2,2,2,80);

/*Table structure for table `channel_order` */

DROP TABLE IF EXISTS `channel_order`;

CREATE TABLE `channel_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(20) DEFAULT NULL COMMENT '订单号',
  `channel_id` int(11) unsigned zerofill NOT NULL,
  `merchant_id` int(11) unsigned NOT NULL,
  `status` int(4) unsigned zerofill NOT NULL,
  `contractor` varchar(50) DEFAULT NULL,
  `contractor_tel` varchar(50) DEFAULT NULL,
  `contractor_mobile` varchar(50) DEFAULT NULL,
  `total_price` double(10,3) unsigned zerofill DEFAULT NULL COMMENT '总价',
  `dept_date` date DEFAULT NULL COMMENT '出发日期',
  `traveler_num` int(2) unsigned zerofill NOT NULL COMMENT '旅客数量',
  `creator_id` int(11) unsigned DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `creator_name` varchar(50) DEFAULT NULL,
  `last_operator_id` int(11) DEFAULT NULL,
  `last_operator_name` varchar(50) DEFAULT NULL,
  `last_operator_date` datetime DEFAULT NULL,
  `goods_id` int(11) NOT NULL,
  `goods_name` varchar(200) NOT NULL,
  `channel_name` varchar(255) NOT NULL,
  `creator_type` int(11) DEFAULT NULL,
  `merchant_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `channel_order` */

/*Table structure for table `channel_order_traveler` */

DROP TABLE IF EXISTS `channel_order_traveler`;

CREATE TABLE `channel_order_traveler` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` int(11) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `gender` int(1) DEFAULT NULL COMMENT '性别',
  `nationality` varchar(50) DEFAULT NULL,
  `id_type` int(2) DEFAULT NULL COMMENT '证件类型',
  `id_no` varchar(50) DEFAULT NULL COMMENT '证件号',
  `effective_date` varchar(50) DEFAULT NULL COMMENT '证件有效期',
  `birth` varchar(20) DEFAULT NULL COMMENT '生日',
  `mobile` varchar(50) DEFAULT NULL,
  `type` int(11) NOT NULL COMMENT '0=成人  1=儿童',
  `price` double(10,3) DEFAULT '0.000',
  PRIMARY KEY (`id`),
  KEY `FK130C19D4BA83CE7C` (`order_id`),
  CONSTRAINT `FK130C19D4BA83CE7C` FOREIGN KEY (`order_id`) REFERENCES `channel_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `channel_order_traveler` */

/*Table structure for table `goods` */

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) unsigned NOT NULL,
  `merchant_name` varchar(50) DEFAULT NULL,
  `title` varchar(200) NOT NULL COMMENT '商品名称',
  `dept` varchar(20) NOT NULL,
  `arrived` varchar(20) DEFAULT NULL,
  `hotel_id` int(11) DEFAULT NULL,
  `hotel_name` varchar(200) DEFAULT NULL,
  `summary` varchar(200) DEFAULT NULL COMMENT '推荐，摘要',
  `travel_days` int(4) DEFAULT NULL COMMENT '行程天数',
  `goods_count` int(5) NOT NULL COMMENT '库存量',
  `sold_count` int(5) NOT NULL DEFAULT '0' COMMENT '已售数量',
  `status` int(2) NOT NULL COMMENT '0=新商品待上架 1=待审核 2=已上架  3=已下架',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `goods` */

insert  into `goods`(`id`,`merchant_id`,`merchant_name`,`title`,`dept`,`arrived`,`hotel_id`,`hotel_name`,`summary`,`travel_days`,`goods_count`,`sold_count`,`status`) values (7,2,'达拉斯','goods1','can','pek',0,NULL,'dfsdfdsd s',5,10,0,3);
insert  into `goods`(`id`,`merchant_id`,`merchant_name`,`title`,`dept`,`arrived`,`hotel_id`,`hotel_name`,`summary`,`travel_days`,`goods_count`,`sold_count`,`status`) values (8,2,'达拉斯','goods2','pek','sha',0,NULL,'s dfs vdsds  fds',8,10,0,3);

/*Table structure for table `goods_details` */

DROP TABLE IF EXISTS `goods_details`;

CREATE TABLE `goods_details` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `travel_plan` text COMMENT '行程安排',
  `cost_desc` text COMMENT '费用说明',
  `book_notes` text COMMENT '预定须知',
  `notes` text COMMENT '备注',
  `pic_path` varchar(200) DEFAULT NULL COMMENT '宣传图片',
  `line_pic_path_a` varchar(200) DEFAULT NULL,
  `line_pic_path_b` varchar(200) DEFAULT NULL,
  `line_pic_path_c` varchar(200) DEFAULT NULL,
  `line_pic_path_d` varchar(200) DEFAULT NULL,
  `line_pic_path_e` varchar(200) DEFAULT NULL,
  `line_pic_path_f` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `goods_details` */

insert  into `goods_details`(`id`,`goods_id`,`travel_plan`,`cost_desc`,`book_notes`,`notes`,`pic_path`,`line_pic_path_a`,`line_pic_path_b`,`line_pic_path_c`,`line_pic_path_d`,`line_pic_path_e`,`line_pic_path_f`) values (1,7,'dsfsdfs ','sdf sdfs','dfdsfs','sdfsdf',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `goods_details`(`id`,`goods_id`,`travel_plan`,`cost_desc`,`book_notes`,`notes`,`pic_path`,`line_pic_path_a`,`line_pic_path_b`,`line_pic_path_c`,`line_pic_path_d`,`line_pic_path_e`,`line_pic_path_f`) values (2,8,'ewefewfw','fwef','wefw','efwefwefwfwef','/resources/goods/8/picPath.jpg','/resources/goods/8/a.jpg','/resources/goods/8/b.jpg','/resources/goods/8/c.jpg','/resources/goods/8/d.jpg',NULL,NULL);

/*Table structure for table `goods_price_seg` */

DROP TABLE IF EXISTS `goods_price_seg`;

CREATE TABLE `goods_price_seg` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `audit_price` double(10,3) NOT NULL DEFAULT '0.000',
  `child_price` double(10,3) NOT NULL DEFAULT '0.000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `goods_price_seg` */

insert  into `goods_price_seg`(`id`,`goods_id`,`start_date`,`end_date`,`audit_price`,`child_price`) values (8,7,'2014-09-18','2014-09-20',1000.000,800.000);
insert  into `goods_price_seg`(`id`,`goods_id`,`start_date`,`end_date`,`audit_price`,`child_price`) values (9,8,'2014-09-18','2014-09-20',1001.000,211.000);

/*Table structure for table `hotel` */

DROP TABLE IF EXISTS `hotel`;

CREATE TABLE `hotel` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `translated_name` varchar(100) DEFAULT NULL,
  `formerly_name` varchar(100) DEFAULT NULL,
  `continent` varchar(30) DEFAULT NULL,
  `country` varchar(60) DEFAULT NULL,
  `city` varchar(60) DEFAULT NULL,
  `state` varchar(60) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `chain_name` varchar(100) DEFAULT NULL COMMENT '连锁名称',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=41971 DEFAULT CHARSET=utf8;

/*Data for the table `hotel` */

/*Table structure for table `log_channel` */

DROP TABLE IF EXISTS `log_channel`;

CREATE TABLE `log_channel` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `channel_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `creator_id` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `creator_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `log_channel` */

insert  into `log_channel`(`id`,`channel_id`,`content`,`creator_id`,`created_date`,`creator_name`) values (1,2,'创建渠道信息，新内容为Channel [id=2, name=taobao, token=61ab279c07a758c9e64f40fec837e4, status=0, operatorId=1, operatorName=管理员, orderCount=0, amount=0.0, goodsCount=0, goodsInvs=[], merchantInvs=[ChannelMerchantInv [id=2, channelId=2, merchantId=2, allocatedAmount=80.0]]]',1,'2014-09-18 20:10:45','admin');

/*Table structure for table `log_goods` */

DROP TABLE IF EXISTS `log_goods`;

CREATE TABLE `log_goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) unsigned NOT NULL,
  `content` text NOT NULL,
  `creator_id` int(11) NOT NULL,
  `creator_name` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `log_goods` */

insert  into `log_goods`(`id`,`goods_id`,`content`,`creator_id`,`creator_name`,`created_date`) values (7,7,'创建商品信息，新内容为Goods [merchantName=达拉斯, title=goods1, dept=can, arrived=pek, status=1, hotelName=null, travelDays=5, goodsCount=10]',2,'dalas','2014-09-18 19:43:54');
insert  into `log_goods`(`id`,`goods_id`,`content`,`creator_id`,`creator_name`,`created_date`) values (8,8,'创建商品信息，新内容为Goods [merchantName=达拉斯, title=goods2, dept=pek, arrived=sha, status=1, hotelName=null, travelDays=8, goodsCount=10]',2,'dalas','2014-09-18 19:59:22');
insert  into `log_goods`(`id`,`goods_id`,`content`,`creator_id`,`creator_name`,`created_date`) values (9,7,'商品通过审核，已上架',1,'admin','2014-09-18 20:02:24');
insert  into `log_goods`(`id`,`goods_id`,`content`,`creator_id`,`creator_name`,`created_date`) values (10,8,'商品通过审核，已上架',1,'admin','2014-09-18 20:02:29');

/*Table structure for table `log_order` */

DROP TABLE IF EXISTS `log_order`;

CREATE TABLE `log_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` int(11) unsigned NOT NULL,
  `content` varchar(500) DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `creator_name` varchar(50) DEFAULT NULL,
  `creator_type` int(2) NOT NULL COMMENT '0=用户 1=客服 2=系统 3=渠道接口',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `log_order` */

/*Table structure for table `merchant` */

DROP TABLE IF EXISTS `merchant`;

CREATE TABLE `merchant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `code_name` varchar(20) DEFAULT NULL,
  `status` int(11) NOT NULL COMMENT '0=正常，1=停用，2=删除',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '0=普通商户,2=平台',
  `tel` varchar(20) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `creator_id` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `creator_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `merchant` */

insert  into `merchant`(`id`,`name`,`code_name`,`status`,`type`,`tel`,`mobile`,`creator_id`,`created_date`,`creator_name`) values (1,'泰奇幻','magicthai',0,2,NULL,NULL,0,'2014-09-18 19:42:49','admin');
insert  into `merchant`(`id`,`name`,`code_name`,`status`,`type`,`tel`,`mobile`,`creator_id`,`created_date`,`creator_name`) values (2,'达拉斯','dalas',0,0,'12312314','13890229876',1,'2014-09-18 19:43:15','管理员');

/*Table structure for table `merchant_details` */

DROP TABLE IF EXISTS `merchant_details`;

CREATE TABLE `merchant_details` (
  `id` int(11) NOT NULL,
  `logo_path` varchar(200) DEFAULT NULL,
  `notes` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `merchant_details` */

insert  into `merchant_details`(`id`,`logo_path`,`notes`) values (1,NULL,NULL);
insert  into `merchant_details`(`id`,`logo_path`,`notes`) values (2,'/upload/logo/_2.jpg','dsfsd');

/*Table structure for table `role_group` */

DROP TABLE IF EXISTS `role_group`;

CREATE TABLE `role_group` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `parent_id` int(11) DEFAULT NULL COMMENT '组的树形',
  `token` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_group` */

/*Table structure for table `snapshot_goods` */

DROP TABLE IF EXISTS `snapshot_goods`;

CREATE TABLE `snapshot_goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) unsigned NOT NULL,
  `merchant_id` int(11) unsigned NOT NULL,
  `merchant_name` varchar(50) DEFAULT NULL,
  `channel_id` int(11) NOT NULL,
  `dept` varchar(20) NOT NULL,
  `arrived` varchar(20) DEFAULT NULL,
  `hotel_id` int(11) DEFAULT NULL,
  `hotel_name` varchar(200) DEFAULT NULL,
  `summary` varchar(200) DEFAULT NULL COMMENT '推荐，摘要',
  `travel_days` int(4) DEFAULT NULL COMMENT '行程天数',
  `goods_count` int(5) NOT NULL COMMENT '库存量',
  `sold_count` int(5) NOT NULL DEFAULT '0' COMMENT '已售数量',
  `status` int(2) NOT NULL COMMENT '0=新商品待上架 1=待审核 2=已上架  3=已下架',
  `adult_total_price` double(8,3) NOT NULL COMMENT '成人商品总价',
  `child_total_price` double(8,3) NOT NULL COMMENT '儿童商品总价',
  `order_id` int(11) NOT NULL,
  `title` varchar(200) NOT NULL COMMENT '商品名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `snapshot_goods` */

/*Table structure for table `snapshot_goods_details` */

DROP TABLE IF EXISTS `snapshot_goods_details`;

CREATE TABLE `snapshot_goods_details` (
  `id` int(11) unsigned NOT NULL,
  `goods_details_id` int(11) unsigned NOT NULL,
  `travel_plan` text COMMENT '行程安排',
  `cost_desc` text COMMENT '费用说明',
  `book_notes` text COMMENT '预定须知',
  `notes` text COMMENT '备注',
  `pic_path` varchar(200) DEFAULT NULL COMMENT '宣传图片',
  `line_pic_path_a` varchar(200) DEFAULT NULL,
  `line_pic_path_b` varchar(200) DEFAULT NULL,
  `line_pic_path_c` varchar(200) DEFAULT NULL,
  `line_pic_path_d` varchar(200) DEFAULT NULL,
  `line_pic_path_e` varchar(200) DEFAULT NULL,
  `line_pic_path_f` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `snapshot_goods_details` */

/*Table structure for table `snapshot_goods_price_seg` */

DROP TABLE IF EXISTS `snapshot_goods_price_seg`;

CREATE TABLE `snapshot_goods_price_seg` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `goods_snapshot_id` int(11) unsigned NOT NULL,
  `goods_id` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `audit_price` double(10,3) NOT NULL DEFAULT '0.000',
  `child_price` double(10,3) NOT NULL DEFAULT '0.000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `snapshot_goods_price_seg` */

/*Table structure for table `tuser` */

DROP TABLE IF EXISTS `tuser`;

CREATE TABLE `tuser` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `code_name` varchar(20) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `login_name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `merchant_id` int(11) unsigned NOT NULL,
  `type` int(2) unsigned NOT NULL DEFAULT '0' COMMENT '0为普通人员，2为平台员工',
  `status` int(1) NOT NULL COMMENT '0为启用，1为禁用，2为删除',
  `creator_id` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `creator_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tuser` */

insert  into `tuser`(`id`,`name`,`code_name`,`mobile`,`login_name`,`password`,`merchant_id`,`type`,`status`,`creator_id`,`created_date`,`creator_name`) values (1,'管理员','admin',NULL,'admin','b59c67bf196a4758191e42f76670ceba',1,2,0,0,NULL,'admin');
insert  into `tuser`(`id`,`name`,`code_name`,`mobile`,`login_name`,`password`,`merchant_id`,`type`,`status`,`creator_id`,`created_date`,`creator_name`) values (2,'达拉斯','dalas',NULL,'dalas','b59c67bf196a4758191e42f76670ceba',2,0,0,1,'2014-09-18 19:43:15','admin');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
