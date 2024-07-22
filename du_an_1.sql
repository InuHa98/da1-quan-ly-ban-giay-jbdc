﻿CREATE DATABASE DuAn1;
GO

USE DuAn1;
GO

CREATE TABLE TaiKhoan (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	tai_khoan VARCHAR(25) NOT NULL,
	mat_khau NVARCHAR(50) NOT NULL,
	email NVARCHAR(250) NOT NULL,
	ho_ten NVARCHAR(250),
	sdt VARCHAR(13) NOT NULL,
	gioi_tinh BIT,
	dia_chi NVARCHAR(2000),
	hinh_anh NVARCHAR(250),
	otp VARCHAR(6),
	adm BIT DEFAULT 0,
	trang_thai BIT DEFAULT 0,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	trang_thai_xoa BIT DEFAULT 0
);

CREATE TABLE KhachHang (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ho_ten NVARCHAR(250) NOT NULL,
	sdt VARCHAR(13) NOT NULL,
	gioi_tinh BIT,
	trang_thai_xoa BIT DEFAULT 0
);

CREATE TABLE DiaChi (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	id_khach_hang INT NOT NULL,
	dia_chi NVARCHAR(2000),
	trang_thai_xoa BIT DEFAULT 0,
	FOREIGN KEY(id_khach_hang) REFERENCES dbo.KhachHang(id)
)

CREATE TABLE DanhMuc (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ten NVARCHAR(250) NOT NULL,
	trang_thai_xoa BIT DEFAULT 0,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE DEFAULT GETDATE()
)


CREATE TABLE ChatLieu (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ten NVARCHAR(250) NOT NULL,
	trang_thai_xoa BIT DEFAULT 0,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE DEFAULT GETDATE()
)

CREATE TABLE XuatXu (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ten NVARCHAR(250) NOT NULL,
	trang_thai_xoa BIT DEFAULT 0,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE DEFAULT GETDATE(),
)

CREATE TABLE MauSac (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ten NVARCHAR(250) NOT NULL,
	trang_thai_xoa BIT DEFAULT 0,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE DEFAULT GETDATE()
)

CREATE TABLE KichCo (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ten NVARCHAR(250) NOT NULL,
	trang_thai_xoa BIT DEFAULT 0,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE DEFAULT GETDATE()
)

CREATE TABLE ThuongHieu (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ten NVARCHAR(250) NOT NULL,
	trang_thai_xoa BIT DEFAULT 0,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE DEFAULT GETDATE()
)

CREATE TABLE KieuDang (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ten NVARCHAR(250) NOT NULL,
	trang_thai_xoa BIT DEFAULT 0,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE DEFAULT GETDATE()
)

CREATE TABLE DeGiay (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ten NVARCHAR(250) NOT NULL,
	trang_thai_xoa BIT DEFAULT 0,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE DEFAULT GETDATE()
);

CREATE TABLE HinhAnh (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	url_anh NVARCHAR(250) NOT NULL,
	trang_thai_xoa BIT DEFAULT 0,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE DEFAULT GETDATE()
)

CREATE TABLE SanPham (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	id_danh_muc INT NOT NULL,
	id_thuong_hieu INT NOT NULL,
	id_xuat_xu INT NOT NULL,
	id_kieu_dang INT NOT NULL,
	id_chat_lieu INT NOT NULL,
	id_de_giay INT NOT NULL,
	id_hinh_anh INT NOT NULL,
	ma VARCHAR(50) NOT NULL,
	ten NVARCHAR(500) NOT NULL,
	mo_ta NVARCHAR(2000),
	gia_ban MONEY NOT NULL,
	qr_code VARCHAR(50),
	trang_thai BIT NOT NULL,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE DEFAULT GETDATE(),
	trang_thai_xoa BIT DEFAULT 0,
	FOREIGN KEY(id_danh_muc) REFERENCES dbo.DanhMuc(id),
	FOREIGN KEY(id_thuong_hieu) REFERENCES dbo.ThuongHieu(id),
	FOREIGN KEY(id_xuat_xu) REFERENCES dbo.XuatXu(id),
	FOREIGN KEY(id_kieu_dang) REFERENCES dbo.KieuDang(id),
	FOREIGN KEY(id_chat_lieu) REFERENCES dbo.ChatLieu(id),
	FOREIGN KEY(id_de_giay) REFERENCES dbo.DeGiay(id),
	FOREIGN KEY(id_hinh_anh) REFERENCES dbo.HinhAnh(id)
)

CREATE TABLE SanPhamChiTiet (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	id_san_pham INT NOT NULL,
	id_kich_co INT NOT NULL,
	id_mau_sac INT NOT NULL,
	gia_tang_them MONEY DEFAULT 0,
	so_luong INT DEFAULT 0,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE DEFAULT GETDATE(),
	trang_thai_xoa BIT DEFAULT 0,
	FOREIGN KEY(id_san_pham) REFERENCES dbo.SanPham(id),
	FOREIGN KEY(id_kich_co) REFERENCES dbo.KichCo(id),
	FOREIGN KEY(id_mau_sac) REFERENCES dbo.MauSac(id)
)

CREATE TABLE PhieuGiamGia (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ma VARCHAR(50) NOT NULL,
	ten NVARCHAR(250) NOT NULL,
	so_luong INT NOT NULL DEFAULT 0,
	ngay_bat_dau DATE NOT NULL,
	ngay_ket_thuc DATE NOT NULL,
	giam_theo_phan_tram BIT NOT NULL,
	gia_tri_giam FLOAT NOT NULL,
	don_toi_thieu MONEY NOT NULL DEFAULT 0,
	giam_toi_da MONEY NOT NULL DEFAULT 0,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE NOT NULL DEFAULT GETDATE(),
	trang_thai BIT NOT NULL,
	trang_thai_xoa BIT DEFAULT 0
)

CREATE TABLE DotGiamGia (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ten NVARCHAR(250) NOT NULL,
	thoi_gian_bat_dau DATE NOT NULL,
	thoi_gian_ket_thuc DATE NOT NULL,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE NOT NULL DEFAULT GETDATE(),
	trang_thai BIT NOT NULL,
	trang_thai_xoa BIT DEFAULT 0
)

CREATE TABLE DotGiamGiaSanPham (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	id_dot_giam_gia INT NOT NULL,
	id_san_pham INT NOT NULL,
	phan_tram_giam FLOAT DEFAULT 0,
	giam_toi_da MONEY DEFAULT 0,
	FOREIGN KEY(id_dot_giam_gia) REFERENCES dbo.DotGiamGia(id),
	FOREIGN KEY(id_san_pham) REFERENCES dbo.SanPham(id)
)

CREATE TABLE HoaDon (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	id_tai_khoan INT NOT NULL,
	id_khach_hang INT,
	id_phieu_giam_gia INT,
	tien_ship MONEY DEFAULT 0,
	tien_giam MONEY NOT NULL DEFAULT 0,
	tong_tien MONEY NOT NULL DEFAULT 0,
	ma VARCHAR(10) NOT NULL,
	mua_tai_quay BIT NOT NULL,
	phuong_thuc_thanh_toan BIT NOT NULL,
	ghi_chu NVARCHAR(2000),
	trang_thai INT NOT NULL,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE NOT NULL DEFAULT GETDATE(),
	trang_thai_xoa BIT DEFAULT 0,
	FOREIGN KEY(id_tai_khoan) REFERENCES dbo.TaiKhoan(id),
	FOREIGN KEY(id_khach_hang) REFERENCES dbo.KhachHang(id),
	FOREIGN KEY(id_phieu_giam_gia) REFERENCES dbo.PhieuGiamGia(id)
)

CREATE TABLE HoaDonChiTiet (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	id_san_pham_chi_tiet INT NOT NULL,
	id_hoa_don INT NOT NULL,
	gia_ban MONEY NOT NULL DEFAULT 0,
	gia_giam MONEY NOT NULL DEFAULT 0,
	so_luong INT NOT NULL DEFAULT 0,
	FOREIGN KEY(id_san_pham_chi_tiet) REFERENCES dbo.SanPhamChiTiet(id),
	FOREIGN KEY(id_hoa_don) REFERENCES dbo.HoaDon(id)
)

INSERT INTO dbo.TaiKhoan
(tai_khoan, mat_khau, email, ho_ten, sdt, gioi_tinh, dia_chi, hinh_anh, otp, trang_thai, adm, ngay_tao, trang_thai_xoa)
VALUES
(
	'admin',       -- username - varchar(25)
    N'123',      -- password - nvarchar(50)
    N'bimbimskim@gmail.com',      -- email - nvarchar(250)
    N'nguyễn tùng lâm',      -- ho_ten - nvarchar(250)
    '1111111111',       -- sdt - varchar(13)
    1,     -- gioi_tinh - bit
    N'',      -- dia_chi - nvarchar(250)
    N'',      -- avatar - nvarchar(250)
    '',       -- otp - varchar(6)
    1,     -- trang_thai - bit
    1,     -- is_admin - bit
    GETDATE(), -- ngay_tao - date
	0 -- trang_thai_xoa - bit
),
(
	'user',       -- username - varchar(25)
    N'123',      -- password - nvarchar(50)
    N'email@gmail.com',      -- email - nvarchar(250)
    N'nguyễn tùng lâm',      -- ho_ten - nvarchar(250)
    '2222222222',       -- sdt - varchar(13)
    1,     -- gioi_tinh - bit
    N'',      -- dia_chi - nvarchar(250)
    N'',      -- avatar - nvarchar(250)
    '',       -- otp - varchar(6)
    1,     -- trang_thai - bit
    0,     -- is_admin - bit
    GETDATE(), -- ngay_tao - date
	0 -- trang_thai_xoa - bit
)
