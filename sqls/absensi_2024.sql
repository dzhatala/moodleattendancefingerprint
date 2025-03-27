-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 27 Mar 2025 pada 06.53
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `absensi_2024`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `datecache`
--

CREATE TABLE `datecache` (
  `id` bigint(20) NOT NULL,
  `retrievedate` timestamp NOT NULL DEFAULT current_timestamp(),
  `coursefullname` varchar(255) NOT NULL,
  `remoteid` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `datecache`
--

INSERT INTO `datecache` (`id`, `retrievedate`, `coursefullname`, `remoteid`) VALUES
(1, '2025-03-24 15:00:01', 'FN_Unknown', '007');

-- --------------------------------------------------------

--
-- Struktur dari tabel `fpinfo`
--

CREATE TABLE `fpinfo` (
  `FPINFO_ID` int(11) NOT NULL,
  `PERSON_ID` bigint(10) NOT NULL,
  `LEFT_THUMB` int(11) DEFAULT NULL,
  `LEFT_INDEX` int(11) DEFAULT NULL,
  `LEFT_MIDDLE` int(11) DEFAULT NULL,
  `LEFT_RING` int(11) DEFAULT NULL,
  `LEFT_PINKY` int(11) DEFAULT NULL,
  `RIGHT_THUMB` int(11) DEFAULT NULL,
  `RIGHT_INDEX` int(11) DEFAULT NULL,
  `RIGHT_MIDDLE` int(11) DEFAULT NULL,
  `RIGHT_RING` int(11) DEFAULT NULL,
  `RIGHT_PINKY` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data untuk tabel `fpinfo`
--

INSERT INTO `fpinfo` (`FPINFO_ID`, `PERSON_ID`, `LEFT_THUMB`, `LEFT_INDEX`, `LEFT_MIDDLE`, `LEFT_RING`, `LEFT_PINKY`, `RIGHT_THUMB`, `RIGHT_INDEX`, `RIGHT_MIDDLE`, `RIGHT_RING`, `RIGHT_PINKY`) VALUES
(1, 1, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL),
(2, 2, NULL, NULL, NULL, NULL, NULL, NULL, 25, 25, NULL, NULL),
(3, 5, NULL, NULL, NULL, NULL, NULL, NULL, 4, NULL, NULL, NULL),
(4, 9, NULL, NULL, NULL, NULL, NULL, NULL, 5, NULL, NULL, NULL),
(5, 14, NULL, NULL, NULL, NULL, NULL, NULL, 6, NULL, NULL, NULL),
(6, 20, NULL, NULL, NULL, NULL, NULL, NULL, 8, NULL, NULL, NULL),
(7, 18, NULL, NULL, NULL, NULL, NULL, NULL, 9, NULL, NULL, NULL),
(8, 17, NULL, NULL, NULL, NULL, NULL, NULL, 10, NULL, NULL, NULL),
(9, 12, NULL, NULL, NULL, NULL, NULL, NULL, 11, NULL, NULL, NULL),
(10, 19, NULL, NULL, NULL, NULL, NULL, NULL, 13, NULL, NULL, NULL),
(11, 10, NULL, NULL, NULL, NULL, NULL, NULL, 14, NULL, NULL, NULL),
(12, 8, NULL, NULL, NULL, NULL, NULL, NULL, 15, NULL, NULL, NULL),
(13, 7, NULL, NULL, NULL, NULL, NULL, NULL, 16, NULL, NULL, NULL),
(14, 6, NULL, NULL, NULL, NULL, NULL, NULL, 18, NULL, NULL, NULL),
(15, 4, NULL, NULL, NULL, NULL, NULL, NULL, 19, NULL, NULL, NULL),
(16, 3, NULL, NULL, NULL, NULL, NULL, NULL, 20, NULL, NULL, NULL),
(17, 23, NULL, NULL, NULL, NULL, NULL, NULL, 7, NULL, NULL, NULL),
(18, 16, NULL, NULL, NULL, NULL, NULL, NULL, 21, NULL, NULL, NULL),
(19, 24, NULL, NULL, NULL, NULL, NULL, NULL, 12, NULL, NULL, NULL),
(20, 11, NULL, NULL, NULL, NULL, NULL, NULL, 17, NULL, NULL, NULL),
(21, 13, NULL, NULL, NULL, NULL, NULL, NULL, 22, NULL, NULL, NULL),
(22, 15, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 23, NULL, NULL),
(23, 25, NULL, NULL, NULL, NULL, NULL, NULL, 24, NULL, NULL, NULL),
(24, 50, NULL, NULL, NULL, NULL, NULL, NULL, 27, NULL, NULL, NULL),
(25, 49, NULL, NULL, NULL, NULL, NULL, NULL, 28, NULL, NULL, NULL),
(26, 47, NULL, NULL, NULL, NULL, NULL, NULL, 30, NULL, NULL, NULL),
(27, 46, NULL, NULL, NULL, NULL, NULL, NULL, 31, NULL, NULL, NULL),
(28, 51, NULL, NULL, NULL, NULL, NULL, NULL, 32, NULL, NULL, NULL),
(29, 45, NULL, NULL, NULL, NULL, NULL, NULL, 33, NULL, NULL, NULL),
(30, 44, NULL, NULL, NULL, NULL, NULL, NULL, 34, NULL, NULL, NULL),
(31, 41, NULL, NULL, NULL, NULL, NULL, NULL, 35, NULL, NULL, NULL),
(32, 42, NULL, NULL, NULL, NULL, NULL, NULL, 36, NULL, NULL, NULL),
(33, 40, NULL, NULL, NULL, NULL, NULL, NULL, 37, NULL, NULL, NULL),
(34, 39, NULL, NULL, NULL, NULL, NULL, NULL, 38, NULL, NULL, NULL),
(35, 38, NULL, NULL, NULL, NULL, NULL, NULL, 39, NULL, NULL, NULL),
(36, 37, NULL, NULL, NULL, NULL, NULL, NULL, 40, NULL, NULL, NULL),
(37, 36, NULL, NULL, NULL, NULL, NULL, NULL, 41, NULL, NULL, NULL),
(38, 33, NULL, NULL, NULL, NULL, NULL, NULL, 42, NULL, NULL, NULL),
(39, 34, NULL, NULL, NULL, NULL, NULL, NULL, 43, NULL, NULL, NULL),
(40, 32, NULL, NULL, NULL, NULL, NULL, NULL, 44, NULL, NULL, NULL),
(41, 29, NULL, NULL, NULL, NULL, NULL, NULL, 45, NULL, NULL, NULL),
(42, 31, NULL, NULL, NULL, NULL, NULL, NULL, 46, NULL, NULL, NULL),
(43, 28, NULL, NULL, NULL, NULL, NULL, NULL, 47, NULL, NULL, NULL),
(44, 27, NULL, NULL, NULL, NULL, NULL, NULL, 48, NULL, NULL, NULL),
(45, 30, NULL, NULL, NULL, NULL, NULL, NULL, 49, NULL, NULL, NULL),
(46, 26, NULL, NULL, NULL, NULL, NULL, NULL, 50, NULL, NULL, NULL),
(47, 43, NULL, NULL, NULL, NULL, NULL, NULL, 51, NULL, NULL, NULL),
(48, 35, NULL, NULL, NULL, NULL, NULL, NULL, 52, NULL, NULL, NULL),
(49, 48, NULL, NULL, NULL, NULL, NULL, NULL, 53, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `identified1n`
--

CREATE TABLE `identified1n` (
  `IDENTIFIED1N_ID` int(11) NOT NULL,
  `TRIALDATE` datetime DEFAULT current_timestamp(),
  `FPID` int(11) DEFAULT -1,
  `SCORE` int(11) DEFAULT -1,
  `PFN` int(11) DEFAULT -1,
  `REGNAME` varchar(2014) DEFAULT NULL COMMENT 'ex: NOS_A_JOB1_MASUK, STI_C_JOB2_MAXUK',
  `SESSION_ID` bigint(20) NOT NULL COMMENT 'cache session id in moodle'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data untuk tabel `identified1n`
--

INSERT INTO `identified1n` (`IDENTIFIED1N_ID`, `TRIALDATE`, `FPID`, `SCORE`, `PFN`, `REGNAME`, `SESSION_ID`) VALUES
(1, '2025-03-20 15:29:33', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(2, '2025-03-20 15:29:35', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(3, '2025-03-20 15:29:44', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(4, '2025-03-20 15:29:57', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(5, '2025-03-20 17:43:14', 1, 85, 1, 'SUB?_JOB?_IN?OUT', 0),
(6, '2025-03-20 17:43:29', 1, 112, 1, 'SUB?_JOB?_IN?OUT', 0),
(7, '2025-03-20 18:12:56', 1, 78, -1, 'JAVA_WS_BETA', -1),
(8, '2025-03-20 18:13:08', 1, 94, -1, 'JAVA_WS_BETA', -1),
(9, '2025-03-25 10:07:24', 1, 90, 1, 'SUB?_JOB?_IN?OUT', 0),
(10, '2025-03-25 10:23:18', 1, 113, 1, 'SUB?_JOB?_IN?OUT', 0),
(11, '2025-03-25 10:23:29', 1, 257, 1, 'SUB?_JOB?_IN?OUT', 0),
(12, '2025-03-25 10:32:40', 1, 86, 1, 'SUB?_JOB?_IN?OUT', 0),
(13, '2025-03-25 10:32:41', 1, 92, 1, 'SUB?_JOB?_IN?OUT', 0),
(14, '2025-03-25 10:32:43', 1, 128, 1, 'SUB?_JOB?_IN?OUT', 0),
(15, '2025-03-25 10:33:09', 2, 432, 2, 'SUB?_JOB?_IN?OUT', 0),
(16, '2025-03-25 10:34:24', 3, 91, 3, 'SUB?_JOB?_IN?OUT', 0),
(17, '2025-03-25 10:34:50', 3, 420, 3, 'SUB?_JOB?_IN?OUT', 0),
(18, '2025-03-25 10:35:21', 4, 292, 4, 'SUB?_JOB?_IN?OUT', 0),
(19, '2025-03-25 10:35:46', 4, 73, 4, 'SUB?_JOB?_IN?OUT', 0),
(20, '2025-03-25 10:36:39', 5, 842, 5, 'SUB?_JOB?_IN?OUT', 0),
(21, '2025-03-25 10:36:55', 5, 446, 5, 'SUB?_JOB?_IN?OUT', 0),
(22, '2025-03-25 10:36:55', 5, 651, 5, 'SUB?_JOB?_IN?OUT', 0),
(23, '2025-03-25 10:37:13', 6, 561, 6, 'SUB?_JOB?_IN?OUT', 0),
(24, '2025-03-25 10:37:23', 6, 495, 6, 'SUB?_JOB?_IN?OUT', 0),
(25, '2025-03-25 10:37:38', 2, 291, 2, 'SUB?_JOB?_IN?OUT', 0),
(26, '2025-03-25 10:38:08', 8, 546, 8, 'SUB?_JOB?_IN?OUT', 0),
(27, '2025-03-25 10:38:16', 8, 788, 8, 'SUB?_JOB?_IN?OUT', 0),
(28, '2025-03-25 10:38:36', 9, 529, 9, 'SUB?_JOB?_IN?OUT', 0),
(29, '2025-03-25 10:38:46', 9, 402, 9, 'SUB?_JOB?_IN?OUT', 0),
(30, '2025-03-25 10:39:01', 10, 253, 10, 'SUB?_JOB?_IN?OUT', 0),
(31, '2025-03-25 10:39:11', 10, 352, 10, 'SUB?_JOB?_IN?OUT', 0),
(32, '2025-03-25 10:39:27', 11, 186, 11, 'SUB?_JOB?_IN?OUT', 0),
(33, '2025-03-25 10:39:36', 11, 373, 11, 'SUB?_JOB?_IN?OUT', 0),
(34, '2025-03-25 10:40:28', 12, 430, 12, 'SUB?_JOB?_IN?OUT', 0),
(35, '2025-03-25 10:41:19', 13, 238, 13, 'SUB?_JOB?_IN?OUT', 0),
(36, '2025-03-25 10:41:33', 13, 199, 13, 'SUB?_JOB?_IN?OUT', 0),
(37, '2025-03-25 10:42:41', 14, 406, 14, 'SUB?_JOB?_IN?OUT', 0),
(38, '2025-03-25 10:42:58', 14, 595, 14, 'SUB?_JOB?_IN?OUT', 0),
(39, '2025-03-25 10:43:40', 15, 758, 15, 'SUB?_JOB?_IN?OUT', 0),
(40, '2025-03-25 10:43:41', 15, 277, 15, 'SUB?_JOB?_IN?OUT', 0),
(41, '2025-03-25 10:43:59', 15, 540, 15, 'SUB?_JOB?_IN?OUT', 0),
(42, '2025-03-25 10:43:59', 15, 502, 15, 'SUB?_JOB?_IN?OUT', 0),
(43, '2025-03-25 10:44:19', 16, 230, 16, 'SUB?_JOB?_IN?OUT', 0),
(44, '2025-03-25 10:44:35', 16, 250, 16, 'SUB?_JOB?_IN?OUT', 0),
(45, '2025-03-25 10:45:04', 17, 329, 17, 'SUB?_JOB?_IN?OUT', 0),
(46, '2025-03-25 10:45:33', 18, 461, 18, 'SUB?_JOB?_IN?OUT', 0),
(47, '2025-03-25 10:45:48', 18, 550, 18, 'SUB?_JOB?_IN?OUT', 0),
(48, '2025-03-25 10:46:12', 19, 484, 19, 'SUB?_JOB?_IN?OUT', 0),
(49, '2025-03-25 10:46:22', 19, 501, 19, 'SUB?_JOB?_IN?OUT', 0),
(50, '2025-03-25 10:46:55', 20, 298, 20, 'SUB?_JOB?_IN?OUT', 0),
(51, '2025-03-25 10:47:09', 20, 589, 20, 'SUB?_JOB?_IN?OUT', 0),
(52, '2025-03-25 10:54:57', 2, 460, 2, 'SUB?_JOB?_IN?OUT', 0),
(53, '2025-03-25 10:55:01', 2, 407, 2, 'SUB?_JOB?_IN?OUT', 0),
(54, '2025-03-25 10:55:10', 7, 120, 7, 'SUB?_JOB?_IN?OUT', 0),
(55, '2025-03-25 10:55:12', 7, 349, 7, 'SUB?_JOB?_IN?OUT', 0),
(56, '2025-03-25 10:55:19', 7, 365, 7, 'SUB?_JOB?_IN?OUT', 0),
(57, '2025-03-25 10:58:42', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(58, '2025-03-25 10:59:15', 21, 276, 21, 'SUB?_JOB?_IN?OUT', 0),
(59, '2025-03-25 10:59:23', 21, 124, 21, 'SUB?_JOB?_IN?OUT', 0),
(60, '2025-03-25 10:59:40', 12, 508, 12, 'SUB?_JOB?_IN?OUT', 0),
(61, '2025-03-25 11:01:15', 12, 373, 12, 'SUB?_JOB?_IN?OUT', 0),
(62, '2025-03-25 11:01:20', 12, 398, 12, 'SUB?_JOB?_IN?OUT', 0),
(63, '2025-03-25 11:01:34', 17, 314, 17, 'SUB?_JOB?_IN?OUT', 0),
(64, '2025-03-25 11:01:50', 17, 104, 17, 'SUB?_JOB?_IN?OUT', 0),
(65, '2025-03-25 11:02:00', 5, 657, 5, 'SUB?_JOB?_IN?OUT', 0),
(66, '2025-03-25 11:02:38', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(67, '2025-03-25 11:02:43', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(68, '2025-03-25 11:03:23', 22, 81, 22, 'SUB?_JOB?_IN?OUT', 0),
(69, '2025-03-25 11:03:35', 22, 412, 22, 'SUB?_JOB?_IN?OUT', 0),
(70, '2025-03-25 11:03:46', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(71, '2025-03-25 11:04:07', 23, 252, 23, 'SUB?_JOB?_IN?OUT', 0),
(72, '2025-03-25 11:04:19', 23, 189, 23, 'SUB?_JOB?_IN?OUT', 0),
(73, '2025-03-25 11:04:38', 24, 421, 24, 'SUB?_JOB?_IN?OUT', 0),
(74, '2025-03-25 11:04:40', 24, 635, 24, 'SUB?_JOB?_IN?OUT', 0),
(75, '2025-03-25 11:05:12', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(76, '2025-03-25 11:05:13', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(77, '2025-03-25 11:05:35', 25, 478, 25, 'SUB?_JOB?_IN?OUT', 0),
(78, '2025-03-25 11:05:52', 25, 320, 25, 'SUB?_JOB?_IN?OUT', 0),
(79, '2025-03-25 11:06:04', 4, 204, 4, 'SUB?_JOB?_IN?OUT', 0),
(80, '2025-03-25 11:06:06', 4, 115, 4, 'SUB?_JOB?_IN?OUT', 0),
(81, '2025-03-25 11:10:16', 1, 69, 1, 'SUB?_JOB?_IN?OUT', 0),
(82, '2025-03-25 11:10:28', 24, 463, 24, 'SUB?_JOB?_IN?OUT', 0),
(83, '2025-03-25 11:10:40', 24, 697, 24, 'SUB?_JOB?_IN?OUT', 0),
(84, '2025-03-26 14:57:16', 1, 245, 1, 'SUB?_JOB?_IN?OUT', 0),
(85, '2025-03-26 14:57:18', 1, 240, 1, 'SUB?_JOB?_IN?OUT', 0),
(86, '2025-03-26 14:57:39', 27, 489, 27, 'SUB?_JOB?_IN?OUT', 0),
(87, '2025-03-26 14:57:42', 27, 85, 27, 'SUB?_JOB?_IN?OUT', 0),
(88, '2025-03-26 14:57:43', 27, 303, 27, 'SUB?_JOB?_IN?OUT', 0),
(89, '2025-03-26 14:58:01', 27, 135, 27, 'SUB?_JOB?_IN?OUT', 0),
(90, '2025-03-26 14:58:42', 28, 88, 28, 'SUB?_JOB?_IN?OUT', 0),
(91, '2025-03-26 14:59:09', 28, 223, 28, 'SUB?_JOB?_IN?OUT', 0),
(92, '2025-03-26 14:59:26', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(93, '2025-03-26 14:59:56', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(94, '2025-03-26 14:59:57', 1, 115, 1, 'SUB?_JOB?_IN?OUT', 0),
(95, '2025-03-26 14:59:59', 1, 113, 1, 'SUB?_JOB?_IN?OUT', 0),
(96, '2025-03-26 14:59:59', 1, 68, 1, 'SUB?_JOB?_IN?OUT', 0),
(97, '2025-03-26 15:00:18', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(98, '2025-03-26 15:00:21', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(99, '2025-03-26 15:00:25', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(100, '2025-03-26 15:00:43', 30, 94, 30, 'SUB?_JOB?_IN?OUT', 0),
(101, '2025-03-26 15:00:54', 30, 117, 30, 'SUB?_JOB?_IN?OUT', 0),
(102, '2025-03-26 15:01:13', 31, 489, 31, 'SUB?_JOB?_IN?OUT', 0),
(103, '2025-03-26 15:01:29', 31, 307, 31, 'SUB?_JOB?_IN?OUT', 0),
(104, '2025-03-26 15:01:45', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(105, '2025-03-26 15:01:55', 32, 291, 32, 'SUB?_JOB?_IN?OUT', 0),
(106, '2025-03-26 15:02:09', 32, 100, 32, 'SUB?_JOB?_IN?OUT', 0),
(107, '2025-03-26 15:02:25', 33, 621, 33, 'SUB?_JOB?_IN?OUT', 0),
(108, '2025-03-26 15:02:27', 33, 608, 33, 'SUB?_JOB?_IN?OUT', 0),
(109, '2025-03-26 15:02:37', 33, 381, 33, 'SUB?_JOB?_IN?OUT', 0),
(110, '2025-03-26 15:02:52', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(111, '2025-03-26 15:02:55', 34, 101, 34, 'SUB?_JOB?_IN?OUT', 0),
(112, '2025-03-26 15:03:08', 34, 118, 34, 'SUB?_JOB?_IN?OUT', 0),
(113, '2025-03-26 15:03:40', 1, 88, 1, 'SUB?_JOB?_IN?OUT', 0),
(114, '2025-03-26 15:03:58', 35, 233, 35, 'SUB?_JOB?_IN?OUT', 0),
(115, '2025-03-26 15:04:09', 35, 453, 35, 'SUB?_JOB?_IN?OUT', 0),
(116, '2025-03-26 15:04:23', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(117, '2025-03-26 15:04:34', 36, 111, 36, 'SUB?_JOB?_IN?OUT', 0),
(118, '2025-03-26 15:04:47', 36, 106, 36, 'SUB?_JOB?_IN?OUT', 0),
(119, '2025-03-26 15:05:32', 37, 315, 37, 'SUB?_JOB?_IN?OUT', 0),
(120, '2025-03-26 15:05:46', 37, 298, 37, 'SUB?_JOB?_IN?OUT', 0),
(121, '2025-03-26 15:08:03', 38, 550, 38, 'SUB?_JOB?_IN?OUT', 0),
(122, '2025-03-26 15:08:14', 38, 290, 38, 'SUB?_JOB?_IN?OUT', 0),
(123, '2025-03-26 15:10:02', 1, 106, 1, 'SUB?_JOB?_IN?OUT', 0),
(124, '2025-03-26 15:10:14', 39, 392, 39, 'SUB?_JOB?_IN?OUT', 0),
(125, '2025-03-26 15:10:35', 39, 167, 39, 'SUB?_JOB?_IN?OUT', 0),
(126, '2025-03-26 15:11:13', 40, 647, 40, 'SUB?_JOB?_IN?OUT', 0),
(127, '2025-03-26 15:11:28', 40, 537, 40, 'SUB?_JOB?_IN?OUT', 0),
(128, '2025-03-26 15:11:47', 41, 115, 41, 'SUB?_JOB?_IN?OUT', 0),
(129, '2025-03-26 15:12:03', 41, 557, 41, 'SUB?_JOB?_IN?OUT', 0),
(130, '2025-03-26 15:12:58', 42, 245, 42, 'SUB?_JOB?_IN?OUT', 0),
(131, '2025-03-26 15:13:12', 42, 454, 42, 'SUB?_JOB?_IN?OUT', 0),
(132, '2025-03-26 15:13:32', 43, 287, 43, 'SUB?_JOB?_IN?OUT', 0),
(133, '2025-03-26 15:13:43', 43, 131, 43, 'SUB?_JOB?_IN?OUT', 0),
(134, '2025-03-26 15:14:19', 44, 321, 44, 'SUB?_JOB?_IN?OUT', 0),
(135, '2025-03-26 15:14:46', -1, 0, 0, 'SUB?_JOB?_IN?OUT', 0),
(136, '2025-03-26 15:14:53', 1, 96, 1, 'SUB?_JOB?_IN?OUT', 0),
(137, '2025-03-26 15:15:07', 45, 478, 45, 'SUB?_JOB?_IN?OUT', 0),
(138, '2025-03-26 15:15:25', 45, 401, 45, 'SUB?_JOB?_IN?OUT', 0),
(139, '2025-03-26 15:15:43', 46, 617, 46, 'SUB?_JOB?_IN?OUT', 0),
(140, '2025-03-26 15:15:57', 46, 483, 46, 'SUB?_JOB?_IN?OUT', 0),
(141, '2025-03-26 15:16:13', 47, 616, 47, 'SUB?_JOB?_IN?OUT', 0),
(142, '2025-03-26 15:16:25', 47, 737, 47, 'SUB?_JOB?_IN?OUT', 0),
(143, '2025-03-26 15:16:52', 48, 475, 48, 'SUB?_JOB?_IN?OUT', 0),
(144, '2025-03-26 15:17:04', 48, 468, 48, 'SUB?_JOB?_IN?OUT', 0),
(145, '2025-03-26 15:17:21', 49, 366, 49, 'SUB?_JOB?_IN?OUT', 0),
(146, '2025-03-26 15:17:33', 49, 219, 49, 'SUB?_JOB?_IN?OUT', 0),
(147, '2025-03-26 15:17:56', 50, 170, 50, 'SUB?_JOB?_IN?OUT', 0),
(148, '2025-03-26 15:17:57', 50, 526, 50, 'SUB?_JOB?_IN?OUT', 0),
(149, '2025-03-26 15:18:13', 50, 127, 50, 'SUB?_JOB?_IN?OUT', 0),
(150, '2025-03-26 15:25:19', 51, 206, 51, 'SUB?_JOB?_IN?OUT', 0),
(151, '2025-03-26 15:25:37', 51, 175, 51, 'SUB?_JOB?_IN?OUT', 0),
(152, '2025-03-26 15:26:09', 52, 471, 52, 'SUB?_JOB?_IN?OUT', 0),
(153, '2025-03-26 15:26:26', 52, 209, 52, 'SUB?_JOB?_IN?OUT', 0),
(154, '2025-03-26 15:26:44', 53, 97, 53, 'SUB?_JOB?_IN?OUT', 0),
(155, '2025-03-26 15:27:02', 53, 112, 53, 'SUB?_JOB?_IN?OUT', 0);

-- --------------------------------------------------------

--
-- Struktur dari tabel `mdl_cp_user`
--

CREATE TABLE `mdl_cp_user` (
  `id` bigint(10) NOT NULL,
  `auth` varchar(20) NOT NULL DEFAULT 'manual',
  `confirmed` tinyint(1) NOT NULL DEFAULT 0,
  `policyagreed` tinyint(1) NOT NULL DEFAULT 0,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `suspended` tinyint(1) NOT NULL DEFAULT 0,
  `mnethostid` bigint(10) NOT NULL DEFAULT 0,
  `username` varchar(100) NOT NULL DEFAULT '',
  `password` varchar(255) NOT NULL DEFAULT '',
  `idnumber` varchar(255) NOT NULL DEFAULT '',
  `firstname` varchar(100) NOT NULL DEFAULT '',
  `lastname` varchar(100) NOT NULL DEFAULT '',
  `email` varchar(100) NOT NULL DEFAULT '',
  `emailstop` tinyint(1) NOT NULL DEFAULT 0,
  `icq` varchar(15) NOT NULL DEFAULT '',
  `skype` varchar(50) NOT NULL DEFAULT '',
  `yahoo` varchar(50) NOT NULL DEFAULT '',
  `aim` varchar(50) NOT NULL DEFAULT '',
  `msn` varchar(50) NOT NULL DEFAULT '',
  `phone1` varchar(20) NOT NULL DEFAULT '',
  `phone2` varchar(20) NOT NULL DEFAULT '',
  `institution` varchar(255) NOT NULL DEFAULT '',
  `department` varchar(255) NOT NULL DEFAULT '',
  `address` varchar(255) NOT NULL DEFAULT '',
  `city` varchar(120) NOT NULL DEFAULT '',
  `country` varchar(2) NOT NULL DEFAULT '',
  `lang` varchar(30) NOT NULL DEFAULT 'en',
  `calendartype` varchar(30) NOT NULL DEFAULT 'gregorian',
  `theme` varchar(50) NOT NULL DEFAULT '',
  `timezone` varchar(100) NOT NULL DEFAULT '99',
  `firstaccess` bigint(10) NOT NULL DEFAULT 0,
  `lastaccess` bigint(10) NOT NULL DEFAULT 0,
  `lastlogin` bigint(10) NOT NULL DEFAULT 0,
  `currentlogin` bigint(10) NOT NULL DEFAULT 0,
  `lastip` varchar(45) NOT NULL DEFAULT '',
  `secret` varchar(15) NOT NULL DEFAULT '',
  `picture` bigint(10) NOT NULL DEFAULT 0,
  `url` varchar(255) NOT NULL DEFAULT '',
  `description` longtext DEFAULT NULL,
  `descriptionformat` tinyint(2) NOT NULL DEFAULT 1,
  `mailformat` tinyint(1) NOT NULL DEFAULT 1,
  `maildigest` tinyint(1) NOT NULL DEFAULT 0,
  `maildisplay` tinyint(2) NOT NULL DEFAULT 2,
  `autosubscribe` tinyint(1) NOT NULL DEFAULT 1,
  `trackforums` tinyint(1) NOT NULL DEFAULT 0,
  `timecreated` bigint(10) NOT NULL DEFAULT 0,
  `timemodified` bigint(10) NOT NULL DEFAULT 0,
  `trustbitmask` bigint(10) NOT NULL DEFAULT 0,
  `imagealt` varchar(255) DEFAULT NULL,
  `lastnamephonetic` varchar(255) DEFAULT NULL,
  `firstnamephonetic` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `alternatename` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='One record for each person' ROW_FORMAT=COMPRESSED;

-- --------------------------------------------------------

--
-- Struktur dari tabel `mdl_user`
--

CREATE TABLE `mdl_user` (
  `id` bigint(10) NOT NULL,
  `remote_id` bigint(10) NOT NULL COMMENT 'remote moodle id for REST synchronization',
  `auth` varchar(20) NOT NULL DEFAULT 'manual',
  `confirmed` tinyint(1) NOT NULL DEFAULT 0,
  `policyagreed` tinyint(1) NOT NULL DEFAULT 0,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `suspended` tinyint(1) NOT NULL DEFAULT 0,
  `mnethostid` bigint(10) NOT NULL DEFAULT 0,
  `username` varchar(100) NOT NULL DEFAULT '',
  `password` varchar(255) NOT NULL DEFAULT '',
  `idnumber` varchar(255) NOT NULL DEFAULT '',
  `firstname` varchar(100) NOT NULL DEFAULT '',
  `lastname` varchar(100) NOT NULL DEFAULT '',
  `email` varchar(100) NOT NULL DEFAULT '',
  `emailstop` tinyint(1) NOT NULL DEFAULT 0,
  `icq` varchar(15) NOT NULL DEFAULT '',
  `skype` varchar(50) NOT NULL DEFAULT '',
  `yahoo` varchar(50) NOT NULL DEFAULT '',
  `aim` varchar(50) NOT NULL DEFAULT '',
  `msn` varchar(50) NOT NULL DEFAULT '',
  `phone1` varchar(20) NOT NULL DEFAULT '',
  `phone2` varchar(20) NOT NULL DEFAULT '',
  `institution` varchar(255) NOT NULL DEFAULT '',
  `department` varchar(255) NOT NULL DEFAULT '',
  `address` varchar(255) NOT NULL DEFAULT '',
  `city` varchar(120) NOT NULL DEFAULT '',
  `country` varchar(2) NOT NULL DEFAULT '',
  `lang` varchar(30) NOT NULL DEFAULT 'en',
  `calendartype` varchar(30) NOT NULL DEFAULT 'gregorian',
  `theme` varchar(50) NOT NULL DEFAULT '',
  `timezone` varchar(100) NOT NULL DEFAULT '99',
  `firstaccess` bigint(10) NOT NULL DEFAULT 0,
  `lastaccess` bigint(10) NOT NULL DEFAULT 0,
  `lastlogin` bigint(10) NOT NULL DEFAULT 0,
  `currentlogin` bigint(10) NOT NULL DEFAULT 0,
  `lastip` varchar(45) NOT NULL DEFAULT '',
  `secret` varchar(15) NOT NULL DEFAULT '',
  `picture` bigint(10) NOT NULL DEFAULT 0,
  `url` varchar(255) NOT NULL DEFAULT '',
  `description` longtext DEFAULT NULL,
  `descriptionformat` tinyint(2) NOT NULL DEFAULT 1,
  `mailformat` tinyint(1) NOT NULL DEFAULT 1,
  `maildigest` tinyint(1) NOT NULL DEFAULT 0,
  `maildisplay` tinyint(2) NOT NULL DEFAULT 2,
  `autosubscribe` tinyint(1) NOT NULL DEFAULT 1,
  `trackforums` tinyint(1) NOT NULL DEFAULT 0,
  `timecreated` bigint(10) NOT NULL DEFAULT 0,
  `timemodified` bigint(10) NOT NULL DEFAULT 0,
  `trustbitmask` bigint(10) NOT NULL DEFAULT 0,
  `imagealt` varchar(255) DEFAULT NULL,
  `lastnamephonetic` varchar(255) DEFAULT NULL,
  `firstnamephonetic` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `alternatename` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='One record for each person' ROW_FORMAT=COMPRESSED;

--
-- Dumping data untuk tabel `mdl_user`
--

INSERT INTO `mdl_user` (`id`, `remote_id`, `auth`, `confirmed`, `policyagreed`, `deleted`, `suspended`, `mnethostid`, `username`, `password`, `idnumber`, `firstname`, `lastname`, `email`, `emailstop`, `icq`, `skype`, `yahoo`, `aim`, `msn`, `phone1`, `phone2`, `institution`, `department`, `address`, `city`, `country`, `lang`, `calendartype`, `theme`, `timezone`, `firstaccess`, `lastaccess`, `lastlogin`, `currentlogin`, `lastip`, `secret`, `picture`, `url`, `description`, `descriptionformat`, `mailformat`, `maildigest`, `maildisplay`, `autosubscribe`, `trackforums`, `timecreated`, `timemodified`, `trustbitmask`, `imagealt`, `lastnamephonetic`, `firstnamephonetic`, `middlename`, `alternatename`) VALUES
(1, 0, 'manual', 0, 0, 0, 0, 0, '007', '9e94b15ed312fa42232fd87a55db0d39', '', 'james', 'bond', 'dzulqarnaenhatala@gmail.com', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(2, 0, 'manual', 1, 0, 0, 0, 1, '1324144028', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Resli Marla Resi', '', 'st2021_1324144028@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(3, 0, 'manual', 1, 0, 0, 0, 1, '1324144007', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'TASYA MARWAN SOULISA', '', 'st2021_1324144007@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(4, 0, 'manual', 1, 0, 0, 0, 1, '1324144005', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Jul Estin Velia Efruan', '', 'st2021_1324144005@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(5, 0, 'manual', 1, 0, 0, 0, 1, '1324144023', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Freny Hehanussa', '', 'st2021_1324144023@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(6, 0, 'manual', 1, 0, 0, 0, 1, '1324144026', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'ELVIRA J. PATTIRANE', '', 'st2021_1324144026@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(7, 0, 'manual', 1, 0, 0, 0, 1, '1324144006', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'GABRIEL MATAHERU', '', 'st2021_1324144006@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(8, 0, 'manual', 1, 0, 0, 0, 1, '1324144019', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'REKEISON WENHENUBUN', '', 'st2021_1324144019@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(9, 0, 'manual', 1, 0, 0, 0, 1, '1324144022', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Jelang chris lagiaduay', '', 'st2021_1324144022@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(10, 0, 'manual', 1, 0, 0, 0, 1, '1324144004', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Prince Neysera Mailopuw', '', 'st2021_1324144004@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(11, 0, 'manual', 1, 0, 0, 0, 1, '1324144013', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Delvio Frezly Adriaansz', '', 'st2021_1324144016@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(12, 0, 'manual', 1, 0, 0, 0, 1, '1324144020', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Inka Umela Manibuy', '', 'st2021_1324144020@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(13, 0, 'manual', 1, 0, 0, 0, 1, '1324144010', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'BILLCLYN W. LUHUKAY', '', 'st2021_1324144010@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(14, 0, 'manual', 1, 0, 0, 0, 1, '1324144001', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Muhamad rizky nende', '', 'st2021_1324144001@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(15, 0, 'manual', 1, 0, 0, 0, 1, '1324144011', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'smith leasa', '', 'st2021_1324144011@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(16, 0, 'manual', 1, 0, 0, 0, 1, '1324144018', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'WIDYA AFRIYANTI TUASAMU', '', 'st2021_1324144018@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(17, 0, 'manual', 1, 0, 0, 0, 1, '1324144002', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Nurul Aini Hunusalela', '', 'st2021_1324144002@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(18, 0, 'manual', 1, 0, 0, 0, 1, '1324144014', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'YUNI SALSABILA PAILOKOL', '', 'st2021_1324144014@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(19, 0, 'manual', 1, 0, 0, 0, 1, '1324144024', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Jubelline Rasty Latuputty', '', 'st2021_1324144024@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(20, 0, 'manual', 1, 0, 0, 0, 1, '1324144003', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Farel Ivan Art Niwele', '', 'st2021_1324144003@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(23, 0, 'manual', 1, 0, 0, 0, 1, '1324144016', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'zefanya mario suripatty', '', 'st2021_1324144016@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(24, 0, 'manual', 1, 0, 0, 0, 1, '1324144009', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Vindry Walasary', '', 'st2021_1324144009@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(25, 0, 'manual', 1, 0, 0, 0, 1, '1324144017', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Efod G Ubfan', '', 'st2021_1324144017@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(26, 0, 'manual', 1, 0, 0, 0, 1, '1324144034', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'FAHREZI LA TAMI', '', 'st2021_1324144034@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(27, 0, 'manual', 1, 0, 0, 0, 1, '1324144035', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'MATHANZA GREFILL LIMBA', '', 'st2021_1324144035@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(28, 0, 'manual', 1, 0, 0, 0, 1, '1324144030', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'FACHRIANSYAH BADARUDIN', '', 'st2021_1324144030@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(29, 0, 'manual', 1, 0, 0, 0, 1, '1324144044', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'DEYREL C V GERUNG', '', 'st2021_1324144044@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(30, 0, 'manual', 1, 0, 0, 0, 1, '1324144048', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'STEVANI Y PEEA', '', 'st2021_1324144048@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(31, 0, 'manual', 1, 0, 0, 0, 1, '1324144041', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'JOSHUA P T HUTUBESSY', '', 'st2021_1324144041@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(32, 0, 'manual', 1, 0, 0, 0, 1, '1324144036', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'CLAUDIA M ARITONANG', '', 'st2021_1324144036@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(33, 0, 'manual', 1, 0, 0, 0, 1, '1324144031', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'EKA FRITY AWAYAL ', '', 'st2021_1324144031@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(34, 0, 'manual', 1, 0, 0, 0, 1, '1324144042', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'GAVRILIA KASTANJA', '', 'st2021_1324144042@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(35, 0, 'manual', 1, 0, 0, 0, 1, '1324144033', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'WA YANA', '', 'st2021_1324144033@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(36, 0, 'manual', 1, 0, 0, 0, 1, '1324144047', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'EVI SONYA JOKTERY', '', 'st2021_1324144047@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(37, 0, 'manual', 1, 0, 0, 0, 1, '1324144032', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'FEBRIAN FAJAR LATING', '', 'st2021_1324144032@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(38, 0, 'manual', 1, 0, 0, 0, 1, '1324144029', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'VIANTIKA FIRELIA PPORMES  ', '', 'st2021_1324144029@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(39, 0, 'manual', 1, 0, 0, 0, 1, '1324144040', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'ILHAM PAIHALY', '', 'st2021_1324144040@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(40, 0, 'manual', 1, 0, 0, 0, 1, '1324144043', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'LA OD RAHMAT HERMAWAN', '', 'st2021_1324144043@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(41, 0, 'manual', 1, 0, 0, 0, 1, '1324144053', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'ULFA NASHIRAH', '', 'st2021_1324144053@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(42, 0, 'manual', 1, 0, 0, 0, 1, '1324144038', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Leinheart Ayal', '', 'st2021_1324144038@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(43, 0, 'manual', 1, 0, 0, 0, 1, '1324144049', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Muhammad Yusal', '', 'st2021_1324144049@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(44, 0, 'manual', 1, 0, 0, 0, 1, '1324144037', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Angellin C Laisatamu', '', 'st2021_1324144037@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(45, 0, 'manual', 1, 0, 0, 0, 1, '1324144045', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'SITA LIMA LESSY', '', 'st2021_1324144045@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(46, 0, 'manual', 1, 0, 0, 0, 1, '1324144039', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Tasha Aghnini', '', 'st2021_1324144039@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(47, 0, 'manual', 1, 0, 0, 0, 1, '1324144055', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Widya larasati', '', 'st2021_1324144055@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(48, 0, 'manual', 1, 0, 0, 0, 1, '1324144050', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Fatmi Assaffa Saimima', '', 'st2021_1324144050@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(49, 0, 'manual', 1, 0, 0, 0, 1, '1324144051', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'wina widya jamal', '', 'st2021_1324144051@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(50, 0, 'manual', 1, 0, 0, 0, 1, '1324144054', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'Wulan Zulidah Sari', '', 'st2021_1324144054@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL),
(51, 0, 'manual', 1, 0, 0, 0, 1, '1324144052', 'cb08ca4a7bb5f9683c19133a84872ca7', '', 'jana juana wattimena', '', 'st2021_1324144052@cs.polnam.ac.id', 0, '', '', '', '', '', '', '', '', '', '', '', '', 'en', 'gregorian', '', '99', 0, 0, 0, 0, '', '', 0, '', NULL, 1, 1, 0, 2, 1, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_manual`
--

CREATE TABLE `tbl_manual` (
  `tm_id` int(11) NOT NULL,
  `NIM` varchar(20) NOT NULL,
  `NAMA` varchar(1024) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `datecache`
--
ALTER TABLE `datecache`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `fpinfo`
--
ALTER TABLE `fpinfo`
  ADD PRIMARY KEY (`FPINFO_ID`),
  ADD UNIQUE KEY `PERSON_ID` (`PERSON_ID`);

--
-- Indeks untuk tabel `identified1n`
--
ALTER TABLE `identified1n`
  ADD PRIMARY KEY (`IDENTIFIED1N_ID`);

--
-- Indeks untuk tabel `mdl_cp_user`
--
ALTER TABLE `mdl_cp_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `mdl_cp_user_mneuse_uix` (`mnethostid`,`username`),
  ADD KEY `mdl_cp_user_del_ix` (`deleted`),
  ADD KEY `mdl_cp_user_con_ix` (`confirmed`),
  ADD KEY `mdl_cp_user_fir_ix` (`firstname`),
  ADD KEY `mdl_cp_user_las_ix` (`lastname`),
  ADD KEY `mdl_cp_user_cit_ix` (`city`),
  ADD KEY `mdl_cp_user_cou_ix` (`country`),
  ADD KEY `mdl_cp_user_las2_ix` (`lastaccess`),
  ADD KEY `mdl_cp_user_ema_ix` (`email`),
  ADD KEY `mdl_cp_user_aut_ix` (`auth`),
  ADD KEY `mdl_cp_user_idn_ix` (`idnumber`(191)),
  ADD KEY `mdl_cp_user_fir2_ix` (`firstnamephonetic`(191)),
  ADD KEY `mdl_cp_user_las3_ix` (`lastnamephonetic`(191)),
  ADD KEY `mdl_cp_user_mid_ix` (`middlename`(191)),
  ADD KEY `mdl_cp_user_alt_ix` (`alternatename`(191));

--
-- Indeks untuk tabel `mdl_user`
--
ALTER TABLE `mdl_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `mdl_user_mneuse_uix` (`mnethostid`,`username`),
  ADD KEY `mdl_user_del_ix` (`deleted`),
  ADD KEY `mdl_user_con_ix` (`confirmed`),
  ADD KEY `mdl_user_fir_ix` (`firstname`),
  ADD KEY `mdl_user_las_ix` (`lastname`),
  ADD KEY `mdl_user_cit_ix` (`city`),
  ADD KEY `mdl_user_cou_ix` (`country`),
  ADD KEY `mdl_user_las2_ix` (`lastaccess`),
  ADD KEY `mdl_user_ema_ix` (`email`),
  ADD KEY `mdl_user_aut_ix` (`auth`),
  ADD KEY `mdl_user_idn_ix` (`idnumber`),
  ADD KEY `mdl_user_fir2_ix` (`firstnamephonetic`),
  ADD KEY `mdl_user_las3_ix` (`lastnamephonetic`),
  ADD KEY `mdl_user_mid_ix` (`middlename`),
  ADD KEY `mdl_user_alt_ix` (`alternatename`);

--
-- Indeks untuk tabel `tbl_manual`
--
ALTER TABLE `tbl_manual`
  ADD PRIMARY KEY (`tm_id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `datecache`
--
ALTER TABLE `datecache`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `fpinfo`
--
ALTER TABLE `fpinfo`
  MODIFY `FPINFO_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT untuk tabel `identified1n`
--
ALTER TABLE `identified1n`
  MODIFY `IDENTIFIED1N_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=156;

--
-- AUTO_INCREMENT untuk tabel `mdl_cp_user`
--
ALTER TABLE `mdl_cp_user`
  MODIFY `id` bigint(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `mdl_user`
--
ALTER TABLE `mdl_user`
  MODIFY `id` bigint(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT untuk tabel `tbl_manual`
--
ALTER TABLE `tbl_manual`
  MODIFY `tm_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `fpinfo`
--
ALTER TABLE `fpinfo`
  ADD CONSTRAINT `fpinfo_ibfk_1` FOREIGN KEY (`PERSON_ID`) REFERENCES `mdl_user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
