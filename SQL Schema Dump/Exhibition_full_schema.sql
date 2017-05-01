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
  `is_deleted` bit(1) NOT NULL,
  PRIMARY KEY (`art_id`),
  KEY `artist_id` (`artist_id`),
  CONSTRAINT `art_ibfk_1` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `art`
--

LOCK TABLES `art` WRITE;
/*!40000 ALTER TABLE `art` DISABLE KEYS */;
INSERT INTO `art` VALUES (1,'Art_1','Test Art Description 1',1,'artwork1_1491747860.jpg','\0'),(4,'Test Artwork','This is Test artwork Upload',1,'30daysAbs_1491774976.jpg','\0'),(6,'Test Artwork','This is Test Artwork Upload',1,'30daysAbs_1491775902.jpg',''),(12,'Blue Sky','test',1,'2017-04-10-15-48-20--1053313750_1491880703.jpg','\0'),(13,'Blue Sky','test',1,'2017-04-10-15-48-20--1053313750_1491880732.jpg',''),(14,'Fall','test',1,'2017-04-10-23-36-49-2054567525_1491881833.jpg',''),(15,'Fall Leaves','test',1,'2017-04-10-23-36-49-2054567525_1491931599.jpg','\0'),(16,'Fall leaves','description',1,'2017-04-10-23-36-49-2054567525_1491951149.jpg',''),(17,'Night Time','Night Time',1,'2017-04-10-23-34-47--47576534_1492375905.jpg',''),(18,'hi','hi',1,'2017-04-10-23-36-49-2054567525_1492555052.jpg',''),(19,'Blue sky','test',1,'2017-04-10-23-34-47--47576534_1492560480.jpg','\0'),(20,'fall','desc',1,'2017-04-10-23-36-49-2054567525_1492560689.jpg','\0');
/*!40000 ALTER TABLE `art` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `art_traits`
--

DROP TABLE IF EXISTS `art_traits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `art_traits` (
  `trait_id` int(11) NOT NULL AUTO_INCREMENT,
  `art_id` int(11) NOT NULL,
  `trait` varchar(128) NOT NULL,
  PRIMARY KEY (`trait_id`),
  KEY `art_id` (`art_id`),
  CONSTRAINT `art_traits_ibfk_1` FOREIGN KEY (`art_id`) REFERENCES `art` (`art_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `art_traits`
--

LOCK TABLES `art_traits` WRITE;
/*!40000 ALTER TABLE `art_traits` DISABLE KEYS */;
/*!40000 ALTER TABLE `art_traits` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist`
--

LOCK TABLES `artist` WRITE;
/*!40000 ALTER TABLE `artist` DISABLE KEYS */;
INSERT INTO `artist` VALUES (1,'Andy Warhol','Test Artist Description 1','andy_warhol_1491747860.jpg');
/*!40000 ALTER TABLE `artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artist_traits`
--

DROP TABLE IF EXISTS `artist_traits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artist_traits` (
  `trait_id` int(11) NOT NULL AUTO_INCREMENT,
  `artist_id` int(11) NOT NULL,
  `trait` varchar(128) NOT NULL,
  PRIMARY KEY (`trait_id`),
  KEY `artist_id` (`artist_id`),
  CONSTRAINT `artist_traits_ibfk_1` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist_traits`
--

LOCK TABLES `artist_traits` WRITE;
/*!40000 ALTER TABLE `artist_traits` DISABLE KEYS */;
INSERT INTO `artist_traits` VALUES (4,1,'cool'),(5,1,'contemporary');
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
INSERT INTO `exhibited_artist` VALUES (1,1);
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
  `photo` varchar(256) NOT NULL,
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
INSERT INTO `gallery` VALUES (1,'Gallery 1',2001,'This is a Test Description','galleryLogo_1491747860.jpeg',40.7233574,-73.9932855,'2017-04-02 17:27:33','2017-04-02 17:27:33'),(2,'Gallery 2',2010,'Test Description 2','art_gallery_logo_14917478860.jpeg',40.7594631,-74.0055771,'2017-04-02 20:21:55','2017-04-02 20:21:55');
/*!40000 ALTER TABLE `gallery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gallery_art`
--

DROP TABLE IF EXISTS `gallery_art`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gallery_art` (
  `gallery_id` int(128) NOT NULL,
  `art_id` int(128) NOT NULL,
  KEY `gallery_id` (`gallery_id`),
  KEY `art_id` (`art_id`),
  CONSTRAINT `gallery_art_ibfk_1` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`gallery_id`),
  CONSTRAINT `gallery_art_ibfk_2` FOREIGN KEY (`art_id`) REFERENCES `art` (`art_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gallery_art`
--

LOCK TABLES `gallery_art` WRITE;
/*!40000 ALTER TABLE `gallery_art` DISABLE KEYS */;
INSERT INTO `gallery_art` VALUES (1,1),(1,4),(1,6),(1,12),(1,13),(1,14);
/*!40000 ALTER TABLE `gallery_art` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gallery_traits`
--

DROP TABLE IF EXISTS `gallery_traits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gallery_traits` (
  `trait_id` int(11) NOT NULL AUTO_INCREMENT,
  `gallery_id` int(128) NOT NULL,
  `trait` varchar(128) NOT NULL,
  PRIMARY KEY (`trait_id`),
  KEY `gallery_id` (`gallery_id`),
  CONSTRAINT `gallery_traits_ibfk_1` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`gallery_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gallery_traits`
--

LOCK TABLES `gallery_traits` WRITE;
/*!40000 ALTER TABLE `gallery_traits` DISABLE KEYS */;
INSERT INTO `gallery_traits` VALUES (1,1,'SoHo');
/*!40000 ALTER TABLE `gallery_traits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `submissions`
--

DROP TABLE IF EXISTS `submissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `submissions` (
  `submission_id` int(11) NOT NULL AUTO_INCREMENT,
  `art_id` int(11) NOT NULL,
  `gallery_id` int(11) NOT NULL,
  `artist_id` int(11) NOT NULL,
  `is_pending` bit(1) DEFAULT b'1',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`submission_id`),
  KEY `art_id_idx` (`art_id`),
  KEY `gallery_id_idx` (`gallery_id`),
  KEY `artist_id_idx` (`artist_id`),
  CONSTRAINT `art_id` FOREIGN KEY (`art_id`) REFERENCES `art` (`art_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `artist_id` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `gallery_id` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`gallery_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `submissions`
--

LOCK TABLES `submissions` WRITE;
/*!40000 ALTER TABLE `submissions` DISABLE KEYS */;
INSERT INTO `submissions` VALUES (1,15,1,1,'','2017-04-16 14:19:00','2017-04-16 14:19:00'),(2,16,1,1,'','2017-04-16 14:58:20','2017-04-16 14:58:20'),(8,17,1,1,'','2017-04-16 18:51:32','2017-04-16 18:51:32'),(9,18,1,1,'','2017-04-18 18:37:43','2017-04-18 18:37:43'),(10,20,1,1,'','2017-04-18 20:12:15','2017-04-18 20:12:15');
/*!40000 ALTER TABLE `submissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'exhibition'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_addArtistTrait` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_addArtistTrait`(
	IN p_artist_id INT,
    IN p_trait_name varchar(256)
)
BEGIN
	IF EXISTS (SELECT 1 FROM artist_traits where artist_id = p_artist_id and lower(trait) = lower(p_trait_name))
    THEN
    BEGIN
		select -1 as return_code, 'Trait already exists' as status ;
    END ;
    ELSE
		INSERT INTO `exhibition`.`artist_traits`
		(`artist_id`,`trait`)
		VALUES
		(p_artist_id,p_trait_name);
        
        SELECT 1 as return_code,'Trait Added Successfully' as status;
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_addArtTrait` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_addArtTrait`(
	IN p_art_id int,
    IN p_trait varchar(1024)
)
BEGIN
	IF EXISTS (SELECT 1 FROM art_traits where art_id = p_art_id and lower(trait) = lower(p_trait))
    THEN
    BEGIN
		select -1 as return_code, 'Trait already exists' as status ;
    END ;
    ELSE
		INSERT INTO `exhibition`.`art_traits`
		(`art_id`,	`trait`)
		VALUES
		(p_art_id,	p_trait);
		
		SELECT '1' as 'return_code','Trait Added Successfully' as 'status';
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_addFavoriteGallery` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_addFavoriteGallery`(
	IN p_gallery_id int,
    IN p_artist_id int
)
BEGIN
	INSERT INTO `exhibition`.`favorite_gallery`
	(`gallery_id`,
	`artist_id`)
	VALUES
	(p_gallery_id,
	p_artist_id);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_addGalleryTrait` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_addGalleryTrait`(
	IN p_gallery_id int,
    IN p_trait varchar(256)
    
)
BEGIN
	IF EXISTS (SELECT 1 FROM gallery_traits where gallery_id = p_gallery_id and lower(trait) = lower(p_trait))
    THEN
    BEGIN
		select -1 as return_code, 'Trait already exists' as status ;
    END ;
    ELSE
		INSERT INTO `exhibition`.`gallery_traits`
		(`gallery_id`,`trait`)
		VALUES
		(p_gallery_id, p_trait);
        
        SELECT 1 as return_code,'Trait Added Successfully' as status;
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_addSubmission` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_addSubmission`(
	IN p_art_id int,
    IN p_artist_id int,
    IN p_gallery_id int
)
BEGIN
	IF EXISTS(SELECT 1 FROM gallery_art where art_id = p_art_id)
    THEN
		SELECT '-1' as 'return_code','Artwork accepted by Gallery' as 'return_status';
	ELSEIF EXISTS (SELECT 1 from submissions where art_id = p_art_id and artist_id = p_artist_id and gallery_id = p_gallery_id)
	THEN
		SELECT '-1'  as 'return_code','Artwork Already Submiited to gallery' as 'return_status';
	ELSE
		INSERT INTO `exhibition`.`submissions`
		(
		`art_id`,
		`gallery_id`,
		`artist_id`)
		VALUES
		(
		p_art_id,
		p_gallery_id,
		p_artist_id);
        
        SElECT '1'  as 'return_code','Artwork submitted successfully' as 'return_status';
	END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_deleteArt` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_deleteArt`(
	IN p_art_id int
)
BEGIN
	UPDATE art
    SET is_deleted = True
    WHERE art_id = p_art_id; 
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_deleteArtistTrait` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_deleteArtistTrait`(
	IN p_trait_id INT,
	IN artistId INT
)
BEGIN
	IF EXISTS(SELECT 1 from artist_traits where trait_id = p_trait_id)
    THEN
		BEGIN
			DELETE FROM `artist_traits` WHERE `trait_id` = p_trait_id;
            
            SELECT 1 as return_code, 'Trait Deleted Successfully' as status;
        END;
	ELSE
        SELECT -1 as return_code , 'Traits Does not Exist' as status;
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_deleteArtTrait` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_deleteArtTrait`(
	IN p_trait_id INT,
	IN p_art_id INT
)
BEGIN
	IF EXISTS(SELECT 1 from art_traits where trait_id = p_trait_id)
    THEN
		BEGIN
			DELETE FROM `art_traits` WHERE `trait_id` = p_trait_id;
            
            SELECT 1 as return_code, 'Trait Deleted Successfully' as status;
        END;
	ELSE
        SELECT -1 as return_code , 'Traits Does not Exist' as status;
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_deleteGalleryTrait` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_deleteGalleryTrait`(
	IN p_trait_id INT,
	IN p_gallery_id INT
)
BEGIN
	IF EXISTS(SELECT 1 from gallery_traits where trait_id = p_trait_id)
    THEN
		BEGIN
			DELETE FROM `gallery_traits` WHERE `trait_id` = p_trait_id;
            
            SELECT 1 as return_code, 'Trait Deleted Successfully' as status;
        END;
	ELSE
        SELECT -1 as return_code , 'Traits Does not Exist' as status;
	END IF;
	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_dropFavoriteGallery` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_dropFavoriteGallery`(
	IN p_gallery_id int,
    IN p_artist_id int
)
BEGIN
	DELETE FROM `exhibition`.`favorite_gallery`
	WHERE `exhibition`.`gallery_id` = p_gallery_id
	AND `exhibition`.`artist_id` = p_artist_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_getArtistDetails` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_getArtistDetails`(
	IN p_artist_id INT
)
BEGIN
	SELECT * 
    FROM artist a
    LEFT JOIN (SELECT GROUP_CONCAT(trait) as 'traits', GROUP_CONCAT(trait_id) as trait_ids, artist_id FROM artist_traits GROUP BY artist_id) at
    ON a.artist_id = at.artist_id
    where a.artist_id = p_artist_id;
    
    SELECT *
    FROM art a
    LEFT JOIN (SELECT GROUP_CONCAT(trait) as 'traits', GROUP_CONCAT(trait_id) as trait_ids, art_id FROM art_traits GROUP BY art_id) at
    ON a.art_id = at.art_id
    where a.artist_id  = p_artist_id
    and a.is_deleted != 1;
    
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_getGalleryDetails` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_getGalleryDetails`(
	IN p_gallery_id INT
)
BEGIN
	#DECLARE p_artist_id INT;
    
    /* QUERY TO FETCH GALLERY DETAILS */
	SELECT ga.gallery_id, ga.name, ga.year, ga.description, ga.photo, ga.latitude, ga.longitude, IF(fav.gallery_id is NOT NULL, TRUE,FALSE) as 'is_fav' 
    FROM gallery ga
    LEFT JOIN favorite_gallery fav
    ON ga.gallery_id = fav.gallery_id
    LEFT JOIN (SELECT GROUP_CONCAT(trait) as 'traits', GROUP_CONCAT(trait_id) as trait_ids, gallery_id FROM gallery_traits GROUP BY gallery_id) at
    ON ga.gallery_id = at.gallery_id
    WHERE ga.gallery_id = p_gallery_id;
    
    /* QUERY TO FETCH ARTIST EXHIBITED BY GALLERY */
    SELECT @p_artist_id:=a.artist_id as artist_id, a.name, a.picture
    FROM exhibited_artist ea 
    JOIN artist a
    ON ea.artist_id = a.artist_id
    WHERE gallery_id = p_gallery_id;
    
    /* QUERY TO FETCH ARTWORK EXHIBITED BY GALLERY */
    SELECT a.art_id, a.name, a.description, a.picture,at.traits,at.trait_ids
    FROM art a
    JOIN gallery_art ga
    ON a.art_id = ga.art_id
    LEFT JOIN (SELECT GROUP_CONCAT(trait) as 'traits',GROUP_CONCAT(trait_id) as trait_ids ,art_id FROM art_traits GROUP BY art_id) at
    ON a.art_id = at.art_id
    WHERE ga.gallery_id = p_gallery_id AND a.is_deleted != 1;
    
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
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
	SELECT ga.gallery_id,ga.name,ga.photo,ga.description,ga.latitude,ga.longitude, IF((fav.gallery_id is NOT NULL )AND (fav.artist_id = p_artist_id), TRUE,FALSE) as 'is_fav' 
    FROM gallery ga
    LEFT JOIN favorite_gallery fav
    ON ga.gallery_id = fav.gallery_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_grabArtistTrait` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_grabArtistTrait`(
	IN p_artist_id int
)
BEGIN
    SELECT art_traits.trait FROM art_traits, art, artist WHERE art_traits.art_id = art.art_id AND art.artist_id = artist.artist_id AND artist.artist_id = p_artist_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertArtist` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertArtist`(
	IN p_name varchar(128),
    IN p_desc varchar(1024),
    IN p_picture varchar(256)
    
)
BEGIN
	INSERT INTO `exhibition`.`artist`
	(`name`,
	`description`,
	`picture`)
	VALUES
	(p_name,
	p_desc,
	p_picture);

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
/*!50003 DROP PROCEDURE IF EXISTS `sp_search` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_search`(
	IN p_search_term varchar(256),
    IN p_artist_id INT
)
BEGIN
	SELECT ga.gallery_id,ga.name,ga.photo,ga.description,ga.latitude,ga.longitude, 
    IF((fav.gallery_id is NOT NULL )AND (fav.artist_id = p_artist_id), TRUE,FALSE) as 'is_fav' 
    FROM gallery ga
    LEFT JOIN favorite_gallery fav
    ON ga.gallery_id = fav.gallery_id 
    WHERE ga.name LIKE CONCAT(p_search_term,'%');
    
	SELECT * 
    FROM artist
    where name like CONCAT(p_search_term,'%');
    
    SELECT a.art_id, a.name, a.description, a.picture,at.traits 
    FROM art a
    LEFT JOIN (SELECT GROUP_CONCAT(trait) as 'traits',art_id FROM art_traits GROUP BY art_id) at
    ON a.art_id = at.art_id
    WHERE a.is_deleted != 1 AND a.name LIKE CONCAT(p_search_term,'%');
    
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_searchTraitArt` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_searchTraitArt`(
	IN traitName varchar(256)
)
BEGIN
	SELECT distinct `art`.* 
	FROM `art`, `art_traits` 
	WHERE `art`.`art_id` = `art_traits`.`art_id` AND `art_traits`.`trait` REGEXP traitName;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_searchTraitArtist` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_searchTraitArtist`(
	IN traitName varchar(128)
)
BEGIN
	SELECT distinct `artist`.* 
	FROM `artist`, `artist_traits` 
	WHERE `artist`.`artist_id` = `artist_traits`.`artist_id` AND `artist_traits`.`trait` REGEXP traitName;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_searchTraitGallery` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_searchTraitGallery`(
	IN traitName varchar(256)
)
BEGIN
	SELECT distinct `gallery`.* 
	FROM `gallery`, `gallery_traits` 
	WHERE `gallery`.`gallery_id` = `gallery_traits`.`gallery_id` AND `gallery_traits`.`trait` REGEXP traitName;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_updateArtistTrait` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_updateArtistTrait`(
	IN p_artist_id INT,
    IN p_trait_id INT,
    IN p_new_trait varchar(256)
)
BEGIN
	IF EXISTS (SELECT 1 FROM artist_traits WHERE artist_id = p_artist_id and trait_id = p_trait_id)
    THEN
		BEGIN
			UPDATE `exhibition`.`artist_traits`
			SET
			`trait` = p_new_trait
			WHERE `trait_id` = p_trait_id;
            
            SELECT 1 as return_code, 'Trait Updated Successfully' as status;
        END;
	ELSE
		SELECT -1 as return_code, 'Trait Doesnt exists' as status;
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_updateArtTrait` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_updateArtTrait`(
	IN p_art_id INT,
    IN p_trait_id INT,
    IN p_new_trait varchar(256)
)
BEGIN
	IF EXISTS (SELECT 1 FROM art_traits WHERE art_id = p_art_id and trait_id = p_trait_id)
    THEN
		BEGIN
			UPDATE `exhibition`.`art_traits`
			SET
			`trait` = p_new_trait
			WHERE `trait_id` = p_trait_id;
            
            SELECT 1 as return_code, 'Trait Updated Successfully' as status;
        END;
	ELSE
		SELECT -1 as return_code, 'Trait Doesnt exists' as status;
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_updateGalleryTrait` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_updateGalleryTrait`(
	IN p_gallery_id INT,
    IN p_trait_id INT,
    IN p_new_trait varchar(256)
)
BEGIN
	IF EXISTS (SELECT 1 FROM gallery_traits WHERE gallery_id = p_gallery_id and trait_id = p_trait_id)
    THEN
		BEGIN
			UPDATE `exhibition`.`gallery_traits`
			SET
			`trait` = p_new_trait
			WHERE `trait_id` = p_trait_id;
            
            SELECT 1 as return_code, 'Trait Updated Successfully' as status;
        END;
	ELSE
		SELECT -1 as return_code, 'Trait Doesnt exists' as status;
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_uploadArt` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_uploadArt`(
	IN p_name varchar(128),
    IN p_desc varchar(1024),
    IN p_artist_id int,
    IN p_picture varchar(256),
	IN p_gallery_id int
)
BEGIN
	INSERT INTO `exhibition`.`art`
	(`name`,`description`,`artist_id`,`picture`,`is_deleted`)
	VALUES
	(p_name,p_desc,p_artist_id,p_picture,0);
	
    
    IF (p_gallery_id is not null)
    THEN
		SET @art_id = LAST_INSERT_ID();
        
        INSERT INTO `exhibition`.`gallery_art`
		(`gallery_id`,`art_id`)
		VALUES
		(p_gallery_id, @art_id);

    END IF;

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

-- Dump completed on 2017-05-01  0:53:08
