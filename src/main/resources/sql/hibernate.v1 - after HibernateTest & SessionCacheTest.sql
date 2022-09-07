/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : hibernate

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 30/08/2022 14:53:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for NEWS
-- ----------------------------
DROP TABLE IF EXISTS `NEWS`;
CREATE TABLE `NEWS`  (
  `ID` int(0) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `AUTHOR` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DATE` date NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `UK_duq2gjdo5k53otrakypw0888b`(`TITLE`) USING BTREE,
  INDEX `news_index`(`TITLE`, `AUTHOR`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of NEWS
-- ----------------------------
INSERT INTO `NEWS` VALUES (1, 'Java', 'LJH', '2022-08-30');

SET FOREIGN_KEY_CHECKS = 1;
