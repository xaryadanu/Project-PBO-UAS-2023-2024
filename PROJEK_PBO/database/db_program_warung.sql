-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 03, 2022 at 03:59 PM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_program_warung`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_admin`
--

CREATE TABLE `tb_admin` (
  `kodeadmin` varchar(50) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `level` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_admin`
--

INSERT INTO `tb_admin` (`kodeadmin`, `nama`, `username`, `password`, `level`) VALUES
('ADM001', 'admin', 'admin', 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `tb_barang`
--

CREATE TABLE `tb_barang` (
  `kodebarang` varchar(50) NOT NULL,
  `namabarang` varchar(50) NOT NULL,
  `harga` int(50) NOT NULL,
  `stok` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_barang`
--

INSERT INTO `tb_barang` (`kodebarang`, `namabarang`, `harga`, `stok`) VALUES
('KB001', 'ROKOK SAMPURNA MILD', 25000, 9);

-- --------------------------------------------------------

--
-- Table structure for table `tb_pembelian`
--

CREATE TABLE `tb_pembelian` (
  `kodetransaksi` varchar(50) NOT NULL,
  `kodebarang` varchar(50) NOT NULL,
  `namabarang` varchar(50) NOT NULL,
  `supplier` varchar(50) NOT NULL,
  `tanggal` date NOT NULL,
  `jumlah` int(50) NOT NULL,
  `hargabeli` int(50) NOT NULL,
  `hargajual` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_pembelian`
--

INSERT INTO `tb_pembelian` (`kodetransaksi`, `kodebarang`, `namabarang`, `supplier`, `tanggal`, `jumlah`, `hargabeli`, `hargajual`) VALUES
('TR001', 'KB001', 'ROKOK SAMPURNA MILD', 'BANG JAMAL', '2022-01-03', 10, 200000, 25000);

--
-- Triggers `tb_pembelian`
--
DELIMITER $$
CREATE TRIGGER `replacehargajual` AFTER INSERT ON `tb_pembelian` FOR EACH ROW BEGIN
UPDATE tb_barang SET harga=new.hargajual WHERE kodebarang=new.kodebarang;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `updatestok` AFTER INSERT ON `tb_pembelian` FOR EACH ROW BEGIN
UPDATE tb_barang SET stok=stok+new.jumlah WHERE kodebarang=new.kodebarang;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_penjualan`
--

CREATE TABLE `tb_penjualan` (
  `kodetransaksi` varchar(50) NOT NULL,
  `kodebarang` varchar(50) NOT NULL,
  `namabarang` varchar(50) NOT NULL,
  `tanggal` date NOT NULL,
  `harga` int(50) NOT NULL,
  `jumlah` int(50) NOT NULL,
  `subtotal` int(50) NOT NULL,
  `total` int(50) NOT NULL,
  `bayar` int(50) NOT NULL,
  `kembalian` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_penjualan`
--

INSERT INTO `tb_penjualan` (`kodetransaksi`, `kodebarang`, `namabarang`, `tanggal`, `harga`, `jumlah`, `subtotal`, `total`, `bayar`, `kembalian`) VALUES
('TR001', 'KB001', 'ROKOK SAMPURNA MILD', '2022-01-03', 25000, 1, 25000, 25000, 50000, 25000);

--
-- Triggers `tb_penjualan`
--
DELIMITER $$
CREATE TRIGGER `updatestok2` AFTER INSERT ON `tb_penjualan` FOR EACH ROW UPDATE tb_barang SET stok=stok-new.jumlah WHERE kodebarang=new.kodebarang
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_supplier`
--

CREATE TABLE `tb_supplier` (
  `kodesupplier` varchar(50) NOT NULL,
  `namasupplier` varchar(50) NOT NULL,
  `tlp` varchar(50) NOT NULL,
  `alamat` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_supplier`
--

INSERT INTO `tb_supplier` (`kodesupplier`, `namasupplier`, `tlp`, `alamat`) VALUES
('KDS001', 'BANG JAMAL', '0897', 'JL.KENARI');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_admin`
--
ALTER TABLE `tb_admin`
  ADD PRIMARY KEY (`kodeadmin`);

--
-- Indexes for table `tb_barang`
--
ALTER TABLE `tb_barang`
  ADD PRIMARY KEY (`kodebarang`);

--
-- Indexes for table `tb_pembelian`
--
ALTER TABLE `tb_pembelian`
  ADD PRIMARY KEY (`kodetransaksi`);

--
-- Indexes for table `tb_supplier`
--
ALTER TABLE `tb_supplier`
  ADD PRIMARY KEY (`kodesupplier`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
