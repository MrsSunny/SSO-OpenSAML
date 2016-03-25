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
INSERT INTO `resource` VALUES (1,'/sysuser/list',NULL,NULL,NULL,NULL,'1',1,'2015-04-05 16:00:00',1,'2015-04-05 16:00:00'),(5,'/baidu/create','page','Opnine',NULL,'dfghjkl','1',67,'2016-03-24 09:00:44',NULL,'2015-04-05 16:00:00'),(8,'/baidu/create/6aa106603f724f9bb1b25387220877d1','page','Opnine',NULL,'dfghjkl','1',67,'2016-03-25 03:26:54',67,'2016-03-25 03:26:54'),(9,'/baidu/create/1286e4d9138a4c70bc8e54c68a0d9200','page','Opnine',NULL,'dfghjkl','1',67,'2016-03-24 09:00:44',NULL,'2015-04-05 16:00:00'),(13,'/baidu/create/809c0d4861de4fefa11558b19e5256e1','page','Opnine',NULL,'dfghjkl','1',67,'2016-03-24 09:00:44',NULL,'2015-04-05 16:00:00'),(14,'/baidu/create/3d266b0230584893b573b8ae3019a756','page','Opnine',0,'dfghjkl','1',67,'2016-03-24 09:01:08',0,'2016-03-24 09:01:08'),(15,'/baidu/create/31d5b88937fb44fdb1af48d1ac11efb1','page','Opnine',0,'dfghjkl','1',67,'2016-03-24 09:04:01',0,'2016-03-24 09:04:01'),(16,'/baidu/create/99a6818e2dca4b35ac7a40d0315f85a3','page','Opnine',0,'dfghjkl','1',67,'2016-03-24 09:06:34',0,'2016-03-24 09:06:34'),(17,'/baidu/create/df7b3bdca1c9402194030d220744d28c','page','Opnine',0,'dfghjkl','1',67,'2016-03-24 10:51:39',0,'2016-03-24 10:51:39'),(48,'/baidu/create/f194c3aaac364515a959ceb6bdbde38b','page','Opnine',0,'dfghjkl','1',67,'2016-03-25 03:24:30',0,'2016-03-25 03:24:30'),(49,'/baidu/create/ef0f09b9857c42ab950e42bd09892d11','page','Opnine',0,'dfghjkl','1',67,'2016-03-25 03:26:54',0,'2016-03-25 03:26:54');
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
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN',NULL,'1',1,'0000-00-00 00:00:00',0,'0000-00-00 00:00:00'),(2,'USER',NULL,'1',1,NULL,0,'0000-00-00 00:00:00');
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
INSERT INTO `role_resource` VALUES (1,1,1,'0000-00-00 00:00:00','1',1,'0000-00-00 00:00:00',NULL),(2,2,2,'2015-12-07 13:14:06','',0,NULL,NULL),(3,2,3,'2015-12-24 07:45:32','1',1,'2015-12-07 13:14:06',NULL);
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
  `role_id` bigint(32) DEFAULT '0',
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
INSERT INTO `role_user` VALUES (1,1,1,'0000-00-00 00:00:00','1',1,'0000-00-00 00:00:00',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence_resource`
--

LOCK TABLES `sequence_resource` WRITE;
/*!40000 ALTER TABLE `sequence_resource` DISABLE KEYS */;
INSERT INTO `sequence_resource` VALUES (49,'1');
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence_role`
--

LOCK TABLES `sequence_role` WRITE;
/*!40000 ALTER TABLE `sequence_role` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence_role_user`
--

LOCK TABLES `sequence_role_user` WRITE;
/*!40000 ALTER TABLE `sequence_role_user` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence_user`
--

LOCK TABLES `sequence_user` WRITE;
/*!40000 ALTER TABLE `sequence_user` DISABLE KEYS */;
INSERT INTO `sequence_user` VALUES (65,'1');
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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(32) NOT NULL,
  `name` varchar(200) NOT NULL,
  `image_path` varchar(100) DEFAULT '""',
  `login_id` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
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
  UNIQUE KEY `login_id_UNIQUE` (`login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','http://soaer.com/1/jpeg','admin','EFD9D1B8BFB00E8E3647990F7D74D1D8','lzx@163.com',NULL,NULL,NULL,NULL,NULL,'2016-02-23 06:06:49','0','2014-12-31 16:00:00',NULL,NULL,'2014-12-31 16:00:00'),(42,'Sunny','','zhenxingLiu','beijingshi','domain@163.com',NULL,'Beijing shi',0,NULL,'192.168.1.1','2016-03-24 04:10:15','1','2016-03-24 04:10:15',NULL,NULL,NULL),(44,'Sunny','','1cc74fe7843f478690d5b87565dab3bb','beijingshi','domain@163.com',NULL,'Beijing shi',0,NULL,'192.168.1.1','2016-03-24 04:26:52','1','2016-03-24 04:26:52',NULL,NULL,NULL),(46,'Sunny','','b290714d74c441c09e0be254220b0b37','beijingshi','domain@163.com',NULL,'Beijing shi',0,NULL,'192.168.1.1','2016-03-24 04:30:16','1','2016-03-24 04:30:16',NULL,NULL,NULL),(48,'Sunny. Liu','http://domain.com/persion.jpg','fef26f1400e64ad8b4b602d57183784e','beijingshi','domain@hotmail.com','13166666666','Shanghai shi',0,NULL,'192.168.1.2','2016-03-24 07:35:07','1','2016-03-24 07:35:07',NULL,NULL,NULL),(50,'Sunny','','d1de33457c2545639028a409446a63ab','beijingshi','domain@163.com',NULL,'Beijing shi',0,NULL,'192.168.1.1','2016-03-24 05:36:06','1','2016-03-24 05:36:06',NULL,NULL,NULL),(54,'Sunny','','e69276b697704d6ebc38477c758f12c8','beijingshi','domain@163.com',NULL,'Beijing shi',0,NULL,'192.168.1.1','2016-03-24 05:41:08','1','2016-03-24 05:41:08',NULL,NULL,NULL),(58,'Sunny','','2b7e1774f88e4cc6aae9c36800d8b9bd','beijingshi','domain@163.com',NULL,'Beijing shi',0,NULL,'192.168.1.1','2016-03-24 07:26:26','1','2016-03-24 07:26:26',NULL,NULL,NULL),(60,'Sunny','','13d4fc81442140d89545cb38e1f9ce5c','beijingshi','domain@163.com',NULL,'Beijing shi',0,NULL,'192.168.1.1','2016-03-24 07:29:52','1','2016-03-24 07:29:52',NULL,NULL,NULL),(64,'Sunny','','863c78cedb484c52b4a18fae194e1b27','beijingshi','domain@163.com',NULL,'Beijing shi',0,NULL,'192.168.1.1','2016-03-24 07:35:07','1','2016-03-24 07:35:07',NULL,NULL,NULL),(65,'Sunny','','53e45b874ff348c587269c390045ce5d','beijingshi','domain@163.com',NULL,'Beijing shi',0,NULL,'192.168.1.1','2016-03-25 07:47:32','1','2016-03-25 07:47:32',NULL,NULL,'2016-03-25 07:47:32');
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

-- Dump completed on 2016-03-25 15:49:07
