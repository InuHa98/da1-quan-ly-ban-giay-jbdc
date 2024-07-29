package com.app.core.inuha.views.all.banhang;

import com.app.common.helper.Pagination;
import com.app.common.helper.QrCodeHelper;
import com.app.core.inuha.models.InuhaHoaDonModel;
import com.app.core.inuha.models.InuhaSanPhamModel;
import com.app.core.inuha.models.sanpham.InuhaChatLieuModel;
import com.app.core.inuha.models.sanpham.InuhaDanhMucModel;
import com.app.core.inuha.models.sanpham.InuhaDeGiayModel;
import com.app.core.inuha.models.sanpham.InuhaKichCoModel;
import com.app.core.inuha.models.sanpham.InuhaKieuDangModel;
import com.app.core.inuha.models.sanpham.InuhaMauSacModel;
import com.app.core.inuha.models.sanpham.InuhaThuongHieuModel;
import com.app.core.inuha.models.sanpham.InuhaXuatXuModel;
import com.app.core.inuha.request.InuhaFilterSanPhamRequest;
import com.app.core.inuha.services.InuhaChatLieuService;
import com.app.core.inuha.services.InuhaDanhMucService;
import com.app.core.inuha.services.InuhaDeGiayService;
import com.app.core.inuha.services.InuhaKichCoService;
import com.app.core.inuha.services.InuhaKieuDangService;
import com.app.core.inuha.services.InuhaMauSacService;
import com.app.core.inuha.services.InuhaSanPhamService;
import com.app.core.inuha.services.InuhaThuongHieuService;
import com.app.core.inuha.services.InuhaXuatXuService;
import com.app.utils.ColorUtils;
import com.app.utils.ResourceUtils;
import com.app.views.UI.combobox.ComboBoxItem;
import com.app.views.UI.dialog.LoadingDialog;
import com.app.views.UI.panel.qrcode.IQRCodeScanEvent;
import com.app.views.UI.panel.qrcode.WebcamQRCodeScanPanel;
import com.app.views.UI.table.TableCustomUI;
import com.app.views.UI.table.celll.TableImageCellRender;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author inuHa
 */
public class InuhaBanHangView extends javax.swing.JPanel {

    private static InuhaBanHangView instance;
        
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    
    private final static InuhaSanPhamService sanPhamService = new InuhaSanPhamService();
    
    private final static InuhaDanhMucService danhMucService = new InuhaDanhMucService();
    
    private final static InuhaThuongHieuService thuongHieuService = new InuhaThuongHieuService();
    
    private final static InuhaXuatXuService xuatXuService = new InuhaXuatXuService();
    
    private final static InuhaKieuDangService kieuDangService = new InuhaKieuDangService();
    
    private final static InuhaChatLieuService chatLieuService = new InuhaChatLieuService();
    
    private final static InuhaDeGiayService deGiayService = new InuhaDeGiayService();
    
    private final static InuhaKichCoService kichCoService = new InuhaKichCoService();
    
    private final static InuhaMauSacService mauSacService = new InuhaMauSacService();
    
    private List<InuhaDanhMucModel> dataDanhMuc = new ArrayList<>();
    
    private List<InuhaThuongHieuModel> dataThuongHieu = new ArrayList<>();
    
    private List<InuhaXuatXuModel> dataXuatXu = new ArrayList<>();
    
    private List<InuhaKieuDangModel> dataKieuDang = new ArrayList<>();
    
    private List<InuhaChatLieuModel> dataChatLieu = new ArrayList<>();
    
    private List<InuhaDeGiayModel> dataDeGiay = new ArrayList<>();
    
    private List<InuhaKichCoModel> dataKichCo = new ArrayList<>();
    
    private List<InuhaMauSacModel> dataMauSac = new ArrayList<>();
    
    private JTextField txtTuKhoa;
    
    public Pagination pagination = new Pagination();
	
    private int sizePage = pagination.getLimitItem();
    
    private List<InuhaHoaDonModel> dataItemsHoaDon = new ArrayList<>();
    
    private List<InuhaSanPhamModel> dataItemsSanPham = new ArrayList<>();
    
    private boolean reLoad = true;
    
    private boolean isScanning = false;
    
    /** Creates new form BanHangView */
    public InuhaBanHangView() {
	initComponents();
	instance = this;
		
	txtTuKhoa = pnlSearchBox.getKeyword();
	txtTuKhoa.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm theo tên hoặc mã...");
	pnlHoaDon.setBackground(ColorUtils.BACKGROUND_DASHBOARD);
	pnlSanPham.setBackground(ColorUtils.BACKGROUND_DASHBOARD);
	pnlGioHang.setBackground(ColorUtils.BACKGROUND_DASHBOARD);
	btnSubmit.setBackground(ColorUtils.BUTTON_PRIMARY);
	
	lblThongTinHoaDon.setIcon(ResourceUtils.getSVG("/svg/bill.svg", new Dimension(20, 20)));
	lblBill.setIcon(ResourceUtils.getSVG("/svg/bill.svg", new Dimension(20, 20)));
	lblProducts.setIcon(ResourceUtils.getSVG("/svg/shoes.svg", new Dimension(20, 20)));
	lblCart.setIcon(ResourceUtils.getSVG("/svg/cart.svg", new Dimension(20, 20)));
	btnReset.setIcon(ResourceUtils.getSVG("/svg/reload.svg", new Dimension(20, 20)));
	btnSubmit.setIcon(ResourceUtils.getSVG("/svg/paid.svg", new Dimension(24, 24)));
	btnAddBill.setIcon(ResourceUtils.getSVG("/svg/plus-c.svg", new Dimension(20, 20)));
	btnCancel.setIcon(ResourceUtils.getSVG("/svg/times.svg", new Dimension(32, 32)));
	btnScanQr.setIcon(ResourceUtils.getSVG("/svg/qr.svg", new Dimension(20, 20)));
	
	txtTuKhoa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
                    handleSearch(null);
                }
            }
        });
	
	Dimension cboSize = new Dimension(100, 28);
	cboDanhMuc.setPreferredSize(cboSize);
	cboThuongHieu.setPreferredSize(cboSize);
	cboXuatXu.setPreferredSize(cboSize);
	cboKieuDang.setPreferredSize(cboSize);
	cboChatLieu.setPreferredSize(cboSize);
	cboDeGiay.setPreferredSize(cboSize);

	setupTableGioHang(tblDanhSachGioHang);
	setupTableHoaDon(tblDanhSachHoaDon);
	setupTableSanPham(tblDanhSachSanPham);
	setupPagination();
	
	executorService.submit(() -> {
	    loadDataDanhMuc();
	    loadDataThuongHieu();
	    loadDataXuatXu();
	    loadDataKieuDang();
	    loadDataChatLieu();
	    loadDataDeGiay();

	    loadDataPageSanPham(1);
	});
    }

    public static InuhaBanHangView getInstance() { 
        if (instance == null) { 
            instance = new InuhaBanHangView();
        }
        return instance;
    }

    private void setupTableHoaDon(JTable table) { 
        pnlDanhSachHoaDon.setBackground(ColorUtils.BACKGROUND_TABLE);
        TableCustomUI.apply(scrDanhSachHoaDon, TableCustomUI.TableType.DEFAULT);
        //TableCustomUI.resizeColumnHeader(table);
	
        table.setRowHeight(30);
        table.getColumnModel().getColumn(2).setCellRenderer(new TableImageCellRender(table));
    }

    private void setupTableGioHang(JTable table) { 
        pnlDanhSachGioHang.setBackground(ColorUtils.BACKGROUND_TABLE);
        TableCustomUI.apply(scrDanhSachGioHang, TableCustomUI.TableType.MULTI_LINE);
	
        table.getColumnModel().getColumn(0).setCellRenderer(new TableImageCellRender(table));
    }
	
    private void setupTableSanPham(JTable table) { 
        pnlDanhSachSanPham.setBackground(ColorUtils.BACKGROUND_TABLE);
        TableCustomUI.apply(scrDanhSachSanPham, TableCustomUI.TableType.DEFAULT);
        TableCustomUI.resizeColumnHeader(table);
	
        table.setRowHeight(50);
        table.getColumnModel().getColumn(2).setCellRenderer(new TableImageCellRender(table));
    }
    
    public void loadDataPageSanPham() { 
        loadDataPageSanPham(pagination.getCurrentPage());
    }
	
    public void loadDataPageSanPham(int page) { 
        try {
            DefaultTableModel model = (DefaultTableModel) tblDanhSachSanPham.getModel();
            if (tblDanhSachSanPham.isEditing()) {
                tblDanhSachSanPham.getCellEditor().stopCellEditing();
            }
            
            model.setRowCount(0);
            
            String keyword = txtTuKhoa.getText().trim();
            keyword = keyword.replaceAll("\\s+", " ");
        
            ComboBoxItem<Integer> danhMuc = (ComboBoxItem<Integer>) cboDanhMuc.getSelectedItem();
            ComboBoxItem<Integer> thuongHieu = (ComboBoxItem<Integer>) cboThuongHieu.getSelectedItem();
	    ComboBoxItem<Integer> xuatXu = (ComboBoxItem<Integer>) cboXuatXu.getSelectedItem();
	    ComboBoxItem<Integer> kieuDang = (ComboBoxItem<Integer>) cboKieuDang.getSelectedItem();
	    ComboBoxItem<Integer> chatLieu = (ComboBoxItem<Integer>) cboChatLieu.getSelectedItem();
	    ComboBoxItem<Integer> deGiay = (ComboBoxItem<Integer>) cboDeGiay.getSelectedItem();

            InuhaFilterSanPhamRequest request = new InuhaFilterSanPhamRequest();
	    request.setKeyword(keyword);
	    request.setDanhMuc(danhMuc);
	    request.setThuongHieu(thuongHieu);
	    request.setXuatXu(xuatXu);
	    request.setKieuDang(kieuDang);
	    request.setChatLieu(chatLieu);
	    request.setDeGiay(deGiay);
	    
            request.setSize(sizePage);
	    
            int totalPages = sanPhamService.getTotalPage(request);
            
            if (totalPages < page) { 
                page = totalPages;
            }
            
            request.setPage(page);

           
            dataItemsSanPham = sanPhamService.getPage(request);
            
            for(InuhaSanPhamModel m: dataItemsSanPham) { 
                model.addRow(m.toDataRowHoaDon());
            }

            rerenderPagination(page, totalPages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    private void setupPagination() { 
        pagination.setPanel(pnlPhanTrang);
        pagination.setCallback(new Pagination.Callback() {
            @Override
            public void onChangeLimitItem(JComboBox<Integer> comboBox) {
                sizePage = (int) comboBox.getSelectedItem();
		LoadingDialog loading = new LoadingDialog();
		executorService.submit(() -> { 
		    loadDataPageSanPham(1);
		    loading.dispose();
		});
                loading.setVisible(true);
            }

            @Override
            public void onClickPage(int page) {
		LoadingDialog loading = new LoadingDialog();
		executorService.submit(() -> { 
		    loadDataPageSanPham(page);
		    loading.dispose();
		});
                loading.setVisible(true);
            }
        });
        pagination.render();
    }
     
    private void rerenderPagination(int currentPage, int totalPages) { 
        pagination.rerender(currentPage, totalPages);
    }
	
    public void loadDataDanhMuc() { 
	reLoad = true;
        dataDanhMuc = danhMucService.getAll();
        cboDanhMuc.removeAllItems();
	
        cboDanhMuc.addItem(new ComboBoxItem<>("-- Tất cả --", -1));
        for(InuhaDanhMucModel m: dataDanhMuc) { 
            cboDanhMuc.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
	reLoad = false;
    }

    public void loadDataThuongHieu() { 
	reLoad = true;
        dataThuongHieu = thuongHieuService.getAll();
        cboThuongHieu.removeAllItems();
	
        cboThuongHieu.addItem(new ComboBoxItem<>("-- Tất cả --", -1));
        for(InuhaThuongHieuModel m: dataThuongHieu) { 
            cboThuongHieu.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
	reLoad = false;
    }

    public void loadDataXuatXu() { 
	reLoad = true;
        dataXuatXu = xuatXuService.getAll();
        cboXuatXu.removeAllItems();
	
        cboXuatXu.addItem(new ComboBoxItem<>("-- Tất cả --", -1));
        for(InuhaXuatXuModel m: dataXuatXu) { 
            cboXuatXu.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
	reLoad = false;
    }

    public void loadDataKieuDang() { 
	reLoad = true;
        dataKieuDang = kieuDangService.getAll();
        cboKieuDang.removeAllItems();
	
        cboKieuDang.addItem(new ComboBoxItem<>("-- Tất cả --", -1));
        for(InuhaKieuDangModel m: dataKieuDang) { 
            cboKieuDang.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
	reLoad = false;
    }
	
    public void loadDataChatLieu() { 
	reLoad = true;
        dataChatLieu = chatLieuService.getAll();
        cboChatLieu.removeAllItems();
	
        cboChatLieu.addItem(new ComboBoxItem<>("-- Tất cả --", -1));
        for(InuhaChatLieuModel m: dataChatLieu) { 
            cboChatLieu.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
	reLoad = false;
    }
	    
    public void loadDataDeGiay() { 
	reLoad = true;
        dataDeGiay = deGiayService.getAll();
        cboDeGiay.removeAllItems();
	
        cboDeGiay.addItem(new ComboBoxItem<>("-- Tất cả --", -1));
        for(InuhaDeGiayModel m: dataDeGiay) { 
            cboDeGiay.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
	reLoad = false;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupHinhThucThanhToan = new javax.swing.ButtonGroup();
        pnlThongTinHoaDon = new com.app.views.UI.panel.RoundPanel();
        splitLine1 = new com.app.views.UI.label.SplitLine();
        btnSubmit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        btnSelectKhachHang = new javax.swing.JButton();
        lblThongTinHoaDon = new javax.swing.JLabel();
        splitLine2 = new com.app.views.UI.label.SplitLine();
        jLabel15 = new javax.swing.JLabel();
        lblMaHoaDon = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblNguoiTao = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        splitLine7 = new com.app.views.UI.label.SplitLine();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btnSeletVoucher = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        lblTongTienHang = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblTongVoucherGiamGia = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblTongThanhToan = new javax.swing.JLabel();
        splitLine8 = new com.app.views.UI.label.SplitLine();
        cboHinhThucThanhToan = new javax.swing.JComboBox<>();
        txtTienMat = new javax.swing.JFormattedTextField();
        txtTienChuyenKhoan = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        lblTienTraLai = new javax.swing.JLabel();
        pnlSanPham = new com.app.views.UI.panel.RoundPanel();
        lblProducts = new javax.swing.JLabel();
        splitLine4 = new com.app.views.UI.label.SplitLine();
        pnlSearchBox = new com.app.views.UI.panel.SearchBox();
        cboDanhMuc = new javax.swing.JComboBox();
        cboThuongHieu = new javax.swing.JComboBox();
        cboXuatXu = new javax.swing.JComboBox();
        cboKieuDang = new javax.swing.JComboBox();
        cboChatLieu = new javax.swing.JComboBox();
        cboDeGiay = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pnlDanhSachSanPham = new com.app.views.UI.panel.RoundPanel();
        scrDanhSachSanPham = new javax.swing.JScrollPane();
        tblDanhSachSanPham = new javax.swing.JTable();
        pnlPhanTrang = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        btnScanQr = new javax.swing.JButton();
        pnlHoaDon = new com.app.views.UI.panel.RoundPanel();
        lblBill = new javax.swing.JLabel();
        splitLine3 = new com.app.views.UI.label.SplitLine();
        btnAddBill = new javax.swing.JButton();
        pnlDanhSachHoaDon = new javax.swing.JPanel();
        scrDanhSachHoaDon = new javax.swing.JScrollPane();
        tblDanhSachHoaDon = new javax.swing.JTable();
        pnlGioHang = new com.app.views.UI.panel.RoundPanel();
        splitLine5 = new com.app.views.UI.label.SplitLine();
        lblCart = new javax.swing.JLabel();
        pnlDanhSachGioHang = new javax.swing.JPanel();
        scrDanhSachGioHang = new javax.swing.JScrollPane();
        tblDanhSachGioHang = new javax.swing.JTable();

        javax.swing.GroupLayout splitLine1Layout = new javax.swing.GroupLayout(splitLine1);
        splitLine1.setLayout(splitLine1Layout);
        splitLine1Layout.setHorizontalGroup(
            splitLine1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        splitLine1Layout.setVerticalGroup(
            splitLine1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        btnSubmit.setText("Thanh toán");
        btnSubmit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnCancel.setText("Huỷ");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel12.setText("Mã hoá đơn:");

        txtSoDienThoai.setText("số điện thoại");
        txtSoDienThoai.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 204, 204));
        jLabel13.setText("Thông tin khách hàng:");

        txtTenKhachHang.setText("tên khách hàng");
        txtTenKhachHang.setEnabled(false);

        jButton3.setText("Khách lẻ");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnSelectKhachHang.setText("Chọn");
        btnSelectKhachHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblThongTinHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblThongTinHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        lblThongTinHoaDon.setText("Thông tin hoá đơn");
        lblThongTinHoaDon.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        splitLine2.setForeground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout splitLine2Layout = new javax.swing.GroupLayout(splitLine2);
        splitLine2.setLayout(splitLine2Layout);
        splitLine2Layout.setHorizontalGroup(
            splitLine2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        splitLine2Layout.setVerticalGroup(
            splitLine2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 9, Short.MAX_VALUE)
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(204, 204, 204));
        jLabel15.setText("Chi tiết hoá đơn:");

        lblMaHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMaHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaHoaDon.setText("HD-00001");

        jLabel16.setText("Ngày tạo:");

        lblNgayTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNgayTao.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNgayTao.setText("29/07/2024");

        jLabel17.setText("Người tạo:");

        lblNguoiTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNguoiTao.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNguoiTao.setText("Admin");

        jLabel18.setText("Trạng thái:");

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTrangThai.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTrangThai.setText("Chưa thanh toán");

        javax.swing.GroupLayout splitLine7Layout = new javax.swing.GroupLayout(splitLine7);
        splitLine7.setLayout(splitLine7Layout);
        splitLine7Layout.setHorizontalGroup(
            splitLine7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        splitLine7Layout.setVerticalGroup(
            splitLine7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(204, 204, 204));
        jLabel19.setText("Chi tiết thanh toán:");

        jLabel20.setText("Voucher:");

        btnSeletVoucher.setText("Chọn hoặc nhập mã");
        btnSeletVoucher.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel14.setText("Tổng tiền hàng:");

        lblTongTienHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTongTienHang.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTongTienHang.setText("đ1.000.000");

        jLabel21.setText("Tổng Voucher giảm giá");

        lblTongVoucherGiamGia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTongVoucherGiamGia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTongVoucherGiamGia.setText("-đ100.000");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Tổng thanh toán");

        lblTongThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTongThanhToan.setForeground(new java.awt.Color(255, 0, 0));
        lblTongThanhToan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTongThanhToan.setText("đ900.000");

        javax.swing.GroupLayout splitLine8Layout = new javax.swing.GroupLayout(splitLine8);
        splitLine8.setLayout(splitLine8Layout);
        splitLine8Layout.setHorizontalGroup(
            splitLine8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        splitLine8Layout.setVerticalGroup(
            splitLine8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );

        cboHinhThucThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thanh toán tiền mặt", "Thanh toán chuyển khoản", "Thanh toán tiền mặt và chuyển khoản" }));

        jLabel23.setText("Tiền trả lại");

        lblTienTraLai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTienTraLai.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTienTraLai.setText("-đ100.000");

        javax.swing.GroupLayout pnlThongTinHoaDonLayout = new javax.swing.GroupLayout(pnlThongTinHoaDon);
        pnlThongTinHoaDon.setLayout(pnlThongTinHoaDonLayout);
        pnlThongTinHoaDonLayout.setHorizontalGroup(
            pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitLine1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(splitLine2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(splitLine7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlThongTinHoaDonLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinHoaDonLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(btnSeletVoucher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlThongTinHoaDonLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMaHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlThongTinHoaDonLayout.createSequentialGroup()
                        .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlThongTinHoaDonLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNguoiTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(23, 23, 23))
            .addGroup(pnlThongTinHoaDonLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblThongTinHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinHoaDonLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlThongTinHoaDonLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlThongTinHoaDonLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(splitLine8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinHoaDonLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlThongTinHoaDonLayout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTienTraLai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtTienChuyenKhoan)
                    .addComponent(txtTienMat)
                    .addComponent(cboHinhThucThanhToan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThongTinHoaDonLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTongThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThongTinHoaDonLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTongVoucherGiamGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlThongTinHoaDonLayout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThongTinHoaDonLayout.createSequentialGroup()
                        .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSelectKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThongTinHoaDonLayout.createSequentialGroup()
                        .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThongTinHoaDonLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTongTienHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        pnlThongTinHoaDonLayout.setVerticalGroup(
            pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinHoaDonLayout.createSequentialGroup()
                .addComponent(lblThongTinHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitLine1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSelectKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSoDienThoai))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenKhachHang)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(splitLine2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaHoaDon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNgayTao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNguoiTao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTrangThai))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeletVoucher))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(splitLine7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTongTienHang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTongVoucherGiamGia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTongThanhToan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(splitLine8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTienMat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTienChuyenKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTienTraLai))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        lblProducts.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblProducts.setForeground(new java.awt.Color(255, 255, 255));
        lblProducts.setText("Sản phẩm");
        lblProducts.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout splitLine4Layout = new javax.swing.GroupLayout(splitLine4);
        splitLine4.setLayout(splitLine4Layout);
        splitLine4Layout.setHorizontalGroup(
            splitLine4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        splitLine4Layout.setVerticalGroup(
            splitLine4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        cboDanhMuc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả --" }));
        cboDanhMuc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboDanhMucItemStateChanged(evt);
            }
        });

        cboThuongHieu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả --" }));
        cboThuongHieu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboThuongHieuItemStateChanged(evt);
            }
        });

        cboXuatXu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả --" }));
        cboXuatXu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboXuatXuItemStateChanged(evt);
            }
        });

        cboKieuDang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả --" }));
        cboKieuDang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboKieuDangItemStateChanged(evt);
            }
        });

        cboChatLieu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả --" }));
        cboChatLieu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboChatLieuItemStateChanged(evt);
            }
        });

        cboDeGiay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả --" }));
        cboDeGiay.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboDeGiayItemStateChanged(evt);
            }
        });

        jLabel8.setText("Đế giày:");

        jLabel9.setText("Chất liệu:");

        jLabel4.setText("Kiểu dáng:");

        jLabel5.setText("Xuất xứ:");

        jLabel6.setText("Thương hiệu:");

        jLabel7.setText("Danh mục:");

        tblDanhSachSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Mã sản phẩm", "", "Tên sản phẩm", "Giá bán", "Số lượng tồn", "Danh mục", "Thương hiệu", "Xuất xứ", "Kiểu dáng", "Chất liệu", "Đế giày"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachSanPham.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDanhSachSanPham.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrDanhSachSanPham.setViewportView(tblDanhSachSanPham);

        javax.swing.GroupLayout pnlDanhSachSanPhamLayout = new javax.swing.GroupLayout(pnlDanhSachSanPham);
        pnlDanhSachSanPham.setLayout(pnlDanhSachSanPhamLayout);
        pnlDanhSachSanPhamLayout.setHorizontalGroup(
            pnlDanhSachSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrDanhSachSanPham)
        );
        pnlDanhSachSanPhamLayout.setVerticalGroup(
            pnlDanhSachSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrDanhSachSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlPhanTrangLayout = new javax.swing.GroupLayout(pnlPhanTrang);
        pnlPhanTrang.setLayout(pnlPhanTrangLayout);
        pnlPhanTrangLayout.setHorizontalGroup(
            pnlPhanTrangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlPhanTrangLayout.setVerticalGroup(
            pnlPhanTrangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );

        btnReset.setText("Làm mới");
        btnReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReset.setMaximumSize(new java.awt.Dimension(79, 35));
        btnReset.setMinimumSize(new java.awt.Dimension(79, 35));
        btnReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnResetMouseClicked(evt);
            }
        });
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnScanQr.setText("Quét QR");
        btnScanQr.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnScanQr.setMaximumSize(new java.awt.Dimension(79, 35));
        btnScanQr.setMinimumSize(new java.awt.Dimension(79, 35));
        btnScanQr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnScanQrMouseClicked(evt);
            }
        });
        btnScanQr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScanQrActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSanPhamLayout = new javax.swing.GroupLayout(pnlSanPham);
        pnlSanPham.setLayout(pnlSanPhamLayout);
        pnlSanPhamLayout.setHorizontalGroup(
            pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitLine4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSanPhamLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSanPhamLayout.createSequentialGroup()
                        .addGroup(pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnlDanhSachSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlSanPhamLayout.createSequentialGroup()
                                .addComponent(pnlSearchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                                .addGroup(pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cboDanhMuc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cboThuongHieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cboXuatXu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnlSanPhamLayout.createSequentialGroup()
                                        .addComponent(cboKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlSanPhamLayout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboDeGiay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(pnlPhanTrang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20))
                    .addGroup(pnlSanPhamLayout.createSequentialGroup()
                        .addComponent(lblProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnScanQr, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13))))
        );
        pnlSanPhamLayout.setVerticalGroup(
            pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSanPhamLayout.createSequentialGroup()
                .addGroup(pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnScanQr, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitLine4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cboChatLieu, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboKieuDang, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboXuatXu, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboThuongHieu, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboDanhMuc, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSearchBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(cboDeGiay))
                .addGap(20, 20, 20)
                .addComponent(pnlDanhSachSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPhanTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        lblBill.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblBill.setForeground(new java.awt.Color(255, 255, 255));
        lblBill.setText("Hoá đơn chờ");
        lblBill.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout splitLine3Layout = new javax.swing.GroupLayout(splitLine3);
        splitLine3.setLayout(splitLine3Layout);
        splitLine3Layout.setHorizontalGroup(
            splitLine3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        splitLine3Layout.setVerticalGroup(
            splitLine3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        btnAddBill.setText("Tạo hoá đơn");
        btnAddBill.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddBill.setMaximumSize(new java.awt.Dimension(79, 35));
        btnAddBill.setMinimumSize(new java.awt.Dimension(79, 35));
        btnAddBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddBillMouseClicked(evt);
            }
        });
        btnAddBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBillActionPerformed(evt);
            }
        });

        tblDanhSachHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Mã hoá đơn", "Người tạo", "Ngày tạo", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrDanhSachHoaDon.setViewportView(tblDanhSachHoaDon);
        if (tblDanhSachHoaDon.getColumnModel().getColumnCount() > 0) {
            tblDanhSachHoaDon.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        javax.swing.GroupLayout pnlDanhSachHoaDonLayout = new javax.swing.GroupLayout(pnlDanhSachHoaDon);
        pnlDanhSachHoaDon.setLayout(pnlDanhSachHoaDonLayout);
        pnlDanhSachHoaDonLayout.setHorizontalGroup(
            pnlDanhSachHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrDanhSachHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        pnlDanhSachHoaDonLayout.setVerticalGroup(
            pnlDanhSachHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachHoaDonLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(scrDanhSachHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlHoaDonLayout = new javax.swing.GroupLayout(pnlHoaDon);
        pnlHoaDon.setLayout(pnlHoaDonLayout);
        pnlHoaDonLayout.setHorizontalGroup(
            pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitLine3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddBill, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addComponent(pnlDanhSachHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlHoaDonLayout.setVerticalGroup(
            pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                .addGroup(pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBill, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddBill, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitLine3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlDanhSachHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout splitLine5Layout = new javax.swing.GroupLayout(splitLine5);
        splitLine5.setLayout(splitLine5Layout);
        splitLine5Layout.setHorizontalGroup(
            splitLine5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        splitLine5Layout.setVerticalGroup(
            splitLine5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        lblCart.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblCart.setForeground(new java.awt.Color(255, 255, 255));
        lblCart.setText("Giỏ hàng");
        lblCart.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        tblDanhSachGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Sản phẩm", "Màu sắc", "Đơn giá", "Số lượng", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrDanhSachGioHang.setViewportView(tblDanhSachGioHang);
        if (tblDanhSachGioHang.getColumnModel().getColumnCount() > 0) {
            tblDanhSachGioHang.getColumnModel().getColumn(0).setMinWidth(60);
            tblDanhSachGioHang.getColumnModel().getColumn(0).setMaxWidth(60);
            tblDanhSachGioHang.getColumnModel().getColumn(1).setMinWidth(150);
            tblDanhSachGioHang.getColumnModel().getColumn(5).setMinWidth(40);
            tblDanhSachGioHang.getColumnModel().getColumn(5).setMaxWidth(40);
        }

        javax.swing.GroupLayout pnlDanhSachGioHangLayout = new javax.swing.GroupLayout(pnlDanhSachGioHang);
        pnlDanhSachGioHang.setLayout(pnlDanhSachGioHangLayout);
        pnlDanhSachGioHangLayout.setHorizontalGroup(
            pnlDanhSachGioHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrDanhSachGioHang, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
        );
        pnlDanhSachGioHangLayout.setVerticalGroup(
            pnlDanhSachGioHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachGioHangLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(scrDanhSachGioHang, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlGioHangLayout = new javax.swing.GroupLayout(pnlGioHang);
        pnlGioHang.setLayout(pnlGioHangLayout);
        pnlGioHangLayout.setHorizontalGroup(
            pnlGioHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitLine5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlGioHangLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblCart, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pnlDanhSachGioHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlGioHangLayout.setVerticalGroup(
            pnlGioHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGioHangLayout.createSequentialGroup()
                .addComponent(lblCart, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(splitLine5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlDanhSachGioHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlGioHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(pnlThongTinHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlGioHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(pnlSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(pnlThongTinHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboDanhMucItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboDanhMucItemStateChanged
        // TODO add your handling code here:
	handleSearch(evt);
    }//GEN-LAST:event_cboDanhMucItemStateChanged

    private void cboThuongHieuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboThuongHieuItemStateChanged
        // TODO add your handling code here:
	handleSearch(evt);
    }//GEN-LAST:event_cboThuongHieuItemStateChanged

    private void cboXuatXuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboXuatXuItemStateChanged
        // TODO add your handling code here:
	handleSearch(evt);
    }//GEN-LAST:event_cboXuatXuItemStateChanged

    private void cboKieuDangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboKieuDangItemStateChanged
        // TODO add your handling code here:
	handleSearch(evt);
    }//GEN-LAST:event_cboKieuDangItemStateChanged

    private void cboChatLieuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboChatLieuItemStateChanged
        // TODO add your handling code here:
	handleSearch(evt);
    }//GEN-LAST:event_cboChatLieuItemStateChanged

    private void cboDeGiayItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboDeGiayItemStateChanged
        // TODO add your handling code here:
	handleSearch(evt);
    }//GEN-LAST:event_cboDeGiayItemStateChanged

    private void btnResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnResetMouseClicked

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
	handleClickButtonReset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnAddBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddBillMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddBillMouseClicked

    private void btnAddBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBillActionPerformed
        // TODO add your handling code here:
	handleClickButtonCreateBill();
    }//GEN-LAST:event_btnAddBillActionPerformed

    private void btnScanQrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnScanQrMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnScanQrMouseClicked

    private void btnScanQrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanQrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnScanQrActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddBill;
    private javax.swing.JButton btnCancel;
    private javax.swing.ButtonGroup btnGroupHinhThucThanhToan;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnScanQr;
    private javax.swing.JButton btnSelectKhachHang;
    private javax.swing.JButton btnSeletVoucher;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JComboBox cboChatLieu;
    private javax.swing.JComboBox cboDanhMuc;
    private javax.swing.JComboBox cboDeGiay;
    private javax.swing.JComboBox<String> cboHinhThucThanhToan;
    private javax.swing.JComboBox cboKieuDang;
    private javax.swing.JComboBox cboThuongHieu;
    private javax.swing.JComboBox cboXuatXu;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblBill;
    private javax.swing.JLabel lblCart;
    private javax.swing.JLabel lblMaHoaDon;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblNguoiTao;
    private javax.swing.JLabel lblProducts;
    private javax.swing.JLabel lblThongTinHoaDon;
    private javax.swing.JLabel lblTienTraLai;
    private javax.swing.JLabel lblTongThanhToan;
    private javax.swing.JLabel lblTongTienHang;
    private javax.swing.JLabel lblTongVoucherGiamGia;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JPanel pnlDanhSachGioHang;
    private javax.swing.JPanel pnlDanhSachHoaDon;
    private com.app.views.UI.panel.RoundPanel pnlDanhSachSanPham;
    private com.app.views.UI.panel.RoundPanel pnlGioHang;
    private com.app.views.UI.panel.RoundPanel pnlHoaDon;
    private javax.swing.JPanel pnlPhanTrang;
    private com.app.views.UI.panel.RoundPanel pnlSanPham;
    private com.app.views.UI.panel.SearchBox pnlSearchBox;
    private com.app.views.UI.panel.RoundPanel pnlThongTinHoaDon;
    private javax.swing.JScrollPane scrDanhSachGioHang;
    private javax.swing.JScrollPane scrDanhSachHoaDon;
    private javax.swing.JScrollPane scrDanhSachSanPham;
    private com.app.views.UI.label.SplitLine splitLine1;
    private com.app.views.UI.label.SplitLine splitLine2;
    private com.app.views.UI.label.SplitLine splitLine3;
    private com.app.views.UI.label.SplitLine splitLine4;
    private com.app.views.UI.label.SplitLine splitLine5;
    private com.app.views.UI.label.SplitLine splitLine6;
    private com.app.views.UI.label.SplitLine splitLine7;
    private com.app.views.UI.label.SplitLine splitLine8;
    private javax.swing.JTable tblDanhSachGioHang;
    private javax.swing.JTable tblDanhSachHoaDon;
    private javax.swing.JTable tblDanhSachSanPham;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JFormattedTextField txtTienChuyenKhoan;
    private javax.swing.JFormattedTextField txtTienMat;
    // End of variables declaration//GEN-END:variables

    private void handleSearch(ItemEvent evt) {
	
	if (reLoad || (evt != null && evt.getStateChange() != ItemEvent.SELECTED)) { 
            return;
        }
	
	LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            loadDataPageSanPham();
            loading.dispose();
        });
        loading.setVisible(true);
    }

    private void handleClickButtonReset() {
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            txtTuKhoa.setText(null);
            cboDanhMuc.setSelectedIndex(0);
            cboThuongHieu.setSelectedIndex(0);
	    cboXuatXu.setSelectedIndex(0);
	    cboKieuDang.setSelectedIndex(0);
	    cboChatLieu.setSelectedIndex(0);
	    cboDeGiay.setSelectedIndex(0);
            loadDataPageSanPham();
            loading.dispose();
        });
        loading.setVisible(true);
    }

    private void handleClickButtonCreateBill() {
	
    }
}
