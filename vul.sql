/*
 Navicat Premium Dump SQL

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50726 (5.7.26)
 Source Host           : 127.0.0.1:3306
 Source Schema         : vul

 Target Server Type    : MySQL
 Target Server Version : 50726 (5.7.26)
 File Encoding         : 65001

 Date: 24/10/2024 22:05:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for asset
-- ----------------------------
DROP TABLE IF EXISTS `asset`;
CREATE TABLE `asset`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `system_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `worth` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 154 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of asset
-- ----------------------------
INSERT INTO `asset` VALUES (9, 'OA_Nginx', '192.168.29.123', 'OA', '3', '2024-08-16 11:24:27', NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$50Acm9OcWu6mONYcTOYbW.QZAd.HVyv1H.b9Gi1CVuKM8U/XLxWP.', '1', '2024-07-01 21:26:19', '2024-07-06 20:21:54');
INSERT INTO `user` VALUES (2, 'user', '$2a$10$50Acm9OcWu6mONYcTOYbW.QZAd.HVyv1H.b9Gi1CVuKM8U/XLxWP.', '2', '2024-07-26 00:13:27', '2024-10-24 22:02:41');

-- ----------------------------
-- Table structure for vul
-- ----------------------------
DROP TABLE IF EXISTS `vul`;
CREATE TABLE `vul`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `solution` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `priority` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `cve` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2336 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vul
-- ----------------------------
INSERT INTO `vul` VALUES (2016, '弱密码', '详情描述 10 - 漏洞的详细信息。', '192.168.178.128', '/path/29', '修复方案描述 10。', 'critical', '3', 'mis', NULL, '2024-08-16 11:05:06', '2024-08-16 11:24:32');
INSERT INTO `vul` VALUES (2017, '文件包含漏洞', '详情描述 11 - 漏洞的详细信息。', '192.168.90.141', '/path/22', '修复方案描述 11。', 'high', '3', 'unfix', NULL, '2024-08-16 11:05:06', '2024-08-16 11:24:32');
INSERT INTO `vul` VALUES (2014, '信息泄露', '详情描述 8 - 漏洞的详细信息。', '192.168.196.87', '/path/95', '修复方案描述 8。', 'medium', '3', 'accept', NULL, '2024-08-16 11:05:06', '2024-08-16 11:24:32');
INSERT INTO `vul` VALUES (2015, '跨站请求伪造', '详情描述 9 - 漏洞的详细信息。', '192.168.81.136', '/path/45', '修复方案描述 9。', 'medium', '3', 'accept', NULL, '2024-08-16 11:05:06', '2024-08-16 11:24:32');
INSERT INTO `vul` VALUES (2012, '跨站请求伪造', '详情描述 6 - 漏洞的详细信息。', '192.168.254.202', '/path/55', '修复方案描述 6。', 'critical', '3', 'mis', NULL, '2024-08-16 11:05:06', '2024-08-16 11:24:32');
INSERT INTO `vul` VALUES (2013, '命令注入', '详情描述 7 - 漏洞的详细信息。', '192.168.222.214', '/path/27', '修复方案描述 7。', 'high', '3', 'fix', NULL, '2024-08-16 11:05:06', '2024-08-16 11:24:32');
INSERT INTO `vul` VALUES (2010, '跨站请求伪造', '详情描述 4 - 漏洞的详细信息。', '192.168.99.115', '/path/65', '修复方案描述 4。', 'critical', '3', 'mis', NULL, '2024-08-16 11:05:06', '2024-08-16 11:24:32');
INSERT INTO `vul` VALUES (2011, '信息泄露', '详情描述 5 - 漏洞的详细信息。', '192.168.185.248', '/path/66', '修复方案描述 5。', 'low', '2', 'accept', NULL, '2024-08-16 11:05:06', '2024-08-16 11:24:32');
INSERT INTO `vul` VALUES (2007, '命令注入', '详情描述 1 - 漏洞的详细信息。', '192.168.110.196', '/path/60', '修复方案描述 1。', 'critical', '3', 'mis', NULL, '2024-08-16 11:05:06', '2024-08-16 11:24:32');
INSERT INTO `vul` VALUES (2008, '路径遍历', '详情描述 2 - 漏洞的详细信息。', '192.168.88.47', '/path/81', '修复方案描述 2。', 'critical', '3', 'fix', NULL, '2024-08-16 11:05:06', '2024-08-16 11:24:32');
INSERT INTO `vul` VALUES (2009, '目录遍历', '详情描述 3 - 漏洞的详细信息。', '192.168.29.123', '/path/48', '修复方案描述 3。', 'critical', '3', 'fix', NULL, '2024-08-16 11:05:06', '2024-08-16 11:24:32');

SET FOREIGN_KEY_CHECKS = 1;
