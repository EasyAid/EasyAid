-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mar 05, 2019 alle 10:59
-- Versione del server: 10.1.37-MariaDB
-- Versione PHP: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `codiceplastico`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `paziente`
--

CREATE TABLE `paziente` (
  `codice_fiscale` varchar(16) NOT NULL,
  `password` varchar(16) NOT NULL,
  `nome` varchar(20) NOT NULL,
  `cognome` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `paziente`
--

INSERT INTO `paziente` (`codice_fiscale`, `password`, `nome`, `cognome`) VALUES
('prova', 'la', 'prova1', 'la1'),
('TRNCST00C18A794P', 'pippo', 'Cristian', 'Tironi');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `paziente`
--
ALTER TABLE `paziente`
  ADD PRIMARY KEY (`codice_fiscale`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
