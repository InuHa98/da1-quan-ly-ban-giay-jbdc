package com.app.core.inuha.repositories;


import com.app.common.helper.JbdcHelper;
import com.app.common.infrastructure.interfaces.IDAOinterface;
import com.app.common.infrastructure.request.FillterRequest;
import com.app.core.inuha.models.InuhaNhanVienModel;
import com.app.core.inuha.request.InuhaFillterRequestNhanVien;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
/**
 *
 * @author InuHa
 */
public class InuhaNhanVienRepository implements IDAOinterface<InuhaNhanVienModel, Integer> {

	@Override
	public int insert(InuhaNhanVienModel model) throws SQLException {
		int result = 0;
		String query = """
  			INSERT INTO NhanVien(tai_khoan, mat_khau, email, ho_ten, sdt, gioi_tinh, dia_chi, hinh_anh, otp, trang_thai, vai_tro_quan_ly, ngay_tao)
  			VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
		""";
		try {
			Object[] args = new Object[] {
					model.getUsername(),
					model.getPassword(),
					model.getEmail(),
					model.getHoTen(),
					model.getSdt(),
					model.isGioiTinh(),
					model.getDiaChi(),
					model.getAvatar(),
					model.getOtp(),
					model.isTrangThai(),
					model.isAdmin(),
					model.getNgayTao()
			};
			result = JbdcHelper.updateAndFlush(query, args);
		} catch(Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}

		return result;
	}

	@Override
	public int update(InuhaNhanVienModel model) throws SQLException {
		int result = 0;
		String query = """
  			UPDATE NhanVien SET
    			tai_khoan = ?,
    			mat_khau = ?,
    			email = ?,
    			ho_ten = ?,
				sdt = ?,
				gioi_tinh = ?,
				dia_chi = ?,
				hinh_anh = ?,
				otp = ?,
				trang_thai = ?,
				vai_tro_quan_ly = ?,
				ngay_tao = ?
  			WHERE id = ?
		""";
		try {
			Object[] args = new Object[] {
					model.getUsername(),
					model.getPassword(),
					model.getEmail(),
					model.getHoTen(),
					model.getSdt(),
					model.isGioiTinh(),
					model.getDiaChi(),
					model.getAvatar(),
					model.getOtp(),
					model.isTrangThai(),
					model.isAdmin(),
					model.getNgayTao(),
					model.getId()
			};
			result = JbdcHelper.updateAndFlush(query, args);
		} catch(Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}

		return result;
	}

	@Override
	public int delete(Integer id) throws SQLException {
		int result = 0;
		String query = "DELETE FROM NhanVien WHERE id = ?";
		try {
			result = JbdcHelper.updateAndFlush(query, id);
		} catch(Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		return result;
	}

	@Override
	public boolean has(Integer id) throws SQLException {
		String query = "SELECT TOP(1) 1 FROM NhanVien WHERE id = ? AND da_xoa = 0";
		try {
			return (boolean) JbdcHelper.value(query, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	@Override
	public Optional<InuhaNhanVienModel> getById(Integer id) throws SQLException {
		ResultSet resultSet = null;
		InuhaNhanVienModel nhanVien = null;

		String query = "SELECT * FROM NhanVien WHERE id = ? AND da_xoa = 0";

		try {
			resultSet = JbdcHelper.query(query, id);
			while(resultSet.next()) {
				nhanVien = buildNhanVien(resultSet);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		finally {
			JbdcHelper.close(resultSet);
		}

		return Optional.ofNullable(nhanVien);
	}

	@Override
	public Set<InuhaNhanVienModel> selectAll(FillterRequest request) throws SQLException {
		Set<InuhaNhanVienModel> list = new HashSet<>();
		ResultSet resultSet = null;

		String query = """
			WITH NhanVienCTE AS (
				SELECT
					*,
					ROW_NUMBER() OVER (ORDER BY id) AS RowNum
				FROM NhanVien
			)
			SELECT *
			FROM NhanVienCTE
			WHERE da_xoa = 0 AND (RowNum BETWEEN ? AND ?)
		""";

		int[] offset = FillterRequest.getOffset(request.getPage(), request.getSize());
		int start = offset[0];
		int limit = offset[1];

		Object[] args = new Object[] {
				start,
				limit
		};

		try {
			resultSet = JbdcHelper.query(query, args);
			while(resultSet.next()) {
				InuhaNhanVienModel nhanVien = buildNhanVien(resultSet);
				list.add(nhanVien);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		finally {
			JbdcHelper.close(resultSet);
		}

		return list;
	}

	@Override
	public int count(FillterRequest request) throws SQLException {
		InuhaFillterRequestNhanVien filter = (InuhaFillterRequestNhanVien) request;
		int totalPages = 0;
		int totalRows = 0;

		String query = "SELECT COUNT(*) FROM NhanVien WHERE ho_ten LIKE ?";

		Object[] args = new Object[] {
				"%" + filter.getKeyword() + "%"
		};

		try {
			totalRows = (int) JbdcHelper.value(query, args);
			totalPages = (int) Math.ceil((double) totalRows / filter.getSize());
		} catch(Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		return totalPages;
	}

	private InuhaNhanVienModel buildNhanVien(ResultSet resultSet) throws SQLException {
		return InuhaNhanVienModel.builder()
				.id(resultSet.getInt("id"))
				.username(resultSet.getString("tai_khoan"))
				.password(resultSet.getString("mat_khau"))
				.email(resultSet.getString("email"))
				.hoTen(resultSet.getString("ho_ten"))
				.sdt(resultSet.getString("sdt"))
				.gioiTinh(resultSet.getBoolean("gioi_tinh"))
				.diaChi(resultSet.getString("dia_chi"))
				.avatar(resultSet.getString("hinh_anh"))
				.otp(resultSet.getString("otp"))
				.trangThai(resultSet.getBoolean("trang_thai"))
				.isAdmin(resultSet.getBoolean("vai_tro_quan_ly"))
				.ngayTao(resultSet.getString("ngay_tao"))
				.build();
	}

	public Optional<InuhaNhanVienModel> findNhanVienByEmail(String email) throws SQLException {
		ResultSet resultSet = null;
		InuhaNhanVienModel nhanVien = null;

		String query = "SELECT * FROM NhanVien WHERE email LIKE ? AND da_xoa = 0";

		try {

			resultSet = JbdcHelper.query(query, email);
			while(resultSet.next()) {
				nhanVien = buildNhanVien(resultSet);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		finally {
			JbdcHelper.close(resultSet);
		}

        return Optional.ofNullable(nhanVien);
	}

	public Optional<InuhaNhanVienModel> findNhanVienByUsername(String username) throws SQLException {
		ResultSet resultSet = null;
		InuhaNhanVienModel nhanVien = null;

		String query = "SELECT * FROM NhanVien WHERE tai_khoan LIKE ? AND da_xoa = 0";

		try {
			resultSet = JbdcHelper.query(query, username);
			while(resultSet.next()) {
				nhanVien = buildNhanVien(resultSet);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		finally {
			JbdcHelper.close(resultSet);
		}

		return Optional.ofNullable(nhanVien);
	}

	public Integer updateOTPById(int id, String otp) throws SQLException {
		int row = 0;

		String query = "UPDATE NhanVien SET otp = ? WHERE id = ? AND da_xoa = 0";

		Object[] args = new Object[] {
				otp,
				id
		};

		try {
			row = JbdcHelper.updateAndFlush(query, args);
		} catch(Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}

		return row;
	}

	public boolean checkOtp(String email, String otp) throws SQLException {
		String query = "SELECT TOP(1) 1 FROM NhanVien WHERE email LIKE ? AND otp = ? AND da_xoa = 0";

		Object[] args = new Object[] {
				email,
				otp
		};

		try {
			return (boolean) JbdcHelper.value(query, args);
		} catch(Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	public Integer updateForgotPassword(String password, String email, String otp) throws SQLException {
		int row = 0;

		String query = """
			UPDATE NhanVien
			SET password = ?, otp = NULL
			WHERE email LIKE ? AND otp = ? AND da_xoa = 0
		""";

		Object[] args = new Object[] {
				password,
				email,
				otp
		};

		try {
			row = JbdcHelper.updateAndFlush(query, args);
		} catch(Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}

		return row;
	}

	public Integer updateAvatar(int id, String avatar) throws SQLException {
		int row = 0;

		String query = "UPDATE NhanVien SET hinh_anh = ? WHERE id = ? AND da_xoa = 0";

		Object[] args = new Object[] {
				avatar,
				id
		};

		try {
			row = JbdcHelper.updateAndFlush(query, args);
		} catch(Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}

		return row;
	}

	public Integer updatePassword(int id, String oldPassword, String newPassword) throws SQLException {
		int row = 0;

		String query = "UPDATE NhanVien SET mat_khau = ? WHERE id = ? AND mat_khau = ? AND da_xoa = 0";

		Object[] args = new Object[] {
				newPassword,
				id,
				oldPassword
		};

		try {
			row = JbdcHelper.updateAndFlush(query, args);
		} catch(Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}

		return row;
	}

}
