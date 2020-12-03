/*
 Navicat Premium Data Transfer

 Source Server         : mac-docker-mysql
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : easyexcel

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 29/11/2020 15:54:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据表主键',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `email` varchar(128) NOT NULL COMMENT '邮箱',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `phone` varchar(32) NOT NULL COMMENT '手机号',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `creator` varchar(128) DEFAULT NULL COMMENT '创建人',
  `operator` varchar(128) DEFAULT NULL COMMENT '修改人',
  `deleted` tinyint(2) NOT NULL DEFAULT '1' COMMENT '删除状态1：有效；-1：无效（删除时设置为-1）',
  `version` bigint(20) DEFAULT NULL COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
