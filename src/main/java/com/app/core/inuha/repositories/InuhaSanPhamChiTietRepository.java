package com.app.core.inuha.repositories;

import com.app.common.helper.JbdcHelper;
import com.app.common.infrastructure.interfaces.IDAOinterface;
import com.app.common.infrastructure.request.FillterRequest;
import com.app.core.inuha.models.InuhaSanPhamChiTietModel;
import com.app.core.inuha.models.InuhaSanPhamModel;
import com.app.core.inuha.models.sanpham.InuhaChatLieuModel;
import com.app.core.inuha.models.sanpham.InuhaDanhMucModel;
import com.app.core.inuha.models.sanpham.InuhaDeGiayModel;
import com.app.core.inuha.models.sanpham.InuhaKichCoModel;
import com.app.core.inuha.models.sanpham.InuhaKieuDangModel;
import com.app.core.inuha.models.sanpham.InuhaMauSacModel;
import com.app.core.inuha.models.sanpham.InuhaThuongHieuModel;
import com.app.core.inuha.models.sanpham.InuhaXuatXuModel;
import com.app.core.inuha.request.InuhaFilterSanPhamChiTietRequest;
import com.app.utils.TimeUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author InuHa
 */
public class InuhaSanPhamChiTietRepository implements IDAOinterface<InuhaSanPhamChiTietModel, Integer> {
    
    private final InuhaSanPhamRepository sanPhamRepository = new InuhaSanPhamRepository();
    
    private final static String TABLE_NAME = "SanPhamChiTiet";
    
    @Override
    public int insert(InuhaSanPhamChiTietModel model) throws SQLException {
        int result = 0;
        String query = String.format("""
            INSERT INTO %s(id_san_pham, id_kich_co, id_mau_sac, so_luong, trang_thai, ngay_tao)
            VALUES (?, ?, ?, ?, ?, ?)
        """, TABLE_NAME);
        try {
            Object[] args = new Object[] {
                model.getSanPham().getId(),
                model.getKichCo().getId(),
                model.getMauSac().getId(),
                model.getSoLuong(),
                model.isTrangThai(),
                TimeUtils.currentDate()
            };
            result = JbdcHelper.updateAndFlush(query, args);
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

        return result;
    }

    @Override
    public int update(InuhaSanPhamChiTietModel model) throws SQLException {
        int result = 0;
        String query = String.format("""
            UPDATE %s SET
                id_kich_co = ?,
                id_mau_sac = ?,
                so_luong = ?,
                trang_thai = ?,
                trang_thai_xoa = ?,
                ngay_cap_nhat = ?
            WHERE id = ?
        """, TABLE_NAME);
        try {
            Object[] args = new Object[] {
                model.getKichCo().getId(),
                model.getMauSac().getId(),
                model.getSoLuong(),
                model.isTrangThai(),
                model.isTrangThaiXoa(),
                TimeUtils.currentDate(),
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
        String query = String.format("""
            DELETE FROM %s WHERE id = ?
        """, TABLE_NAME);
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
        String query = String.format("SELECT TOP(1) 1 FROM %s WHERE id = ? AND trang_thai_xoa = 0", TABLE_NAME);
        try {
            return JbdcHelper.value(query, id) != null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }

    public boolean has(Integer idKichCo, Integer idMauSac) throws SQLException {
        String query = String.format("SELECT TOP(1) 1 FROM %s WHERE id_kich_co = ? AND id_mau_sac = ? AND trang_thai_xoa = 0", TABLE_NAME);
        try {
            return JbdcHelper.value(query, idKichCo, idMauSac) != null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }
        
    public boolean hasUse(Integer id) throws SQLException {
        String query = """
            SELECT TOP(1) 1
            FROM HoaDonChitiet
            WHERE id_san_pham_chi_tiet = ?
        """;
        try {
            return JbdcHelper.value(query, id) != null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }
    
    public boolean has(InuhaSanPhamChiTietModel model) throws SQLException {
        String query = String.format("""
            SELECT TOP(1) 1
            FROM %s
            WHERE
                id != ? AND 
                id_kich_co = ? AND
                id_mau_sac = ? AND
                trang_thai_xoa = 0
        """, TABLE_NAME);
        try {
            return JbdcHelper.value(query, model.getId(), model.getKichCo().getId(), model.getMauSac().getId()) != null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }
        
    @Override
    public Optional<InuhaSanPhamChiTietModel> getById(Integer id) throws SQLException {
        ResultSet resultSet = null;
        InuhaSanPhamChiTietModel model = null;

        String query = String.format("""
            SELECT
                spct.*,
                kc.ten AS ten_kich_co,
                kc.ngay_tao AS ngay_tao_kich_co,
                kc.ngay_cap_nhat AS ngay_cap_nhat_kich_co,
                ms.ten AS ten_mau_sac,
                ms.ngay_tao AS ngay_tao_mau_sac,
                ms.ngay_cap_nhat AS ngay_cap_nhat_mau_sac
            FROM %s AS spct
                LEFT JOIN KichCo AS kc ON kc.id = spct.id_kich_co
                LEFT JOIN MauSac AS ms ON ms.id = spct.id_mau_sac
            WHERE
                spct.id = ? AND
                spct.trang_thai_xoa = 0
        """, TABLE_NAME);

        try {
            resultSet = JbdcHelper.query(query, id);
            while(resultSet.next()) {
                model = buildData(resultSet, false);
                Optional<InuhaSanPhamModel> sanPham = sanPhamRepository.getById(resultSet.getInt("id_san_pham"));
                if (sanPham.isPresent()) { 
                    model.setSanPham(sanPham.get());
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        finally {
            JbdcHelper.close(resultSet);
        }

        return Optional.ofNullable(model);
    }

    @Override
    public List<InuhaSanPhamChiTietModel> selectAll() throws SQLException {
        List<InuhaSanPhamChiTietModel> list = new ArrayList<>();
        ResultSet resultSet = null;

        String query = String.format("""
            SELECT
                spct.*,
                ROW_NUMBER() OVER (ORDER BY spct.id DESC) AS stt,
                kc.ten AS ten_kich_co,
                kc.ngay_tao AS ngay_tao_kich_co,
                kc.ngay_cap_nhat AS ngay_cap_nhat_kich_co,
                ms.ten AS ten_mau_sac,
                ms.ngay_tao AS ngay_tao_mau_sac,
                ms.ngay_cap_nhat AS ngay_cap_nhat_mau_sac
            FROM %s AS spct
                LEFT JOIN KichCo AS kc ON kc.id = spct.id_kich_co
                LEFT JOIN MauSac AS ms ON ms.id = spct.id_mau_sac
            WHERE
                spct.trang_thai_xoa = 0                    
            ORDER BY spct.id DESC 
        """, TABLE_NAME);

        try {
            resultSet = JbdcHelper.query(query);
            while(resultSet.next()) {
                InuhaSanPhamChiTietModel model = buildData(resultSet);
                Optional<InuhaSanPhamModel> sanPham = sanPhamRepository.getById(resultSet.getInt("id_san_pham"));
                if (sanPham.isPresent()) { 
                    model.setSanPham(sanPham.get());
                }
                list.add(model);
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
    
    public List<InuhaSanPhamChiTietModel> selectAll(Integer idSanPham) throws SQLException {
        List<InuhaSanPhamChiTietModel> list = new ArrayList<>();
        ResultSet resultSet = null;

        String query = String.format("""
            SELECT
                spct.*,
                ROW_NUMBER() OVER (ORDER BY spct.id DESC) AS stt,
                kc.ten AS ten_kich_co,
                kc.ngay_tao AS ngay_tao_kich_co,
                kc.ngay_cap_nhat AS ngay_cap_nhat_kich_co,
                ms.ten AS ten_mau_sac,
                ms.ngay_tao AS ngay_tao_mau_sac,
                ms.ngay_cap_nhat AS ngay_cap_nhat_mau_sac
            FROM %s AS spct
                LEFT JOIN KichCo AS kc ON kc.id = spct.id_kich_co
                LEFT JOIN MauSac AS ms ON ms.id = spct.id_mau_sac
            WHERE
                spct.id_san_pham = ? AND
                spct.trang_thai_xoa = 0                    
            ORDER BY spct.id DESC 
        """, TABLE_NAME);

        try {
            resultSet = JbdcHelper.query(query, idSanPham);
            while(resultSet.next()) {
                InuhaSanPhamChiTietModel model = buildData(resultSet);
                Optional<InuhaSanPhamModel> sanPham = sanPhamRepository.getById(resultSet.getInt("id_san_pham"));
                if (sanPham.isPresent()) { 
                    model.setSanPham(sanPham.get());
                }
                list.add(model);
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
    public List<InuhaSanPhamChiTietModel> selectPage(FillterRequest request) throws SQLException {
        
        InuhaFilterSanPhamChiTietRequest filter = (InuhaFilterSanPhamChiTietRequest) request;
        
        List<InuhaSanPhamChiTietModel> list = new ArrayList<>();
        ResultSet resultSet = null;
        
        String query = String.format("""
            WITH TableCTE AS (
                SELECT
                    spct.*,
                    ROW_NUMBER() OVER (ORDER BY spct.id DESC) AS stt,
                    kc.ten AS ten_kich_co,
                    kc.ngay_tao AS ngay_tao_kich_co,
                    kc.ngay_cap_nhat AS ngay_cap_nhat_kich_co,
                    ms.ten AS ten_mau_sac,
                    ms.ngay_tao AS ngay_tao_mau_sac,
                    ms.ngay_cap_nhat AS ngay_cap_nhat_mau_sac
                FROM %s AS spct
                    LEFT JOIN KichCo AS kc ON kc.id = spct.id_kich_co
                    LEFT JOIN MauSac AS ms ON ms.id = spct.id_mau_sac
                WHERE
                    spct.id_san_pham = ? AND
                    spct.trang_thai_xoa = 0 AND
                    (
                        (COALESCE(?, 0) < 1 OR spct.id_kich_co = ?) AND
                        (COALESCE(?, 0) < 1 OR spct.id_mau_sac = ?) AND
                        (COALESCE(?, 0) < 0 OR spct.trang_thai = ?)
                    )
            )
            SELECT *
            FROM TableCTE
            WHERE stt BETWEEN ? AND ?
        """, TABLE_NAME);

        int[] offset = FillterRequest.getOffset(filter.getPage(), filter.getSize());
        int start = offset[0];
        int limit = offset[1];

        Object[] args = new Object[] {
            filter.getIdSanPham(),
            filter.getIdKichCo(),
            filter.getIdKichCo(),
            filter.getIdMauSac(),
            filter.getIdMauSac(),
            filter.getTrangThai(),
            filter.getTrangThai(),
            start,
            limit
        };

        try {
            resultSet = JbdcHelper.query(query, args);
            while(resultSet.next()) {
                InuhaSanPhamChiTietModel model = buildData(resultSet);
                list.add(model);
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
        
        InuhaFilterSanPhamChiTietRequest filter = (InuhaFilterSanPhamChiTietRequest) request;
        
        int totalPages = 0;
        int totalRows = 0;

        String query = String.format("""
            SELECT COUNT(*)
            FROM %s
            WHERE
                id_san_pham = ? AND
                trang_thai_xoa = 0 AND  
                (
                    (COALESCE(?, 0) < 1 OR id_kich_co = ?) AND
                    (COALESCE(?, 0) < 1 OR id_mau_sac = ?) AND
                    (COALESCE(?, 0) < 0 OR trang_thai = ?)
                ) 
        """, TABLE_NAME);

        Object[] args = new Object[] { 
            filter.getIdSanPham(),
            filter.getIdKichCo(),
            filter.getIdKichCo(),
            filter.getIdMauSac(),
            filter.getIdMauSac(),
            filter.getTrangThai(),
            filter.getTrangThai()
        };
        
        try {
            totalRows = (int) JbdcHelper.value(query, args);
            totalPages = (int) Math.ceil((double) totalRows / request.getSize());
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        return totalPages;
    }
    
    private InuhaSanPhamChiTietModel buildData(ResultSet resultSet) throws SQLException { 
        return buildData(resultSet, true);
    }
    
    private InuhaSanPhamChiTietModel buildData(ResultSet resultSet, boolean addSTT) throws SQLException { 
        InuhaKichCoModel kichCo = new InuhaKichCoModel(resultSet.getInt("id_kich_co"), -1, resultSet.getString("ten_kich_co"), resultSet.getString("ngay_tao_kich_co"), false, resultSet.getString("ngay_cap_nhat_kich_co"));
        InuhaMauSacModel mauSac = new InuhaMauSacModel(resultSet.getInt("id_mau_sac"), -1, resultSet.getString("ten_mau_sac"), resultSet.getString("ngay_tao_mau_sac"), false, resultSet.getString("ngay_cap_nhat_mau_sac"));
        
        return InuhaSanPhamChiTietModel.builder()
            .id(resultSet.getInt("id"))
            .stt(addSTT ? resultSet.getInt("stt") : -1)
            .trangThai(resultSet.getBoolean("trang_thai"))
            .ngayTao(resultSet.getString("ngay_tao"))
            .ngayCapNhat(resultSet.getString("ngay_cap_nhat"))
            .trangThaiXoa(resultSet.getBoolean("trang_thai_xoa"))
            .soLuong(resultSet.getInt("so_luong"))
            .kichCo(kichCo)
            .mauSac(mauSac)
            .build();
    }
    
    
    public String getLastCode() throws SQLException {
        String query = String.format("SELECT TOP(1) id FROM %s ORDER BY id DESC", TABLE_NAME);
        try {
            return (String) JbdcHelper.value(query);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }
    
}
