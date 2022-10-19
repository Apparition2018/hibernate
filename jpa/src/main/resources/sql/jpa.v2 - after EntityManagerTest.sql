/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : jpa

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 14/10/2022 11:27:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for id_gen
-- ----------------------------
DROP TABLE IF EXISTS `id_gen`;
CREATE TABLE `id_gen`  (
  `sequence_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `next_val` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`sequence_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of id_gen
-- ----------------------------
INSERT INTO `id_gen` VALUES ('customer_id', 4);
INSERT INTO `id_gen` VALUES ('order_id', 0);

-- ----------------------------
-- Table structure for jpa_customer
-- ----------------------------
DROP TABLE IF EXISTS `jpa_customer`;
CREATE TABLE `jpa_customer`  (
  `id` int(0) NOT NULL,
  `age` int(0) NOT NULL,
  `birth` date NULL DEFAULT NULL,
  `createTime` datetime(6) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `last_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jpa_customer
-- ----------------------------
INSERT INTO `jpa_customer` VALUES (1, 30, '2022-10-14', '2022-10-14 11:25:12.946000', 'ljh@163.com', 'MALE', 'LJH');
INSERT INTO `jpa_customer` VALUES (2, 15, '2022-10-14', '2022-10-14 11:27:13.188000', 'bb@163.com', 'MALE', 'BB');
INSERT INTO `jpa_customer` VALUES (3, 18, '2022-10-14', '2022-10-14 11:27:24.242000', 'cc@163.com', 'MALE', 'CC');
INSERT INTO `jpa_customer` VALUES (4, 18, '2022-10-14', '2022-10-14 11:27:35.727000', 'ddd@163.com', 'MALE', 'DDD');

-- ----------------------------
-- Table structure for jpa_order
-- ----------------------------
DROP TABLE IF EXISTS `jpa_order`;
CREATE TABLE `jpa_order`  (
  `id` int(0) NOT NULL,
  `order_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `customer_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKjer96a5d7picpl74xtmm1wckq`(`customer_id`) USING BTREE,
  CONSTRAINT `FKjer96a5d7picpl74xtmm1wckq` FOREIGN KEY (`customer_id`) REFERENCES `jpa_customer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jpa_order
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
