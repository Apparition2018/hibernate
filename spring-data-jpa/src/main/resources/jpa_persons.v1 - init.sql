/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : jpa

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 14/11/2019 17:37:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for jpa_persons
-- ----------------------------
DROP TABLE IF EXISTS `jpa_persons`;
CREATE TABLE `jpa_persons`  (
  `id` int(11) NOT NULL,
  `last_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `birth` datetime(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jpa_persons
-- ----------------------------
INSERT INTO `jpa_persons` VALUES (1, 'MM', 'mm@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (2, 'UU', 'uu@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (3, 'RR', 'rr@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (4, 'TT', 'tt@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (5, 'HH', 'hh@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (6, 'XX', 'xx@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (7, 'IX', 'ii@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (8, 'KK', 'kk@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (9, 'ZZ', 'zz@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (10, 'NN', 'nn@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (11, 'JJ', 'jj@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (12, 'PP', 'pp@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (13, 'QQ', 'qq@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (14, 'EE', 'ee@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (15, 'WW', 'ww@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (16, 'AA', 'aa@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (17, 'VV', 'vv@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (18, 'FF', 'ff@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (19, 'SS', 'ss@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (20, 'GG', 'gg@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (21, 'BB', 'bb@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (22, 'CC', 'cc@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (23, 'LL', 'll@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (24, 'DD', 'dd@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (25, 'OO', 'oo@ljh.com', '2019-11-14 10:10:41.000000');
INSERT INTO `jpa_persons` VALUES (26, 'YY', 'yy@ljh.com', '2019-11-14 10:10:41.000000');

SET FOREIGN_KEY_CHECKS = 1;
