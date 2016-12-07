-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 16-11-2016 a las 11:01:29
-- Versión del servidor: 5.6.12-log
-- Versión de PHP: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `libreria`
--
CREATE DATABASE IF NOT EXISTS `libreria` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `libreria`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libros`
--

CREATE TABLE IF NOT EXISTS `libros` (
  `ID` int(11) NOT NULL DEFAULT '0',
  `TITULO` varchar(50) DEFAULT NULL,
  `AUTOR` varchar(50) DEFAULT NULL,
  `EDITORIAL` varchar(50) DEFAULT NULL,
  `ISBN` varchar(20) DEFAULT NULL,
  `PUBLICACION` int(11) DEFAULT NULL,
  `PRECIO` double DEFAULT NULL,
  `DESCRIPCION` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `libros`
--

INSERT INTO `libros` (`ID`, `TITULO`, `AUTOR`, `EDITORIAL`, `ISBN`, `PUBLICACION`, `PRECIO`, `DESCRIPCION`) VALUES
(1, 'Java 2 v5.0', 'F. Javier Moldes', 'Anaya', '84-415-1832-7', 2005, 9.99, 'Muy Bonito.'),
(3, 'Invent Your Own Computer Games with Python', 'Al Sweigart', 'CPSIA', '978-0-9821060-1-3', 2010, 24.99, 'Para empezar a programar con Python.'),
(4, 'Making Games with Python & Pygame', 'Al Sweigart', 'CPSIA', '978-1469901732', 2012, 24.99, 'Continuación del libro Invent Your Own Computer Games with Python.'),
(5, 'How to draw what you see', 'Rudy de Reyna', 'Watson-Guptill', '0-8230-2375-3', 1996, 13, 'Para iniciarse en el dibujo artístico con diferentes técnicas.'),
(6, 'Python. Pocket Reference.', 'Mark Lutz', 'OReilly', '978-0-596-15808-8', 2010, 15.25, 'Pequeño. Un libro de referencia de Python para llevar en el bolsillo.');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
