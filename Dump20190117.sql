-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: nikolaosdb
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.18.04.1

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
-- Table structure for table `picture`
--

DROP TABLE IF EXISTS `picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `picture` (
  `pic_id` int(11) NOT NULL AUTO_INCREMENT,
  `pic_name` varchar(100) NOT NULL,
  `pic_file` longblob NOT NULL,
  PRIMARY KEY (`pic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `picture`
--

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `webgroup`
--

DROP TABLE IF EXISTS `webgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `webgroup` (
  `groupid` int(11) NOT NULL AUTO_INCREMENT,
  `groupname` varchar(50) NOT NULL,
  `groupcreationdate` datetime NOT NULL,
  PRIMARY KEY (`groupid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `webgroup`
--

LOCK TABLES `webgroup` WRITE;
/*!40000 ALTER TABLE `webgroup` DISABLE KEYS */;
INSERT INTO `webgroup` VALUES (1,'administrator','2019-01-17 00:00:00'),(2,'appuser','2019-01-17 00:00:00');
/*!40000 ALTER TABLE `webgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `webuser`
--

DROP TABLE IF EXISTS `webuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `webuser` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `userpwd` varchar(50) NOT NULL,
  `usercreationdate` datetime NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `webuser`
--

LOCK TABLES `webuser` WRITE;
/*!40000 ALTER TABLE `webuser` DISABLE KEYS */;
INSERT INTO `webuser` VALUES (1,'admin','admin00','2019-01-17 00:00:00'),(2,'kostas','kostas00','2019-01-17 00:00:00'),(3,'maria','maria00','2019-01-17 00:00:00'),(5,'nikolaos','nikos00','2019-01-17 07:41:46'),(6,'stavroula','stavroula00','2019-01-17 07:56:21'),(7,'margarita','margarita00','2019-01-17 08:13:21'),(9,'argyrw','argyrw00','2019-01-17 08:17:56'),(10,'aspasia','aspasia00','2019-01-17 08:27:14'),(11,'giorgos','giorgos00','2019-01-17 08:29:29'),(12,'stavros','stavros00','2019-01-17 08:31:33'),(13,'evlambia','evlambia00','2019-01-17 08:43:53'),(14,'thalia','thalia00','2019-01-17 08:52:15'),(15,'melpw','melpw00','2019-01-17 08:57:16'),(17,'melpw','melpw00','2019-01-17 09:04:54');
/*!40000 ALTER TABLE `webuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `webusergroup`
--

DROP TABLE IF EXISTS `webusergroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `webusergroup` (
  `userid` int(11) NOT NULL,
  `groupid` int(11) NOT NULL,
  `usergroupdoc` datetime NOT NULL,
  PRIMARY KEY (`userid`,`groupid`),
  KEY `fk_webusergroup_2_idx` (`groupid`),
  CONSTRAINT `fk_webusergroup_1` FOREIGN KEY (`userid`) REFERENCES `webuser` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_webusergroup_2` FOREIGN KEY (`groupid`) REFERENCES `webgroup` (`groupid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `webusergroup`
--

LOCK TABLES `webusergroup` WRITE;
/*!40000 ALTER TABLE `webusergroup` DISABLE KEYS */;
INSERT INTO `webusergroup` VALUES (1,1,'2019-01-17 00:00:00'),(2,2,'2019-01-17 00:00:00'),(3,2,'2019-01-17 00:00:00');
/*!40000 ALTER TABLE `webusergroup` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-17  9:11:40
