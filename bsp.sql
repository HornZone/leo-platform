/*
Navicat MySQL Data Transfer

Source Server         : bsp
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : bsp

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2019-04-02 22:25:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bsp_proattrnm`
-- ----------------------------
DROP TABLE IF EXISTS `bsp_proattrnm`;
CREATE TABLE `bsp_proattrnm` (
  `id` bigint(20) NOT NULL,
  `crudType` varchar(16) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(16) DEFAULT NULL,
  `creatorName` varchar(256) DEFAULT NULL,
  `lastUpdateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastUpdator` varchar(16) DEFAULT NULL,
  `lastUpdatorName` varchar(256) DEFAULT NULL,
  `version` varchar(64) DEFAULT NULL,
  `optLock` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `isNick` int(11) DEFAULT NULL,
  `isColor` int(11) DEFAULT NULL,
  `isEnum` int(11) DEFAULT NULL,
  `isKey` int(11) DEFAULT NULL,
  `isInput` int(11) DEFAULT NULL,
  `isSale` int(11) DEFAULT NULL,
  `isRadio` int(11) DEFAULT NULL,
  `isMulti` int(11) DEFAULT NULL,
  `isSearch` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bsp_proattrnm
-- ----------------------------

-- ----------------------------
-- Table structure for `bsp_proattrval`
-- ----------------------------
DROP TABLE IF EXISTS `bsp_proattrval`;
CREATE TABLE `bsp_proattrval` (
  `id` bigint(20) NOT NULL,
  `crudType` varchar(16) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(16) DEFAULT NULL,
  `creatorName` varchar(256) DEFAULT NULL,
  `lastUpdateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastUpdator` varchar(16) DEFAULT NULL,
  `lastUpdatorName` varchar(256) DEFAULT NULL,
  `version` varchar(64) DEFAULT NULL,
  `optLock` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL,
  `attrnmId` bigint(20) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bsp_proattrval
-- ----------------------------

-- ----------------------------
-- Table structure for `bsp_proattrvalnmref`
-- ----------------------------
DROP TABLE IF EXISTS `bsp_proattrvalnmref`;
CREATE TABLE `bsp_proattrvalnmref` (
  `id` bigint(20) NOT NULL,
  `crudType` varchar(16) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(16) DEFAULT NULL,
  `creatorName` varchar(256) DEFAULT NULL,
  `lastUpdateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastUpdator` varchar(16) DEFAULT NULL,
  `lastUpdatorName` varchar(256) DEFAULT NULL,
  `version` varchar(64) DEFAULT NULL,
  `optLock` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL,
  `attrnmId` bigint(20) DEFAULT NULL,
  `attrvalId` bigint(20) DEFAULT NULL,
  `entityCode` varchar(256) DEFAULT NULL,
  `entityType` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bsp_proattrvalnmref
-- ----------------------------

-- ----------------------------
-- Table structure for `bsp_probrand`
-- ----------------------------
DROP TABLE IF EXISTS `bsp_probrand`;
CREATE TABLE `bsp_probrand` (
  `id` bigint(20) NOT NULL,
  `crudType` varchar(16) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(16) DEFAULT NULL,
  `creatorName` varchar(256) DEFAULT NULL,
  `lastUpdateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastUpdator` varchar(16) DEFAULT NULL,
  `lastUpdatorName` varchar(256) DEFAULT NULL,
  `version` varchar(64) DEFAULT NULL,
  `optLock` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL,
  `cname` varchar(256) DEFAULT NULL,
  `ename` varchar(256) DEFAULT NULL,
  `logo` varchar(256) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `address` varchar(256) DEFAULT NULL,
  `story` varchar(256) DEFAULT NULL,
  `fatherBrandId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bsp_probrand
-- ----------------------------
INSERT INTO `bsp_probrand` VALUES ('1', 'read', '001001', '2016-09-14 08:55:00', 'admin', 'admin', '2016-09-14 08:33:45', 'admin', 'admin', '0.01', '1', '??', '??', '???', 'fruitpai', 'F:\\a.jpg', '1', '????', '??????', '1');

-- ----------------------------
-- Table structure for `bsp_procategory`
-- ----------------------------
DROP TABLE IF EXISTS `bsp_procategory`;
CREATE TABLE `bsp_procategory` (
  `id` bigint(20) NOT NULL,
  `crudType` varchar(16) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(16) DEFAULT NULL,
  `creatorName` varchar(256) DEFAULT NULL,
  `lastUpdateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastUpdator` varchar(16) DEFAULT NULL,
  `lastUpdatorName` varchar(256) DEFAULT NULL,
  `version` varchar(64) DEFAULT NULL,
  `optLock` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL,
  `cname` varchar(256) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `isfather` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bsp_procategory
-- ----------------------------

-- ----------------------------
-- Table structure for `bsp_procategoryref`
-- ----------------------------
DROP TABLE IF EXISTS `bsp_procategoryref`;
CREATE TABLE `bsp_procategoryref` (
  `id` bigint(20) NOT NULL,
  `crudType` varchar(16) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(16) DEFAULT NULL,
  `creatorName` varchar(256) DEFAULT NULL,
  `lastUpdateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastUpdator` varchar(16) DEFAULT NULL,
  `lastUpdatorName` varchar(256) DEFAULT NULL,
  `version` varchar(64) DEFAULT NULL,
  `optLock` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL,
  `categoryId` bigint(20) DEFAULT NULL,
  `entityCode` varchar(256) DEFAULT NULL,
  `entityType` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bsp_procategoryref
-- ----------------------------

-- ----------------------------
-- Table structure for `bsp_propackagedgood`
-- ----------------------------
DROP TABLE IF EXISTS `bsp_propackagedgood`;
CREATE TABLE `bsp_propackagedgood` (
  `id` bigint(20) NOT NULL,
  `crudType` varchar(16) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(16) DEFAULT NULL,
  `creatorName` varchar(256) DEFAULT NULL,
  `lastUpdateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastUpdator` varchar(16) DEFAULT NULL,
  `lastUpdatorName` varchar(256) DEFAULT NULL,
  `version` varchar(64) DEFAULT NULL,
  `optLock` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL,
  `cname` varchar(256) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `type` int(2) DEFAULT NULL,
  `isStepBrand` int(2) DEFAULT NULL,
  `packageKey` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bsp_propackagedgood
-- ----------------------------

-- ----------------------------
-- Table structure for `bsp_propic`
-- ----------------------------
DROP TABLE IF EXISTS `bsp_propic`;
CREATE TABLE `bsp_propic` (
  `id` bigint(20) NOT NULL,
  `crudType` varchar(16) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(16) DEFAULT NULL,
  `creatorName` varchar(256) DEFAULT NULL,
  `lastUpdateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastUpdator` varchar(16) DEFAULT NULL,
  `lastUpdatorName` varchar(256) DEFAULT NULL,
  `version` varchar(64) DEFAULT NULL,
  `optLock` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL,
  `address` varchar(256) DEFAULT NULL,
  `position` varchar(256) DEFAULT NULL,
  `isMain` int(2) DEFAULT NULL,
  `entityCode` varchar(256) DEFAULT NULL,
  `entityType` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bsp_propic
-- ----------------------------

-- ----------------------------
-- Table structure for `bsp_proproduct`
-- ----------------------------
DROP TABLE IF EXISTS `bsp_proproduct`;
CREATE TABLE `bsp_proproduct` (
  `id` bigint(20) NOT NULL,
  `crudType` varchar(16) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(16) DEFAULT NULL,
  `creatorName` varchar(256) DEFAULT NULL,
  `lastUpdateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastUpdator` varchar(16) DEFAULT NULL,
  `lastUpdatorName` varchar(256) DEFAULT NULL,
  `version` varchar(64) DEFAULT NULL,
  `optLock` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL,
  `cname` varchar(256) DEFAULT NULL,
  `origin` varchar(256) DEFAULT NULL,
  `pretium` double DEFAULT NULL,
  `bid` double DEFAULT NULL,
  `brandId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_4` (`brandId`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`brandId`) REFERENCES `bsp_probrand` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bsp_proproduct
-- ----------------------------

-- ----------------------------
-- Table structure for `bsp_prosku`
-- ----------------------------
DROP TABLE IF EXISTS `bsp_prosku`;
CREATE TABLE `bsp_prosku` (
  `id` bigint(20) NOT NULL,
  `crudType` varchar(16) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(16) DEFAULT NULL,
  `creatorName` varchar(256) DEFAULT NULL,
  `lastUpdateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastUpdator` varchar(16) DEFAULT NULL,
  `lastUpdatorName` varchar(256) DEFAULT NULL,
  `version` varchar(64) DEFAULT NULL,
  `optLock` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL,
  `amount` int(10) DEFAULT NULL,
  `listPrice` double DEFAULT NULL,
  `barCode` varchar(256) DEFAULT NULL,
  `outCode` varchar(256) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `productCode` varchar(256) DEFAULT NULL,
  `productId` bigint(20) DEFAULT NULL,
  `cname` varchar(256) DEFAULT NULL,
  `merchantCode` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_3` (`productId`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`productId`) REFERENCES `bsp_proproduct` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bsp_prosku
-- ----------------------------

-- ----------------------------
-- Table structure for `bsp_prosubpack`
-- ----------------------------
DROP TABLE IF EXISTS `bsp_prosubpack`;
CREATE TABLE `bsp_prosubpack` (
  `id` bigint(20) NOT NULL,
  `crudType` varchar(16) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(16) DEFAULT NULL,
  `creatorName` varchar(256) DEFAULT NULL,
  `lastUpdateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastUpdator` varchar(16) DEFAULT NULL,
  `lastUpdatorName` varchar(256) DEFAULT NULL,
  `version` varchar(64) DEFAULT NULL,
  `optLock` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL,
  `packagedgoodId` bigint(20) DEFAULT NULL,
  `amount` int(10) DEFAULT NULL,
  `listPrice` double DEFAULT NULL,
  `skuId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_packaged` (`packagedgoodId`),
  KEY `FK_Reference_skusubpackaged` (`skuId`),
  CONSTRAINT `FK_Reference_packaged` FOREIGN KEY (`packagedgoodId`) REFERENCES `bsp_propackagedgood` (`id`),
  CONSTRAINT `FK_Reference_skusubpackaged` FOREIGN KEY (`skuId`) REFERENCES `bsp_prosku` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bsp_prosubpack
-- ----------------------------
