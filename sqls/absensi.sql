-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 14 Nov 2022 pada 08.51
-- Versi server: 10.1.37-MariaDB
-- Versi PHP: 5.6.39

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `absensi`
--

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

-- --------------------------------------------------------

--
-- Struktur dari tabel `identified1n`
--

CREATE TABLE `identified1n` (
  `IDENTIFIED1N_ID` int(11) NOT NULL,
  `TRIALDATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `FPID` int(11) DEFAULT '-1',
  `SCORE` int(11) DEFAULT '-1',
  `PFN` int(11) DEFAULT '-1',
  `REGNAME` varchar(2014) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'ex: NOS_A_JOB1_MASUK, STI_C_JOB2_MAXUK',
  `SESSION_ID` bigint(20) NOT NULL COMMENT 'cache session id in moodle'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `mdl_cp_user`
--

CREATE TABLE `mdl_cp_user` (
  `id` bigint(10) NOT NULL,
  `auth` varchar(20) NOT NULL DEFAULT 'manual',
  `confirmed` tinyint(1) NOT NULL DEFAULT '0',
  `policyagreed` tinyint(1) NOT NULL DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `suspended` tinyint(1) NOT NULL DEFAULT '0',
  `mnethostid` bigint(10) NOT NULL DEFAULT '0',
  `username` varchar(100) NOT NULL DEFAULT '',
  `password` varchar(255) NOT NULL DEFAULT '',
  `idnumber` varchar(255) NOT NULL DEFAULT '',
  `firstname` varchar(100) NOT NULL DEFAULT '',
  `lastname` varchar(100) NOT NULL DEFAULT '',
  `email` varchar(100) NOT NULL DEFAULT '',
  `emailstop` tinyint(1) NOT NULL DEFAULT '0',
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
  `firstaccess` bigint(10) NOT NULL DEFAULT '0',
  `lastaccess` bigint(10) NOT NULL DEFAULT '0',
  `lastlogin` bigint(10) NOT NULL DEFAULT '0',
  `currentlogin` bigint(10) NOT NULL DEFAULT '0',
  `lastip` varchar(45) NOT NULL DEFAULT '',
  `secret` varchar(15) NOT NULL DEFAULT '',
  `picture` bigint(10) NOT NULL DEFAULT '0',
  `url` varchar(255) NOT NULL DEFAULT '',
  `description` longtext,
  `descriptionformat` tinyint(2) NOT NULL DEFAULT '1',
  `mailformat` tinyint(1) NOT NULL DEFAULT '1',
  `maildigest` tinyint(1) NOT NULL DEFAULT '0',
  `maildisplay` tinyint(2) NOT NULL DEFAULT '2',
  `autosubscribe` tinyint(1) NOT NULL DEFAULT '1',
  `trackforums` tinyint(1) NOT NULL DEFAULT '0',
  `timecreated` bigint(10) NOT NULL DEFAULT '0',
  `timemodified` bigint(10) NOT NULL DEFAULT '0',
  `trustbitmask` bigint(10) NOT NULL DEFAULT '0',
  `imagealt` varchar(255) DEFAULT NULL,
  `lastnamephonetic` varchar(255) DEFAULT NULL,
  `firstnamephonetic` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `alternatename` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='One record for each person' ROW_FORMAT=COMPRESSED;

-- --------------------------------------------------------

--
-- Struktur dari tabel `mdl_user`
--

CREATE TABLE `mdl_user` (
  `id` bigint(10) NOT NULL,
  `remote_id` bigint(10) NOT NULL COMMENT 'remote moodle id for REST synchronization',
  `auth` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'manual',
  `confirmed` tinyint(1) NOT NULL DEFAULT '0',
  `policyagreed` tinyint(1) NOT NULL DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `suspended` tinyint(1) NOT NULL DEFAULT '0',
  `mnethostid` bigint(10) NOT NULL DEFAULT '0',
  `username` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `idnumber` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `firstname` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `lastname` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `email` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `emailstop` tinyint(1) NOT NULL DEFAULT '0',
  `icq` varchar(15) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `skype` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `yahoo` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `aim` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `msn` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `phone1` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `phone2` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `institution` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `department` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `address` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `city` varchar(120) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `country` varchar(2) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `lang` varchar(30) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'en',
  `calendartype` varchar(30) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'gregorian',
  `theme` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `timezone` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT '99',
  `firstaccess` bigint(10) NOT NULL DEFAULT '0',
  `lastaccess` bigint(10) NOT NULL DEFAULT '0',
  `lastlogin` bigint(10) NOT NULL DEFAULT '0',
  `currentlogin` bigint(10) NOT NULL DEFAULT '0',
  `lastip` varchar(45) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `secret` varchar(15) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `picture` bigint(10) NOT NULL DEFAULT '0',
  `url` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `description` longtext COLLATE utf8_unicode_ci,
  `descriptionformat` tinyint(2) NOT NULL DEFAULT '1',
  `mailformat` tinyint(1) NOT NULL DEFAULT '1',
  `maildigest` tinyint(1) NOT NULL DEFAULT '0',
  `maildisplay` tinyint(2) NOT NULL DEFAULT '2',
  `autosubscribe` tinyint(1) NOT NULL DEFAULT '1',
  `trackforums` tinyint(1) NOT NULL DEFAULT '0',
  `timecreated` bigint(10) NOT NULL DEFAULT '0',
  `timemodified` bigint(10) NOT NULL DEFAULT '0',
  `trustbitmask` bigint(10) NOT NULL DEFAULT '0',
  `imagealt` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lastnamephonetic` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `firstnamephonetic` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `middlename` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `alternatename` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='One record for each person' ROW_FORMAT=COMPRESSED;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_manual`
--

CREATE TABLE `tbl_manual` (
  `tm_id` int(11) NOT NULL,
  `NIM` varchar(20) NOT NULL,
  `NAMA` varchar(1024) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

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
-- AUTO_INCREMENT untuk tabel `fpinfo`
--
ALTER TABLE `fpinfo`
  MODIFY `FPINFO_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `identified1n`
--
ALTER TABLE `identified1n`
  MODIFY `IDENTIFIED1N_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `mdl_cp_user`
--
ALTER TABLE `mdl_cp_user`
  MODIFY `id` bigint(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `mdl_user`
--
ALTER TABLE `mdl_user`
  MODIFY `id` bigint(10) NOT NULL AUTO_INCREMENT;

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
