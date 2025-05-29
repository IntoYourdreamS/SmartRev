-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 27 Bulan Mei 2025 pada 18.37
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.0.30

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
-- Struktur dari tabel `barcode`
--

CREATE TABLE `barcode` (
  `id` int(11) NOT NULL,
  `id_produk` varchar(10) NOT NULL,
  `kode_barcode` varchar(50) NOT NULL,
  `tgl_expired` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `barcode`
--

INSERT INTO `barcode` (`id`, `id_produk`, `kode_barcode`, `tgl_expired`) VALUES
(7, 'PR001', '131', '2025-05-09'),
(8, 'PR001', '121', '2025-05-31'),
(12, 'PR003', '243', '2025-05-29'),
(13, 'PR002', '090', '2025-05-31');

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_karyawan`
--

CREATE TABLE `detail_karyawan` (
  `id_karyawan` varchar(50) DEFAULT NULL,
  `RFID` int(100) NOT NULL,
  `tanggal_masuk` timestamp NOT NULL DEFAULT current_timestamp(),
  `tanggal_keluar` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `detail_karyawan`
--

INSERT INTO `detail_karyawan` (`id_karyawan`, `RFID`, `tanggal_masuk`, `tanggal_keluar`) VALUES
('KR001', 12415125, '2025-05-22 12:48:18', '2025-05-22 12:48:26'),
('KR003', 123456, '2025-05-22 12:49:23', '2025-05-22 12:49:29'),
('KR001', 12415125, '2025-05-22 12:49:39', '2025-05-22 12:49:42'),
('KR003', 123456, '2025-05-22 12:54:21', '2025-05-22 12:55:14'),
('KR003', 123456, '2025-05-23 21:34:08', NULL),
('KR003', 123456, '2025-05-24 05:56:12', NULL),
('KR003', 123456, '2025-05-24 06:04:32', '2025-05-24 06:09:22'),
('KR003', 123456, '2025-05-24 06:10:03', NULL),
('KR001', 12415125, '2025-05-24 06:17:41', NULL),
('KR003', 123456, '2025-05-25 05:57:52', '2025-05-25 06:01:47'),
('KR001', 12415125, '2025-05-25 06:02:12', NULL),
('KR001', 12415125, '2025-05-25 06:43:19', NULL),
('KR003', 123456, '2025-05-25 06:44:10', '2025-05-25 06:56:08'),
('KR001', 12415125, '2025-05-25 06:50:16', NULL),
('KR001', 12415125, '2025-05-25 06:50:20', '2025-05-25 06:56:13'),
('KR001', 12415125, '2025-05-25 06:51:05', '2025-05-25 06:55:56'),
('KR001', 12415125, '2025-05-25 06:56:38', NULL),
('KR001', 12415125, '2025-05-25 06:57:38', '2025-05-25 07:00:12'),
('KR001', 12415125, '2025-05-25 07:00:46', '2025-05-25 07:04:11'),
('KR001', 12415125, '2025-05-25 07:21:39', '2025-05-25 07:22:40'),
('KR001', 12415125, '2025-05-25 07:23:03', '2025-05-25 07:23:37'),
('KR001', 12415125, '2025-05-25 07:23:54', '2025-05-25 07:24:45'),
('KR001', 12415125, '2025-05-25 07:25:05', '2025-05-25 07:42:07'),
('KR001', 12415125, '2025-05-25 07:42:24', NULL),
('KR003', 123456, '2025-05-25 07:57:31', NULL),
('KR003', 123456, '2025-05-25 08:13:33', '2025-05-25 08:15:29'),
('KR003', 123456, '2025-05-25 09:16:02', '2025-05-25 09:16:11'),
('KR003', 123456, '2025-05-25 09:19:04', '2025-05-25 09:19:18'),
('KR001', 12415125, '2025-05-25 09:19:25', '2025-05-25 09:19:31'),
('KR003', 123456, '2025-05-25 09:20:42', '2025-05-25 09:20:47'),
('KR001', 12415125, '2025-05-25 09:20:56', '2025-05-25 09:23:23'),
('KR003', 123456, '2025-05-25 09:23:43', '2025-05-25 09:23:50'),
('KR001', 12415125, '2025-05-25 09:23:58', '2025-05-25 09:25:35'),
('KR003', 123456, '2025-05-25 09:26:05', '2025-05-25 09:26:13'),
('KR001', 12415125, '2025-05-25 09:26:22', '2025-05-25 09:26:27'),
('KR001', 12415125, '2025-05-25 13:28:16', NULL),
('KR001', 12415125, '2025-05-25 17:56:27', '2025-05-25 18:03:22'),
('KR001', 12415125, '2025-05-25 18:03:47', '2025-05-25 18:13:44'),
('KR001', 12415125, '2025-05-25 18:16:48', '2025-05-25 18:16:51'),
('KR001', 12415125, '2025-05-25 18:18:47', NULL),
('KR001', 12415125, '2025-05-26 05:41:55', '2025-05-26 05:42:02'),
('KR001', 12415125, '2025-05-26 05:43:09', '2025-05-26 05:43:30'),
('KR001', 12415125, '2025-05-26 05:44:18', '2025-05-26 05:44:29'),
('KR001', 12415125, '2025-05-26 05:46:51', NULL),
('KR001', 12415125, '2025-05-26 06:06:26', NULL),
('KR001', 12415125, '2025-05-26 06:50:23', '2025-05-26 06:51:09'),
('KR001', 12415125, '2025-05-26 06:52:35', '2025-05-26 06:53:04'),
('KR001', 12415125, '2025-05-26 06:53:53', NULL),
('KR001', 12415125, '2025-05-26 07:13:50', NULL),
('KR001', 12415125, '2025-05-26 07:20:48', '2025-05-26 07:34:48'),
('KR001', 12415125, '2025-05-26 07:35:22', NULL),
('KR001', 12415125, '2025-05-26 07:35:26', NULL),
('KR001', 12415125, '2025-05-26 07:35:27', NULL),
('KR001', 12415125, '2025-05-26 08:25:18', NULL),
('KR001', 12415125, '2025-05-26 08:49:53', '2025-05-26 08:54:37'),
('KR001', 12415125, '2025-05-26 08:56:35', '2025-05-26 09:46:15'),
('KR001', 12415125, '2025-05-26 09:46:46', '2025-05-26 10:10:23'),
('KR001', 12415125, '2025-05-26 10:04:41', '2025-05-26 10:10:16'),
('KR001', 12415125, '2025-05-26 10:10:48', '2025-05-26 10:15:39'),
('KR001', 12415125, '2025-05-26 14:14:12', '2025-05-26 14:14:34'),
('KR001', 12415125, '2025-05-26 14:15:28', '2025-05-26 15:00:30'),
('KR001', 12415125, '2025-05-26 16:40:13', NULL),
('KR001', 12415125, '2025-05-26 16:49:03', NULL),
('KR001', 12415125, '2025-05-27 13:59:22', '2025-05-27 14:06:22'),
('KR001', 12415125, '2025-05-27 14:06:58', '2025-05-27 14:09:08'),
('KR001', 12415125, '2025-05-27 14:09:58', '2025-05-27 14:14:41'),
('KR001', 12415125, '2025-05-27 14:57:38', '2025-05-27 15:04:15'),
('KR001', 12415125, '2025-05-27 15:46:12', '2025-05-27 15:47:20'),
('KR001', 12415125, '2025-05-27 16:12:04', '2025-05-27 16:15:09'),
('KR001', 12415125, '2025-05-27 16:15:37', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_pembelian`
--

CREATE TABLE `detail_pembelian` (
  `id_pembelian` varchar(8) DEFAULT NULL,
  `id_produk` varchar(8) DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `sub_total` int(25) DEFAULT NULL,
  `harga_beli` int(25) NOT NULL,
  `kategori` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `detail_pembelian`
--

INSERT INTO `detail_pembelian` (`id_pembelian`, `id_produk`, `jumlah`, `sub_total`, `harga_beli`, `kategori`) VALUES
('PB001', 'PR001', 5, 225000, 45000, 'BahanBaku'),
(NULL, 'PR011', 1, NULL, 123123, NULL),
(NULL, 'PR012', 4, NULL, 123213, NULL),
(NULL, 'PR013', 1, NULL, 2999, 'kont'),
(NULL, 'PR014', 2, NULL, 1234, ''),
(NULL, 'PR015', 1, NULL, 2999, 'jajan'),
(NULL, 'PR016', 1, NULL, 8999, 'GH'),
('PB002', 'PR017', 2, NULL, 500, 'kopi'),
('PB003', 'PR018', 2, NULL, 4000, 'bumbu dapur'),
('PB004', 'PR019', 2, NULL, 1000, 'bumbu dapur'),
('PB005', 'PR020', 2, NULL, 8000, 'bumbu dapur'),
('PB006', 'PR021', 10, NULL, 5000, 'bumbu dapur'),
('PB007', 'PR022', 2, NULL, 1000, 'bumbu dapur'),
('PB008', 'PR023', 2, NULL, 1111, 'bumbu dapur'),
('PB009', 'PR024', 2, NULL, 4000, 'bumbu dapur'),
('PB003', 'PR025', 1, 212, 212, 'aaada'),
('PB008', 'PR003', 1, 222, 222, 'minuman'),
('PB009', 'PR004', 1, 16000, 16000, 'bahan dapur'),
('PB010', 'PR002', 1, 6000, 6000, 'bumbu dapur');

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_penjualan`
--

CREATE TABLE `detail_penjualan` (
  `id_penjualan` varchar(8) DEFAULT NULL,
  `id_produk` varchar(8) DEFAULT NULL,
  `kategori` varchar(15) NOT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `harga_satuan` decimal(10,2) DEFAULT NULL,
  `subtotal` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `detail_penjualan`
--

INSERT INTO `detail_penjualan` (`id_penjualan`, `id_produk`, `kategori`, `jumlah`, `harga_satuan`, `subtotal`) VALUES
('TR001', '121', 'BahanBaku', 2, 50000.00, 100000.00),
('TR001', '12345', 'BahanBaku', 2, 6000.00, 12000.00),
('TR002', '121', 'BahanBaku', 1, 50000.00, 50000.00),
('TR003', '121', 'BahanBaku', 2, 50000.00, 100000.00),
('TR004', '121', 'BahanBaku', 2, 50000.00, 100000.00),
('TR005', '12345', 'BahanBaku', 1, 6000.00, 6000.00),
('TR006', 'PR003', 'minuman', 1, 333.00, 333.00),
('TR007', 'PR001', 'BahanBaku', 1, 50000.00, 50000.00),
('TR008', 'PR001', 'BahanBaku', 1, 50000.00, 50000.00),
('TR009', 'PR004', 'bahan dapur', 1, 20000.00, 20000.00),
('TR010', 'PR004', 'bahan dapur', 1, 20000.00, 20000.00),
('TR011', 'PR004', 'bahan dapur', 1, 20000.00, 20000.00),
('TR012', 'PR001', 'BahanBaku', 1, 50000.00, 50000.00),
('TR013', 'PR001', '121', 1, 50000.00, 50000.00),
('TR014', 'PR001', '131', 1, 50000.00, 50000.00),
('TR015', 'PR001', '121', 1, 50000.00, 50000.00),
('TR016', 'PR001', '324', 1, 50000.00, 50000.00),
('TR016', 'PR004', '132', 1, 20000.00, 20000.00),
('TR017', 'PR002', '321', 1, 4000.00, 4000.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `karyawan`
--

CREATE TABLE `karyawan` (
  `id_karyawan` varchar(8) NOT NULL,
  `nama_karyawan` varchar(25) DEFAULT NULL,
  `no_telp` int(9) NOT NULL,
  `password` varchar(25) DEFAULT NULL,
  `tanggal_masuk` timestamp NOT NULL DEFAULT current_timestamp(),
  `role` enum('Owner','Karyawan') NOT NULL,
  `RFID` int(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `nama_karyawan`, `no_telp`, `password`, `tanggal_masuk`, `role`, `RFID`) VALUES
('KR001', 'SobriAJA', 12521512, '12345', '2025-03-20 14:43:31', 'Owner', 12415125),
('KR002', 'nandoBOY', 12345678, '67891', '2025-04-28 16:27:57', 'Owner', 87689537),
('KR003', 'nandoribet', 12345, 'ribet123', '2025-05-07 18:44:00', 'Karyawan', 123456),
('KR004', 'sultanbeh', 54321, 'tan12345', '2025-05-08 00:53:32', 'Owner', 654321);

-- --------------------------------------------------------

--
-- Struktur dari tabel `pembelian`
--

CREATE TABLE `pembelian` (
  `id_pembelian` varchar(8) NOT NULL,
  `id_karyawan` varchar(8) DEFAULT NULL,
  `id_supplier` varchar(8) DEFAULT NULL,
  `tanggal` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `pembelian`
--

INSERT INTO `pembelian` (`id_pembelian`, `id_karyawan`, `id_supplier`, `tanggal`) VALUES
('PB001', 'KR001', 'SP003', '2025-05-25'),
('PB002', 'KR001', 'SP003', '2025-05-25'),
('PB003', 'KR001', 'SP003', '2025-05-25'),
('PB004', 'KR001', 'SP003', '2025-05-26'),
('PB005', 'KR001', 'SP003', '2025-05-26'),
('PB006', 'KR001', 'SP003', '2025-05-26'),
('PB007', 'KR001', 'SP003', '2025-05-26'),
('PB008', 'KR001', 'SP003', '2025-05-26'),
('PB009', 'KR001', 'SP003', '2025-05-26'),
('PB010', 'KR001', 'SP003', '2025-05-27');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penjualan`
--

CREATE TABLE `penjualan` (
  `id_penjualan` varchar(8) NOT NULL,
  `id_karyawan` varchar(8) DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `total_harga` decimal(10,2) DEFAULT NULL,
  `bayar` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `penjualan`
--

INSERT INTO `penjualan` (`id_penjualan`, `id_karyawan`, `tanggal`, `total_harga`, `bayar`) VALUES
('TR001', NULL, '2025-03-20', 112000.00, 120000.00),
('TR002', NULL, '2025-03-20', 50000.00, 60000.00),
('TR003', 'KR001', '2025-03-21', 100000.00, 120000.00),
('TR004', 'KR001', '2025-03-21', 100000.00, 120000.00),
('TR005', '', '2025-04-28', 6000.00, 6000.00),
('TR006', 'KR001', '2025-05-26', 333.00, 444.00),
('TR007', 'KR001', '2025-05-26', 50000.00, 100000.00),
('TR008', 'KR001', '2025-05-26', 50000.00, 60000.00),
('TR009', 'KR001', '2025-05-26', 20000.00, 22000.00),
('TR010', 'KR001', '2025-05-26', 20000.00, 20000.00),
('TR011', '', '2025-05-26', 20000.00, 30000.00),
('TR012', '', '2025-05-26', 50000.00, 100000.00),
('TR013', '', '2025-05-26', 50000.00, 100000.00),
('TR014', '', '2025-05-26', 50000.00, 100000.00),
('TR015', '', '2025-05-26', 50000.00, 80000.00),
('TR016', 'KR001', '2025-05-26', 70000.00, 90000.00),
('TR017', 'KR001', '2025-05-26', 4000.00, 5000.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `produk`
--

CREATE TABLE `produk` (
  `id_produk` varchar(8) NOT NULL,
  `nama_produk` varchar(255) DEFAULT NULL,
  `harga` int(10) DEFAULT NULL,
  `stok` int(11) DEFAULT NULL,
  `id_supplier` varchar(8) DEFAULT NULL,
  `kategori` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `produk`
--

INSERT INTO `produk` (`id_produk`, `nama_produk`, `harga`, `stok`, `id_supplier`, `kategori`) VALUES
('PR001', 'Beras', 50000, 2, 'SP003', 'BahanBaku'),
('PR002', 'gula', 4000, 1, 'SP003', 'bumbu dapur'),
('PR003', 'kopi', 333, 1, 'SP003', 'minuman'),
('PR004', 'gas elpiji', 20000, 0, 'SP003', 'bahan dapur');

-- --------------------------------------------------------

--
-- Struktur dari tabel `retur_penjualan`
--

CREATE TABLE `retur_penjualan` (
  `id_retur_penjualan` varchar(8) NOT NULL,
  `id_pembelian` varchar(8) DEFAULT NULL,
  `tanggal_retur` date DEFAULT NULL,
  `alasan` varchar(255) DEFAULT NULL,
  `id_produk` varchar(8) DEFAULT NULL,
  `barcode` varchar(100) DEFAULT NULL,
  `nama_produk` varchar(50) DEFAULT NULL,
  `id_supplier` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `retur_penjualan`
--

INSERT INTO `retur_penjualan` (`id_retur_penjualan`, `id_pembelian`, `tanggal_retur`, `alasan`, `id_produk`, `barcode`, `nama_produk`, `id_supplier`) VALUES
('RT001', 'PB001', '2025-05-24', 'rusak', 'PR001', '43234', 'indomie', 'SP001'),
('RT002', 'PB001', '2025-05-24', 'kadaluarsa', 'PR001', '121', 'Beras', 'SP003');

-- --------------------------------------------------------

--
-- Struktur dari tabel `stok_opname`
--

CREATE TABLE `stok_opname` (
  `kd_barang` char(6) NOT NULL,
  `namaBarang` varchar(25) NOT NULL,
  `stokSistem` int(5) NOT NULL,
  `stokFisik` int(5) NOT NULL,
  `selisih` varchar(5) NOT NULL,
  `catatan` text NOT NULL,
  `create_at` date NOT NULL DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `stok_opname`
--

INSERT INTO `stok_opname` (`kd_barang`, `namaBarang`, `stokSistem`, `stokFisik`, `selisih`, `catatan`, `create_at`) VALUES
('PR002', 'gula', 1, 1, '0', 'Stok sesuai', '2025-05-27'),
('PR003', 'kopi', 1, 1, '0', 'Stok sesuai', '2025-05-27'),
('PR004', 'gas elpiji', 2, 2, '0', 'Stok sesuai', '2025-05-27');

-- --------------------------------------------------------

--
-- Struktur dari tabel `supplier`
--

CREATE TABLE `supplier` (
  `id_supplier` varchar(8) NOT NULL,
  `nama_supplier` varchar(255) DEFAULT NULL,
  `alamat` varchar(255) DEFAULT NULL,
  `telepon` varchar(20) DEFAULT NULL,
  `create_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `supplier`
--

INSERT INTO `supplier` (`id_supplier`, `nama_supplier`, `alamat`, `telepon`, `create_at`) VALUES
('SP003', 'CV Sejahtera', 'Jl. Merdeka No. 45', '082345678901', '2025-05-02 04:02:16'),
('SP004', 'nando', 'sdssds', '121212', '2025-05-21 16:41:44');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `barcode`
--
ALTER TABLE `barcode`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `kode_barcode` (`kode_barcode`),
  ADD KEY `id_produk` (`id_produk`);

--
-- Indeks untuk tabel `detail_karyawan`
--
ALTER TABLE `detail_karyawan`
  ADD KEY `id_karyawan` (`id_karyawan`);

--
-- Indeks untuk tabel `detail_pembelian`
--
ALTER TABLE `detail_pembelian`
  ADD KEY `id_pembelian` (`id_pembelian`,`id_produk`),
  ADD KEY `id_produk` (`id_produk`);

--
-- Indeks untuk tabel `detail_penjualan`
--
ALTER TABLE `detail_penjualan`
  ADD KEY `id_penjualan` (`id_penjualan`),
  ADD KEY `id_produk` (`id_produk`);

--
-- Indeks untuk tabel `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`id_karyawan`);

--
-- Indeks untuk tabel `pembelian`
--
ALTER TABLE `pembelian`
  ADD PRIMARY KEY (`id_pembelian`),
  ADD KEY `id_karyawan` (`id_karyawan`,`id_supplier`),
  ADD KEY `id_supplier` (`id_supplier`);

--
-- Indeks untuk tabel `penjualan`
--
ALTER TABLE `penjualan`
  ADD PRIMARY KEY (`id_penjualan`),
  ADD KEY `id_karyawan` (`id_karyawan`);

--
-- Indeks untuk tabel `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`id_produk`);

--
-- Indeks untuk tabel `retur_penjualan`
--
ALTER TABLE `retur_penjualan`
  ADD PRIMARY KEY (`id_retur_penjualan`),
  ADD KEY `fk_retur_produk` (`id_produk`);

--
-- Indeks untuk tabel `stok_opname`
--
ALTER TABLE `stok_opname`
  ADD PRIMARY KEY (`kd_barang`);

--
-- Indeks untuk tabel `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id_supplier`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `barcode`
--
ALTER TABLE `barcode`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `barcode`
--
ALTER TABLE `barcode`
  ADD CONSTRAINT `barcode_ibfk_1` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `detail_karyawan`
--
ALTER TABLE `detail_karyawan`
  ADD CONSTRAINT `detail_karyawan_ibfk_1` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`);

--
-- Ketidakleluasaan untuk tabel `retur_penjualan`
--
ALTER TABLE `retur_penjualan`
  ADD CONSTRAINT `fk_retur_produk` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
