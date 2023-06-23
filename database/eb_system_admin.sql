/*
 Navicat Premium Data Transfer

 Source Server         : FreshFresh-Shop-Production
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : freshfresh.cjdjlhtnc540.us-west-2.rds.amazonaws.com:3306
 Source Schema         : fresh_shop

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 11/06/2023 13:50:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for eb_system_admin
-- ----------------------------
DROP TABLE IF EXISTS `eb_system_admin`;
CREATE TABLE `eb_system_admin` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '后台管理员表ID',
  `account` varchar(32) NOT NULL DEFAULT '' COMMENT '后台管理员账号',
  `head_pic` varchar(255) NOT NULL DEFAULT '',
  `pwd` varchar(100) NOT NULL DEFAULT '' COMMENT '后台管理员密码',
  `real_name` varchar(16) NOT NULL DEFAULT '' COMMENT '后台管理员姓名',
  `roles` varchar(128) NOT NULL DEFAULT '' COMMENT '后台管理员权限(menus_id)',
  `last_ip` varchar(16) DEFAULT NULL COMMENT '后台管理员最后一次登录ip',
  `last_time` int(10) unsigned DEFAULT NULL COMMENT '后台管理员最后一次登录时间',
  `add_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '后台管理员添加时间',
  `login_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '登录次数',
  `level` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '后台管理员级别',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '后台管理员状态 1有效0无效',
  `is_del` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `account` (`account`) USING BTREE,
  KEY `status` (`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='后台管理员表';

SET FOREIGN_KEY_CHECKS = 1;
