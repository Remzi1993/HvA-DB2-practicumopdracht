-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema big_five_safari
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema big_five_safari
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `big_five_safari` ;
USE `big_five_safari` ;

-- -----------------------------------------------------
-- Table `big_five_safari`.`Accommodatie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `big_five_safari`.`Accommodatie` ;

CREATE TABLE IF NOT EXISTS `big_five_safari`.`Accommodatie` (
  `accommodatie_code` VARCHAR(5) NOT NULL,
  `naam` VARCHAR(50) NOT NULL,
  `stad` VARCHAR(50) NOT NULL,
  `land` VARCHAR(50) NOT NULL,
  `kamer` VARCHAR(100) NOT NULL,
  `personen` INT NOT NULL,
  PRIMARY KEY (`accommodatie_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `big_five_safari`.`Hotel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `big_five_safari`.`Hotel` ;

CREATE TABLE IF NOT EXISTS `big_five_safari`.`Hotel` (
  `accommodatie_code` VARCHAR(5) NOT NULL,
  `prijs_per_nacht` DECIMAL(7,2) NOT NULL,
  `ontbijt` BIT NOT NULL,
  INDEX `fk_Hotel_Accommodatie_idx` (`accommodatie_code` ASC) VISIBLE,
  UNIQUE INDEX `accommodatie_code_UNIQUE` (`accommodatie_code` ASC) VISIBLE,
  PRIMARY KEY (`accommodatie_code`),
  CONSTRAINT `fk_Hotel_Accommodatie`
    FOREIGN KEY (`accommodatie_code`)
    REFERENCES `big_five_safari`.`Accommodatie` (`accommodatie_code`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `big_five_safari`.`Lodge`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `big_five_safari`.`Lodge` ;

CREATE TABLE IF NOT EXISTS `big_five_safari`.`Lodge` (
  `accommodatie_code` VARCHAR(5) NOT NULL,
  `prijs_per_week` DECIMAL(7,2) NOT NULL,
  `autohuur` BIT NOT NULL,
  INDEX `fk_Lodge_Accommodatie1_idx` (`accommodatie_code` ASC) VISIBLE,
  UNIQUE INDEX `accommodatie_code_UNIQUE` (`accommodatie_code` ASC) VISIBLE,
  PRIMARY KEY (`accommodatie_code`),
  CONSTRAINT `fk_Lodge_Accommodatie1`
    FOREIGN KEY (`accommodatie_code`)
    REFERENCES `big_five_safari`.`Accommodatie` (`accommodatie_code`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `big_five_safari`.`Reiziger`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `big_five_safari`.`Reiziger` ;

CREATE TABLE IF NOT EXISTS `big_five_safari`.`Reiziger` (
  `reiziger_code` VARCHAR(15) NOT NULL,
  `voornaam` VARCHAR(30) NOT NULL,
  `achternaam` VARCHAR(30) NOT NULL,
  `adres` VARCHAR(100) NOT NULL,
  `postcode` CHAR(7) NOT NULL,
  `plaats` VARCHAR(50) NOT NULL,
  `land` VARCHAR(50) NOT NULL,
  `hoofdreiziger` VARCHAR(15) NULL,
  PRIMARY KEY (`reiziger_code`),
  INDEX `fk_Reiziger_Reiziger1_idx` (`hoofdreiziger` ASC) VISIBLE,
  CONSTRAINT `fk_Reiziger_Reiziger1`
    FOREIGN KEY (`hoofdreiziger`)
    REFERENCES `big_five_safari`.`Reiziger` (`reiziger_code`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `big_five_safari`.`Reservering`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `big_five_safari`.`Reservering` ;

CREATE TABLE IF NOT EXISTS `big_five_safari`.`Reservering` (
  `reservering_id` INT NOT NULL AUTO_INCREMENT,
  `accommodatie_code` VARCHAR(5) NOT NULL,
  `reiziger_code` VARCHAR(15) NOT NULL,
  `aankomst_datum` DATE NOT NULL,
  `vertrek_datum` DATE NOT NULL,
  `betaald` BIT NOT NULL,
  PRIMARY KEY (`reservering_id`),
  INDEX `fk_table1_Accommodatie1_idx` (`accommodatie_code` ASC) VISIBLE,
  INDEX `fk_table1_Reiziger1_idx` (`reiziger_code` ASC) INVISIBLE,
  CONSTRAINT `fk_table1_Accommodatie1`
    FOREIGN KEY (`accommodatie_code`)
    REFERENCES `big_five_safari`.`Accommodatie` (`accommodatie_code`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_table1_Reiziger1`
    FOREIGN KEY (`reiziger_code`)
    REFERENCES `big_five_safari`.`Reiziger` (`reiziger_code`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `big_five_safari` ;

-- -----------------------------------------------------
-- Placeholder table for view `big_five_safari`.`Boekingsoverzicht`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `big_five_safari`.`Boekingsoverzicht` (`reservering_id` INT, `aankomst_datum` INT, `vertrek_datum` INT, `betaald` INT, `accommodatie_code` INT, `reiziger_code` INT, `reiziger` INT, `naam` INT, `stad` INT, `land` INT);

-- -----------------------------------------------------
-- function GeboektOp
-- -----------------------------------------------------

USE `big_five_safari`;
DROP function IF EXISTS `big_five_safari`.`GeboektOp`;

DELIMITER $$
USE `big_five_safari`$$
# HvA FDMCI Databases 2 practicumopdracht - week 3E
CREATE FUNCTION `GeboektOp`(pCode VARCHAR(5), pDatum DATE)
RETURNS varchar(15)
READS SQL DATA
DETERMINISTIC
BEGIN
DECLARE reizigerCode VARCHAR(15);

SELECT R.reiziger_code
FROM reservering R
WHERE (pDatum BETWEEN R.aankomst_datum AND R.vertrek_datum) AND R.accommodatie_code = pCode INTO reizigerCode;

RETURN reizigerCode;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure verwijderAccommodatie
-- -----------------------------------------------------

USE `big_five_safari`;
DROP procedure IF EXISTS `big_five_safari`.`verwijderAccommodatie`;

DELIMITER $$
USE `big_five_safari`$$
# HvA FDMCI Databases 2 practicumopdracht - week 4C
CREATE PROCEDURE verwijderAccommodatie(IN accommodatieCode VARCHAR(5))
BEGIN
START TRANSACTION;
DELETE FROM accommodatie A 
WHERE A.accommodatie_code = accommodatieCode
AND NOT EXISTS (select * from reservering R where R.vertrek_datum < CURDATE() AND R.betaald = 0);
COMMIT;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- View `big_five_safari`.`Boekingsoverzicht`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `big_five_safari`.`Boekingsoverzicht`;
DROP VIEW IF EXISTS `big_five_safari`.`Boekingsoverzicht` ;
USE `big_five_safari`;
# HvA FDMCI Databases 2 practicumopdracht - week 4A
CREATE  OR REPLACE VIEW `Boekingsoverzicht` AS
SELECT R.reservering_id, R.aankomst_datum, R.vertrek_datum, R.betaald, R.accommodatie_code,
R.reiziger_code, CONCAT(RE.voornaam, ' ', RE.achternaam) AS reiziger, A.naam, A.stad, A.land
FROM reservering R
INNER JOIN reiziger RE ON RE.reiziger_code = R.reiziger_code
INNER JOIN accommodatie A ON A.accommodatie_code = R.accommodatie_code
ORDER BY R.aankomst_datum;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `big_five_safari`.`Accommodatie`
-- -----------------------------------------------------
START TRANSACTION;
USE `big_five_safari`;
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('HIBZ1', 'Indigo Beach', 'Zanzibar', 'Tanzania', 'Swahili-stijl budget tweepersoonskamer', 2);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('HIBZ2', 'Indigo Beach', 'Zanzibar', 'Tanzania', 'Bungalow met uitzicht op de tuin', 8);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('HZBK1', 'Zanbluu Beach Hotel', 'Kiwengwa', 'Tanzania', 'Superior Suite met uitzicht op zee en eigen zwembad', 2);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('HEVO1', 'Etosha Village', 'Okaukuejo', 'Namibië', 'Standaard kamer met 2 Bedden', 2);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('HSCW1', 'Safari Court Hotel', 'Windhoek', 'Namibië', 'Classic tweepersoonskamer met 2 aparte bedden', 2);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('HPHC1', 'Premier Hotel Cape Town', 'Kaapstad', 'Zuid-Afrika', 'Familie loft met queensize bed en 2 aparte bedden', 4);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('HRMS1', 'River Manor Boutique Hotel', 'Stellen Bosch', 'Zuid-Afrika', 'Loft studio kamer', 2);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('HRMS2', 'River Manor Boutique Hotel', 'Stellen Bosch', 'Zuid-Afrika', 'Superior tweepersoonskamer', 2);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('HHIH1', 'Holiday Inn', 'Harare', 'Zimbabwe', 'Standaard kamer', 3);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('HRHB1', 'Bulawayo Rainbow Hotel', 'Bulawayo', 'Zimbabwe', 'Deluxe tweepersoonskamer ', 2);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('LAAC1', 'Sasa Safari Camp', 'Outjo', 'Namibië', 'Tweepersoonskamer met Uitzicht', 2);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('LASL1', 'Africa Safari Lodge', 'Mariental', 'Namibië', 'Familiekamer', 4);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('LIML1', 'Immanuel Wilderness Lodge', 'Windhoek', 'Namibië', 'Tweepersoonskamer met Uitzicht op de Tuin', 2);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('LBBL1', 'Baby Bush Lodge', 'Kiwengwa', 'Tanzania', 'Bruidssuite met Balkon', 2);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('LNZL1', 'Ngoma Zanga Lodge', 'Livingstone', 'Zambia', 'Deluxe kamer met kingsize bed', 3);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('LMIL1', 'Mika Lodge', 'Lusaka', 'Zambia', 'Executive Suite', 2);
INSERT INTO `big_five_safari`.`Accommodatie` (`accommodatie_code`, `naam`, `stad`, `land`, `kamer`, `personen`) VALUES ('LIEK1', 'Impala Ecolodge', 'Kisumu', 'Kenia', 'Luxe suite', 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `big_five_safari`.`Hotel`
-- -----------------------------------------------------
START TRANSACTION;
USE `big_five_safari`;
INSERT INTO `big_five_safari`.`Hotel` (`accommodatie_code`, `prijs_per_nacht`, `ontbijt`) VALUES ('HIBZ1', 75, 1);
INSERT INTO `big_five_safari`.`Hotel` (`accommodatie_code`, `prijs_per_nacht`, `ontbijt`) VALUES ('HIBZ2', 243, 0);
INSERT INTO `big_five_safari`.`Hotel` (`accommodatie_code`, `prijs_per_nacht`, `ontbijt`) VALUES ('HZBK1', 369, 1);
INSERT INTO `big_five_safari`.`Hotel` (`accommodatie_code`, `prijs_per_nacht`, `ontbijt`) VALUES ('HEVO1', 191, 1);
INSERT INTO `big_five_safari`.`Hotel` (`accommodatie_code`, `prijs_per_nacht`, `ontbijt`) VALUES ('HSCW1', 105, 0);
INSERT INTO `big_five_safari`.`Hotel` (`accommodatie_code`, `prijs_per_nacht`, `ontbijt`) VALUES ('HPHC1', 127, 0);
INSERT INTO `big_five_safari`.`Hotel` (`accommodatie_code`, `prijs_per_nacht`, `ontbijt`) VALUES ('HRMS1', 99, 1);
INSERT INTO `big_five_safari`.`Hotel` (`accommodatie_code`, `prijs_per_nacht`, `ontbijt`) VALUES ('HRMS2', 166, 1);
INSERT INTO `big_five_safari`.`Hotel` (`accommodatie_code`, `prijs_per_nacht`, `ontbijt`) VALUES ('HHIH1', 64, 0);
INSERT INTO `big_five_safari`.`Hotel` (`accommodatie_code`, `prijs_per_nacht`, `ontbijt`) VALUES ('HRHB1', 90, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `big_five_safari`.`Lodge`
-- -----------------------------------------------------
START TRANSACTION;
USE `big_five_safari`;
INSERT INTO `big_five_safari`.`Lodge` (`accommodatie_code`, `prijs_per_week`, `autohuur`) VALUES ('LAAC1', 447, 0);
INSERT INTO `big_five_safari`.`Lodge` (`accommodatie_code`, `prijs_per_week`, `autohuur`) VALUES ('LASL1', 946, 1);
INSERT INTO `big_five_safari`.`Lodge` (`accommodatie_code`, `prijs_per_week`, `autohuur`) VALUES ('LIML1', 788, 1);
INSERT INTO `big_five_safari`.`Lodge` (`accommodatie_code`, `prijs_per_week`, `autohuur`) VALUES ('LBBL1', 939, 1);
INSERT INTO `big_five_safari`.`Lodge` (`accommodatie_code`, `prijs_per_week`, `autohuur`) VALUES ('LNZL1', 899, 1);
INSERT INTO `big_five_safari`.`Lodge` (`accommodatie_code`, `prijs_per_week`, `autohuur`) VALUES ('LMIL1', 1091, 1);
INSERT INTO `big_five_safari`.`Lodge` (`accommodatie_code`, `prijs_per_week`, `autohuur`) VALUES ('LIEK1', 1182, 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `big_five_safari`.`Reiziger`
-- -----------------------------------------------------
START TRANSACTION;
USE `big_five_safari`;
INSERT INTO `big_five_safari`.`Reiziger` (`reiziger_code`, `voornaam`, `achternaam`, `adres`, `postcode`, `plaats`, `land`, `hoofdreiziger`) VALUES ('MeijerP', 'Pieter', 'Meijer', 'Expeditiestraat 2', '1135 GB', 'Edam', 'Nederland', NULL);
INSERT INTO `big_five_safari`.`Reiziger` (`reiziger_code`, `voornaam`, `achternaam`, `adres`, `postcode`, `plaats`, `land`, `hoofdreiziger`) VALUES ('FonteijnC', 'Claar', 'Fonteijn', 'Castricummerwerf 70', '1901 RF', 'Castricum', 'Nederland', 'MeijerP');
INSERT INTO `big_five_safari`.`Reiziger` (`reiziger_code`, `voornaam`, `achternaam`, `adres`, `postcode`, `plaats`, `land`, `hoofdreiziger`) VALUES ('NazariM', 'Moustafa', 'Nazari', 'Vlakdissen 12', '1648 HJ', 'Den Goorn', 'Nederland', NULL);
INSERT INTO `big_five_safari`.`Reiziger` (`reiziger_code`, `voornaam`, `achternaam`, `adres`, `postcode`, `plaats`, `land`, `hoofdreiziger`) VALUES ('FadilT', 'Tahera', 'Fadil', 'Houtsberg 16', '6091 NA', 'Leveroy', 'Nederland', NULL);
INSERT INTO `big_five_safari`.`Reiziger` (`reiziger_code`, `voornaam`, `achternaam`, `adres`, `postcode`, `plaats`, `land`, `hoofdreiziger`) VALUES ('FadilO', 'Omar', 'Fadil', 'Houtsberg 16', '6091 NA', 'Leveroy', 'Nederland', 'FadilT');
INSERT INTO `big_five_safari`.`Reiziger` (`reiziger_code`, `voornaam`, `achternaam`, `adres`, `postcode`, `plaats`, `land`, `hoofdreiziger`) VALUES ('NguyenR', 'Roy', 'Nguyen', 'Bukkemweg 1', '5081 CT', 'Hilvarenbeek', 'Nederland', NULL);
INSERT INTO `big_five_safari`.`Reiziger` (`reiziger_code`, `voornaam`, `achternaam`, `adres`, `postcode`, `plaats`, `land`, `hoofdreiziger`) VALUES ('JeremyD', 'Demitri', 'Jeremy', 'Rijksstraatweg 289', '3956 CP', 'Leersum', 'Nederland', NULL);
INSERT INTO `big_five_safari`.`Reiziger` (`reiziger_code`, `voornaam`, `achternaam`, `adres`, `postcode`, `plaats`, `land`, `hoofdreiziger`) VALUES ('JeremyB', 'Bram', 'Jeremy', 'Rijksstraatweg 289', '3956 CP', 'Leersum', 'Nederland', 'JeremyD');
INSERT INTO `big_five_safari`.`Reiziger` (`reiziger_code`, `voornaam`, `achternaam`, `adres`, `postcode`, `plaats`, `land`, `hoofdreiziger`) VALUES ('JeremyD1', 'Devid', 'Jeremy', 'De Kronkels 25', '3752 LM', 'Bunschoten', 'Nederland', 'JeremyD');
INSERT INTO `big_five_safari`.`Reiziger` (`reiziger_code`, `voornaam`, `achternaam`, `adres`, `postcode`, `plaats`, `land`, `hoofdreiziger`) VALUES ('WongH', 'Hu', 'Wong', 'Rembrandtstraat 7', '3662 HN', 'Oud-Bijerland', 'Nederland', NULL);
INSERT INTO `big_five_safari`.`Reiziger` (`reiziger_code`, `voornaam`, `achternaam`, `adres`, `postcode`, `plaats`, `land`, `hoofdreiziger`) VALUES ('MulderF', 'Frank', 'Mulder', 'Lakenblekerstraat 49A', '1431 GE', 'Aalsmeer', 'Nederland', NULL);
INSERT INTO `big_five_safari`.`Reiziger` (`reiziger_code`, `voornaam`, `achternaam`, `adres`, `postcode`, `plaats`, `land`, `hoofdreiziger`) VALUES ('MulderD', 'Derek', 'Mulder', 'Lakenblekerstraat 49A', '1431 GE', 'Aalsmeer', 'Nederland', 'MulderF');
INSERT INTO `big_five_safari`.`Reiziger` (`reiziger_code`, `voornaam`, `achternaam`, `adres`, `postcode`, `plaats`, `land`, `hoofdreiziger`) VALUES ('MulderS', 'Sophie', 'Mulder', 'Lakenblekerstraat 49A', '1431 GE', 'Aalsmeer', 'Nederland', 'MulderF');
INSERT INTO `big_five_safari`.`Reiziger` (`reiziger_code`, `voornaam`, `achternaam`, `adres`, `postcode`, `plaats`, `land`, `hoofdreiziger`) VALUES ('RijenF', 'Fred', 'van Rijen', 'Einsteinstraat 10', '1446 VG', 'Purmerend', 'Nederland', 'MulderF');

COMMIT;


-- -----------------------------------------------------
-- Data for table `big_five_safari`.`Reservering`
-- -----------------------------------------------------
START TRANSACTION;
USE `big_five_safari`;
INSERT INTO `big_five_safari`.`Reservering` (`reservering_id`, `accommodatie_code`, `reiziger_code`, `aankomst_datum`, `vertrek_datum`, `betaald`) VALUES (1, 'HIBZ2', 'FadilT', '2022-07-12', '2022-07-19', 0);
INSERT INTO `big_five_safari`.`Reservering` (`reservering_id`, `accommodatie_code`, `reiziger_code`, `aankomst_datum`, `vertrek_datum`, `betaald`) VALUES (2, 'HSCW1', 'JeremyD', '2022-07-10', '2022-07-19', 1);
INSERT INTO `big_five_safari`.`Reservering` (`reservering_id`, `accommodatie_code`, `reiziger_code`, `aankomst_datum`, `vertrek_datum`, `betaald`) VALUES (3, 'HPHC1', 'WongH', '2022-07-31', '2022-08-05', 0);
INSERT INTO `big_five_safari`.`Reservering` (`reservering_id`, `accommodatie_code`, `reiziger_code`, `aankomst_datum`, `vertrek_datum`, `betaald`) VALUES (4, 'LBBL1', 'FadilT', '2022-07-19', '2022-07-26', 0);
INSERT INTO `big_five_safari`.`Reservering` (`reservering_id`, `accommodatie_code`, `reiziger_code`, `aankomst_datum`, `vertrek_datum`, `betaald`) VALUES (5, 'LNZL1', 'MulderF', '2022-08-12', '2022-08-19', 1);
INSERT INTO `big_five_safari`.`Reservering` (`reservering_id`, `accommodatie_code`, `reiziger_code`, `aankomst_datum`, `vertrek_datum`, `betaald`) VALUES (6, 'HRHB1', 'NazariM', '2022-07-01', '2022-07-05', 0);
INSERT INTO `big_five_safari`.`Reservering` (`reservering_id`, `accommodatie_code`, `reiziger_code`, `aankomst_datum`, `vertrek_datum`, `betaald`) VALUES (7, 'LNZL1', 'NazariM', '2022-07-05', '2022-07-12', 0);
INSERT INTO `big_five_safari`.`Reservering` (`reservering_id`, `accommodatie_code`, `reiziger_code`, `aankomst_datum`, `vertrek_datum`, `betaald`) VALUES (8, 'HRMS1', 'NguyenR', '2022-07-24', '2022-07-26', 1);
INSERT INTO `big_five_safari`.`Reservering` (`reservering_id`, `accommodatie_code`, `reiziger_code`, `aankomst_datum`, `vertrek_datum`, `betaald`) VALUES (9, 'HPHC1', 'NguyenR', '2022-07-26', '2022-07-31', 1);
INSERT INTO `big_five_safari`.`Reservering` (`reservering_id`, `accommodatie_code`, `reiziger_code`, `aankomst_datum`, `vertrek_datum`, `betaald`) VALUES (10, 'LIEK1', 'NguyenR', '2022-08-01', '2022-08-08', 1);
INSERT INTO `big_five_safari`.`Reservering` (`reservering_id`, `accommodatie_code`, `reiziger_code`, `aankomst_datum`, `vertrek_datum`, `betaald`) VALUES (11, 'LAAC1', 'MeijerP', '2022-08-19', '2022-08-26', 0);
INSERT INTO `big_five_safari`.`Reservering` (`reservering_id`, `accommodatie_code`, `reiziger_code`, `aankomst_datum`, `vertrek_datum`, `betaald`) VALUES (12, 'LMIL1', 'MeijerP', '2022-08-26', '2022-09-02', 0);

COMMIT;

-- begin attached script 'script'
# HvA FDMCI Databases 2 practicumopdracht - week 3C
ALTER TABLE reservering
ADD CONSTRAINT check_vertrek_datum CHECK (vertrek_datum > aankomst_datum);

# HvA FDMCI Databases 2 practicumopdracht - week 4E
ALTER TABLE accommodatie
ADD CONSTRAINT valide_accommodatie_code CHECK (accommodatie_code REGEXP '^.{4}[0-9]$');

# HvA FDMCI Databases 2 practicumopdracht - week 4F
CREATE USER IF NOT EXISTS 'administratie'@'%' IDENTIFIED BY 'Vakantie2020';
GRANT SELECT ON big_five_safari.Boekingsoverzicht To 'administratie'@'%';
GRANT EXECUTE ON PROCEDURE big_five_safari.verwijderAccommodatie to 'administratie'@'%';

# Maak een test admin user aan voor deze practicumopdracht
CREATE USER IF NOT EXISTS 'Testadmin'@'%' IDENTIFIED BY 'DitIsEenDummyTestAccount';
GRANT ALL PRIVILEGES ON big_five_safari.* TO 'Testadmin'@'%';
-- end attached script 'script'
