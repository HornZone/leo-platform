/*
Navicat MySQL Data Transfer

Source Server         : bsp
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : rms

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2019-04-02 22:25:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `personal_calendar`
-- ----------------------------
DROP TABLE IF EXISTS `personal_calendar`;
CREATE TABLE `personal_calendar` (
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `details` varchar(1000) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `length` int(11) DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `background_color` varchar(100) DEFAULT NULL,
  `text_color` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `office_calendar_user_id_start_date` (`user_id`,`start_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of personal_calendar
-- ----------------------------

-- ----------------------------
-- Table structure for `personal_message`
-- ----------------------------
DROP TABLE IF EXISTS `personal_message`;
CREATE TABLE `personal_message` (
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sender_id` bigint(20) DEFAULT NULL,
  `receiver_id` bigint(20) DEFAULT NULL,
  `send_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `title` varchar(200) DEFAULT NULL,
  `sender_state` varchar(20) DEFAULT NULL,
  `sender_state_change_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `receiver_state` varchar(20) DEFAULT NULL,
  `receiver_state_change_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `type` varchar(20) DEFAULT NULL,
  `is_read` tinyint(1) DEFAULT NULL,
  `is_replied` tinyint(1) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_personal_message_sender_id_sender_state` (`sender_id`,`sender_state`),
  KEY `idx_personal_message_receiver_id_receiver_state` (`receiver_id`,`receiver_state`,`is_read`),
  KEY `idx_personal_sender_state_change_date` (`sender_state_change_date`),
  KEY `idx_personal_receiver_state_change_date` (`receiver_state_change_date`),
  KEY `idx_personal_parent_id` (`parent_id`),
  KEY `idx_personal_parent_ids` (`parent_ids`),
  KEY `idx_personal_message_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of personal_message
-- ----------------------------

-- ----------------------------
-- Table structure for `personal_message_content`
-- ----------------------------
DROP TABLE IF EXISTS `personal_message_content`;
CREATE TABLE `personal_message_content` (
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` bigint(20) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_personal_message_content_message_id` (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of personal_message_content
-- ----------------------------

-- ----------------------------
-- Table structure for `sequence`
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `name` varchar(50) NOT NULL,
  `current_value` int(11) NOT NULL,
  `increment` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence` VALUES ('auth', '0', '1');
INSERT INTO `sequence` VALUES ('calendar', '0', '1');
INSERT INTO `sequence` VALUES ('default_code_sequence', '12', '1');
INSERT INTO `sequence` VALUES ('default_id_sequence', '13', '1');
INSERT INTO `sequence` VALUES ('group', '0', '1');
INSERT INTO `sequence` VALUES ('grouprelation', '0', '1');
INSERT INTO `sequence` VALUES ('job', '0', '1');
INSERT INTO `sequence` VALUES ('message', '0', '1');
INSERT INTO `sequence` VALUES ('messagecontent', '0', '1');
INSERT INTO `sequence` VALUES ('organization', '0', '1');
INSERT INTO `sequence` VALUES ('permission', '0', '1');
INSERT INTO `sequence` VALUES ('resource', '0', '1');
INSERT INTO `sequence` VALUES ('role', '0', '1');
INSERT INTO `sequence` VALUES ('roleresourcepermission', '0', '1');
INSERT INTO `sequence` VALUES ('TestSeq', '11', '1');
INSERT INTO `sequence` VALUES ('user', '14', '1');
INSERT INTO `sequence` VALUES ('userlastonline', '0', '1');
INSERT INTO `sequence` VALUES ('useronline', '0', '1');
INSERT INTO `sequence` VALUES ('userorganizationjob', '0', '1');
INSERT INTO `sequence` VALUES ('userstatushistory', '0', '1');

-- ----------------------------
-- Table structure for `sys_auth`
-- ----------------------------
DROP TABLE IF EXISTS `sys_auth`;
CREATE TABLE `sys_auth` (
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organization_id` bigint(20) DEFAULT NULL,
  `job_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `role_ids` varchar(500) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_auth_organization` (`organization_id`),
  KEY `idx_sys_auth_job` (`job_id`),
  KEY `idx_sys_auth_user` (`user_id`),
  KEY `idx_sys_auth_group` (`group_id`),
  KEY `idx_sys_auth_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_auth
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_group`
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  `default_group` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_group_type` (`type`),
  KEY `idx_sys_group_show` (`is_show`),
  KEY `idx_sys_group_default_group` (`default_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_group
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_group_relation`
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_relation`;
CREATE TABLE `sys_group_relation` (
  `crudType` varchar(16) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(16) DEFAULT NULL,
  `creatorName` varchar(256) DEFAULT NULL,
  `lastUpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdator` varchar(16) DEFAULT NULL,
  `lastUpdatorName` varchar(256) DEFAULT NULL,
  `version` varchar(64) DEFAULT NULL,
  `optLock` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) DEFAULT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `start_user_id` bigint(20) DEFAULT NULL,
  `end_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_group_relation_group` (`group_id`),
  KEY `idx_sys_group_relation_organization` (`organization_id`),
  KEY `idx_sys_group_relation_user` (`user_id`),
  KEY `idx_sys_group_relation_start_user_id` (`start_user_id`),
  KEY `idx_sys_group_relation_end_user_id` (`end_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_group_relation
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_job`
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(200) DEFAULT '',
  `icon` varchar(200) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_job_nam` (`name`),
  KEY `idx_sys_job_parent_id` (`parent_id`),
  KEY `idx_sys_job_parent_ids_weight` (`parent_ids`,`weight`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_job
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_organization`
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(200) DEFAULT '',
  `icon` varchar(200) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_organization_name` (`name`),
  KEY `idx_sys_organization_type` (`type`),
  KEY `idx_sys_organization_parent_id` (`parent_id`),
  KEY `idx_sys_organization_parent_ids_weight` (`parent_ids`,`weight`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_organization
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `permission` varchar(100) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_permission_name` (`name`),
  KEY `idx_sys_permission_permission` (`permission`),
  KEY `idx_sys_permission_show` (`is_show`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `identity` varchar(100) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(200) DEFAULT '',
  `icon` varchar(200) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_resource_name` (`name`),
  KEY `idx_sys_resource_identity` (`identity`),
  KEY `idx_sys_resource_user` (`url`),
  KEY `idx_sys_resource_parent_id` (`parent_id`),
  KEY `idx_sys_resource_parent_ids_weight` (`parent_ids`,`weight`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `role` varchar(100) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_role_name` (`name`),
  KEY `idx_sys_role_role` (`role`),
  KEY `idx_sys_role_show` (`is_show`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role_resource_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource_permission`;
CREATE TABLE `sys_role_resource_permission` (
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `resource_id` bigint(20) DEFAULT NULL,
  `permission_ids` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_sys_role_resource_permission` (`role_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_resource_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `crudType` varchar(16) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(16) DEFAULT NULL,
  `creatorName` varchar(256) DEFAULT NULL,
  `lastUpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdator` varchar(16) DEFAULT NULL,
  `lastUpdatorName` varchar(256) DEFAULT NULL,
  `version` varchar(64) DEFAULT NULL,
  `optLock` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `mobile_phone_number` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(10) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(50) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `admin` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_sys_user_username` (`username`),
  UNIQUE KEY `unique_sys_user_email` (`email`),
  UNIQUE KEY `unique_sys_user_mobile_phone_number` (`mobile_phone_number`),
  KEY `idx_sys_user_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('read', 'U00000001', '2017-01-08 07:56:37', 'system', 'sys', '2016-12-04 11:08:13', 'system', 'sys', '0.0.1-version', '0', '???????', '???????', '1', 'zhang', 'zhouqian_caven@sina.com', '15858110948', '2b8e7ff4fd0d1aa537a8c8ac4a3c4c75', 'yDd1956wn1', '2016-12-04 11:08:13', 'nomal', '0', '1');

-- ----------------------------
-- Table structure for `sys_user_last_online`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_last_online`;
CREATE TABLE `sys_user_last_online` (
  `crudType` varchar(16) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(16) DEFAULT NULL,
  `creatorName` varchar(256) DEFAULT NULL,
  `lastUpdateTime` timestamp NOT NULL DEFAULT '2015-01-01 00:00:00',
  `lastUpdator` varchar(16) DEFAULT NULL,
  `lastUpdatorName` varchar(256) DEFAULT NULL,
  `version` varchar(64) DEFAULT NULL,
  `optLock` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `uid` varchar(100) DEFAULT NULL,
  `host` varchar(100) DEFAULT NULL,
  `user_agent` varchar(200) DEFAULT NULL,
  `system_host` varchar(100) DEFAULT NULL,
  `last_login_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_stop_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `login_count` bigint(20) DEFAULT NULL,
  `total_online_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_sys_user_last_online_sys_user_id` (`user_id`),
  KEY `idx_sys_user_last_online_username` (`username`),
  KEY `idx_sys_user_last_online_host` (`host`),
  KEY `idx_sys_user_last_online_system_host` (`system_host`),
  KEY `idx_sys_user_last_online_last_login_timestamp` (`last_login_timestamp`),
  KEY `idx_sys_user_last_online_last_stop_timestamp` (`last_stop_timestamp`),
  KEY `idx_sys_user_last_online_user_agent` (`user_agent`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_last_online
-- ----------------------------
INSERT INTO `sys_user_last_online` VALUES (null, null, '2016-11-19 20:07:31', null, null, '2015-01-01 00:00:00', null, null, null, null, null, null, '2', '0', null, '233444', null, null, null, '2015-06-08 18:49:12', '2015-07-13 18:49:24', '2', '96000020');

-- ----------------------------
-- Table structure for `sys_user_online`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_online`;
CREATE TABLE `sys_user_online` (
  `crudType` varchar(16) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(16) DEFAULT NULL,
  `creatorName` varchar(256) DEFAULT NULL,
  `lastUpdateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdator` varchar(16) DEFAULT NULL,
  `lastUpdatorName` varchar(256) DEFAULT NULL,
  `version` varchar(64) DEFAULT NULL,
  `optLock` bigint(20) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL,
  `id` varchar(100) NOT NULL,
  `user_id` bigint(20) DEFAULT '0',
  `username` varchar(100) DEFAULT NULL,
  `host` varchar(100) DEFAULT NULL,
  `system_host` varchar(100) DEFAULT NULL,
  `user_agent` varchar(200) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `start_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_access_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `timeout` bigint(20) DEFAULT NULL,
  `session` mediumtext,
  PRIMARY KEY (`id`),
  KEY `idx_sys_user_online_sys_user_id` (`user_id`),
  KEY `idx_sys_user_online_username` (`username`),
  KEY `idx_sys_user_online_host` (`host`),
  KEY `idx_sys_user_online_system_host` (`system_host`),
  KEY `idx_sys_user_online_last_access_time` (`last_access_time`),
  KEY `idx_sys_user_online_user_agent` (`user_agent`),
  KEY `idx_sys_user_online_start_timestamp` (`start_timestamp`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_online
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user_organization_job`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_organization_job`;
CREATE TABLE `sys_user_organization_job` (
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  `job_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_sys_user_organization_job` (`user_id`,`organization_id`,`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_organization_job
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user_status_history`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_status_history`;
CREATE TABLE `sys_user_status_history` (
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `reason` varchar(200) DEFAULT NULL,
  `op_user_id` bigint(20) DEFAULT NULL,
  `op_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `idx_sys_user_block_history_user_id_block_date` (`user_id`,`op_date`),
  KEY `idx_sys_user_block_history_op_user_id_op_date` (`op_user_id`,`op_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_status_history
-- ----------------------------

-- ----------------------------
-- Function structure for `currval`
-- ----------------------------
DROP FUNCTION IF EXISTS `currval`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `currval`(seq_name VARCHAR(50)) RETURNS int(11)
    DETERMINISTIC
BEGIN
     DECLARE value INTEGER; 
     SET value = 0; 
     SELECT current_value INTO value 
          FROM sequence
          WHERE name = seq_name; 
     RETURN value; 
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `nextval`
-- ----------------------------
DROP FUNCTION IF EXISTS `nextval`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `nextval`(seq_name VARCHAR(50)) RETURNS int(11)
    DETERMINISTIC
BEGIN
     UPDATE sequence
          SET current_value = current_value + increment 
          WHERE name = seq_name; 
     RETURN currval(seq_name); 
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `setval`
-- ----------------------------
DROP FUNCTION IF EXISTS `setval`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `setval`(seq_name VARCHAR(50), value INTEGER) RETURNS int(11)
    DETERMINISTIC
BEGIN
     UPDATE sequence
          SET current_value = value 
          WHERE name = seq_name; 
     RETURN currval(seq_name); 
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trigger_sys_user_off_online`;
DELIMITER ;;
CREATE TRIGGER `trigger_sys_user_off_online` AFTER DELETE ON `sys_user_online` FOR EACH ROW begin
   if OLD.`user_id` is not null then
      if not exists(select `user_id` from `sys_user_last_online` where `user_id` = OLD.`user_id`) then
        insert into `sys_user_last_online`
                  (`user_id`, `username`, `uid`, `host`, `user_agent`, `system_host`,
                   `last_login_timestamp`, `last_stop_timestamp`, `login_count`, `total_online_time`)
                values
                   (OLD.`user_id`,OLD.`username`, OLD.`id`, OLD.`host`, OLD.`user_agent`, OLD.`system_host`,
                    OLD.`start_timestamp`, OLD.`last_access_time`,
                    1, (OLD.`last_access_time` - OLD.`start_timestamp`));
      else
        update `sys_user_last_online`
          set `username` = OLD.`username`, `uid` = OLD.`id`, `host` = OLD.`host`, `user_agent` = OLD.`user_agent`,
            `system_host` = OLD.`system_host`, `last_login_timestamp` = OLD.`start_timestamp`,
             `last_stop_timestamp` = OLD.`last_access_time`, `login_count` = `login_count` + 1,
             `total_online_time` = `total_online_time` + (OLD.`last_access_time` - OLD.`start_timestamp`)
        where `user_id` = OLD.`user_id`;
      end if ;
   end if;
end
;;
DELIMITER ;
