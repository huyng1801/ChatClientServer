CREATE DATABASE ung_dung_chat;
go
USE ung_dung_chat;
go
CREATE TABLE nguoi_dung (
  ma_nguoi_dung int NOT NULL IDENTITY(1,1) PRIMARY KEY,
  ten_nguoi_dung nvarchar(50) NOT NULL UNIQUE DEFAULT '',
  mat_khau nvarchar(50) NOT NULL DEFAULT ''
);
CREATE TABLE tin_nhan (
  ma_tin_nhan int NOT NULL IDENTITY(1,1) PRIMARY KEY,
  thoi_gian datetime2(6) NOT NULL DEFAULT SYSDATETIME(),
  noi_dung_tin_nhan ntext NOT NULL DEFAULT '0',
  ten_nguoi_gui nvarchar(50) NOT NULL DEFAULT '0',
  ten_nguoi_nhan nvarchar(50) NOT NULL DEFAULT '',
  FOREIGN KEY (ten_nguoi_gui) REFERENCES nguoi_dung (ten_nguoi_dung) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (ten_nguoi_nhan) REFERENCES nguoi_dung (ten_nguoi_dung) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Insert data into the ta_mnr_user table
INSERT INTO nguoi_dung (ten_nguoi_dung, mat_khau) VALUES
(N'admin', '123'),
(N'dat', '123'),
(N'day', '123'),
(N'vu', '123'),
(N'teo', '123');

-- Insert data into the ta_mnr_tinnhan table
INSERT INTO tin_nhan (thoi_gian, noi_dung_tin_nhan, ten_nguoi_gui, ten_nguoi_nhan) VALUES
('2023-05-29 09:07:01.440594', N'Hello', 'admin', 'dat'),
('2023-05-29 09:07:01.822559', N'What is your name?', 'admin', 'dat'),
('2023-05-29 09:07:02.038532', N'My name is Dat', 'admin', 'dat'),
('2023-05-29 09:07:02.169784', N'How are you?', 'admin', 'dat'),
('2023-06-03 16:45:49.352019', N'Im fine', 'admin', 'dat'),
('2023-06-03 16:56:04.905630', N'How do you feel?', 'dat', 'admin'),
('2023-06-03 17:15:03.873380', N'Im so tired', 'admin', 'dat'),
('2023-06-03 17:19:25.969331', N'Me too', 'admin', 'admin');
CREATE TABLE nhom (
  ma_nhom int NOT NULL IDENTITY(1,1) PRIMARY KEY,
  ten_nhom nvarchar(50) NOT NULL UNIQUE DEFAULT ''
);
CREATE TABLE nhom_nguoi_dung (
	ma_nhom_nguoi_dung int NOT NULL IDENTITY(1,1) PRIMARY KEY,
	ten_nguoi_dung nvarchar(50) not null,
	ten_nhom nvarchar(50) not null,
	FOREIGN KEY (ten_nguoi_dung) REFERENCES nguoi_dung (ten_nguoi_dung) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (ten_nhom) REFERENCES nhom (ten_nhom) ON DELETE NO ACTION ON UPDATE NO ACTION,
);
CREATE TABLE tin_nhan_nhom (
  ma_tin_nhan_nhom int NOT NULL IDENTITY(1,1) PRIMARY KEY,
  thoi_gian datetime2(6) NOT NULL DEFAULT SYSDATETIME(),
  noi_dung_tin_nhan_nhom ntext NOT NULL DEFAULT '0',
  ten_nguoi_dung nvarchar(50) NOT NULL,
  ten_nhom nvarchar(50) NOT NULL,
  FOREIGN KEY (ten_nguoi_dung) REFERENCES nguoi_dung (ten_nguoi_dung) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (ten_nhom) REFERENCES nhom (ten_nhom) ON DELETE NO ACTION ON UPDATE NO ACTION
);
