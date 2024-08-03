package com.app.core.inuha.repositories;


import com.app.common.helper.JbdcHelper;
import com.app.common.infrastructure.interfaces.IDAOinterface;
import com.app.common.infrastructure.request.FillterRequest;
import com.app.core.inuha.models.InuhaTaiKhoanModel;
import com.app.core.inuha.request.InuhaFillterTaiKhoanRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author InuHa
 */
public class InuhaTaiKhoanRepository implements IDAOinterface<InuhaTaiKhoanModel, Integer> {

    private static InuhaTaiKhoanRepository instance = null;
    
    public static InuhaTaiKhoanRepository getInstance() { 
	if (instance == null) { 
	    instance = new InuhaTaiKhoanRepository();
	}
	return instance;
    }
    
    private InuhaTaiKhoanRepository() { 
	
    }
    
    @Override
    public int insert(InuhaTaiKhoanModel model) throws SQLException {
        int result = 0;
        String query = """
            INSERT INTO TaiKhoan(tai_khoan, mat_khau, email, ho_ten, sdt, gioi_tinh, dia_chi, hinh_anh, otp, trang_thai, adm, ngay_tao)
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
    public int update(InuhaTaiKhoanModel model) throws SQLException {
        int result = 0;
        String query = """
            UPDATE TaiKhoan SET
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
                adm = ?,
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
        String query = "DELETE FROM TaiKhoan WHERE id = ?";
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
        String query = "SELECT TOP(1) 1 FROM TaiKhoan WHERE id = ? AND trang_thai_xoa = 0";
        try {
            return JbdcHelper.value(query, id) != null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public Optional<InuhaTaiKhoanModel> getById(Integer id) throws SQLException {
        ResultSet resultSet = null;
        InuhaTaiKhoanModel TaiKhoan = null;

        String query = "SELECT * FROM TaiKhoan WHERE id = ? AND trang_thai_xoa = 0";

        try {
            resultSet = JbdcHelper.query(query, id);
            while(resultSet.next()) {
                TaiKhoan = buildTaiKhoan(resultSet);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        finally {
            JbdcHelper.close(resultSet);
        }

        return Optional.ofNullable(TaiKhoan);
    }

    @Override
    public List<InuhaTaiKhoanModel> selectAll() throws SQLException {
        List<InuhaTaiKhoanModel> list = new ArrayList<>();
        ResultSet resultSet = null;

        String query = """
            SELECT *
            FROM TaiKhoan
            WHERE trang_thai_xoa = 0
        """;

        try {
            resultSet = JbdcHelper.query(query);
            while(resultSet.next()) {
                InuhaTaiKhoanModel taiKhoan = buildTaiKhoan(resultSet);
                list.add(taiKhoan);
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
    public List<InuhaTaiKhoanModel> selectPage(FillterRequest request) throws SQLException {
        List<InuhaTaiKhoanModel> list = new ArrayList<>();
        ResultSet resultSet = null;

        String query = """
            WITH TaiKhoanCTE AS (
                SELECT
                    *,
                    ROW_NUMBER() OVER (ORDER BY id) AS stt
                FROM TaiKhoan
            )
            SELECT *
            FROM TaiKhoanCTE
            WHERE trang_thai_xoa = 0 AND (stt BETWEEN ? AND ?)
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
                InuhaTaiKhoanModel taiKhoan = buildTaiKhoan(resultSet);
                list.add(taiKhoan);
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
        InuhaFillterTaiKhoanRequest filter = (InuhaFillterTaiKhoanRequest) request;
        int totalPages = 0;
        int totalRows = 0;

        String query = "SELECT COUNT(*) FROM TaiKhoan WHERE ho_ten LIKE ?";

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

    private InuhaTaiKhoanModel buildTaiKhoan(ResultSet resultSet) throws SQLException {
        return InuhaTaiKhoanModel.builder()
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
            .isAdmin(resultSet.getBoolean("adm"))
            .ngayTao(resultSet.getString("ngay_tao"))
            .build();
    }

    public Optional<InuhaTaiKhoanModel> findByEmail(String email) throws SQLException {
        ResultSet resultSet = null;
        InuhaTaiKhoanModel taiKhoan = null;

        String query = "SELECT * FROM TaiKhoan WHERE email LIKE ? AND trang_thai_xoa = 0";

        try {

            resultSet = JbdcHelper.query(query, email);
            while(resultSet.next()) {
                    taiKhoan = buildTaiKhoan(resultSet);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        finally {
            JbdcHelper.close(resultSet);
        }

    return Optional.ofNullable(taiKhoan);
    }

    public Optional<InuhaTaiKhoanModel> findByUsername(String username) throws SQLException {
        ResultSet resultSet = null;
        InuhaTaiKhoanModel taiKhoan = null;

        String query = "SELECT * FROM TaiKhoan WHERE tai_khoan LIKE ? AND trang_thai_xoa = 0";

        try {
            resultSet = JbdcHelper.query(query, username);
            while(resultSet.next()) {
                taiKhoan = buildTaiKhoan(resultSet);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        finally {
            JbdcHelper.close(resultSet);
        }

        return Optional.ofNullable(taiKhoan);
    }

    public Integer updateOTPById(int id, String otp) throws SQLException {
        int row = 0;

        String query = "UPDATE TaiKhoan SET otp = ? WHERE id = ? AND trang_thai_xoa = 0";

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
        String query = "SELECT TOP(1) 1 FROM TaiKhoan WHERE email LIKE ? AND otp = ? AND trang_thai_xoa = 0";

        Object[] args = new Object[] {
            email,
            otp
        };

        try {
            return JbdcHelper.value(query, args) != null;
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }

    public Integer updateForgotPassword(String password, String email, String otp) throws SQLException {
        int row = 0;

        String query = """
            UPDATE TaiKhoan
            SET mat_khau = ?, otp = NULL
            WHERE email LIKE ? AND otp = ? AND trang_thai_xoa = 0
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

        String query = "UPDATE TaiKhoan SET hinh_anh = ? WHERE id = ? AND trang_thai_xoa = 0";

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

        String query = "UPDATE TaiKhoan SET mat_khau = ? WHERE id = ? AND mat_khau = ? AND trang_thai_xoa = 0";

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
