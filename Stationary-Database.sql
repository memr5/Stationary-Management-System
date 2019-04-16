-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: stationary-db
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `history` (
  `time_stamp` timestamp(6) NOT NULL,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `profit` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES ('0000-00-00 00:00:00.000000',2,12,4,1000),('2019-04-14 19:37:45.535000',14,1,2,0),('2019-04-14 19:45:07.359000',14,1,2,6),('2019-04-14 20:17:47.668000',14,7,2,8),('2019-04-14 20:17:47.679000',14,5,1,5);
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `type_of_product` longtext NOT NULL,
  `product_name` longtext NOT NULL,
  `actual_price` int(11) NOT NULL,
  `selling_price` int(11) NOT NULL,
  `discount` int(11) NOT NULL,
  `specification` longtext NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'FILE','A4 size plastic  file',50,55,5,'A-4 size plastic file with blue base & transprant white cover & clip',8),(2,'FILE','A4 size plastic  file',50,55,5,'A-4 size plastic file with blue base & blue cover & clip',7),(3,'PAPER SHEET','A4 size writing pad spiral',60,64,7,'80 sheets with good quality of paper 75gsm',9),(4,'BOOK','Acknowledgement book',30,35,3,'80 sheets 75 gsm paper',9),(5,'PEN','Ball pen(Elasto grip)',10,15,0,'Comfi-tip 0.5mm (Elasto grip)',5),(6,'PEN','Ball pen for meeting',50,60,10,'Ball pen for meeting with EU logo print',9),(7,'CLIPS','Binder clips 41mm,32mm,19mm, 51mm',30,35,5,'Metal fold back clip',7),(8,'PINS','Board pins',30,34,10,'Round head(steel/bras)',9),(9,'PAPER SHEET','Brown paper sheet ',70,75,0,'Brown thick paper sheet',9),(10,'TAPE','Brown tape',30,33,0,'Packing tap 48mmx50m',0),(11,'PAPER SHEET','Bubble sheet',40,45,3,'Bubble sheet for packing roll (100mtr)',9),(12,'CARD HOLDER','Business card holder',100,120,10,'Business card holder 480 card with jacket',9),(13,'PAPER SHEET','Calculator paper roll',35,40,5,'Paper roll size width 57mm, dia 65mm, one ply',9),(14,'CLIPS','Clips 30mm',40,45,10,'Paper clip zig zag 30mm, bright electro nickel plated non-tear ends',9),(15,'CLIPS','Clips 50mm',45,50,10,'Paper clip zig zag 50mm,bright electro nickel plated non-tear ends',9),(16,'PINS','Colour push pins',35,40,5,'Colour push pin box (100pcs)',0),(17,'PEN','Correction pen',40,50,10,'Good quality correction pen(flammable & ozone free chemical)',9),(18,'TAPE','Correction tape',45,50,0,'PVC tape coated with wax',9),(19,'TAPE','Double side tape',46,55,10,'Double sided foam glued tape , suitable for 1/2 kg load size 24mmx3m',9),(20,'FILE','File folder A to Z and 1 to 31',60,67,2,'Thick Index file folder (size 35 x26 cms)',9),(21,'FILE','File separator A-4 size',60,70,10,'Paper separator A-4 size 27.5 gsm paper + punching',0),(22,'FILE','File separator small size',60,75,15,'Paper separator small -size 14 X 23cm + punching 27.5gsm',9),(23,'FILE','File stickers',10,15,0,'File stickers self adhesive size 19 x 6 cm with EU logo print',99),(24,'FILE','File stickers',10,12,0,'File stickers self adhesive size 19 x 4 cm with EU logo print',9),(25,'PEN','Gel pen',10,12,0,'Maxel tip, waterproof ink, comfortable grip, 0.5',9);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `type` int(11) NOT NULL,
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `password` tinytext NOT NULL,
  `user_name` tinytext NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,1,'vatsal_99','vatsalparsaniya'),(1,2,'memr5','memr5'),(0,14,'mmm','mmm'),(0,15,'fff','fff'),(0,16,'aaa','aaa'),(0,17,'xxx','xxx'),(0,18,'qqq','qqq');
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

-- Dump completed on 2019-04-15 19:04:31
