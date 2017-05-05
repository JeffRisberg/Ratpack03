# noinspection SqlNoDataSourceInspectionForFile

DROP DATABASE IF EXISTS `ratpack03`;

CREATE DATABASE `ratpack03`;

USE `ratpack03`;

CREATE TABLE `USER` (
  ID           INT PRIMARY KEY AUTO_INCREMENT,
  `EMAIL`      VARCHAR(255),
  `FIRSTNAME`  VARCHAR(255),
  `LASTNAME`   VARCHAR(255),
  `VALIDATED`  BIT(1) DEFAULT FALSE,
  `ADDRESS1`   VARCHAR(255),
  `ADDRESS2`   VARCHAR(255),
  `CITY`       VARCHAR(255),
  `STATE`      VARCHAR(255),
  DATE_CREATED DATE,
  LAST_UPDATED DATE
);

INSERT INTO `USER` (EMAIL, FIRSTNAME, LASTNAME, ADDRESS1, ADDRESS2, CITY, STATE, DATE_CREATED)
VALUES ('luke@gmail.com', 'Luke', 'Daily', '', '', 'Palo Alto', 'CA', '2016-06-15');
INSERT INTO `USER` (EMAIL, FIRSTNAME, LASTNAME, ADDRESS1, ADDRESS2, CITY, STATE, DATE_CREATED)
VALUES ('rob@gmail.com', 'Rob', 'Fletch', '', '', 'Menlo Park', 'CA', '2017-01-03');
INSERT INTO `USER` (EMAIL, FIRSTNAME, LASTNAME, VALIDATED, ADDRESS1, ADDRESS2, CITY, STATE, DATE_CREATED)
VALUES ('dan@gmail.com', 'Dan', 'Woods', 1, '101 Redwood Shores Parkway', '', 'Redwood City', 'CA', '2017-02-27');


CREATE TABLE `DONATION` (
  ID           INT PRIMARY KEY AUTO_INCREMENT,
  `USER_ID`    BIGINT NULL,
  CHARITY_NAME VARCHAR(255) NOT NULL,
  AMOUNT       INT NOT NULL,
  DATE_CREATED DATE,
  LAST_UPDATED DATE
);


UPDATE `USER`
SET last_updated = now();

DROP DATABASE IF EXISTS `ratpack03Aux`;

CREATE DATABASE `ratpack03Aux`;

USE `ratpack03Aux`;

CREATE TABLE `LOGGING_EVENT` (
  ID           INT PRIMARY KEY AUTO_INCREMENT,
  `EVENT_DATE` TIMESTAMP default now(),
  `USER_EMAIL` VARCHAR(255),
  `TYPE`       VARCHAR(255),
  `DETAIL`     VARCHAR(255)
);

INSERT INTO `LOGGING_EVENT` (EVENT_DATE, USER_EMAIL, TYPE, DETAIL)
VALUES ('2017-02-27 15:20:00', 'admin@gmail.com', "Setup", "Tables created");
