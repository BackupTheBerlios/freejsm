-- MySQL dump 10.9
--
-- Host: localhost    Database: freejsm
-- ------------------------------------------------------
-- Server version	4.1.10-Debian_4-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

--
-- Table structure for table `ARTICLE`
--

DROP TABLE IF EXISTS `ARTICLE`;
CREATE TABLE `ARTICLE` (
  `AR_ID` int(11) unsigned NOT NULL auto_increment,
  `FO_ID` int(11) unsigned NOT NULL default '0',
  `CA_ID` int(11) unsigned NOT NULL default '0',
  `TVA_ID` int(11) unsigned NOT NULL default '0',
  `AR_CODE` varchar(255) default NULL,
  `AR_DESIGN` varchar(255) default NULL,
  `AR_UNIT` varchar(255) default NULL,
  `AR_COND` int(11) unsigned default NULL,
  `AR_ACTIV` tinyint(3) unsigned NOT NULL default '0',
  PRIMARY KEY  (`AR_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ARTICLE`
--


/*!40000 ALTER TABLE `ARTICLE` DISABLE KEYS */;
LOCK TABLES `ARTICLE` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `ARTICLE` ENABLE KEYS */;

--
-- Table structure for table `CATEGORIE`
--

DROP TABLE IF EXISTS `CATEGORIE`;
CREATE TABLE `CATEGORIE` (
  `CA_ID` int(11) unsigned NOT NULL auto_increment,
  `CA_PARENT` int(11) unsigned default NULL,
  `CA_NOM` varchar(255) default NULL,
  `CA_VISIBLE` tinyint(3) unsigned default NULL,
  PRIMARY KEY  (`CA_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CATEGORIE`
--


/*!40000 ALTER TABLE `CATEGORIE` DISABLE KEYS */;
LOCK TABLES `CATEGORIE` WRITE;
INSERT INTO `CATEGORIE` VALUES (1,0,'ABD',1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `CATEGORIE` ENABLE KEYS */;

--
-- Table structure for table `COMMANDE`
--

DROP TABLE IF EXISTS `COMMANDE`;
CREATE TABLE `COMMANDE` (
  `CO_ID` int(11) unsigned NOT NULL auto_increment,
  `UTIL_ID` int(11) unsigned NOT NULL default '0',
  `ST_ID` int(11) unsigned NOT NULL default '0',
  `CO_DATE` date NOT NULL default '0000-00-00',
  PRIMARY KEY  (`CO_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `COMMANDE`
--


/*!40000 ALTER TABLE `COMMANDE` DISABLE KEYS */;
LOCK TABLES `COMMANDE` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `COMMANDE` ENABLE KEYS */;

--
-- Table structure for table `CONTENU_COMMANDE`
--

DROP TABLE IF EXISTS `CONTENU_COMMANDE`;
CREATE TABLE `CONTENU_COMMANDE` (
  `CC_ID` int(11) unsigned NOT NULL auto_increment,
  `AR_ID` int(11) unsigned NOT NULL default '0',
  `CC_QT` float unsigned NOT NULL default '0',
  `AR_PRIX` float unsigned NOT NULL default '0',
  `CO_ID` int(11) unsigned NOT NULL default '0',
  PRIMARY KEY  (`CC_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CONTENU_COMMANDE`
--


/*!40000 ALTER TABLE `CONTENU_COMMANDE` DISABLE KEYS */;
LOCK TABLES `CONTENU_COMMANDE` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `CONTENU_COMMANDE` ENABLE KEYS */;

--
-- Table structure for table `CONTENU_ENTREE`
--

DROP TABLE IF EXISTS `CONTENU_ENTREE`;
CREATE TABLE `CONTENU_ENTREE` (
  `CE_ID` int(11) unsigned NOT NULL auto_increment,
  `AR_ID` int(11) unsigned NOT NULL default '0',
  `CE_QT` float unsigned NOT NULL default '0',
  `AR_PRIX` float unsigned NOT NULL default '0',
  `ENT_ID` int(11) unsigned NOT NULL default '0',
  PRIMARY KEY  (`CE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CONTENU_ENTREE`
--


/*!40000 ALTER TABLE `CONTENU_ENTREE` DISABLE KEYS */;
LOCK TABLES `CONTENU_ENTREE` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `CONTENU_ENTREE` ENABLE KEYS */;

--
-- Table structure for table `CONTENU_SORTIE`
--

DROP TABLE IF EXISTS `CONTENU_SORTIE`;
CREATE TABLE `CONTENU_SORTIE` (
  `CS_ID` int(11) unsigned NOT NULL auto_increment,
  `AR_ID` int(11) unsigned NOT NULL default '0',
  `AR_PRIX` float unsigned NOT NULL default '0',
  `CS_QT` float unsigned NOT NULL default '0',
  `SORT_ID` int(11) unsigned NOT NULL default '0',
  PRIMARY KEY  (`CS_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CONTENU_SORTIE`
--


/*!40000 ALTER TABLE `CONTENU_SORTIE` DISABLE KEYS */;
LOCK TABLES `CONTENU_SORTIE` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `CONTENU_SORTIE` ENABLE KEYS */;

--
-- Table structure for table `CONTENU_STOCK`
--

DROP TABLE IF EXISTS `CONTENU_STOCK`;
CREATE TABLE `CONTENU_STOCK` (
  `CS_ID` int(11) unsigned NOT NULL auto_increment,
  `ST_ID` int(11) unsigned NOT NULL default '0',
  `AR_ID` int(11) unsigned NOT NULL default '0',
  `CS_QT` float default NULL,
  `CS_SEUIL` float default NULL,
  `CS_EC` tinyint(3) unsigned default NULL,
  `CS_PRIXUNITAIRE` float default NULL,
  PRIMARY KEY  (`CS_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CONTENU_STOCK`
--


/*!40000 ALTER TABLE `CONTENU_STOCK` DISABLE KEYS */;
LOCK TABLES `CONTENU_STOCK` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `CONTENU_STOCK` ENABLE KEYS */;

--
-- Table structure for table `ENTREE`
--

DROP TABLE IF EXISTS `ENTREE`;
CREATE TABLE `ENTREE` (
  `ENT_ID` int(11) unsigned NOT NULL auto_increment,
  `UTIL_ID` int(11) unsigned NOT NULL default '0',
  `ST_ID` int(11) unsigned NOT NULL default '0',
  `ENT_DATE` date NOT NULL default '0000-00-00',
  PRIMARY KEY  (`ENT_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ENTREE`
--


/*!40000 ALTER TABLE `ENTREE` DISABLE KEYS */;
LOCK TABLES `ENTREE` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `ENTREE` ENABLE KEYS */;

--
-- Table structure for table `EXT_JO`
--

DROP TABLE IF EXISTS `EXT_JO`;
CREATE TABLE `EXT_JO` (
  `JO_ID` int(11) unsigned NOT NULL auto_increment,
  `UTIL_ID` int(11) unsigned NOT NULL default '0',
  `JO_TABNOM` varchar(255) default NULL,
  `JO_PRECVAL` text,
  `JO_NOUVAL` text,
  `JO_OPERATION` char(1) default NULL,
  PRIMARY KEY  (`JO_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `EXT_JO`
--


/*!40000 ALTER TABLE `EXT_JO` DISABLE KEYS */;
LOCK TABLES `EXT_JO` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `EXT_JO` ENABLE KEYS */;

--
-- Table structure for table `FONCTION`
--

DROP TABLE IF EXISTS `FONCTION`;
CREATE TABLE `FONCTION` (
  `ST_ID` int(11) unsigned NOT NULL default '0',
  `UTIL_ID` int(11) unsigned NOT NULL default '0',
  `FO_LEVEL` tinyint(3) unsigned NOT NULL default '0',
  PRIMARY KEY  (`ST_ID`,`UTIL_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `FONCTION`
--


/*!40000 ALTER TABLE `FONCTION` DISABLE KEYS */;
LOCK TABLES `FONCTION` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `FONCTION` ENABLE KEYS */;

--
-- Table structure for table `FOURNISSEUR`
--

DROP TABLE IF EXISTS `FOURNISSEUR`;
CREATE TABLE `FOURNISSEUR` (
  `FO_ID` int(11) unsigned NOT NULL auto_increment,
  `FO_NOM` varchar(255) default NULL,
  `FO_VILLE` varchar(255) default NULL,
  `FO_CP` varchar(5) default NULL,
  `FO_TEL` varchar(14) default NULL,
  `FO_MAIL` varchar(255) default NULL,
  `FO_FAX` varchar(14) default NULL,
  `FO_ADRESSE` varchar(255) default NULL,
  `FO_ACTIV` tinyint(3) unsigned default NULL,
  PRIMARY KEY  (`FO_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `FOURNISSEUR`
--


/*!40000 ALTER TABLE `FOURNISSEUR` DISABLE KEYS */;
LOCK TABLES `FOURNISSEUR` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `FOURNISSEUR` ENABLE KEYS */;

--
-- Table structure for table `JEN`
--

DROP TABLE IF EXISTS `JEN`;
CREATE TABLE `JEN` (
  `JEN_QT` float NOT NULL default '0',
  `AR_ID` int(10) unsigned NOT NULL default '0',
  `JEN_PRIXUNITAIRE` float unsigned NOT NULL default '0',
  `JEN_DATE` date NOT NULL default '0000-00-00',
  `ST_ID` int(10) unsigned NOT NULL default '0',
  ` JEN_ID` int(10) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (` JEN_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `JEN`
--


/*!40000 ALTER TABLE `JEN` DISABLE KEYS */;
LOCK TABLES `JEN` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `JEN` ENABLE KEYS */;

--
-- Table structure for table `JEP`
--

DROP TABLE IF EXISTS `JEP`;
CREATE TABLE `JEP` (
  `JEP_ID` int(10) unsigned NOT NULL auto_increment,
  `JEP_QT` float NOT NULL default '0',
  `AR_ID` int(10) unsigned NOT NULL default '0',
  `JEP_PRIXUNITAIRE` float NOT NULL default '0',
  `ST_ID` int(10) unsigned NOT NULL default '0',
  `JEP_DATE` date NOT NULL default '0000-00-00',
  PRIMARY KEY  (`JEP_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `JEP`
--


/*!40000 ALTER TABLE `JEP` DISABLE KEYS */;
LOCK TABLES `JEP` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `JEP` ENABLE KEYS */;

--
-- Table structure for table `JOURNAL`
--

DROP TABLE IF EXISTS `JOURNAL`;
CREATE TABLE `JOURNAL` (
  `JO_ID` int(11) unsigned NOT NULL auto_increment,
  `UTIL_ID` int(11) unsigned NOT NULL default '0',
  `JO_TABNOM` varchar(255) default NULL,
  `JO_PRECVAL` text,
  `JO_NOUVAL` text,
  `JO_OPERATION` char(1) default NULL,
  PRIMARY KEY  (`JO_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `JOURNAL`
--


/*!40000 ALTER TABLE `JOURNAL` DISABLE KEYS */;
LOCK TABLES `JOURNAL` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `JOURNAL` ENABLE KEYS */;

--
-- Table structure for table `PARAMETRE`
--

DROP TABLE IF EXISTS `PARAMETRE`;
CREATE TABLE `PARAMETRE` (
  `PM_CLEF` varchar(255) NOT NULL default '',
  `PM_VALEUR` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`PM_CLEF`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `PARAMETRE`
--


/*!40000 ALTER TABLE `PARAMETRE` DISABLE KEYS */;
LOCK TABLES `PARAMETRE` WRITE;
INSERT INTO `PARAMETRE` VALUES ('CG_RS','A.S.E.I. Montsaunes'),('CG_ADDR','18 Rue colbert\nBat B Apt 45'),('CG_CP','31260'),('CG_VILLE','Salit du Salat'),('CG_TEL','05.61.90.62.09'),('CG_FAX','05.61.90.62.08'),('CG_MAIL','truc@asei.Fr'),('RG_NOM',''),('RG_PRENOM',''),('RG_ADDR',''),('RG_CP',''),('RG_VILLE',''),('RG_TEL',''),('RG_FAX',''),('RG_MAIL','');
UNLOCK TABLES;
/*!40000 ALTER TABLE `PARAMETRE` ENABLE KEYS */;

--
-- Table structure for table `SORTIE`
--

DROP TABLE IF EXISTS `SORTIE`;
CREATE TABLE `SORTIE` (
  `SORT_ID` int(11) unsigned NOT NULL auto_increment,
  `UTIL_ID` int(11) unsigned NOT NULL default '0',
  `ST_ID` int(11) unsigned NOT NULL default '0',
  `SORT_DATE` date NOT NULL default '0000-00-00',
  PRIMARY KEY  (`SORT_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `SORTIE`
--


/*!40000 ALTER TABLE `SORTIE` DISABLE KEYS */;
LOCK TABLES `SORTIE` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `SORTIE` ENABLE KEYS */;

--
-- Table structure for table `STOCK`
--

DROP TABLE IF EXISTS `STOCK`;
CREATE TABLE `STOCK` (
  `ST_ID` int(11) unsigned NOT NULL auto_increment,
  `ST_NOM` varchar(255) default NULL,
  `ST_VILLE` varchar(255) default NULL,
  `ST_ADRESSE` varchar(255) default NULL,
  `ST_CP` varchar(5) default NULL,
  `ST_TEL` varchar(20) default NULL,
  `ST_MAIL` varchar(255) default NULL,
  `ST_FAX` varchar(20) default NULL,
  `ST_ACTIV` tinyint(3) unsigned default NULL,
  PRIMARY KEY  (`ST_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `STOCK`
--


/*!40000 ALTER TABLE `STOCK` DISABLE KEYS */;
LOCK TABLES `STOCK` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `STOCK` ENABLE KEYS */;

--
-- Table structure for table `TVA`
--

DROP TABLE IF EXISTS `TVA`;
CREATE TABLE `TVA` (
  `TVA_ID` int(11) unsigned NOT NULL auto_increment,
  `TVA_TX` float default NULL,
  `TVA_ACTIV` tinyint(3) unsigned default NULL,
  `TVA_DESIGN` varchar(255) default NULL,
  PRIMARY KEY  (`TVA_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TVA`
--


/*!40000 ALTER TABLE `TVA` DISABLE KEYS */;
LOCK TABLES `TVA` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `TVA` ENABLE KEYS */;

--
-- Table structure for table `UTILISATEUR`
--

DROP TABLE IF EXISTS `UTILISATEUR`;
CREATE TABLE `UTILISATEUR` (
  `UTIL_ID` int(11) unsigned NOT NULL auto_increment,
  `UTIL_NOM` varchar(255) default NULL,
  `UTIL_PRENOM` varchar(255) default NULL,
  `UTIL_TELEPHONE` varchar(20) default NULL,
  `UTIL_MAIL` varchar(255) default NULL,
  `UTIL_LOGIN` varchar(255) default NULL,
  `UTIL_PASSWORD` varchar(255) default NULL,
  `UTIL_ACTIV` tinyint(4) unsigned default NULL,
  PRIMARY KEY  (`UTIL_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `UTILISATEUR`
--


/*!40000 ALTER TABLE `UTILISATEUR` DISABLE KEYS */;
LOCK TABLES `UTILISATEUR` WRITE;
INSERT INTO `UTILISATEUR` VALUES (0,'Admin','admin','','','Admin','admin',1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `UTILISATEUR` ENABLE KEYS */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

