package com.app.core.inuha.views.quanly.components.sanpham;

import com.app.common.helper.ExcelHelper;
import com.app.common.helper.MessageModal;
import com.app.common.helper.MessageToast;
import com.app.common.helper.Pagination;
import com.app.common.helper.QrCodeHelper;
import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.core.inuha.models.InuhaSanPhamChiTietModel;
import com.app.core.inuha.models.InuhaSanPhamModel;
import com.app.core.inuha.models.sanpham.InuhaKichCoModel;
import com.app.core.inuha.models.sanpham.InuhaMauSacModel;
import com.app.core.inuha.models.sanpham.InuhaThuongHieuModel;
import com.app.core.inuha.models.sanpham.InuhaXuatXuModel;
import com.app.core.inuha.request.InuhaFilterSanPhamChiTietRequest;
import com.app.core.inuha.request.InuhaFilterSanPhamRequest;
import com.app.core.inuha.services.InuhaKichCoService;
import com.app.core.inuha.services.InuhaMauSacService;
import com.app.core.inuha.services.InuhaSanPhamChiTietService;
import com.app.core.inuha.services.InuhaSanPhamService;
import com.app.core.inuha.services.InuhaThuongHieuService;
import com.app.core.inuha.services.InuhaXuatXuService;
import com.app.core.inuha.views.quanly.InuhaSanPhamView;
import com.app.utils.ColorUtils;
import com.app.utils.CurrencyUtils;
import com.app.utils.ProductUtils;
import com.app.utils.QrCodeUtils;
import com.app.utils.ResourceUtils;
import com.app.views.UI.combobox.ComboBoxItem;
import com.app.views.UI.dialog.LoadingDialog;
import com.app.views.UI.picturebox.DefaultPictureBoxRender;
import com.app.views.UI.picturebox.PictureBox;
import com.app.views.UI.picturebox.SuperEllipse2D;
import com.app.views.UI.table.TableCustomUI;
import com.app.views.UI.table.celll.CheckBoxTableHeaderRenderer;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;
import com.app.views.UI.table.ITableActionEvent;
import com.app.views.UI.table.celll.TableActionCellEditor;
import com.app.views.UI.table.celll.TableActionCellRender;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author inuHa
 */
public class InuhaDetailSanPhamView extends JPanel {

    private static InuhaDetailSanPhamView instance;
        
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    
    private final static InuhaSanPhamChiTietService sanPhamChiTietService = new InuhaSanPhamChiTietService();
        
    private final static InuhaKichCoService kichCoService = new InuhaKichCoService();
    
    private final static InuhaMauSacService mauSacService = new InuhaMauSacService();
    
    private List<InuhaKichCoModel> dataKichCo = new ArrayList<>();
    
    private List<InuhaMauSacModel> dataMauSac = new ArrayList<>();
    
    public Pagination pagination = new Pagination();
    
    private int sizePage = pagination.getLimitItem();
    
    private List<InuhaSanPhamChiTietModel> dataItems = new ArrayList<>();
    
    private InuhaSanPhamModel sanPham = null;
    /**
     * Creates new form InuhaSanPhamView
     */
    
    public static InuhaDetailSanPhamView getInstance() { 
        if (instance == null) { 
            instance = new InuhaDetailSanPhamView(null);
        }
        return instance;
    }
    
    private boolean isReset = true;
    
    public InuhaDetailSanPhamView(InuhaSanPhamModel sanPham) {
        initComponents();
        instance = this;
        this.sanPham = sanPham;
	
	pnlInfo.setBackground(ColorUtils.BACKGROUND_PRIMARY);
	pnlInfo.setOpaque(false);
        
        btnThemSanPhamChiTiet.setIcon(ResourceUtils.getSVG("/svg/plus.svg", new Dimension(20, 20)));
        
        btnThemSanPhamChiTiet.setBackground(ColorUtils.BUTTON_PRIMARY);
        
        cboTrangThai.removeAllItems();
        cboTrangThai.addItem(new ComboBoxItem<>("-- Tất cả trạng thái --", -1));
        cboTrangThai.addItem(new ComboBoxItem<>("Đang bán", 1));
        cboTrangThai.addItem(new ComboBoxItem<>("Dừng bán", 0));
        
        pictureBox.setImage(ProductUtils.getImage(sanPham.getHinhAnh()));
        pictureBox.setBoxFit(PictureBox.BoxFit.CONTAIN);
        pictureBox.setPictureBoxRender(new DefaultPictureBoxRender() {
            @Override
            public Shape render(Rectangle rec) {
                return new SuperEllipse2D(rec.x, rec.y, rec.width, rec.height, 12f).getShape();
            }
        });
        
        
        lblTenSanPham.setText(sanPham.getTen());
        lblTrangThai.setText(ProductUtils.getTrangThai(sanPham.isTrangThai()));
	lblGiaNhap.setText(CurrencyUtils.parseString(sanPham.getGiaNhap()));
        lblGiaBan.setText(CurrencyUtils.parseString(sanPham.getGiaBan()));
        lblDanhMuc.setText(sanPham.getDanhMuc().getTen());
        lblThuongHieu.setText(sanPham.getThuongHieu().getTen());
        lblXuatXu.setText(sanPham.getXuatXu().getTen());
        lblKieuDang.setText(sanPham.getKieuDang().getTen());
        lblChatLieu.setText(sanPham.getChatLieu().getTen());
        lblDeGiay.setText(sanPham.getDeGiay().getTen());
        
        lblTrangThai.setForeground(ColorUtils.PRIMARY_COLOR);
	lblGiaNhap.setForeground(ColorUtils.PRIMARY_COLOR);
        lblGiaBan.setForeground(ColorUtils.PRIMARY_COLOR);
        lblDanhMuc.setForeground(ColorUtils.PRIMARY_COLOR);
        lblThuongHieu.setForeground(ColorUtils.PRIMARY_COLOR);
        lblXuatXu.setForeground(ColorUtils.PRIMARY_COLOR);
        lblKieuDang.setForeground(ColorUtils.PRIMARY_COLOR);
        lblChatLieu.setForeground(ColorUtils.PRIMARY_COLOR);
        lblDeGiay.setForeground(ColorUtils.PRIMARY_COLOR);
        
        loadDataKichCo();
        loadDataMauSac();
        
        setupTable(tblDanhSach);
        loadDataPage(1);
        setupPagination();
        isReset = false;
    }
    
    private void setupTable(JTable table) { 
        
        ITableActionEvent event = new ITableActionEvent() {
            @Override
            public void onEdit(int row) {
                InuhaSanPhamChiTietModel item = dataItems.get(row);
                showEdit(item);
            }

            @Override
            public void onDelete(int row) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                InuhaSanPhamChiTietModel item = dataItems.get(row);
                
                LoadingDialog loadingDialog = new LoadingDialog();

                executorService.submit(() -> {
                    if (MessageModal.confirmWarning("Xoá sản phẩm chi tiết ", "Bạn thực sự muốn xoá sản phẩm chi tiết này?")) {
                        executorService.submit(() -> {
                            try {
                                sanPhamChiTietService.delete(item.getId());
                                loadingDialog.dispose();
                                InuhaSanPhamView.getInstance().loadDataPage();
                                loadDataPage();
                                MessageToast.success("Xoá thành công sản phẩm chi tiết");
                            } catch (ServiceResponseException e) {
                                loadingDialog.dispose();
                                MessageToast.error(e.getMessage());
                            } catch (Exception e) {
                                loadingDialog.dispose();
                                MessageModal.error(ErrorConstant.DEFAULT_ERROR);
                            } 
                        });
                        loadingDialog.setVisible(true);
                    }
                });
            }

            @Override
            public void onView(int row) {
		InuhaSanPhamChiTietModel item = dataItems.get(row);
		handleClickButtomView(item);
            }
        };
        
        pnlDanhSach.setBackground(ColorUtils.BACKGROUND_GRAY);
        TableCustomUI.apply(scrDanhSach, TableCustomUI.TableType.DEFAULT);
        tblDanhSach.setBackground(ColorUtils.BACKGROUND_GRAY);
        tblDanhSach.getTableHeader().setBackground(ColorUtils.BACKGROUND_GRAY);
        table.setRowHeight(50);
        table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(table, 0));
        table.getColumnModel().getColumn(7).setCellRenderer(new TableActionCellRender(table));
        table.getColumnModel().getColumn(7).setCellEditor(new TableActionCellEditor(event));
    }
    

    public void loadDataPage() { 
        loadDataPage(pagination.getCurrentPage());
    }
    
    public void loadDataPage(int page) { 
        try {
            DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
            if (tblDanhSach.isEditing()) {
                tblDanhSach.getCellEditor().stopCellEditing();
            }
            
            model.setRowCount(0);
            
            ComboBoxItem<Integer> kichCo = (ComboBoxItem<Integer>) cboKichCo.getSelectedItem();
            ComboBoxItem<Integer> mauSac = (ComboBoxItem<Integer>) cboMauSac.getSelectedItem();
            ComboBoxItem<Integer> trangThai = (ComboBoxItem<Integer>) cboTrangThai.getSelectedItem();

            InuhaFilterSanPhamChiTietRequest request = new InuhaFilterSanPhamChiTietRequest(this.sanPham.getId(), kichCo.getValue(), mauSac.getValue(), trangThai.getValue());
            request.setSize(sizePage);
	    
            int totalPages = sanPhamChiTietService.getTotalPage(request);
            
            if (totalPages < page) { 
                page = totalPages;
            }
            
            request.setPage(page);
           
            dataItems = sanPhamChiTietService.getPage(request);
            
            for(InuhaSanPhamChiTietModel m: dataItems) { 
                model.addRow(m.toDataRowSanPhamChiTiet());
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
                loadDataPage(1);
            }

            @Override
            public void onClickPage(int page) {
                loadDataPage(page);
            }
        });
        pagination.render();
    }
        
    private void rerenderPagination(int currentPage, int totalPages) { 
        currentPage = currentPage < 1 ? 1 : currentPage;
        pagination.setCurrentPage(currentPage);
        pagination.setTotalPages(totalPages);
        pagination.renderListPage();
    }
   
    public void loadDataKichCo() { 
        isReset = true;
        dataKichCo = kichCoService.getAll();
        cboKichCo.removeAllItems();
        
        cboKichCo.addItem(new ComboBoxItem<>("-- Tất cả kích cỡ --", -1));
        for(InuhaKichCoModel m: dataKichCo) { 
            cboKichCo.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
        isReset = false;
    }
    
    public void loadDataMauSac() { 
        isReset = true;
        dataMauSac = mauSacService.getAll();
        cboMauSac.removeAllItems();
        
        cboMauSac.addItem(new ComboBoxItem<>("-- Tất cả màu sắc --", -1));
        for(InuhaMauSacModel m: dataMauSac) { 
            cboMauSac.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
        isReset = false;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlInfo = new com.app.views.UI.panel.RoundPanel();
        lblTenSanPham = new javax.swing.JLabel();
        pictureBox = new com.app.views.UI.picturebox.PictureBox();
        jLabel1 = new javax.swing.JLabel();
        lblGiaBan = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblDanhMuc = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblThuongHieu = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblXuatXu = new javax.swing.JLabel();
        lblKieuDang = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblChatLieu = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblDeGiay = new javax.swing.JLabel();
        btnSaveQR = new javax.swing.JButton();
        btnScanQR = new javax.swing.JButton();
        lblGiaNhap = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pnlList = new com.app.views.UI.panel.RoundPanel();
        btnThemSanPhamChiTiet = new javax.swing.JButton();
        pnlPhanTrang = new javax.swing.JPanel();
        pnlDanhSach = new com.app.views.UI.panel.RoundPanel();
        scrDanhSach = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        cboKichCo = new javax.swing.JComboBox();
        cboMauSac = new javax.swing.JComboBox();
        btnExport = new javax.swing.JButton();
        cboTrangThai = new javax.swing.JComboBox();
        btnReset = new javax.swing.JButton();

        lblTenSanPham.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTenSanPham.setText("Thông tin sản phẩm");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Giá bán:");

        lblGiaBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblGiaBan.setText("100.000đ");

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTrangThai.setText("Đang bán");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Trạng thái:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Danh mục:");

        lblDanhMuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDanhMuc.setText("Thời trang");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Thương hiệu:");

        lblThuongHieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblThuongHieu.setText("Adidas");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Xuất xứ:");

        lblXuatXu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblXuatXu.setText("Nhật bản");

        lblKieuDang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblKieuDang.setText("Nam");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Kiểu dáng:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Chất liệu:");

        lblChatLieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblChatLieu.setText("Real 1:1");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Đế giày:");

        lblDeGiay.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDeGiay.setText("Đế bằng");

        btnSaveQR.setText("Lưu QR Code");
        btnSaveQR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSaveQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveQRActionPerformed(evt);
            }
        });

        btnScanQR.setText("Quét QR");
        btnScanQR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnScanQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScanQRActionPerformed(evt);
            }
        });

        lblGiaNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblGiaNhap.setText("100.000đ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Giá nhập:");

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addComponent(pictureBox, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnScanQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSaveQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInfoLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlInfoLayout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDanhMuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblThuongHieu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblXuatXu, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                            .addComponent(lblGiaBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblKieuDang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(28, 28, 28)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(lblTenSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(170, 170, 170))))
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(lblTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInfoLayout.createSequentialGroup()
                                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel2)
                                            .addComponent(lblTrangThai))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel4)
                                            .addComponent(lblDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(pnlInfoLayout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblChatLieu))))
                                .addGap(9, 9, 9)
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(lblDeGiay))
                                    .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(lblThuongHieu)
                                        .addComponent(jLabel7)
                                        .addComponent(lblKieuDang))))
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addComponent(btnSaveQR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnScanQR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblGiaBan)
                                .addComponent(jLabel3)
                                .addComponent(lblGiaNhap)))
                        .addGap(21, 21, 21))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(pictureBox, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))))
        );

        btnThemSanPhamChiTiet.setText("Thêm sản phẩm chi tiết");
        btnThemSanPhamChiTiet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThemSanPhamChiTiet.setMaximumSize(new java.awt.Dimension(50, 23));
        btnThemSanPhamChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamChiTietActionPerformed(evt);
            }
        });

        pnlPhanTrang.setOpaque(false);

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

        tblDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "#", "Tên sản phẩm", "Kích cỡ", "Màu sắc", "Số lượng", "Trạng thái", "Hành động"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSach.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDanhSach.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDanhSach.setShowGrid(true);
        tblDanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachMouseClicked(evt);
            }
        });
        scrDanhSach.setViewportView(tblDanhSach);
        if (tblDanhSach.getColumnModel().getColumnCount() > 0) {
            tblDanhSach.getColumnModel().getColumn(0).setMaxWidth(30);
            tblDanhSach.getColumnModel().getColumn(1).setMaxWidth(50);
            tblDanhSach.getColumnModel().getColumn(7).setMinWidth(120);
        }

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrDanhSach, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(scrDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addContainerGap())
        );

        cboKichCo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả kích cỡ --" }));
        cboKichCo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboKichCoItemStateChanged(evt);
            }
        });

        cboMauSac.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả màu sắc --" }));
        cboMauSac.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboMauSacItemStateChanged(evt);
            }
        });

        btnExport.setText("Export Excel");
        btnExport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả trạng thái --" }));
        cboTrangThai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTrangThaiItemStateChanged(evt);
            }
        });

        btnReset.setText("Làm mới");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlListLayout = new javax.swing.GroupLayout(pnlList);
        pnlList.setLayout(pnlListLayout);
        pnlListLayout.setHorizontalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPhanTrang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlListLayout.createSequentialGroup()
                        .addComponent(cboKichCo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExport)
                        .addGap(18, 18, 18)
                        .addComponent(btnThemSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        pnlListLayout.setVerticalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReset, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboKichCo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnThemSanPhamChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(pnlPhanTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblDanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachMouseClicked
        // TODO add your handling code here:
        List<Integer> columns = List.of(0, 7);
        if (SwingUtilities.isLeftMouseButton(evt)) { 
            int row = tblDanhSach.rowAtPoint(evt.getPoint());
            int col = tblDanhSach.columnAtPoint(evt.getPoint());
            boolean isSelected = (boolean) tblDanhSach.getValueAt(row, 0);
            if (!columns.contains(col)) { 
                tblDanhSach.setValueAt(!isSelected, row, 0);
            }
        }
    }//GEN-LAST:event_tblDanhSachMouseClicked

    private void btnThemSanPhamChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamChiTietActionPerformed
        // TODO add your handling code here:
        handleClickButtonAdd();
    }//GEN-LAST:event_btnThemSanPhamChiTietActionPerformed

    private void btnScanQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanQRActionPerformed
        // TODO add your handling code here:
        handleClickButtonScanQrCode();
    }//GEN-LAST:event_btnScanQRActionPerformed

    private void btnSaveQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveQRActionPerformed
        // TODO add your handling code here:
        handleClickButtonSaveQR();
    }//GEN-LAST:event_btnSaveQRActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        handleClickButtonReset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void cboKichCoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboKichCoItemStateChanged
        // TODO add your handling code here:
        handleFilter();
    }//GEN-LAST:event_cboKichCoItemStateChanged

    private void cboMauSacItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboMauSacItemStateChanged
        // TODO add your handling code here:
        handleFilter();
    }//GEN-LAST:event_cboMauSacItemStateChanged

    private void cboTrangThaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTrangThaiItemStateChanged
        // TODO add your handling code here:
        handleFilter();
    }//GEN-LAST:event_cboTrangThaiItemStateChanged

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:
	handleClickButtonExport();
    }//GEN-LAST:event_btnExportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSaveQR;
    private javax.swing.JButton btnScanQR;
    private javax.swing.JButton btnThemSanPhamChiTiet;
    private javax.swing.JComboBox cboKichCo;
    private javax.swing.JComboBox cboMauSac;
    private javax.swing.JComboBox cboTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblChatLieu;
    private javax.swing.JLabel lblDanhMuc;
    private javax.swing.JLabel lblDeGiay;
    private javax.swing.JLabel lblGiaBan;
    private javax.swing.JLabel lblGiaNhap;
    private javax.swing.JLabel lblKieuDang;
    private javax.swing.JLabel lblTenSanPham;
    private javax.swing.JLabel lblThuongHieu;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JLabel lblXuatXu;
    private com.app.views.UI.picturebox.PictureBox pictureBox;
    private com.app.views.UI.panel.RoundPanel pnlDanhSach;
    private com.app.views.UI.panel.RoundPanel pnlInfo;
    private com.app.views.UI.panel.RoundPanel pnlList;
    private javax.swing.JPanel pnlPhanTrang;
    private javax.swing.JScrollPane scrDanhSach;
    private javax.swing.JTable tblDanhSach;
    // End of variables declaration//GEN-END:variables

    private void handleClickButtonExport() {
        List<InuhaSanPhamChiTietModel> items = findSelectedItems(tblDanhSach);
    }
    
    private List<InuhaSanPhamChiTietModel> findSelectedItems(JTable table) {
        List<InuhaSanPhamChiTietModel> data = new ArrayList<>();
        for (int row = 0; row < table.getRowCount(); row++) {
            Boolean value = (Boolean) table.getValueAt(row, 0);
            if (Boolean.TRUE.equals(value)) {
                data.add(dataItems.get(row));
            }
        }
        return data;
    }

    private void handleClickButtonAdd() {
        ModalDialog.showModal(this, new SimpleModalBorder(new InuhaAddSanPhamChiTietView(this.sanPham), "Thêm sản phẩm chi tiết"), ID_MODAL_ADD);
    }
    
    private void handleFilter() {
        if (isReset) { 
            return;
        }
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            loadDataPage();
            loading.dispose();
        });
        loading.setVisible(true);
    }
    
    private void handleClickButtonReset() {
        isReset = true;
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            cboKichCo.setSelectedIndex(0);
            cboMauSac.setSelectedIndex(0);
            cboTrangThai.setSelectedIndex(0);
            loadDataPage();
            loading.dispose();
            isReset = false;
        });
        loading.setVisible(true);
    }
    
    private void handleClickButtonScanQrCode() {
        QrCodeHelper.showWebcam("Tìm kiếm bằng QR", result -> {

            LoadingDialog loading = new LoadingDialog();
            executorService.submit(() -> {
                try {
                    String code = result.getText();

                    InuhaSanPhamChiTietModel sanPhamChiTietModel = null;
                    int id;
                    if ((id = QrCodeUtils.getIdSanPhamChiTiet(code)) > 0) {
                        sanPhamChiTietModel = sanPhamChiTietService.getById(id);
                    } else { 
                        loading.dispose();
                        MessageToast.error("QRCode không hợp lệ!!!");
                        return;
                    }
                    loading.dispose();
                    showEdit(sanPhamChiTietModel);                
                } catch (ServiceResponseException e) {
                    loading.dispose();
                    MessageModal.error(e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    loading.dispose();
                    MessageModal.error(ErrorConstant.DEFAULT_ERROR);
                }
            });
            loading.setVisible(true);
        });

    }
    
    public final static String ID_MODAL_ADD = "add-sanphamchitiet";

    private void showEdit(InuhaSanPhamChiTietModel item) {
        ModalDialog.showModal(instance, new SimpleModalBorder(new InuhaAddSanPhamChiTietView(this.sanPham, item), "Chỉnh sửa chi tiết sản phẩm"), ID_MODAL_ADD);
    }

    private void showDetail(InuhaSanPhamChiTietModel item) {
        ModalDialog.showModal(instance, new SimpleModalBorder(new InuhaDetailSanPhamChiTietView(item), null), ID_MODAL_ADD);
    }
	
    private void handleClickButtonSaveQR() {
        QrCodeHelper.save(QrCodeUtils.generateCodeSanPham(this.sanPham.getId()), this.sanPham.getMa());
    }
    
    private void handleClickButtomView(InuhaSanPhamChiTietModel item) {
	ModalDialog.showModal(this, new SimpleModalBorder(new InuhaDetailSanPhamChiTietView(item), "Thông tin sản phẩm chi tiết"));
    }
}
