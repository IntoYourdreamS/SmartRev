-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 17, 2025 at 03:59 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 7.4.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smart`
--

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE `karyawan` (
  `id_karyawan` varchar(8) NOT NULL,
  `Nama_Karyawan` varchar(100) NOT NULL,
  `Alamat` text NOT NULL,
  `No_Hp` varchar(15) NOT NULL,
  `Posisi` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `RFID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `Nama_Karyawan`, `Alamat`, `No_Hp`, `Posisi`, `Password`, `RFID`) VALUES
('KR001', 'Putra', 'Jember', '124215125', 'Karyawan', '123', 1424124125);

-- --------------------------------------------------------

--
-- Table structure for table `laporan`
--

CREATE TABLE `laporan` (
  `No` int(11) NOT NULL,
  `Tanggal` date NOT NULL,
  `Kasir` varchar(100) NOT NULL,
  `Jam_Masuk` time NOT NULL,
  `Jam_Pulang` time NOT NULL,
  `Kehadiran` enum('Hadir','Tidak Hadir','Izin','Sakit') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tb_restock`
--

CREATE TABLE `tb_restock` (
  `Kode_Barang` varchar(20) NOT NULL,
  `Nama_Barang` varchar(100) NOT NULL,
  `Harga_Satuan` decimal(10,2) NOT NULL,
  `Jumlah_Jual` int(11) NOT NULL,
  `Harga_Akhir` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tb_transaksi`
--

CREATE TABLE `tb_transaksi` (
  `Kode_Barang` varchar(20) NOT NULL,
  `Nama_Barang` varchar(100) NOT NULL,
  `Stock_Barang` int(11) NOT NULL,
  `Harga_Satuan` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`id_karyawan`);

--
-- Indexes for table `laporan`
--
ALTER TABLE `laporan`
  ADD PRIMARY KEY (`No`);

--
-- Indexes for table `tb_restock`
--
ALTER TABLE `tb_restock`
  ADD PRIMARY KEY (`Kode_Barang`);

--
-- Indexes for table `tb_transaksi`
--
ALTER TABLE `tb_transaksi`
  ADD PRIMARY KEY (`Kode_Barang`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `laporan`
--
ALTER TABLE `laporan`
  MODIFY `No` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
