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

 Date: 15/11/2019 16:35:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for jpa_persons
-- ----------------------------
DROP TABLE IF EXISTS `jpa_persons`;
CREATE TABLE `jpa_persons`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `add_id` int(11) NULL DEFAULT NULL,
  `birth` datetime(6) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `last_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `address_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKqxvej0aspup6unktlrfguosi2`(`address_id`) USING BTREE,
  CONSTRAINT `FKqxvej0aspup6unktlrfguosi2` FOREIGN KEY (`address_id`) REFERENCES `jpa_address` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jpa_persons
-- ----------------------------
INSERT INTO `jpa_persons` VALUES (1, 98, '2019-11-15 16:31:35.678000', 'aa@ljh.com', 'aa', NULL);
INSERT INTO `jpa_persons` VALUES (2, 99, '2019-11-15 16:31:35.678000', 'bb@ljh.com', 'bb', NULL);
INSERT INTO `jpa_persons` VALUES (3, 100, '2019-11-15 16:31:35.678000', 'cc@ljh.com', 'cc', NULL);
INSERT INTO `jpa_persons` VALUES (4, 101, '2019-11-15 16:31:35.678000', 'dd@ljh.com', 'dd', NULL);
INSERT INTO `jpa_persons` VALUES (5, 102, '2019-11-15 16:31:35.678000', 'ee@ljh.com', 'ee', NULL);
INSERT INTO `jpa_persons` VALUES (6, 103, '2019-11-15 16:31:35.678000', 'ff@ljh.com', 'ff', NULL);
INSERT INTO `jpa_persons` VALUES (7, 104, '2019-11-15 16:31:35.678000', 'gg@ljh.com', 'gg', NULL);
INSERT INTO `jpa_persons` VALUES (8, 105, '2019-11-15 16:31:35.678000', 'hh@ljh.com', 'hh', NULL);
INSERT INTO `jpa_persons` VALUES (9, 106, '2019-11-15 16:31:35.678000', 'ii@ljh.com', 'ii', NULL);
INSERT INTO `jpa_persons` VALUES (10, 107, '2019-11-15 16:31:35.678000', 'jj@ljh.com', 'jj', NULL);
INSERT INTO `jpa_persons` VALUES (11, 108, '2019-11-15 16:31:35.678000', 'kk@ljh.com', 'kk', NULL);
INSERT INTO `jpa_persons` VALUES (12, 109, '2019-11-15 16:31:35.678000', 'll@ljh.com', 'll', NULL);
INSERT INTO `jpa_persons` VALUES (13, 110, '2019-11-15 16:31:35.678000', 'mm@ljh.com', 'mm', NULL);
INSERT INTO `jpa_persons` VALUES (14, 111, '2019-11-15 16:31:35.678000', 'nn@ljh.com', 'nn', NULL);
INSERT INTO `jpa_persons` VALUES (15, 112, '2019-11-15 16:31:35.678000', 'oo@ljh.com', 'oo', NULL);
INSERT INTO `jpa_persons` VALUES (16, 113, '2019-11-15 16:31:35.678000', 'pp@ljh.com', 'pp', NULL);
INSERT INTO `jpa_persons` VALUES (17, 114, '2019-11-15 16:31:35.678000', 'qq@ljh.com', 'qq', NULL);
INSERT INTO `jpa_persons` VALUES (18, 115, '2019-11-15 16:31:35.678000', 'rr@ljh.com', 'rr', NULL);
INSERT INTO `jpa_persons` VALUES (19, 116, '2019-11-15 16:31:35.678000', 'ss@ljh.com', 'ss', NULL);
INSERT INTO `jpa_persons` VALUES (20, 117, '2019-11-15 16:31:35.678000', 'tt@ljh.com', 'tt', NULL);
INSERT INTO `jpa_persons` VALUES (21, 118, '2019-11-15 16:31:35.678000', 'uu@ljh.com', 'uu', NULL);
INSERT INTO `jpa_persons` VALUES (22, 119, '2019-11-15 16:31:35.678000', 'vv@ljh.com', 'vv', NULL);
INSERT INTO `jpa_persons` VALUES (23, 120, '2019-11-15 16:31:35.678000', 'ww@ljh.com', 'ww', NULL);
INSERT INTO `jpa_persons` VALUES (24, 121, '2019-11-15 16:31:35.678000', 'xx@ljh.com', 'xx', NULL);
INSERT INTO `jpa_persons` VALUES (25, 122, '2019-11-15 16:31:35.678000', 'yy@ljh.com', 'yy', NULL);
INSERT INTO `jpa_persons` VALUES (26, 123, '2019-11-15 16:31:35.678000', 'zz@ljh.com', 'zz', NULL);
INSERT INTO `jpa_persons` VALUES (27, NULL, '2019-11-15 16:33:12.991000', 'xyz@ljh.com', 'xyz', NULL);

SET FOREIGN_KEY_CHECKS = 1;
