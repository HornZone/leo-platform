/*
Navicat MySQL Data Transfer

Source Server         : bsp
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : roncoo_pay

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2019-04-02 22:26:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `pms_menu`
-- ----------------------------
DROP TABLE IF EXISTS `pms_menu`;
CREATE TABLE `pms_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `creater` varchar(50) NOT NULL COMMENT '???',
  `create_time` datetime NOT NULL COMMENT '????',
  `editor` varchar(50) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '????',
  `status` varchar(20) NOT NULL,
  `remark` varchar(300) DEFAULT NULL,
  `is_leaf` varchar(20) DEFAULT NULL,
  `level` smallint(6) DEFAULT NULL,
  `parent_id` bigint(20) NOT NULL,
  `target_name` varchar(100) DEFAULT NULL,
  `number` varchar(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='???';

-- ----------------------------
-- Records of pms_menu
-- ----------------------------
INSERT INTO `pms_menu` VALUES ('1', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'NO', '1', '0', '#', '001', '????', '##');
INSERT INTO `pms_menu` VALUES ('2', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '1', 'cdgl', '00101', '????', 'pms/menu/list');
INSERT INTO `pms_menu` VALUES ('3', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '1', 'qxgl', '00102', '????', 'pms/permission/list');
INSERT INTO `pms_menu` VALUES ('4', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '1', 'jsgl', '00103', '????', 'pms/role/list');
INSERT INTO `pms_menu` VALUES ('5', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '1', 'czygl', '00104', '?????', 'pms/operator/list');
INSERT INTO `pms_menu` VALUES ('10', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'NO', '1', '0', '#', '002', '????', '##');
INSERT INTO `pms_menu` VALUES ('12', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '10', 'zhxx', '00201', '????', 'account/list');
INSERT INTO `pms_menu` VALUES ('13', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '10', 'zhlsxx', '00202', '??????', 'account/historyList');
INSERT INTO `pms_menu` VALUES ('20', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'NO', '1', '0', '#', '003', '????', '##');
INSERT INTO `pms_menu` VALUES ('22', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '20', 'yhxx', '00301', '????', 'user/info/list');
INSERT INTO `pms_menu` VALUES ('30', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'NO', '1', '0', '#', '004', '????', '##');
INSERT INTO `pms_menu` VALUES ('32', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '30', 'zfcpgl', '00401', '??????', 'pay/product/list');
INSERT INTO `pms_menu` VALUES ('33', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '30', 'yhzfpz', '00402', '??????', 'pay/config/list');
INSERT INTO `pms_menu` VALUES ('40', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'NO', '1', '0', '#', '005', '????', '##');
INSERT INTO `pms_menu` VALUES ('42', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '40', 'zfddgl', '00501', '??????', 'trade/listPaymentOrder');
INSERT INTO `pms_menu` VALUES ('43', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '40', 'zfjjgl', '00502', '??????', 'trade/listPaymentRecord');
INSERT INTO `pms_menu` VALUES ('50', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'NO', '1', '0', '#', '006', '????', '##');
INSERT INTO `pms_menu` VALUES ('52', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '50', 'jsjlgl', '00601', '??????', 'sett/list');
INSERT INTO `pms_menu` VALUES ('60', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'NO', '1', '0', '#', '007', '????', '##');
INSERT INTO `pms_menu` VALUES ('62', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '60', 'dzcclb', '00701', '??????', 'reconciliation/list/mistake');
INSERT INTO `pms_menu` VALUES ('63', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '60', 'dzpclb', '00702', '??????', 'reconciliation/list/checkbatch');
INSERT INTO `pms_menu` VALUES ('64', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '', 'YES', '2', '60', 'dzhcclb', '00703', '???????', 'reconciliation/list/scratchPool');

-- ----------------------------
-- Table structure for `pms_menu_role`
-- ----------------------------
DROP TABLE IF EXISTS `pms_menu_role`;
CREATE TABLE `pms_menu_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '??',
  `version` bigint(20) DEFAULT NULL,
  `creater` varchar(50) DEFAULT NULL COMMENT '???',
  `create_time` datetime DEFAULT NULL COMMENT '????',
  `editor` varchar(50) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '????',
  `status` varchar(20) DEFAULT NULL,
  `remark` varchar(300) DEFAULT NULL,
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ak_key_2` (`role_id`,`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1053 DEFAULT CHARSET=utf8 COMMENT='????????';

-- ----------------------------
-- Records of pms_menu_role
-- ----------------------------
INSERT INTO `pms_menu_role` VALUES ('1000', null, null, null, null, null, null, null, '1', '1');
INSERT INTO `pms_menu_role` VALUES ('1001', null, null, null, null, null, null, null, '1', '2');
INSERT INTO `pms_menu_role` VALUES ('1002', null, null, null, null, null, null, null, '1', '3');
INSERT INTO `pms_menu_role` VALUES ('1003', null, null, null, null, null, null, null, '1', '4');
INSERT INTO `pms_menu_role` VALUES ('1004', null, null, null, null, null, null, null, '1', '5');
INSERT INTO `pms_menu_role` VALUES ('1005', null, null, null, null, null, null, null, '1', '10');
INSERT INTO `pms_menu_role` VALUES ('1006', null, null, null, null, null, null, null, '1', '12');
INSERT INTO `pms_menu_role` VALUES ('1007', null, null, null, null, null, null, null, '1', '13');
INSERT INTO `pms_menu_role` VALUES ('1008', null, null, null, null, null, null, null, '1', '20');
INSERT INTO `pms_menu_role` VALUES ('1009', null, null, null, null, null, null, null, '1', '22');
INSERT INTO `pms_menu_role` VALUES ('1010', null, null, null, null, null, null, null, '1', '30');
INSERT INTO `pms_menu_role` VALUES ('1011', null, null, null, null, null, null, null, '1', '32');
INSERT INTO `pms_menu_role` VALUES ('1012', null, null, null, null, null, null, null, '1', '33');
INSERT INTO `pms_menu_role` VALUES ('1013', null, null, null, null, null, null, null, '1', '40');
INSERT INTO `pms_menu_role` VALUES ('1014', null, null, null, null, null, null, null, '1', '42');
INSERT INTO `pms_menu_role` VALUES ('1015', null, null, null, null, null, null, null, '1', '43');
INSERT INTO `pms_menu_role` VALUES ('1016', null, null, null, null, null, null, null, '1', '50');
INSERT INTO `pms_menu_role` VALUES ('1017', null, null, null, null, null, null, null, '1', '52');
INSERT INTO `pms_menu_role` VALUES ('1018', null, null, null, null, null, null, null, '1', '60');
INSERT INTO `pms_menu_role` VALUES ('1019', null, null, null, null, null, null, null, '1', '62');
INSERT INTO `pms_menu_role` VALUES ('1020', null, null, null, null, null, null, null, '1', '63');
INSERT INTO `pms_menu_role` VALUES ('1021', null, null, null, null, null, null, null, '1', '64');
INSERT INTO `pms_menu_role` VALUES ('1031', null, null, null, null, null, null, null, '2', '1');
INSERT INTO `pms_menu_role` VALUES ('1032', null, null, null, null, null, null, null, '2', '2');
INSERT INTO `pms_menu_role` VALUES ('1033', null, null, null, null, null, null, null, '2', '3');
INSERT INTO `pms_menu_role` VALUES ('1034', null, null, null, null, null, null, null, '2', '4');
INSERT INTO `pms_menu_role` VALUES ('1035', null, null, null, null, null, null, null, '2', '5');
INSERT INTO `pms_menu_role` VALUES ('1036', null, null, null, null, null, null, null, '2', '10');
INSERT INTO `pms_menu_role` VALUES ('1037', null, null, null, null, null, null, null, '2', '12');
INSERT INTO `pms_menu_role` VALUES ('1038', null, null, null, null, null, null, null, '2', '13');
INSERT INTO `pms_menu_role` VALUES ('1039', null, null, null, null, null, null, null, '2', '20');
INSERT INTO `pms_menu_role` VALUES ('1040', null, null, null, null, null, null, null, '2', '22');
INSERT INTO `pms_menu_role` VALUES ('1041', null, null, null, null, null, null, null, '2', '30');
INSERT INTO `pms_menu_role` VALUES ('1042', null, null, null, null, null, null, null, '2', '32');
INSERT INTO `pms_menu_role` VALUES ('1043', null, null, null, null, null, null, null, '2', '33');
INSERT INTO `pms_menu_role` VALUES ('1044', null, null, null, null, null, null, null, '2', '40');
INSERT INTO `pms_menu_role` VALUES ('1045', null, null, null, null, null, null, null, '2', '42');
INSERT INTO `pms_menu_role` VALUES ('1046', null, null, null, null, null, null, null, '2', '43');
INSERT INTO `pms_menu_role` VALUES ('1047', null, null, null, null, null, null, null, '2', '50');
INSERT INTO `pms_menu_role` VALUES ('1048', null, null, null, null, null, null, null, '2', '52');
INSERT INTO `pms_menu_role` VALUES ('1049', null, null, null, null, null, null, null, '2', '60');
INSERT INTO `pms_menu_role` VALUES ('1050', null, null, null, null, null, null, null, '2', '62');
INSERT INTO `pms_menu_role` VALUES ('1051', null, null, null, null, null, null, null, '2', '63');
INSERT INTO `pms_menu_role` VALUES ('1052', null, null, null, null, null, null, null, '2', '64');

-- ----------------------------
-- Table structure for `pms_operator`
-- ----------------------------
DROP TABLE IF EXISTS `pms_operator`;
CREATE TABLE `pms_operator` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '??',
  `version` bigint(20) NOT NULL,
  `creater` varchar(50) NOT NULL COMMENT '???',
  `create_time` datetime NOT NULL COMMENT '????',
  `editor` varchar(50) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '????',
  `status` varchar(20) NOT NULL,
  `remark` varchar(300) DEFAULT NULL,
  `real_name` varchar(50) NOT NULL,
  `mobile_no` varchar(15) NOT NULL,
  `login_name` varchar(50) NOT NULL,
  `login_pwd` varchar(256) NOT NULL,
  `type` varchar(20) NOT NULL,
  `salt` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ak_key_2` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='????';

-- ----------------------------
-- Records of pms_operator
-- ----------------------------
INSERT INTO `pms_operator` VALUES ('1', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '?????', '?????', '18620936193', 'admin', 'd3c59d25033dbf980d29554025c23a75', 'ADMIN', '8d78869f470951332959580424d4bf4f');
INSERT INTO `pms_operator` VALUES ('2', '0', 'roncoo', '2016-06-03 11:07:43', 'guest', '2016-06-03 11:07:43', 'ACTIVE', '??', '??', '18926215592', 'guest', '3f0dbf580ee39ec03b632cb33935a363', 'USER', '183d9f2f0f2ce760e98427a5603d1c73');

-- ----------------------------
-- Table structure for `pms_operator_log`
-- ----------------------------
DROP TABLE IF EXISTS `pms_operator_log`;
CREATE TABLE `pms_operator_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '??',
  `version` bigint(20) NOT NULL,
  `creater` varchar(50) NOT NULL COMMENT '???',
  `create_time` datetime NOT NULL COMMENT '????',
  `editor` varchar(50) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '????',
  `status` varchar(20) NOT NULL,
  `remark` varchar(300) DEFAULT NULL,
  `operator_id` bigint(20) NOT NULL,
  `operator_name` varchar(50) NOT NULL,
  `operate_type` varchar(50) NOT NULL COMMENT '?????1:???2:???3:???4:???',
  `ip` varchar(100) NOT NULL,
  `content` varchar(3000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='??_????????';

-- ----------------------------
-- Records of pms_operator_log
-- ----------------------------

-- ----------------------------
-- Table structure for `pms_permission`
-- ----------------------------
DROP TABLE IF EXISTS `pms_permission`;
CREATE TABLE `pms_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '??',
  `version` bigint(20) NOT NULL,
  `creater` varchar(50) NOT NULL COMMENT '???',
  `create_time` datetime NOT NULL COMMENT '????',
  `editor` varchar(50) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '????',
  `status` varchar(20) NOT NULL,
  `remark` varchar(300) DEFAULT NULL,
  `permission_name` varchar(100) NOT NULL,
  `permission` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ak_key_2` (`permission`),
  KEY `ak_key_3` (`permission_name`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8 COMMENT='???';

-- ----------------------------
-- Records of pms_permission
-- ----------------------------
INSERT INTO `pms_permission` VALUES ('1', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'pms:menu:view');
INSERT INTO `pms_permission` VALUES ('2', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'pms:menu:add');
INSERT INTO `pms_permission` VALUES ('3', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'pms:menu:edit');
INSERT INTO `pms_permission` VALUES ('4', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'pms:menu:delete');
INSERT INTO `pms_permission` VALUES ('11', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'pms:permission:view');
INSERT INTO `pms_permission` VALUES ('12', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'pms:permission:add');
INSERT INTO `pms_permission` VALUES ('13', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'pms:permission:edit');
INSERT INTO `pms_permission` VALUES ('14', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'pms:permission:delete');
INSERT INTO `pms_permission` VALUES ('21', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'pms:role:view');
INSERT INTO `pms_permission` VALUES ('22', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'pms:role:add');
INSERT INTO `pms_permission` VALUES ('23', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'pms:role:edit');
INSERT INTO `pms_permission` VALUES ('24', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'pms:role:delete');
INSERT INTO `pms_permission` VALUES ('25', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-????', '????-??-????', 'pms:role:assignpermission');
INSERT INTO `pms_permission` VALUES ('31', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-???-??', '????-???-??', 'pms:operator:view');
INSERT INTO `pms_permission` VALUES ('32', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-???-??', '????-???-??', 'pms:operator:add');
INSERT INTO `pms_permission` VALUES ('33', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-???-??', '????-???-??', 'pms:operator:edit');
INSERT INTO `pms_permission` VALUES ('34', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-???-?????', '????-???-?????', 'pms:operator:changestatus');
INSERT INTO `pms_permission` VALUES ('35', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-???-????', '????-???-????', 'pms:operator:resetpwd');
INSERT INTO `pms_permission` VALUES ('51', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'account:accountInfo:view');
INSERT INTO `pms_permission` VALUES ('52', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'account:accountInfo:add');
INSERT INTO `pms_permission` VALUES ('53', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'account:accountInfo:edit');
INSERT INTO `pms_permission` VALUES ('54', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'account:accountInfo:delete');
INSERT INTO `pms_permission` VALUES ('61', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'account:accountHistory:view');
INSERT INTO `pms_permission` VALUES ('71', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'user:userInfo:view');
INSERT INTO `pms_permission` VALUES ('72', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'user:userInfo:add');
INSERT INTO `pms_permission` VALUES ('73', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'user:userInfo:edit');
INSERT INTO `pms_permission` VALUES ('74', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'user:userInfo:delete');
INSERT INTO `pms_permission` VALUES ('81', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'pay:product:view');
INSERT INTO `pms_permission` VALUES ('82', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'pay:product:add');
INSERT INTO `pms_permission` VALUES ('83', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'pay:product:edit');
INSERT INTO `pms_permission` VALUES ('84', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'pay:product:delete');
INSERT INTO `pms_permission` VALUES ('85', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'pay:way:view');
INSERT INTO `pms_permission` VALUES ('86', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'pay:way:add');
INSERT INTO `pms_permission` VALUES ('87', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'pay:way:edit');
INSERT INTO `pms_permission` VALUES ('88', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'pay:way:delete');
INSERT INTO `pms_permission` VALUES ('91', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'pay:config:view');
INSERT INTO `pms_permission` VALUES ('92', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'pay:config:add');
INSERT INTO `pms_permission` VALUES ('93', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'pay:config:edit');
INSERT INTO `pms_permission` VALUES ('94', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-????-??', '????-????-??', 'pay:config:delete');
INSERT INTO `pms_permission` VALUES ('101', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'trade:order:view');
INSERT INTO `pms_permission` VALUES ('102', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'trade:order:add');
INSERT INTO `pms_permission` VALUES ('103', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'trade:order:edit');
INSERT INTO `pms_permission` VALUES ('104', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'trade:order:delete');
INSERT INTO `pms_permission` VALUES ('111', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'trade:record:view');
INSERT INTO `pms_permission` VALUES ('112', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'trade:record:add');
INSERT INTO `pms_permission` VALUES ('113', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'trade:record:edit');
INSERT INTO `pms_permission` VALUES ('114', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'trade:record:delete');
INSERT INTO `pms_permission` VALUES ('121', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'sett:record:view');
INSERT INTO `pms_permission` VALUES ('122', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'sett:record:add');
INSERT INTO `pms_permission` VALUES ('123', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'sett:record:edit');
INSERT INTO `pms_permission` VALUES ('124', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'sett:record:delete');
INSERT INTO `pms_permission` VALUES ('131', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'recon:mistake:view');
INSERT INTO `pms_permission` VALUES ('132', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'recon:mistake:add');
INSERT INTO `pms_permission` VALUES ('133', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'recon:mistake:edit');
INSERT INTO `pms_permission` VALUES ('134', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'recon:mistake:delete');
INSERT INTO `pms_permission` VALUES ('141', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'recon:batch:view');
INSERT INTO `pms_permission` VALUES ('142', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'recon:batch:add');
INSERT INTO `pms_permission` VALUES ('143', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'recon:batch:edit');
INSERT INTO `pms_permission` VALUES ('144', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-??-??', '????-??-??', 'recon:batch:delete');
INSERT INTO `pms_permission` VALUES ('151', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-???-??', '????-???-??', 'recon:scratchPool:view');
INSERT INTO `pms_permission` VALUES ('152', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-???-??', '????-???-??', 'recon:scratchPool:add');
INSERT INTO `pms_permission` VALUES ('153', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-???-??', '????-???-??', 'recon:scratchPool:edit');
INSERT INTO `pms_permission` VALUES ('154', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '????-???-??', '????-???-??', 'recon:scratchPool:delete');

-- ----------------------------
-- Table structure for `pms_role`
-- ----------------------------
DROP TABLE IF EXISTS `pms_role`;
CREATE TABLE `pms_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '??',
  `version` bigint(20) DEFAULT NULL,
  `creater` varchar(50) DEFAULT NULL COMMENT '???',
  `create_time` datetime DEFAULT NULL COMMENT '????',
  `editor` varchar(50) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '????',
  `status` varchar(20) DEFAULT NULL,
  `remark` varchar(300) DEFAULT NULL,
  `role_code` varchar(20) NOT NULL COMMENT '?????1:????????0:????????',
  `role_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ak_key_2` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='???';

-- ----------------------------
-- Records of pms_role
-- ----------------------------
INSERT INTO `pms_role` VALUES ('1', '0', 'roncoo', '2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43', 'ACTIVE', '???????', 'admin', '???????');
INSERT INTO `pms_role` VALUES ('2', '0', 'roncoo', '2016-06-03 11:07:43', 'guest', '2016-06-03 11:07:43', 'ACTIVE', '????', 'guest', '????');

-- ----------------------------
-- Table structure for `pms_role_operator`
-- ----------------------------
DROP TABLE IF EXISTS `pms_role_operator`;
CREATE TABLE `pms_role_operator` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '??',
  `version` bigint(20) NOT NULL,
  `creater` varchar(50) NOT NULL COMMENT '???',
  `create_time` datetime NOT NULL COMMENT '????',
  `editor` varchar(50) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '????',
  `status` varchar(20) NOT NULL,
  `remark` varchar(300) DEFAULT NULL,
  `role_id` bigint(20) NOT NULL,
  `operator_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ak_key_2` (`role_id`,`operator_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='?????????';

-- ----------------------------
-- Records of pms_role_operator
-- ----------------------------
INSERT INTO `pms_role_operator` VALUES ('1', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '1', '1');
INSERT INTO `pms_role_operator` VALUES ('2', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '1');
INSERT INTO `pms_role_operator` VALUES ('3', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '2');

-- ----------------------------
-- Table structure for `pms_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `pms_role_permission`;
CREATE TABLE `pms_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '??',
  `version` bigint(20) DEFAULT NULL,
  `creater` varchar(50) DEFAULT NULL COMMENT '???',
  `create_time` datetime DEFAULT NULL COMMENT '????',
  `editor` varchar(50) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '????',
  `status` varchar(20) DEFAULT NULL,
  `remark` varchar(300) DEFAULT NULL,
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ak_key_2` (`role_id`,`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1080 DEFAULT CHARSET=utf8 COMMENT='????????';

-- ----------------------------
-- Records of pms_role_permission
-- ----------------------------
INSERT INTO `pms_role_permission` VALUES ('1000', null, null, null, null, null, null, null, '1', '61');
INSERT INTO `pms_role_permission` VALUES ('1001', null, null, null, null, null, null, null, '1', '52');
INSERT INTO `pms_role_permission` VALUES ('1002', null, null, null, null, null, null, null, '1', '54');
INSERT INTO `pms_role_permission` VALUES ('1003', null, null, null, null, null, null, null, '1', '53');
INSERT INTO `pms_role_permission` VALUES ('1004', null, null, null, null, null, null, null, '1', '51');
INSERT INTO `pms_role_permission` VALUES ('1005', null, null, null, null, null, null, null, '1', '92');
INSERT INTO `pms_role_permission` VALUES ('1006', null, null, null, null, null, null, null, '1', '94');
INSERT INTO `pms_role_permission` VALUES ('1007', null, null, null, null, null, null, null, '1', '93');
INSERT INTO `pms_role_permission` VALUES ('1008', null, null, null, null, null, null, null, '1', '91');
INSERT INTO `pms_role_permission` VALUES ('1009', null, null, null, null, null, null, null, '1', '82');
INSERT INTO `pms_role_permission` VALUES ('1010', null, null, null, null, null, null, null, '1', '84');
INSERT INTO `pms_role_permission` VALUES ('1011', null, null, null, null, null, null, null, '1', '83');
INSERT INTO `pms_role_permission` VALUES ('1012', null, null, null, null, null, null, null, '1', '81');
INSERT INTO `pms_role_permission` VALUES ('1013', null, null, null, null, null, null, null, '1', '86');
INSERT INTO `pms_role_permission` VALUES ('1014', null, null, null, null, null, null, null, '1', '88');
INSERT INTO `pms_role_permission` VALUES ('1015', null, null, null, null, null, null, null, '1', '87');
INSERT INTO `pms_role_permission` VALUES ('1016', null, null, null, null, null, null, null, '1', '85');
INSERT INTO `pms_role_permission` VALUES ('1017', null, null, null, null, null, null, null, '1', '2');
INSERT INTO `pms_role_permission` VALUES ('1018', null, null, null, null, null, null, null, '1', '4');
INSERT INTO `pms_role_permission` VALUES ('1019', null, null, null, null, null, null, null, '1', '3');
INSERT INTO `pms_role_permission` VALUES ('1020', null, null, null, null, null, null, null, '1', '1');
INSERT INTO `pms_role_permission` VALUES ('1021', null, null, null, null, null, null, null, '1', '32');
INSERT INTO `pms_role_permission` VALUES ('1022', null, null, null, null, null, null, null, '1', '34');
INSERT INTO `pms_role_permission` VALUES ('1023', null, null, null, null, null, null, null, '1', '33');
INSERT INTO `pms_role_permission` VALUES ('1024', null, null, null, null, null, null, null, '1', '35');
INSERT INTO `pms_role_permission` VALUES ('1025', null, null, null, null, null, null, null, '1', '31');
INSERT INTO `pms_role_permission` VALUES ('1026', null, null, null, null, null, null, null, '1', '12');
INSERT INTO `pms_role_permission` VALUES ('1027', null, null, null, null, null, null, null, '1', '14');
INSERT INTO `pms_role_permission` VALUES ('1028', null, null, null, null, null, null, null, '1', '13');
INSERT INTO `pms_role_permission` VALUES ('1029', null, null, null, null, null, null, null, '1', '11');
INSERT INTO `pms_role_permission` VALUES ('1030', null, null, null, null, null, null, null, '1', '22');
INSERT INTO `pms_role_permission` VALUES ('1031', null, null, null, null, null, null, null, '1', '25');
INSERT INTO `pms_role_permission` VALUES ('1032', null, null, null, null, null, null, null, '1', '24');
INSERT INTO `pms_role_permission` VALUES ('1033', null, null, null, null, null, null, null, '1', '23');
INSERT INTO `pms_role_permission` VALUES ('1034', null, null, null, null, null, null, null, '1', '21');
INSERT INTO `pms_role_permission` VALUES ('1035', null, null, null, null, null, null, null, '1', '142');
INSERT INTO `pms_role_permission` VALUES ('1036', null, null, null, null, null, null, null, '1', '144');
INSERT INTO `pms_role_permission` VALUES ('1037', null, null, null, null, null, null, null, '1', '143');
INSERT INTO `pms_role_permission` VALUES ('1038', null, null, null, null, null, null, null, '1', '141');
INSERT INTO `pms_role_permission` VALUES ('1039', null, null, null, null, null, null, null, '1', '132');
INSERT INTO `pms_role_permission` VALUES ('1040', null, null, null, null, null, null, null, '1', '134');
INSERT INTO `pms_role_permission` VALUES ('1041', null, null, null, null, null, null, null, '1', '133');
INSERT INTO `pms_role_permission` VALUES ('1042', null, null, null, null, null, null, null, '1', '131');
INSERT INTO `pms_role_permission` VALUES ('1043', null, null, null, null, null, null, null, '1', '152');
INSERT INTO `pms_role_permission` VALUES ('1044', null, null, null, null, null, null, null, '1', '154');
INSERT INTO `pms_role_permission` VALUES ('1045', null, null, null, null, null, null, null, '1', '153');
INSERT INTO `pms_role_permission` VALUES ('1046', null, null, null, null, null, null, null, '1', '151');
INSERT INTO `pms_role_permission` VALUES ('1047', null, null, null, null, null, null, null, '1', '122');
INSERT INTO `pms_role_permission` VALUES ('1048', null, null, null, null, null, null, null, '1', '124');
INSERT INTO `pms_role_permission` VALUES ('1049', null, null, null, null, null, null, null, '1', '123');
INSERT INTO `pms_role_permission` VALUES ('1050', null, null, null, null, null, null, null, '1', '121');
INSERT INTO `pms_role_permission` VALUES ('1051', null, null, null, null, null, null, null, '1', '102');
INSERT INTO `pms_role_permission` VALUES ('1052', null, null, null, null, null, null, null, '1', '104');
INSERT INTO `pms_role_permission` VALUES ('1053', null, null, null, null, null, null, null, '1', '103');
INSERT INTO `pms_role_permission` VALUES ('1054', null, null, null, null, null, null, null, '1', '101');
INSERT INTO `pms_role_permission` VALUES ('1055', null, null, null, null, null, null, null, '1', '112');
INSERT INTO `pms_role_permission` VALUES ('1056', null, null, null, null, null, null, null, '1', '114');
INSERT INTO `pms_role_permission` VALUES ('1057', null, null, null, null, null, null, null, '1', '113');
INSERT INTO `pms_role_permission` VALUES ('1058', null, null, null, null, null, null, null, '1', '111');
INSERT INTO `pms_role_permission` VALUES ('1059', null, null, null, null, null, null, null, '1', '72');
INSERT INTO `pms_role_permission` VALUES ('1060', null, null, null, null, null, null, null, '1', '74');
INSERT INTO `pms_role_permission` VALUES ('1061', null, null, null, null, null, null, null, '1', '73');
INSERT INTO `pms_role_permission` VALUES ('1062', null, null, null, null, null, null, null, '1', '71');
INSERT INTO `pms_role_permission` VALUES ('1063', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '1');
INSERT INTO `pms_role_permission` VALUES ('1064', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '11');
INSERT INTO `pms_role_permission` VALUES ('1065', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '21');
INSERT INTO `pms_role_permission` VALUES ('1066', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '31');
INSERT INTO `pms_role_permission` VALUES ('1067', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '41');
INSERT INTO `pms_role_permission` VALUES ('1068', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '51');
INSERT INTO `pms_role_permission` VALUES ('1069', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '61');
INSERT INTO `pms_role_permission` VALUES ('1070', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '71');
INSERT INTO `pms_role_permission` VALUES ('1071', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '81');
INSERT INTO `pms_role_permission` VALUES ('1072', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '85');
INSERT INTO `pms_role_permission` VALUES ('1073', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '91');
INSERT INTO `pms_role_permission` VALUES ('1074', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '101');
INSERT INTO `pms_role_permission` VALUES ('1075', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '111');
INSERT INTO `pms_role_permission` VALUES ('1076', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '121');
INSERT INTO `pms_role_permission` VALUES ('1077', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '131');
INSERT INTO `pms_role_permission` VALUES ('1078', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '141');
INSERT INTO `pms_role_permission` VALUES ('1079', '0', 'roncoo', '2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43', 'ACTIVE', '', '2', '151');

-- ----------------------------
-- Table structure for `rp_account`
-- ----------------------------
DROP TABLE IF EXISTS `rp_account`;
CREATE TABLE `rp_account` (
  `id` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `edit_time` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `account_no` varchar(50) NOT NULL,
  `balance` decimal(20,6) NOT NULL,
  `unbalance` decimal(20,6) NOT NULL,
  `security_money` decimal(20,6) NOT NULL,
  `status` varchar(36) NOT NULL,
  `total_income` decimal(20,6) NOT NULL,
  `total_expend` decimal(20,6) NOT NULL,
  `today_income` decimal(20,6) NOT NULL,
  `today_expend` decimal(20,6) NOT NULL,
  `account_type` varchar(50) NOT NULL,
  `sett_amount` decimal(20,6) NOT NULL,
  `user_no` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='?????';

-- ----------------------------
-- Records of rp_account
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_account_check_batch`
-- ----------------------------
DROP TABLE IF EXISTS `rp_account_check_batch`;
CREATE TABLE `rp_account_check_batch` (
  `id` varchar(50) NOT NULL,
  `version` int(10) unsigned NOT NULL,
  `create_time` datetime NOT NULL,
  `editor` varchar(100) DEFAULT NULL COMMENT '???',
  `creater` varchar(100) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '??????',
  `status` varchar(30) NOT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `batch_no` varchar(30) NOT NULL,
  `bill_date` date NOT NULL,
  `bill_type` varchar(30) DEFAULT NULL,
  `handle_status` varchar(10) DEFAULT NULL,
  `bank_type` varchar(30) DEFAULT NULL,
  `mistake_count` int(8) DEFAULT NULL,
  `unhandle_mistake_count` int(8) DEFAULT NULL,
  `trade_count` int(8) DEFAULT NULL,
  `bank_trade_count` int(8) DEFAULT NULL,
  `trade_amount` decimal(20,6) DEFAULT NULL,
  `bank_trade_amount` decimal(20,6) DEFAULT NULL,
  `refund_amount` decimal(20,6) DEFAULT NULL,
  `bank_refund_amount` decimal(20,6) DEFAULT NULL,
  `bank_fee` decimal(20,6) DEFAULT NULL,
  `org_check_file_path` varchar(500) DEFAULT NULL,
  `release_check_file_path` varchar(500) DEFAULT NULL,
  `release_status` varchar(15) DEFAULT NULL,
  `check_fail_msg` varchar(300) DEFAULT NULL,
  `bank_err_msg` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='????? rp_account_check_batch';

-- ----------------------------
-- Records of rp_account_check_batch
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_account_check_mistake`
-- ----------------------------
DROP TABLE IF EXISTS `rp_account_check_mistake`;
CREATE TABLE `rp_account_check_mistake` (
  `id` varchar(50) NOT NULL,
  `version` int(10) unsigned NOT NULL,
  `create_time` datetime NOT NULL,
  `editor` varchar(100) DEFAULT NULL COMMENT '???',
  `creater` varchar(100) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '??????',
  `status` varchar(30) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `account_check_batch_no` varchar(50) NOT NULL,
  `bill_date` date NOT NULL,
  `bank_type` varchar(30) NOT NULL,
  `order_time` datetime DEFAULT NULL,
  `merchant_name` varchar(100) DEFAULT NULL,
  `merchant_no` varchar(50) DEFAULT NULL,
  `order_no` varchar(40) DEFAULT NULL,
  `trade_time` datetime DEFAULT NULL,
  `trx_no` varchar(20) DEFAULT NULL,
  `order_amount` decimal(20,6) DEFAULT NULL,
  `refund_amount` decimal(20,6) DEFAULT NULL,
  `trade_status` varchar(30) DEFAULT NULL,
  `fee` decimal(20,6) DEFAULT NULL,
  `bank_trade_time` datetime DEFAULT NULL,
  `bank_order_no` varchar(40) DEFAULT NULL,
  `bank_trx_no` varchar(40) DEFAULT NULL,
  `bank_trade_status` varchar(30) DEFAULT NULL,
  `bank_amount` decimal(20,6) DEFAULT NULL,
  `bank_refund_amount` decimal(20,6) DEFAULT NULL,
  `bank_fee` decimal(20,6) DEFAULT NULL,
  `err_type` varchar(30) NOT NULL,
  `handle_status` varchar(10) NOT NULL,
  `handle_value` varchar(1000) DEFAULT NULL,
  `handle_remark` varchar(1000) DEFAULT NULL,
  `operator_name` varchar(100) DEFAULT NULL,
  `operator_account_no` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='????? rp_account_check_mistake';

-- ----------------------------
-- Records of rp_account_check_mistake
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_account_check_mistake_scratch_pool`
-- ----------------------------
DROP TABLE IF EXISTS `rp_account_check_mistake_scratch_pool`;
CREATE TABLE `rp_account_check_mistake_scratch_pool` (
  `id` varchar(50) NOT NULL,
  `version` int(10) unsigned NOT NULL,
  `create_time` datetime NOT NULL,
  `editor` varchar(100) DEFAULT NULL COMMENT '???',
  `creater` varchar(100) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '??????',
  `product_name` varchar(50) DEFAULT NULL COMMENT '????',
  `merchant_order_no` varchar(30) NOT NULL COMMENT '?????',
  `trx_no` char(20) NOT NULL COMMENT '?????',
  `bank_order_no` char(20) DEFAULT NULL COMMENT '?????',
  `bank_trx_no` varchar(30) DEFAULT NULL COMMENT '?????',
  `order_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '????',
  `plat_income` decimal(20,6) DEFAULT NULL COMMENT '????',
  `fee_rate` decimal(20,6) DEFAULT NULL COMMENT '??',
  `plat_cost` decimal(20,6) DEFAULT NULL COMMENT '????',
  `plat_profit` decimal(20,6) DEFAULT NULL COMMENT '????',
  `status` varchar(30) DEFAULT NULL COMMENT '??(????:paymentrecordstatusenum)',
  `pay_way_code` varchar(50) DEFAULT NULL COMMENT '??????',
  `pay_way_name` varchar(100) DEFAULT NULL COMMENT '??????',
  `pay_success_time` datetime DEFAULT NULL COMMENT '??????',
  `complete_time` datetime DEFAULT NULL COMMENT '????',
  `is_refund` varchar(30) DEFAULT '101' COMMENT '????(100:?,101:?,????:101)',
  `refund_times` smallint(6) DEFAULT '0' COMMENT '????(????:0)',
  `success_refund_amount` decimal(20,6) DEFAULT NULL COMMENT '???????',
  `remark` varchar(500) DEFAULT NULL COMMENT '??',
  `batch_no` varchar(50) DEFAULT NULL,
  `bill_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='?????';

-- ----------------------------
-- Records of rp_account_check_mistake_scratch_pool
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_account_history`
-- ----------------------------
DROP TABLE IF EXISTS `rp_account_history`;
CREATE TABLE `rp_account_history` (
  `id` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `edit_time` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `account_no` varchar(50) NOT NULL,
  `amount` decimal(20,6) NOT NULL,
  `balance` decimal(20,6) NOT NULL,
  `fund_direction` varchar(36) NOT NULL,
  `is_allow_sett` varchar(36) NOT NULL,
  `is_complete_sett` varchar(36) NOT NULL,
  `request_no` varchar(36) NOT NULL,
  `bank_trx_no` varchar(30) DEFAULT NULL,
  `trx_type` varchar(36) NOT NULL,
  `risk_day` int(11) DEFAULT NULL,
  `user_no` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='???????';

-- ----------------------------
-- Records of rp_account_history
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_notify_record`
-- ----------------------------
DROP TABLE IF EXISTS `rp_notify_record`;
CREATE TABLE `rp_notify_record` (
  `id` varchar(50) NOT NULL,
  `version` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `editor` varchar(100) DEFAULT NULL COMMENT '???',
  `creater` varchar(100) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '??????',
  `notify_times` int(11) NOT NULL,
  `limit_notify_times` int(11) NOT NULL,
  `url` varchar(2000) NOT NULL,
  `merchant_order_no` varchar(50) NOT NULL,
  `merchant_no` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL COMMENT '100:?? 101:??',
  `notify_type` varchar(30) DEFAULT NULL COMMENT '????',
  PRIMARY KEY (`id`),
  KEY `ak_key_2` (`merchant_order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='????? rp_notify_record';

-- ----------------------------
-- Records of rp_notify_record
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_notify_record_log`
-- ----------------------------
DROP TABLE IF EXISTS `rp_notify_record_log`;
CREATE TABLE `rp_notify_record_log` (
  `id` varchar(50) NOT NULL,
  `version` int(11) NOT NULL,
  `editor` varchar(100) DEFAULT NULL COMMENT '???',
  `creater` varchar(100) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '??????',
  `create_time` datetime NOT NULL,
  `notify_id` varchar(50) NOT NULL,
  `request` varchar(2000) NOT NULL,
  `response` varchar(2000) NOT NULL,
  `merchant_no` varchar(50) NOT NULL,
  `merchant_order_no` varchar(50) NOT NULL COMMENT '?????',
  `http_status` varchar(50) NOT NULL COMMENT 'http??',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='??????? rp_notify_record_log';

-- ----------------------------
-- Records of rp_notify_record_log
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_pay_product`
-- ----------------------------
DROP TABLE IF EXISTS `rp_pay_product`;
CREATE TABLE `rp_pay_product` (
  `id` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `edit_time` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `status` varchar(36) NOT NULL,
  `product_code` varchar(50) NOT NULL COMMENT '??????',
  `product_name` varchar(200) NOT NULL COMMENT '??????',
  `audit_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='?????';

-- ----------------------------
-- Records of rp_pay_product
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_pay_way`
-- ----------------------------
DROP TABLE IF EXISTS `rp_pay_way`;
CREATE TABLE `rp_pay_way` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT 'version',
  `create_time` datetime NOT NULL COMMENT '????',
  `edit_time` datetime DEFAULT NULL COMMENT '????',
  `pay_way_code` varchar(50) NOT NULL COMMENT '??????',
  `pay_way_name` varchar(100) NOT NULL COMMENT '??????',
  `pay_type_code` varchar(50) NOT NULL COMMENT '??????',
  `pay_type_name` varchar(100) NOT NULL COMMENT '??????',
  `pay_product_code` varchar(50) DEFAULT NULL COMMENT '??????',
  `status` varchar(36) NOT NULL COMMENT '??(100:????,101?????)',
  `sorts` int(11) DEFAULT '1000' COMMENT '??(????,???1000)',
  `pay_rate` double NOT NULL COMMENT '??????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='????';

-- ----------------------------
-- Records of rp_pay_way
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_refund_record`
-- ----------------------------
DROP TABLE IF EXISTS `rp_refund_record`;
CREATE TABLE `rp_refund_record` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `version` int(11) NOT NULL COMMENT '???',
  `create_time` datetime DEFAULT NULL COMMENT '????',
  `editor` varchar(100) DEFAULT NULL COMMENT '???',
  `creater` varchar(100) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '??????',
  `org_merchant_order_no` varchar(50) DEFAULT NULL COMMENT '??????',
  `org_trx_no` varchar(50) DEFAULT NULL COMMENT '??????',
  `org_bank_order_no` varchar(50) DEFAULT NULL COMMENT '??????',
  `org_bank_trx_no` varchar(50) DEFAULT NULL COMMENT '??????',
  `merchant_name` varchar(100) DEFAULT NULL COMMENT '????',
  `merchant_no` varchar(100) NOT NULL COMMENT '????',
  `org_product_name` varchar(60) DEFAULT NULL COMMENT '?????',
  `org_biz_type` varchar(30) DEFAULT NULL COMMENT '?????',
  `org_payment_type` varchar(30) DEFAULT NULL COMMENT '???????',
  `refund_amount` decimal(20,6) DEFAULT NULL COMMENT '??????',
  `refund_trx_no` varchar(50) NOT NULL COMMENT '?????',
  `refund_order_no` varchar(50) NOT NULL COMMENT '?????',
  `bank_refund_order_no` varchar(50) DEFAULT NULL COMMENT '???????',
  `bank_refund_trx_no` varchar(30) DEFAULT NULL COMMENT '???????',
  `result_notify_url` varchar(500) DEFAULT NULL COMMENT '??????url',
  `refund_status` varchar(30) DEFAULT NULL COMMENT '????',
  `refund_from` varchar(30) DEFAULT NULL COMMENT '??????????',
  `refund_way` varchar(30) DEFAULT NULL COMMENT '????',
  `refund_request_time` datetime DEFAULT NULL COMMENT '??????',
  `refund_success_time` datetime DEFAULT NULL COMMENT ' ??????',
  `refund_complete_time` datetime DEFAULT NULL COMMENT '??????',
  `request_apply_user_id` varchar(50) DEFAULT NULL COMMENT '????,??????',
  `request_apply_user_name` varchar(90) DEFAULT NULL COMMENT '????,?????',
  `refund_reason` varchar(500) DEFAULT NULL COMMENT '????',
  `remark` varchar(3000) DEFAULT NULL COMMENT '??',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ak_key_2` (`refund_trx_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='?????';

-- ----------------------------
-- Records of rp_refund_record
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_sett_daily_collect`
-- ----------------------------
DROP TABLE IF EXISTS `rp_sett_daily_collect`;
CREATE TABLE `rp_sett_daily_collect` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '???',
  `create_time` datetime NOT NULL COMMENT '????',
  `edit_time` datetime NOT NULL COMMENT '????',
  `account_no` varchar(20) NOT NULL COMMENT '????',
  `user_name` varchar(200) DEFAULT NULL COMMENT '????',
  `collect_date` date NOT NULL COMMENT '????',
  `collect_type` varchar(50) NOT NULL COMMENT '????(????:settdailycollecttypeenum)',
  `total_amount` decimal(24,10) NOT NULL COMMENT '?????',
  `total_count` int(11) NOT NULL COMMENT '?????',
  `sett_status` varchar(50) NOT NULL COMMENT '????(????:settdailycollectstatusenum)',
  `remark` varchar(300) DEFAULT NULL COMMENT '??',
  `risk_day` int(11) DEFAULT NULL COMMENT '???????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='???????';

-- ----------------------------
-- Records of rp_sett_daily_collect
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_sett_record`
-- ----------------------------
DROP TABLE IF EXISTS `rp_sett_record`;
CREATE TABLE `rp_sett_record` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '???',
  `create_time` datetime NOT NULL COMMENT '????',
  `edit_time` datetime NOT NULL COMMENT '????',
  `sett_mode` varchar(50) DEFAULT NULL COMMENT '??????(??settmodetypeenum)',
  `account_no` varchar(20) NOT NULL COMMENT '????',
  `user_no` varchar(20) DEFAULT NULL COMMENT '????',
  `user_name` varchar(200) DEFAULT NULL COMMENT '????',
  `user_type` varchar(50) DEFAULT NULL COMMENT '????',
  `sett_date` date DEFAULT NULL COMMENT '????',
  `bank_code` varchar(20) DEFAULT NULL COMMENT '????',
  `bank_name` varchar(100) DEFAULT NULL COMMENT '????',
  `bank_account_name` varchar(60) DEFAULT NULL COMMENT '???',
  `bank_account_no` varchar(20) DEFAULT NULL COMMENT '????',
  `bank_account_type` varchar(50) DEFAULT NULL COMMENT '????',
  `country` varchar(200) DEFAULT NULL COMMENT '???????',
  `province` varchar(50) DEFAULT NULL COMMENT '???????',
  `city` varchar(50) DEFAULT NULL COMMENT '???????',
  `areas` varchar(50) DEFAULT NULL COMMENT '??????',
  `bank_account_address` varchar(300) DEFAULT NULL COMMENT '?????',
  `mobile_no` varchar(20) DEFAULT NULL COMMENT '??????',
  `sett_amount` decimal(24,10) DEFAULT NULL COMMENT '????',
  `sett_fee` decimal(16,6) DEFAULT NULL COMMENT '?????',
  `remit_amount` decimal(16,2) DEFAULT NULL COMMENT '??????',
  `sett_status` varchar(50) DEFAULT NULL COMMENT '????(????:settrecordstatusenum)',
  `remit_confirm_time` datetime DEFAULT NULL COMMENT '??????',
  `remark` varchar(200) DEFAULT NULL COMMENT '??',
  `remit_remark` varchar(200) DEFAULT NULL COMMENT '????',
  `operator_loginname` varchar(50) DEFAULT NULL COMMENT '??????',
  `operator_realname` varchar(50) DEFAULT NULL COMMENT '?????',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='????';

-- ----------------------------
-- Records of rp_sett_record
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_sett_record_annex`
-- ----------------------------
DROP TABLE IF EXISTS `rp_sett_record_annex`;
CREATE TABLE `rp_sett_record_annex` (
  `id` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `edit_time` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `is_delete` varchar(36) NOT NULL,
  `annex_name` varchar(200) DEFAULT NULL,
  `annex_address` varchar(500) NOT NULL,
  `settlement_id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rp_sett_record_annex
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_trade_payment_order`
-- ----------------------------
DROP TABLE IF EXISTS `rp_trade_payment_order`;
CREATE TABLE `rp_trade_payment_order` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '???',
  `create_time` datetime NOT NULL COMMENT '????',
  `editor` varchar(100) DEFAULT NULL COMMENT '???',
  `creater` varchar(100) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '??????',
  `status` varchar(50) DEFAULT NULL COMMENT '??(????:orderstatusenum)',
  `product_name` varchar(300) DEFAULT NULL COMMENT '????',
  `merchant_order_no` varchar(30) NOT NULL COMMENT '?????',
  `order_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '????',
  `order_from` varchar(30) DEFAULT NULL COMMENT '????',
  `merchant_name` varchar(100) DEFAULT NULL COMMENT '????',
  `merchant_no` varchar(100) NOT NULL COMMENT '????',
  `order_time` datetime DEFAULT NULL COMMENT '????',
  `order_date` date DEFAULT NULL COMMENT '????',
  `order_ip` varchar(50) DEFAULT NULL COMMENT '??ip(???ip,???????)',
  `order_referer_url` varchar(100) DEFAULT NULL COMMENT '??????????(??????)',
  `return_url` varchar(600) DEFAULT NULL COMMENT '??????url',
  `notify_url` varchar(600) DEFAULT NULL COMMENT '??????url',
  `cancel_reason` varchar(600) DEFAULT NULL COMMENT '??????',
  `order_period` smallint(6) DEFAULT NULL COMMENT '?????(????)',
  `expire_time` datetime DEFAULT NULL COMMENT '????',
  `pay_way_code` varchar(50) DEFAULT NULL COMMENT '??????',
  `pay_way_name` varchar(100) DEFAULT NULL COMMENT '??????',
  `remark` varchar(200) DEFAULT NULL COMMENT '????',
  `trx_type` varchar(30) DEFAULT NULL COMMENT '??????  ???????',
  `trx_no` varchar(50) DEFAULT NULL COMMENT '?????',
  `pay_type_code` varchar(50) DEFAULT NULL COMMENT '??????',
  `pay_type_name` varchar(100) DEFAULT NULL COMMENT '??????',
  `fund_into_type` varchar(30) DEFAULT NULL COMMENT '??????',
  `is_refund` varchar(30) DEFAULT '101' COMMENT '????(100:?,101:?,????:101)',
  `refund_times` int(11) DEFAULT '0' COMMENT '????(????:0)',
  `success_refund_amount` decimal(20,6) DEFAULT NULL COMMENT '???????',
  `field1` varchar(500) DEFAULT NULL,
  `field2` varchar(500) DEFAULT NULL,
  `field3` varchar(500) DEFAULT NULL,
  `field4` varchar(500) DEFAULT NULL,
  `field5` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ak_key_2` (`merchant_order_no`,`merchant_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='roncoo pay ???? ?????';

-- ----------------------------
-- Records of rp_trade_payment_order
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_trade_payment_record`
-- ----------------------------
DROP TABLE IF EXISTS `rp_trade_payment_record`;
CREATE TABLE `rp_trade_payment_record` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '???',
  `create_time` datetime DEFAULT NULL COMMENT '????',
  `status` varchar(30) DEFAULT NULL COMMENT '??(????:paymentrecordstatusenum)',
  `editor` varchar(100) DEFAULT NULL COMMENT '???',
  `creater` varchar(100) DEFAULT NULL COMMENT '???',
  `edit_time` datetime DEFAULT NULL COMMENT '??????',
  `product_name` varchar(50) DEFAULT NULL COMMENT '????',
  `merchant_order_no` varchar(50) NOT NULL COMMENT '?????',
  `trx_no` varchar(50) NOT NULL COMMENT '?????',
  `bank_order_no` varchar(50) DEFAULT NULL COMMENT '?????',
  `bank_trx_no` varchar(50) DEFAULT NULL COMMENT '?????',
  `merchant_name` varchar(300) DEFAULT NULL COMMENT '????',
  `merchant_no` varchar(50) NOT NULL COMMENT '????',
  `payer_user_no` varchar(50) DEFAULT NULL COMMENT '?????',
  `payer_name` varchar(60) DEFAULT NULL COMMENT '?????',
  `payer_pay_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '???????',
  `payer_fee` decimal(20,6) DEFAULT '0.000000' COMMENT '??????',
  `payer_account_type` varchar(30) DEFAULT NULL COMMENT '???????(????????:accounttypeenum)',
  `receiver_user_no` varchar(15) DEFAULT NULL COMMENT '?????',
  `receiver_name` varchar(60) DEFAULT NULL COMMENT '?????',
  `receiver_pay_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '???????',
  `receiver_fee` decimal(20,6) DEFAULT '0.000000' COMMENT '??????',
  `receiver_account_type` varchar(30) DEFAULT NULL COMMENT '???????(????????:accounttypeenum)',
  `order_ip` varchar(30) DEFAULT NULL COMMENT '??ip(???ip,??????)',
  `order_referer_url` varchar(100) DEFAULT NULL COMMENT '??????????(??????)',
  `order_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '????',
  `plat_income` decimal(20,6) DEFAULT NULL COMMENT '????',
  `fee_rate` decimal(20,6) DEFAULT NULL COMMENT '??',
  `plat_cost` decimal(20,6) DEFAULT NULL COMMENT '????',
  `plat_profit` decimal(20,6) DEFAULT NULL COMMENT '????',
  `return_url` varchar(600) DEFAULT NULL COMMENT '??????url',
  `notify_url` varchar(600) DEFAULT NULL COMMENT '??????url',
  `pay_way_code` varchar(50) DEFAULT NULL COMMENT '??????',
  `pay_way_name` varchar(100) DEFAULT NULL COMMENT '??????',
  `pay_success_time` datetime DEFAULT NULL COMMENT '??????',
  `complete_time` datetime DEFAULT NULL COMMENT '????',
  `is_refund` varchar(30) DEFAULT '101' COMMENT '????(100:?,101:?,????:101)',
  `refund_times` int(11) DEFAULT '0' COMMENT '????(????:0)',
  `success_refund_amount` decimal(20,6) DEFAULT NULL COMMENT '???????',
  `trx_type` varchar(30) DEFAULT NULL COMMENT '??????  ???????',
  `order_from` varchar(30) DEFAULT NULL COMMENT '????',
  `pay_type_code` varchar(50) DEFAULT NULL COMMENT '??????',
  `pay_type_name` varchar(100) DEFAULT NULL COMMENT '??????',
  `fund_into_type` varchar(30) DEFAULT NULL COMMENT '??????',
  `remark` varchar(3000) DEFAULT NULL COMMENT '??',
  `field1` varchar(500) DEFAULT NULL,
  `field2` varchar(500) DEFAULT NULL,
  `field3` varchar(500) DEFAULT NULL,
  `field4` varchar(500) DEFAULT NULL,
  `field5` varchar(500) DEFAULT NULL,
  `bank_return_msg` varchar(2000) DEFAULT NULL COMMENT '??????',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ak_key_2` (`trx_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='?????';

-- ----------------------------
-- Records of rp_trade_payment_record
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_user_bank_account`
-- ----------------------------
DROP TABLE IF EXISTS `rp_user_bank_account`;
CREATE TABLE `rp_user_bank_account` (
  `id` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `edit_time` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `status` varchar(36) NOT NULL,
  `user_no` varchar(50) NOT NULL,
  `bank_name` varchar(200) NOT NULL,
  `bank_code` varchar(50) NOT NULL,
  `bank_account_name` varchar(100) NOT NULL,
  `bank_account_no` varchar(36) NOT NULL,
  `card_type` varchar(36) NOT NULL,
  `card_no` varchar(36) NOT NULL,
  `mobile_no` varchar(50) NOT NULL,
  `is_default` varchar(36) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `areas` varchar(20) DEFAULT NULL,
  `street` varchar(300) DEFAULT NULL,
  `bank_account_type` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='???????';

-- ----------------------------
-- Records of rp_user_bank_account
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_user_info`
-- ----------------------------
DROP TABLE IF EXISTS `rp_user_info`;
CREATE TABLE `rp_user_info` (
  `id` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `status` varchar(36) NOT NULL,
  `user_no` varchar(50) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `account_no` varchar(50) NOT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `pay_pwd` varchar(50) DEFAULT '123456' COMMENT '????',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ak_key_2` (`account_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='?????????????';

-- ----------------------------
-- Records of rp_user_info
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_user_pay_config`
-- ----------------------------
DROP TABLE IF EXISTS `rp_user_pay_config`;
CREATE TABLE `rp_user_pay_config` (
  `id` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `edit_time` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `status` varchar(36) NOT NULL,
  `audit_status` varchar(45) DEFAULT NULL,
  `is_auto_sett` varchar(36) NOT NULL DEFAULT 'no',
  `product_code` varchar(50) NOT NULL COMMENT '??????',
  `product_name` varchar(200) NOT NULL COMMENT '??????',
  `user_no` varchar(50) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `risk_day` int(11) DEFAULT NULL,
  `pay_key` varchar(50) DEFAULT NULL,
  `fund_into_type` varchar(50) DEFAULT NULL,
  `pay_secret` varchar(50) DEFAULT NULL,
  `security_rating` varchar(20) DEFAULT 'MD5' COMMENT '????',
  `merchant_server_ip` varchar(200) DEFAULT NULL COMMENT '?????IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='?????';

-- ----------------------------
-- Records of rp_user_pay_config
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_user_pay_info`
-- ----------------------------
DROP TABLE IF EXISTS `rp_user_pay_info`;
CREATE TABLE `rp_user_pay_info` (
  `id_` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `edit_time` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `status` varchar(36) NOT NULL,
  `app_id` varchar(50) NOT NULL,
  `app_sectet` varchar(100) DEFAULT NULL,
  `merchant_id` varchar(50) DEFAULT NULL,
  `app_type` varchar(50) DEFAULT NULL,
  `user_no` varchar(50) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `partner_key` varchar(100) DEFAULT NULL,
  `pay_way_code` varchar(50) NOT NULL COMMENT '??????',
  `pay_way_name` varchar(100) NOT NULL COMMENT '??????',
  `offline_app_id` varchar(50) DEFAULT NULL,
  `rsa_private_key` varchar(100) DEFAULT NULL,
  `rsa_public_key` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='??????????????????';

-- ----------------------------
-- Records of rp_user_pay_info
-- ----------------------------

-- ----------------------------
-- Table structure for `seq_table`
-- ----------------------------
DROP TABLE IF EXISTS `seq_table`;
CREATE TABLE `seq_table` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `CURRENT_VALUE` bigint(20) NOT NULL DEFAULT '1000000002',
  `INCREMENT` smallint(6) NOT NULL DEFAULT '1',
  `REMARK` varchar(100) NOT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seq_table
-- ----------------------------
INSERT INTO `seq_table` VALUES ('ACCOUNT_NO_SEQ', '10000000', '1', '??--????');
INSERT INTO `seq_table` VALUES ('ACTIVITY_NO_SEQ', '10000006', '1', '??--????');
INSERT INTO `seq_table` VALUES ('BANK_ORDER_NO_SEQ', '10000000', '1', '??--?????');
INSERT INTO `seq_table` VALUES ('RECONCILIATION_BATCH_NO_SEQ', '10000000', '1', '??--???');
INSERT INTO `seq_table` VALUES ('TRX_NO_SEQ', '10000000', '1', '??--?????');
INSERT INTO `seq_table` VALUES ('USER_NO_SEQ', '10001113', '1', '??--????');
