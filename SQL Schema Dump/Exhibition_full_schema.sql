CREATE DATABASE  IF NOT EXISTS `exhibition` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `exhibition`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: exhibition
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `art`
--

DROP TABLE IF EXISTS `art`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `art` (
  `art_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` varchar(1024) NOT NULL,
  `artist_id` int(11) NOT NULL,
  `picture` varchar(256) NOT NULL,
  PRIMARY KEY (`art_id`),
  KEY `artist_id` (`artist_id`),
  CONSTRAINT `art_ibfk_1` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `art`
--

LOCK TABLES `art` WRITE;
/*!40000 ALTER TABLE `art` DISABLE KEYS */;
/*!40000 ALTER TABLE `art` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artist`
--

DROP TABLE IF EXISTS `artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artist` (
  `artist_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` varchar(1024) NOT NULL,
  `picture` varchar(256) NOT NULL,
  PRIMARY KEY (`artist_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist`
--

LOCK TABLES `artist` WRITE;
/*!40000 ALTER TABLE `artist` DISABLE KEYS */;
INSERT INTO `artist` VALUES (1,'Artist 1','Test Artist Description 1','');
/*!40000 ALTER TABLE `artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artist_traits`
--

DROP TABLE IF EXISTS `artist_traits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artist_traits` (
  `artist_id` int(11) NOT NULL,
  `trait` varchar(128) NOT NULL,
  KEY `artist_id` (`artist_id`),
  CONSTRAINT `artist_traits_ibfk_1` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist_traits`
--

LOCK TABLES `artist_traits` WRITE;
/*!40000 ALTER TABLE `artist_traits` DISABLE KEYS */;
/*!40000 ALTER TABLE `artist_traits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exhibited_artist`
--

DROP TABLE IF EXISTS `exhibited_artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exhibited_artist` (
  `gallery_id` int(11) NOT NULL,
  `artist_id` int(11) NOT NULL,
  KEY `artist_id` (`artist_id`),
  KEY `gallery_id` (`gallery_id`),
  CONSTRAINT `exhibited_artist_ibfk_1` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`),
  CONSTRAINT `exhibited_artist_ibfk_2` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`gallery_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exhibited_artist`
--

LOCK TABLES `exhibited_artist` WRITE;
/*!40000 ALTER TABLE `exhibited_artist` DISABLE KEYS */;
/*!40000 ALTER TABLE `exhibited_artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite_gallery`
--

DROP TABLE IF EXISTS `favorite_gallery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `favorite_gallery` (
  `gallery_id` int(128) NOT NULL,
  `artist_id` int(128) NOT NULL,
  PRIMARY KEY (`gallery_id`,`artist_id`),
  KEY `artist_id` (`artist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite_gallery`
--

LOCK TABLES `favorite_gallery` WRITE;
/*!40000 ALTER TABLE `favorite_gallery` DISABLE KEYS */;
INSERT INTO `favorite_gallery` VALUES (1,1);
/*!40000 ALTER TABLE `favorite_gallery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gallery`
--

DROP TABLE IF EXISTS `gallery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gallery` (
  `gallery_id` int(128) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `year` int(11) NOT NULL,
  `description` varchar(1024) NOT NULL,
  `photo` blob NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`gallery_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gallery`
--

LOCK TABLES `gallery` WRITE;
/*!40000 ALTER TABLE `gallery` DISABLE KEYS */;
INSERT INTO `gallery` VALUES (1,'Gallery 1',2001,'This is a Test Description','',40.7233574,-73.9932855,'2017-04-02 17:27:33','2017-04-02 17:27:33'),(2,'Gallery 2',2010,'Test Description 2','',40.7594631,-74.0055771,'2017-04-02 20:21:55','2017-04-02 20:21:55');
/*!40000 ALTER TABLE `gallery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gallery_traits`
--

DROP TABLE IF EXISTS `gallery_traits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gallery_traits` (
  `gallery_id` int(128) NOT NULL,
  `trait` varchar(128) NOT NULL,
  KEY `gallery_id` (`gallery_id`),
  CONSTRAINT `gallery_traits_ibfk_1` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`gallery_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gallery_traits`
--

LOCK TABLES `gallery_traits` WRITE;
/*!40000 ALTER TABLE `gallery_traits` DISABLE KEYS */;
/*!40000 ALTER TABLE `gallery_traits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `galletry_art`
--

DROP TABLE IF EXISTS `galletry_art`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `galletry_art` (
  `gallery_id` int(128) NOT NULL,
  `art_id` int(128) NOT NULL,
  KEY `gallery_id` (`gallery_id`),
  KEY `art_id` (`art_id`),
  CONSTRAINT `galletry_art_ibfk_1` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`gallery_id`),
  CONSTRAINT `galletry_art_ibfk_2` FOREIGN KEY (`art_id`) REFERENCES `art` (`art_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `galletry_art`
--

LOCK TABLES `galletry_art` WRITE;
/*!40000 ALTER TABLE `galletry_art` DISABLE KEYS */;
/*!40000 ALTER TABLE `galletry_art` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'exhibition'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_getGalleryList` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_getGalleryList`(
	IN p_artist_id INT
)
BEGIN
	SELECT ga.gallery_id,ga.name,ga.photo,ga.description,ga.latitude,ga.longitude, IF(fav.gallery_id is NOT NULL, TRUE,FALSE) as 'is_fav' 
    FROM gallery ga
    LEFT JOIN favorite_gallery fav
    ON ga.gallery_id = fav.gallery_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertGallery` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertGallery`(
	IN p_name VARCHAR (128),
    IN p_year INT,
    IN p_desc VARCHAR (1024),
    IN p_photo BLOB,
    IN p_lat DOUBLE,
    IN p_long DOUBLE    
)
BEGIN
	INSERT INTO `exhibition`.`gallery`
(`name`,`year`,`description`,`photo`,`latitude`,`longitude`,`create_date`,`modify_date`)
VALUES
(p_name,p_year,p_desc,p_photo,p_lat,p_long, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-02 20:24:42
