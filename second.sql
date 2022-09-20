-- --------------------------------------------------------
-- 主機:                           127.0.0.1
-- 伺服器版本:                        10.8.3-MariaDB - mariadb.org binary distribution
-- 伺服器作業系統:                      Win64
-- HeidiSQL 版本:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 傾印 second 的資料庫結構
CREATE DATABASE IF NOT EXISTS `second` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `second`;

-- 傾印  資料表 second.bud 結構
CREATE TABLE IF NOT EXISTS `bud` (
  `bud_ymd` char(8) NOT NULL,
  `bud_type` char(1) NOT NULL,
  `bud_u_time` datetime NOT NULL,
  PRIMARY KEY (`bud_ymd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在傾印表格  second.bud 的資料：~10 rows (近似值)
/*!40000 ALTER TABLE `bud` DISABLE KEYS */;
REPLACE INTO `bud` (`bud_ymd`, `bud_type`, `bud_u_time`) VALUES
	('20220101', 'N', '2021-10-28 14:25:00'),
	('20220102', 'N', '2021-10-28 14:25:00'),
	('20220103', 'Y', '2021-10-28 14:25:00'),
	('20220104', 'Y', '2021-10-28 14:25:00'),
	('20220907', 'Y', '2021-10-28 14:25:00'),
	('20220908', 'Y', '2021-10-28 14:25:00'),
	('20220909', 'N', '2022-09-16 17:21:17'),
	('20220912', 'Y', '2021-10-28 14:25:00');
/*!40000 ALTER TABLE `bud` ENABLE KEYS */;

-- 傾印  資料表 second.nfa 結構
CREATE TABLE IF NOT EXISTS `nfa` (
  `nfa_uuid` char(17) NOT NULL,
  `nfa_subject` varchar(60) NOT NULL,
  `nfa_content` varchar(16287) NOT NULL,
  `nfa_enable` char(1) NOT NULL,
  `nfa_s_time` char(8) NOT NULL,
  `nfa_e_time` char(8) NOT NULL,
  `nfa_u_time` datetime NOT NULL,
  PRIMARY KEY (`nfa_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在傾印表格  second.nfa 的資料：~3 rows (近似值)
/*!40000 ALTER TABLE `nfa` DISABLE KEYS */;
REPLACE INTO `nfa` (`nfa_uuid`, `nfa_subject`, `nfa_content`, `nfa_enable`, `nfa_s_time`, `nfa_e_time`, `nfa_u_time`) VALUES
	('20220901113007123', '專案事宜', '測試測試', 'Y', '20220901', '20220930', '2022-09-11 14:25:00'),
	('20220912113007123', '專案一提醒', '請於 9/15 繳交', 'Y', '20220902', '20220930', '2022-09-11 14:25:00');
/*!40000 ALTER TABLE `nfa` ENABLE KEYS */;

-- 傾印  資料表 second.prod 結構
CREATE TABLE IF NOT EXISTS `prod` (
  `prod_id` char(7) NOT NULL,
  `prod_kind` char(3) NOT NULL,
  `prod_name` varchar(120) NOT NULL,
  `prod_ename` varchar(120) NOT NULL,
  `prod_ccy` char(3) NOT NULL,
  `prod_enable` char(3) NOT NULL,
  `prod_i_time` datetime NOT NULL,
  `prod_u_time` datetime NOT NULL,
  PRIMARY KEY (`prod_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在傾印表格  second.prod 的資料：~7 rows (近似值)
/*!40000 ALTER TABLE `prod` DISABLE KEYS */;
REPLACE INTO `prod` (`prod_id`, `prod_kind`, `prod_name`, `prod_ename`, `prod_ccy`, `prod_enable`, `prod_i_time`, `prod_u_time`) VALUES
	('EAT_JPN', 'EAT', '茶', 'tea', 'JPN', 'Y', '2021-10-28 14:25:00', '2022-09-16 10:15:33'),
	('EAT_THB', 'EAT', '飯', 'rice', 'THB', 'N', '2021-10-28 14:25:00', '2022-09-16 10:15:01'),
	('EAT_TWD', 'EAT', '飯', 'rice', 'TWD', 'Y', '2021-10-28 14:25:00', '2022-09-16 10:13:16'),
	('EAT_USD', 'EAT', '茶', 'tea', 'USD', 'Y', '2021-10-28 14:25:00', '2022-09-16 10:14:07'),
	('USE_JPN', 'USE', '電腦', 'pc', 'JPN', 'N', '2021-10-28 14:25:00', '2022-09-16 10:16:26'),
	('USE_TWD', 'USE', '電腦', 'pc', 'TWD', 'Y', '2021-10-28 14:25:00', '2022-09-16 10:03:36');
/*!40000 ALTER TABLE `prod` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
