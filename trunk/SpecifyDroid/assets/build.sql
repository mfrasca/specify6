DROP TABLE IF EXISTS `trip`;
CREATE TABLE `trip` (
  `_id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `Name` VAR CHAR(32),
  `Type` VAR CHAR(16),
  `Notes` VAR CHAR(255) DEFAULT NULL,
  `TripDate` DATE NOT NULL,
  `FirstName1` VAR CHAR(32) DEFAULT NULL,
  `LastName1` VAR CHAR(32) DEFAULT NULL,
  `FirstName2` VAR CHAR(32) DEFAULT NULL,
  `LastName2` VAR CHAR(32) DEFAULT NULL,
  `FirstName3` VAR CHAR(32) DEFAULT NULL,
  `LastName3` VAR CHAR(32) DEFAULT NULL,
  `TimestampCreated` DATETIME NOT NULL,
  `TimestampModified` DATETIME NOT NULL
);

DROP TABLE IF EXISTS `tripdatadef`;
CREATE TABLE `tripdatadef` (
  `_id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `Name` VAR CHAR(32) NOT NULL,
  `Title` VAR CHAR(32) NOT NULL,
  `DataType` SHORT NOT NULL,
  `ColumnIndex` INTEGER NOT NULL,
  `TripID` INTEGER NOT NULL,
  CONSTRAINT `FKTRIPDATADEFTRIPID` FOREIGN KEY (`TripID`) REFERENCES `trip` (`TripID`)
);

DROP TABLE IF EXISTS `tripdatacell`;
CREATE TABLE `tripdatacell` (
  `_id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `TripDataDefID` INTEGER,
  `TripID` INTEGER,
  `TripRowIndex` INTEGER,
  `Data` VAR CHAR(255),
  CONSTRAINT `FKTRIPDATACELLTRIPDATADEFID` FOREIGN KEY (`TripDataDefID`) REFERENCES `tripdatadef` (`TripDataDefID`),
  CONSTRAINT `FKTRIPDATACELLTRIPID` FOREIGN KEY (`TripID`) REFERENCES `trip` (`TripID`)
);


DROP TABLE IF EXISTS `taxon`;
CREATE TABLE `taxon` (
  `_id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `ParentID` INTEGER,
  `Name` VAR CHAR(128),
  `RankID` SHORT
);