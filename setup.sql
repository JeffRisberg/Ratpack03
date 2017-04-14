# noinspection SqlNoDataSourceInspectionForFile

DROP DATABASE IF EXISTS `ratpack03`;

CREATE DATABASE `ratpack03`;

USE `ratpack03`;

CREATE TABLE `USER` (
  ID INT PRIMARY KEY AUTO_INCREMENT,
  `USERNAME` VARCHAR(255),
  `EMAIL` VARCHAR(255),
  DATE_CREATED DATE,
  LAST_UPDATED DATE
);

INSERT INTO `USER` (USERNAME, EMAIL) VALUES('Luke Daley','luke@gmail.com');
INSERT INTO `USER` (USERNAME, EMAIL) VALUES('Rob Fletch','rob@gmail.com');
INSERT INTO `USER` (USERNAME, EMAIL) VALUES('Dan Woods','dan@gmail.com');

CREATE TABLE `EVENT` (
  ID INT PRIMARY KEY AUTO_INCREMENT,
  `TYPE` VARCHAR(255),
  `DETAIL` VARCHAR(255),
  DATE_CREATED DATE,
  LAST_UPDATED DATE
);

CREATE TABLE `METRIC` (
  ID INT PRIMARY KEY AUTO_INCREMENT,
  `NAME` VARCHAR(255),
  DATE_CREATED DATE,
  LAST_UPDATED DATE
);

CREATE TABLE `CHARITY` (
  ID INT PRIMARY KEY AUTO_INCREMENT,
  `NAME` VARCHAR(255),
  `EIN` VARCHAR(255),
  DATE_CREATED DATE,
  LAST_UPDATED DATE
);