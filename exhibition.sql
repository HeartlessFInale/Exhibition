-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 02, 2017 at 03:13 PM
-- Server version: 5.7.14
-- PHP Version: 7.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
--
-- Database: `exhibition`
--


CREATE TABLE `artist` (
  `artist_id` int(11) PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `name` varchar(128) UNIQUE NOT NULL,
  `description` varchar(1024) NOT NULL,
  `picture` varchar(256) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE `art` (
  `art_id` int(11) PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `name` varchar(128) NOT NULL,
  `description` varchar(1024) NOT NULL,
  `artist_id` int(11) NOT NULL,
  `picture` varchar(256) NOT NULL,
  FOREIGN KEY (`artist_id`) REFERENCES `artist`(`artist_id`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE `gallery` (
  `gallery_id` int(128) PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `name` varchar(128) NOT NULL,
  `year` int(11) NOT NULL,
  `description` varchar(1024) NOT NULL,
  `photo` varchar(256) NOT NULL,
  `x_coordinate` int(11),
  `y_coordinate` int(11)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE `artist_traits` (
  `artist_id` int(11) NOT NULL,
  `trait` varchar(128) NOT NULL,
  FOREIGN KEY (`artist_id`) REFERENCES `artist`(`artist_id`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE `exhibited_artist` (
  `gallery_id` int(11) NOT NULL,
  `artist_id` int(11) NOT NULL,
  FOREIGN KEY (`artist_id`) REFERENCES `artist`(`artist_id`),
  FOREIGN KEY (`gallery_id`) REFERENCES `gallery`(`gallery_id`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE `gallery_traits` (
  `gallery_id` int(128) NOT NULL,
  `trait` varchar(128) NOT NULL,
  FOREIGN KEY (`gallery_id`) REFERENCES `gallery`(`gallery_id`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE `gallery_art` (
  `gallery_id` int(128) NOT NULL,
  `art_id` int(128) NOT NULL,
  FOREIGN KEY (`gallery_id`) REFERENCES `gallery`(`gallery_id`),
  FOREIGN KEY (`art_id`) REFERENCES `art`(`art_id`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

CREATE TABLE `favorite_gallery` (
  `gallery_id` int(128) NOT NULL,
  `art_id` int(128) NOT NULL,
  FOREIGN KEY (`gallery_id`) REFERENCES `gallery`(`gallery_id`),
  FOREIGN KEY (`art_id`) REFERENCES `art`(`art_id`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;
