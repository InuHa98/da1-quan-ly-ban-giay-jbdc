package com.app.core.inuha.views.quanly;

import com.app.Application;
import com.app.common.helper.MessageModal;
import com.app.common.helper.Pagination;
import com.app.core.inuha.models.sanpham.InuhaDanhMucModel;
import com.app.core.inuha.models.sanpham.InuhaThuongHieuModel;
import com.app.core.inuha.models.sanpham.InuhaXuatXuModel;
import com.app.core.inuha.services.InuhaDanhMucService;
import com.app.core.inuha.services.InuhaThuongHieuService;
import com.app.core.inuha.services.InuhaXuatXuService;
import com.app.core.inuha.views.quanly.components.sanpham.InuhaAddSanPhamView;
import com.app.utils.ColorUtils;
import com.app.utils.ResourceUtils;
import com.app.views.UI.combobox.ComboBoxItem;
import com.app.views.UI.dialog.LoadingDialog;
import com.app.views.UI.panel.RoundPanel;
import com.app.views.UI.table.TableCustomUI;
import com.app.views.UI.table.celll.CheckBoxTableHeaderRenderer;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;
import com.app.views.UI.table.ITableActionEvent;
import com.app.views.UI.table.celll.TableActionCellEditor;
import com.app.views.UI.table.celll.TableActionCellRender;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author inuHa
 */
public class InuhaSanPhamView extends RoundPanel {

    private static InuhaSanPhamView instance;
        
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    
    private final static InuhaDanhMucService danhMucService = new InuhaDanhMucService();
    
    private final static InuhaThuongHieuService thuongHieuService = new InuhaThuongHieuService();
    
    private final static InuhaXuatXuService xuatXuService = new InuhaXuatXuService();
    
    private List<InuhaDanhMucModel> dataDanhMuc = new ArrayList<>();
    
    private List<InuhaThuongHieuModel> dataThuongHieu = new ArrayList<>();
    
    private List<InuhaXuatXuModel> dataXuatXu = new ArrayList<>();
    
    private JTextField txtTuKhoa;
    
    private Pagination pagination = new Pagination();
    
    /**
     * Creates new form InuhaSanPhamView
     */
    
    public static InuhaSanPhamView getInstance() { 
        if (instance == null) { 
            instance = new InuhaSanPhamView();
        }
        return instance;
    }

    public InuhaSanPhamView() {
        initComponents();
        instance = this;
        pnlSearchBox.setPlaceholder("Nhập tên hoặc mã sản phẩm ...");
        txtTuKhoa = pnlSearchBox.getKeyword();
        pnlContainer.setBackground(ColorUtils.BACKGROUND_DASHBOARD);
        tbpTab.setBackground(ColorUtils.BACKGROUND_DASHBOARD);
        lblFilter.setIcon(ResourceUtils.getSVG("/svg/filter.svg", new Dimension(20, 20)));
        lblList.setIcon(ResourceUtils.getSVG("/svg/list.svg", new Dimension(20, 20)));
        btnThemSanPham.setIcon(ResourceUtils.getSVG("/svg/plus.svg", new Dimension(20, 20)));
        btnXoaSanPham.setIcon(ResourceUtils.getSVG("/svg/trash.svg", new Dimension(20, 20)));
        btnSearch.setIcon(ResourceUtils.getSVG("/svg/search.svg", new Dimension(20, 20)));
        
        btnClear.setBackground(ColorUtils.BUTTON_GRAY);
        btnThemSanPham.setBackground(ColorUtils.BUTTON_PRIMARY);
        
        
        LoadingDialog loading = new LoadingDialog(Application.app);
        executorService.submit(() -> {
            loadDataDanhMuc();
            loadDataThuongHieu();
            loadDataXuatXu();
            
            setupTable(tblDanhSach);
            fillTable();
            renderPagination();
            loading.dispose();
        });
        loading.setVisible(true);
    }
    
    public void loadDataDanhMuc() { 
        dataDanhMuc = danhMucService.getAll();
        cboDanhMuc.removeAllItems();
       
        if (dataDanhMuc.isEmpty()) { 
            cboDanhMuc.addItem(new ComboBoxItem<>("-- Chưa có mục nào --", -1));
            cboDanhMuc.setEnabled(false);
            return;
        }
        
        cboDanhMuc.setEnabled(true);
        for(InuhaDanhMucModel m: dataDanhMuc) { 
            cboDanhMuc.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }

    public void loadDataThuongHieu() { 
        dataThuongHieu = thuongHieuService.getAll();
        cboThuongHieu.removeAllItems();
        
        if (dataThuongHieu.isEmpty()) { 
            cboThuongHieu.addItem(new ComboBoxItem<>("-- Chưa có mục nào --", -1));
            cboThuongHieu.setEnabled(false);
            return;
        }
        
        cboThuongHieu.setEnabled(true);
        
        for(InuhaThuongHieuModel m: dataThuongHieu) { 
            cboThuongHieu.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }
    
    public void loadDataXuatXu() { 
        dataXuatXu = xuatXuService.getAll();
        cboXuatXu.removeAllItems();
        
        if (dataXuatXu.isEmpty()) { 
            cboXuatXu.addItem(new ComboBoxItem<>("-- Chưa có mục nào --", -1));
            cboXuatXu.setEnabled(false);
            return;
        }
        
        cboXuatXu.setEnabled(true);
        
        for(InuhaXuatXuModel m: dataXuatXu) { 
            cboXuatXu.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }
    
    private void setupTable(JTable table) { 
        
        
        ITableActionEvent event = new ITableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Edit row : " + row);
            }

            @Override
            public void onDelete(int row) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.removeRow(row);
            }

            @Override
            public void onView(int row) {
                System.out.println("View row : " + row);
            }
        };
        pnlTable.setBackground(ColorUtils.BACKGROUND_TABLE);
        
        TableCustomUI.apply(scrDanhSach, TableCustomUI.TableType.DEFAULT);
        table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(table, 0));
        table.getColumnModel().getColumn(9).setCellRenderer(new TableActionCellRender(table));
        table.getColumnModel().getColumn(9).setCellEditor(new TableActionCellEditor(event));
    }
    
    private void renderPagination() { 
        pagination.setPanel(pnlPhanTrang);
        pagination.setCurrentPage(1);
        
        pagination.setTotalPages(3);
        pagination.setCallback(new Pagination.Callback() {
            @Override
            public void onChangeLimitItem(JComboBox<Integer> comboBox) {
                System.err.println(comboBox.getSelectedItem());
            }

            @Override
            public void onClickPage(int page) {
                System.err.println(page);
            }
        });
        pagination.render();
    }
    
    private void fillTable() { 
        try {
            DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
            if (tblDanhSach.isEditing()) {
                tblDanhSach.getCellEditor().stopCellEditing();
            }
            
            model.setRowCount(0);
            model.addRow(new Object[] {false, 1, "a", "b", "c", "d", "e", "f", "g", "h"});
            model.addRow(new Object[] {false, 2, "a", "b", "c", "d", "e", "f", "g", "h"});
            model.addRow(new Object[] {false, 3, "a", "b", "c", "d", "e", "f", "g", "h"});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlContainer = new com.app.views.UI.panel.RoundPanel();
        tbpTab = new javax.swing.JTabbedPane();
        pnlDanhSachSanPham = new javax.swing.JPanel();
        pnlFilter = new com.app.views.UI.panel.RoundPanel();
        pnlSearchBox = new com.app.views.UI.panel.SearchBox();
        cboThuongHieu = new javax.swing.JComboBox();
        cboXuatXu = new javax.swing.JComboBox();
        btnClear = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblFilter = new javax.swing.JLabel();
        splitLine1 = new com.app.views.UI.label.SplitLine();
        cboDanhMuc = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        pnlList = new com.app.views.UI.panel.RoundPanel();
        lblList = new javax.swing.JLabel();
        btnThemSanPham = new javax.swing.JButton();
        splitLine2 = new com.app.views.UI.label.SplitLine();
        btnXoaSanPham = new javax.swing.JButton();
        pnlPhanTrang = new javax.swing.JPanel();
        pnlTable = new com.app.views.UI.panel.RoundPanel();
        scrDanhSach = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        pnlDanhSachSanPhamChiTiet = new javax.swing.JPanel();

        pnlDanhSachSanPham.setOpaque(false);

        cboThuongHieu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả thương hiệu --" }));

        cboXuatXu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả xuất xứ --" }));

        btnClear.setText("Huỷ lọc");
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnSearch.setText("Tìm kiếm");
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel1.setText("Thương hiệu:");

        jLabel2.setText("Từ khoá:");

        jLabel3.setText("Xuất xứ:");

        lblFilter.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblFilter.setText("Bộ lọc");

        javax.swing.GroupLayout splitLine1Layout = new javax.swing.GroupLayout(splitLine1);
        splitLine1.setLayout(splitLine1Layout);
        splitLine1Layout.setHorizontalGroup(
            splitLine1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        splitLine1Layout.setVerticalGroup(
            splitLine1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        cboDanhMuc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả danh mục --" }));

        jLabel4.setText("Danh mục:");

        javax.swing.GroupLayout pnlFilterLayout = new javax.swing.GroupLayout(pnlFilter);
        pnlFilter.setLayout(pnlFilterLayout);
        pnlFilterLayout.setHorizontalGroup(
            pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilterLayout.createSequentialGroup()
                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFilterLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlFilterLayout.createSequentialGroup()
                                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                                    .addComponent(pnlSearchBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlFilterLayout.createSequentialGroup()
                                        .addComponent(cboDanhMuc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(20, 20, 20))
                                    .addGroup(pnlFilterLayout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(21, 21, 21)))
                                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboThuongHieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(20, 20, 20)
                                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboXuatXu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnClear)
                                .addGap(20, 20, 20))))
                    .addGroup(pnlFilterLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(splitLine1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlFilterLayout.setVerticalGroup(
            pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilterLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lblFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitLine1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSearchBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlFilterLayout.createSequentialGroup()
                        .addComponent(cboXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboThuongHieu, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboDanhMuc, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(20, 20, 20))
        );

        lblList.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblList.setText("Danh sách sản phẩm");

        btnThemSanPham.setText("Thêm sản phẩm");
        btnThemSanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThemSanPham.setMaximumSize(new java.awt.Dimension(50, 23));
        btnThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout splitLine2Layout = new javax.swing.GroupLayout(splitLine2);
        splitLine2.setLayout(splitLine2Layout);
        splitLine2Layout.setHorizontalGroup(
            splitLine2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        splitLine2Layout.setVerticalGroup(
            splitLine2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        btnXoaSanPham.setText("Xoá");
        btnXoaSanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoaSanPham.setMaximumSize(new java.awt.Dimension(50, 23));
        btnXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPhamActionPerformed(evt);
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
                "", "#", "Mã sản phẩm", "Tên sản phẩm", "Danh mục", "Thương hiệu", "Xuất xứ", "Số lượng", "Trạng thái", "Hành động"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, true
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
            tblDanhSach.getColumnModel().getColumn(0).setMaxWidth(40);
            tblDanhSach.getColumnModel().getColumn(1).setMaxWidth(50);
        }

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrDanhSach, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(scrDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout pnlListLayout = new javax.swing.GroupLayout(pnlList);
        pnlList.setLayout(pnlListLayout);
        pnlListLayout.setHorizontalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(splitLine2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlListLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPhanTrang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlListLayout.createSequentialGroup()
                        .addComponent(lblList, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
                        .addComponent(btnXoaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnThemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        pnlListLayout.setVerticalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblList, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitLine2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnlPhanTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout pnlDanhSachSanPhamLayout = new javax.swing.GroupLayout(pnlDanhSachSanPham);
        pnlDanhSachSanPham.setLayout(pnlDanhSachSanPhamLayout);
        pnlDanhSachSanPhamLayout.setHorizontalGroup(
            pnlDanhSachSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlDanhSachSanPhamLayout.setVerticalGroup(
            pnlDanhSachSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachSanPhamLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(pnlFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbpTab.addTab("Sanh sách sản phẩm", pnlDanhSachSanPham);

        pnlDanhSachSanPhamChiTiet.setOpaque(false);

        javax.swing.GroupLayout pnlDanhSachSanPhamChiTietLayout = new javax.swing.GroupLayout(pnlDanhSachSanPhamChiTiet);
        pnlDanhSachSanPhamChiTiet.setLayout(pnlDanhSachSanPhamChiTietLayout);
        pnlDanhSachSanPhamChiTietLayout.setHorizontalGroup(
            pnlDanhSachSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1003, Short.MAX_VALUE)
        );
        pnlDanhSachSanPhamChiTietLayout.setVerticalGroup(
            pnlDanhSachSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );

        tbpTab.addTab("Danh sách sản phẩm chi tiết", pnlDanhSachSanPhamChiTiet);

        javax.swing.GroupLayout pnlContainerLayout = new javax.swing.GroupLayout(pnlContainer);
        pnlContainer.setLayout(pnlContainerLayout);
        pnlContainerLayout.setHorizontalGroup(
            pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContainerLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(tbpTab)
                .addGap(16, 16, 16))
        );
        pnlContainerLayout.setVerticalGroup(
            pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbpTab)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblDanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachMouseClicked
        // TODO add your handling code here:
        System.out.println("row click");
    }//GEN-LAST:event_tblDanhSachMouseClicked

    private void btnXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPhamActionPerformed
        // TODO add your handling code here:
        handleClickButtonDelete();
    }//GEN-LAST:event_btnXoaSanPhamActionPerformed

    private void btnThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamActionPerformed
        // TODO add your handling code here:
        handleClickButtonAdd();
    }//GEN-LAST:event_btnThemSanPhamActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnThemSanPham;
    private javax.swing.JButton btnXoaSanPham;
    private javax.swing.JComboBox cboDanhMuc;
    private javax.swing.JComboBox cboThuongHieu;
    private javax.swing.JComboBox cboXuatXu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblFilter;
    private javax.swing.JLabel lblList;
    private com.app.views.UI.panel.RoundPanel pnlContainer;
    private javax.swing.JPanel pnlDanhSachSanPham;
    private javax.swing.JPanel pnlDanhSachSanPhamChiTiet;
    private com.app.views.UI.panel.RoundPanel pnlFilter;
    private com.app.views.UI.panel.RoundPanel pnlList;
    private javax.swing.JPanel pnlPhanTrang;
    private com.app.views.UI.panel.SearchBox pnlSearchBox;
    private com.app.views.UI.panel.RoundPanel pnlTable;
    private javax.swing.JScrollPane scrDanhSach;
    private com.app.views.UI.label.SplitLine splitLine1;
    private com.app.views.UI.label.SplitLine splitLine2;
    private javax.swing.JTable tblDanhSach;
    private javax.swing.JTabbedPane tbpTab;
    // End of variables declaration//GEN-END:variables

    private void handleClickButtonDelete() {
        List<Integer> rows = findSelectedRows(tblDanhSach);
        if (rows.isEmpty()) { 
            MessageModal.error("Vui lòng chọn ít nhất một sản phẩm muốn xoá!!!");
            return;
        }
    }
    
    private List<Integer> findSelectedRows(JTable table) {
        List<Integer> trueRows = new ArrayList<>();
        for (int row = 0; row < table.getRowCount(); row++) {
            Boolean value = (Boolean) table.getValueAt(row, 0);
            if (Boolean.TRUE.equals(value)) {
                trueRows.add(row);
            }
        }
        return trueRows;
    }

    private void handleClickButtonAdd() {
        ModalDialog.showModal(this, new SimpleModalBorder(new InuhaAddSanPhamView(), "Thêm sản phẩm"));
    }

    
}
