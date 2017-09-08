-- MySQL dump 10.13  Distrib 5.6.27, for osx10.8 (x86_64)
--
-- Host: localhost    Database: sms
-- ------------------------------------------------------
-- Server version	5.6.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `id` bigint(32) NOT NULL,
  `url` varchar(200) NOT NULL,
  `type` varchar(45) DEFAULT 'N/A',
  `name` varchar(45) DEFAULT 'N/A',
  `parent_id` bigint(32) DEFAULT '0',
  `description` varchar(300) DEFAULT 'N/A',
  `usable_status` varchar(45) NOT NULL DEFAULT '1',
  `create_user_id` bigint(32) NOT NULL,
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_user_id` bigint(32) DEFAULT '0',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url_UNIQUE` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES (16,'/user/getById','page','Opnine',0,'dfghjkl','1',67,'2016-04-20 07:29:11',0,'2016-04-20 07:29:11');
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(32) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(200) DEFAULT 'N/A',
  `usable_status` varchar(45) NOT NULL DEFAULT '0',
  `create_user_id` bigint(32) NOT NULL DEFAULT '0',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_user_id` bigint(32) NOT NULL DEFAULT '0',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `role_name` (`name`),
  KEY `role_create_user_id` (`create_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN',NULL,'1',1,'2016-03-24 05:41:08',0,'2016-03-24 05:41:08'),(2,'USER','N/A','0',0,'2016-04-20 06:20:47',0,'2016-04-20 06:20:47');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_resource`
--

DROP TABLE IF EXISTS `role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_resource` (
  `id` bigint(32) NOT NULL,
  `role_id` bigint(32) DEFAULT NULL,
  `resource_id` bigint(32) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `usable_status` varchar(45) NOT NULL,
  `create_user_id` bigint(32) NOT NULL,
  `modify_date` timestamp NULL DEFAULT NULL,
  `modify_user_id` bigint(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_resource`
--

LOCK TABLES `role_resource` WRITE;
/*!40000 ALTER TABLE `role_resource` DISABLE KEYS */;
INSERT INTO `role_resource` VALUES (1,1,16,'2016-04-20 07:06:19','1',1,'0000-00-00 00:00:00',NULL),(2,2,16,'2016-04-20 07:06:19','',0,NULL,NULL),(3,2,16,'2016-04-20 07:06:19','1',1,'2015-12-07 13:14:06',NULL);
/*!40000 ALTER TABLE `role_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_user`
--

DROP TABLE IF EXISTS `role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_user` (
  `id` bigint(32) NOT NULL,
  `user_id` bigint(32) NOT NULL DEFAULT '0',
  `role_id` bigint(32) NOT NULL DEFAULT '0',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `usable_status` varchar(45) DEFAULT '0',
  `create_user_id` bigint(32) DEFAULT '0',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_user_id` bigint(32) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_user`
--

LOCK TABLES `role_user` WRITE;
/*!40000 ALTER TABLE `role_user` DISABLE KEYS */;
INSERT INTO `role_user` VALUES (1,54,1,'2016-04-20 06:19:55',NULL,1,'2016-04-20 06:19:55',0),(2,54,2,'2016-04-20 06:21:27','0',0,'2016-04-20 06:21:27',0);
/*!40000 ALTER TABLE `role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sequence_resource`
--

DROP TABLE IF EXISTS `sequence_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sequence_resource` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `other` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `other_UNIQUE` (`other`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence_resource`
--

LOCK TABLES `sequence_resource` WRITE;
/*!40000 ALTER TABLE `sequence_resource` DISABLE KEYS */;
INSERT INTO `sequence_resource` VALUES (51,'1');
/*!40000 ALTER TABLE `sequence_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sequence_role`
--

DROP TABLE IF EXISTS `sequence_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sequence_role` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `other` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `other_UNIQUE` (`other`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence_role`
--

LOCK TABLES `sequence_role` WRITE;
/*!40000 ALTER TABLE `sequence_role` DISABLE KEYS */;
INSERT INTO `sequence_role` VALUES (9,'1');
/*!40000 ALTER TABLE `sequence_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sequence_role_resource`
--

DROP TABLE IF EXISTS `sequence_role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sequence_role_resource` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `other` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `other_UNIQUE` (`other`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence_role_resource`
--

LOCK TABLES `sequence_role_resource` WRITE;
/*!40000 ALTER TABLE `sequence_role_resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `sequence_role_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sequence_role_user`
--

DROP TABLE IF EXISTS `sequence_role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sequence_role_user` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `other` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `other_UNIQUE` (`other`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence_role_user`
--

LOCK TABLES `sequence_role_user` WRITE;
/*!40000 ALTER TABLE `sequence_role_user` DISABLE KEYS */;
INSERT INTO `sequence_role_user` VALUES (4,'1');
/*!40000 ALTER TABLE `sequence_role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sequence_sys_app`
--

DROP TABLE IF EXISTS `sequence_sys_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sequence_sys_app` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `other` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `other_UNIQUE` (`other`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence_sys_app`
--

LOCK TABLES `sequence_sys_app` WRITE;
/*!40000 ALTER TABLE `sequence_sys_app` DISABLE KEYS */;
/*!40000 ALTER TABLE `sequence_sys_app` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sequence_user`
--

DROP TABLE IF EXISTS `sequence_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sequence_user` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `other` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `other_UNIQUE` (`other`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence_user`
--

LOCK TABLES `sequence_user` WRITE;
/*!40000 ALTER TABLE `sequence_user` DISABLE KEYS */;
INSERT INTO `sequence_user` VALUES (69,'1');
/*!40000 ALTER TABLE `sequence_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_app`
--

DROP TABLE IF EXISTS `sys_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_app` (
  `id` bigint(32) NOT NULL,
  `app_domain` varchar(45) NOT NULL,
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `usable_status` varchar(2) DEFAULT '0',
  `create_user_id` bigint(32) DEFAULT '0',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modify_user_id` bigint(32) DEFAULT '0',
  `app_index` varchar(45) DEFAULT 'N/A',
  PRIMARY KEY (`id`),
  UNIQUE KEY `app_domain_UNIQUE` (`app_domain`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_app`
--

LOCK TABLES `sys_app` WRITE;
/*!40000 ALTER TABLE `sys_app` DISABLE KEYS */;
INSERT INTO `sys_app` VALUES (10000,'soaer.com',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_app` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,400);
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(32) NOT NULL,
  `name` varchar(200) NOT NULL,
  `image_path` varchar(100) DEFAULT '""',
  `password` varchar(100) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `adress` varchar(45) DEFAULT 'N/A',
  `confirmnum` int(11) DEFAULT '0',
  `login_sum` int(11) DEFAULT '0',
  `last_login_ip` varchar(100) DEFAULT 'N/A',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `usable_status` varchar(45) NOT NULL DEFAULT '1',
  `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `token` varchar(200) DEFAULT 'N/A',
  `login_type` varchar(45) DEFAULT 'N/A',
  `last_login_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (54,'Sunny','','E9169BC458322D62368AD877538482ED','domain@163.com',NULL,'Beijing shi',0,NULL,'192.168.1.1','2016-03-29 10:08:54','0','2016-03-29 10:08:54',NULL,NULL,'2016-03-29 10:08:54');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-08 15:02:34
