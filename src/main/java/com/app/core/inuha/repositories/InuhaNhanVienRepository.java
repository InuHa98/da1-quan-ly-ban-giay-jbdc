package com.app.core.inuha.repositories;


import com.app.common.helper.JbdcHelper;
import com.app.common.infrastructure.interfaces.IDAOinterface;
import com.app.core.inuha.models.InuhaNhanVienModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
/**
 *
 * @author InuHa
 */
public class InuhaNhanVienRepository implements IDAOinterface {

	@Override
	public Object getById(Object id) throws SQLException {
		return null;
	}

	@Override
	public Set selectAll() throws SQLException {
		return null;
	}

	@Override
	public Long insert(Object model) throws SQLException {
		return null;
	}

	@Override
	public int update(Object model) throws SQLException {
		return 0;
	}

	@Override
	public int delete(Object id) throws SQLException {
		return 0;
	}

	@Override
	public boolean has(Object id) throws SQLException {
		return false;
	}

	@Override
	public int count() throws SQLException {
		return 0;
	}

	public Optional<InuhaNhanVienModel> findNhanVienByEmail(String email) throws SQLException {
		ResultSet resultSet = null;
		InuhaNhanVienModel nhanVien = null;

		String query = """
			SELECT
				nv.id,
				nv.username,
				nv.password,
				nv.ho_ten,
				nv.email,
				nv.sdt,
				nv.avatar,
				nv.is_admin
			FROM NhanVien AS nv
			WHERE nv.email LIKE ?
		""";

		try {
			resultSet = JbdcHelper.query(query, email);
			while(resultSet.next()) {
				nhanVien = InuhaNhanVienModel.builder()
						.id(resultSet.getInt("id"))
						.username(resultSet.getString("username"))
						.password(resultSet.getString("password"))
						.hoTen(resultSet.getString("ho_ten"))
						.email(resultSet.getString("email"))
						.sdt(resultSet.getString("sdt"))
						.avatar(resultSet.getString("avatar"))
						.isAdmin(resultSet.getBoolean("is_admin"))
						.build();
			}
		} catch(Exception e) {
			e.printStackTrace(System.out);
		}
		finally {
			JbdcHelper.close(resultSet);
		}

        return Optional.ofNullable(nhanVien);
	}

	public Optional<InuhaNhanVienModel> findNhanVienByUsername(String username) throws SQLException {
		ResultSet resultSet = null;
		InuhaNhanVienModel nhanVien = null;

		String query = """
			SELECT
				nv.id,
				nv.username,
				nv.password,
				nv.ho_ten,
				nv.email,
				nv.sdt,
				nv.avatar,
				nv.is_admin
			FROM NhanVien AS nv
			WHERE nv.username LIKE ?
		""";

		try {
			resultSet = JbdcHelper.query(query, username);
			while(resultSet.next()) {
				nhanVien = InuhaNhanVienModel.builder()
						.id(resultSet.getInt("id"))
						.username(resultSet.getString("username"))
						.password(resultSet.getString("password"))
						.hoTen(resultSet.getString("ho_ten"))
						.email(resultSet.getString("email"))
						.sdt(resultSet.getString("sdt"))
						.avatar(resultSet.getString("avatar"))
						.isAdmin(resultSet.getBoolean("is_admin"))
						.build();
			}
		} catch(Exception e) {
			e.printStackTrace(System.out);
		}
		finally {
			JbdcHelper.close(resultSet);
		}

		return Optional.ofNullable(nhanVien);
	}

	public Integer updateOTPById(int id, String otp) throws SQLException {
		int row = 0;

		String query = """
			UPDATE NhanVien SET otp = ? WHERE id = ?
		""";

		Object[] args = new Object[] {
				otp,
				id
		};

		try {
			row = JbdcHelper.update(query, true, args);
		} catch(Exception e) {
			e.printStackTrace(System.out);
		}

		return row;
	}

	public boolean checkOtp(String email, String otp) throws SQLException {
		ResultSet resultSet = null;
		String query = """
			SELECT TOP(1) 1
			FROM NhanVien
			WHERE email LIKE ? AND otp = ?
		""";

		Object[] args = new Object[] {
				email,
				otp
		};

		try {
			resultSet = JbdcHelper.query(query, args);
			if (resultSet.next()) {
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace(System.out);
		}
		finally {
			JbdcHelper.close(resultSet);
		}
		return false;
	}

	public Integer updateForgotPassword(String password, String email, String otp) throws SQLException {
		int row = 0;

		String query = """
			UPDATE NhanVien
			SET password = ?, otp = NULL
			WHERE email LIKE ? AND otp = ?
		""";

		Object[] args = new Object[] {
				password,
				email,
				otp
		};

		try {
			row = JbdcHelper.update(query, true, args);
		} catch(Exception e) {
			e.printStackTrace(System.out);
		}

		return row;
	}

	public Integer updateAvatar(int id, String avatar) throws SQLException {
		int row = 0;

		String query = """
			UPDATE NhanVien
			SET avatar = ?
			WHERE id = ?
		""";

		Object[] args = new Object[] {
				avatar,
				id
		};

		try {
			row = JbdcHelper.update(query, true, args);
		} catch(Exception e) {
			e.printStackTrace(System.out);
		}

		return row;
	}

	public Integer updatePassword(int id, String oldPassword, String newPassword) throws SQLException {
		int row = 0;

		String query = """
			UPDATE NhanVien
			SET password = ?
			WHERE id = ? AND password = ?
		""";

		Object[] args = new Object[] {
				newPassword,
				id,
				oldPassword
		};

		try {
			row = JbdcHelper.update(query, true, args);
		} catch(Exception e) {
			e.printStackTrace(System.out);
		}

		return row;
	}

}
