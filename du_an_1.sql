CREATE DATABASE DuAn1;
GO

USE DuAn1;
GO

CREATE TABLE NhanVien (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	tai_khoan VARCHAR(25) NOT NULL,
	mat_khau NVARCHAR(50) NOT NULL,
	email NVARCHAR(250) NOT NULL,
	ho_ten NVARCHAR(250),
	sdt VARCHAR(13) NOT NULL,
	gioi_tinh BIT,
	dia_chi NVARCHAR(250),
	hinh_anh NVARCHAR(250),
	otp VARCHAR(6),
	trang_thai BIT,
	vai_tro_quan_ly BIT DEFAULT 0,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	da_xoa BIT DEFAULT 0
)

CREATE TABLE KhachHang (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ho_ten NVARCHAR(250) NOT NULL,
	sdt VARCHAR(13) NOT NULL,
	gioi_tinh BIT,
	diem INT DEFAULT 0,
	da_xoa BIT DEFAULT 0
)

CREATE TABLE DanhMuc (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	refid INT,
	ten NVARCHAR(250) NOT NULL,
	ghi_chu NVARCHAR(250),
	da_xoa BIT DEFAULT 0,
	FOREIGN KEY(refid) REFERENCES dbo.DanhMuc(id)
)


CREATE TABLE ChatLieu (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ten NVARCHAR(250) NOT NULL,
	da_xoa BIT DEFAULT 0
)

CREATE TABLE XuatXu (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ten NVARCHAR(250) NOT NULL,
	da_xoa BIT DEFAULT 0
)

CREATE TABLE MauSac (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ten NVARCHAR(250) NOT NULL,
	da_xoa BIT DEFAULT 0
)

CREATE TABLE KichCo (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ten NVARCHAR(250) NOT NULL,
	ghi_chu NVARCHAR(250),
	da_xoa BIT DEFAULT 0
)

CREATE TABLE ThuongHieu (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ten NVARCHAR(250) NOT NULL,
	da_xoa BIT DEFAULT 0
)

CREATE TABLE KieuDang (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ten NVARCHAR(250) NOT NULL,
	da_xoa BIT DEFAULT 0
)

CREATE TABLE SanPham(
	id INT IDENTITY(1, 1) PRIMARY KEY,
	id_nhan_vien INT NOT NULL,
	id_danh_muc INT NOT NULL,
	id_thuong_hieu INT NOT NULL,
	id_kich_co INT NOT NULL,
	id_chat_lieu INT NOT NULL,
	id_mau_sac INT NOT NULL,
	id_kieu_dang INT NOT NULL,
	id_xuat_xu INT NOT NULL,
	ma VARCHAR(50) NOT NULL UNIQUE,
	ten NVARCHAR(250) NOT NULL,
	mo_ta NVARCHAR(1000),
	gia_ban MONEY NOT NULL,
	so_luong INT NOT NULL DEFAULT 0,
	bar_code VARCHAR(13),
	trang_thai BIT NOT NULL,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE DEFAULT GETDATE(),
	da_xoa BIT DEFAULT 0,
	FOREIGN KEY(id_nhan_vien) REFERENCES dbo.NhanVien(id),
	FOREIGN KEY(id_danh_muc) REFERENCES dbo.DanhMuc(id),
	FOREIGN KEY(id_thuong_hieu) REFERENCES dbo.ThuongHieu(id),
	FOREIGN KEY(id_kich_co) REFERENCES dbo.KichCo(id),
	FOREIGN KEY(id_chat_lieu) REFERENCES dbo.ChatLieu(id),
	FOREIGN KEY(id_mau_sac) REFERENCES dbo.MauSac(id),
	FOREIGN KEY(id_kieu_dang) REFERENCES dbo.KieuDang(id),
	FOREIGN KEY(id_xuat_xu) REFERENCES dbo.XuatXu(id)
)

CREATE TABLE HinhAnh(
	id INT IDENTITY(1, 1) PRIMARY KEY,
	id_san_pham INT NOT NULL,
	lien_ket NVARCHAR(250) NOT NULL,
	FOREIGN KEY(id_san_pham) REFERENCES dbo.SanPham(id)
)

CREATE TABLE LichSuNhapHang(
	id INT IDENTITY(1, 1) PRIMARY KEY,
	id_san_pham INT NOT NULL,
	so_luong INT NOT NULL DEFAULT 0,
	gia_nhap MONEY NOT NULL DEFAULT 0,
	ngay_nhap DATE NOT NULL DEFAULT GETDATE(),
	FOREIGN KEY(id_san_pham) REFERENCES dbo.SanPham(id)
)


CREATE TABLE HoaDon (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	id_nhan_vien INT NOT NULL,
	id_khach_hang INT,
	phi_ship MONEY DEFAULT 0,
	tien_hang MONEY NOT NULL DEFAULT 0,
	tong_tien MONEY NOT NULL DEFAULT 0,
	ho_ten NVARCHAR(250),
	sdt VARCHAR(13),
	dia_chi NVARCHAR(500),
	ghi_chu NVARCHAR(1000),
	trang_thai VARCHAR(10) NOT NULL,
	ngay_tao DATE NOT NULL DEFAULT GETDATE(),
	ngay_cap_nhat DATE NOT NULL DEFAULT GETDATE(),
	FOREIGN KEY(id_nhan_vien) REFERENCES dbo.NhanVien(id),
	FOREIGN KEY(id_khach_hang) REFERENCES dbo.KhachHang(id),
	CHECK(trang_thai IN('TAO_MOI', 'DANG_GIAO', 'THANH_TOAN'))
)

CREATE TABLE HoaDonChiTiet (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	id_san_pham INT NOT NULL,
	id_hoa_don INT NOT NULL,
	gia_ban MONEY NOT NULL DEFAULT 0,
	gia_giam MONEY NOT NULL DEFAULT 0,
	so_luong INT NOT NULL DEFAULT 0,
	FOREIGN KEY(id_san_pham) REFERENCES dbo.SanPham(id),
	FOREIGN KEY(id_hoa_don) REFERENCES dbo.HoaDon(id)
)

CREATE TABLE MaGiamGia(
	id INT IDENTITY(1, 1) PRIMARY KEY,
	ma VARCHAR(50) NOT NULL,
	so_luong INT NOT NULL DEFAULT 0,
	ngay_bat_dau DATE NOT NULL,
	ngay_ket_thuc DATE NOT NULL,
	phan_tram_giam_gia INT NOT NULL DEFAULT 0,
	don_hang_toi_thieu MONEY NOT NULL DEFAULT 0,
	giam_toi_da MONEY NOT NULL DEFAULT 0,
	trang_thai BIT NOT NULL,
	da_xoa BIT DEFAULT 0
)

CREATE TABLE MaGiamGiaSanPham (
	id INT IDENTITY(1, 1) PRIMARY KEY,
	id_ma_giam_gia INT NOT NULL,
	id_san_pham INT NOT NULL,
	FOREIGN KEY(id_ma_giam_gia) REFERENCES dbo.MaGiamGia(id),
	FOREIGN KEY(id_san_pham) REFERENCES dbo.SanPham(id)
)

INSERT INTO dbo.NhanVien
(tai_khoan, mat_khau, email, ho_ten, sdt, gioi_tinh, dia_chi, hinh_anh, otp, trang_thai,vai_tro_quan_ly, ngay_tao)
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
    GETDATE() -- ngay_tao - date
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
    GETDATE() -- ngay_tao - date
)
