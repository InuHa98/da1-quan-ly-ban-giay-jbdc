/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

package com.app.core.inuha.views.quanly.phieugiamgia;

import com.app.common.helper.Pagination;
import com.app.common.infrastructure.constants.TrangThaiPhieuGiamGiaConstant;
import com.app.core.inuha.models.InuhaPhieuGiamGiaModel;
import com.app.core.inuha.request.InuhaFilterPhieuGiamGiaRequest;
import com.app.core.inuha.services.InuhaPhieuGiamGiaService;
import com.app.utils.ColorUtils;
import com.app.utils.ResourceUtils;
import com.app.views.UI.combobox.ComboBoxItem;
import com.app.views.UI.dialog.LoadingDialog;
import com.app.views.UI.table.TableCustomUI;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import raven.datetime.component.date.DatePicker;

/**
 *
 * @author inuHa
 */
public class InuhaPhieuGiamGiaView extends javax.swing.JPanel {

    private static InuhaPhieuGiamGiaView instance;
        
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    
    private final InuhaPhieuGiamGiaService phieuGiamGiaService = InuhaPhieuGiamGiaService.getInstance();
    
    public Pagination pagination = new Pagination();
    
    private int sizePage = pagination.getLimitItem();
    
    private List<InuhaPhieuGiamGiaModel> dataItems = new ArrayList<>();
    
    public final static String MODAL_ID_CREATE = "modal_create_phieu_giam_gia";
    
    private JTextField txtTuKhoa;
    
    private DatePicker datePicker = new DatePicker();
    
    
    public static InuhaPhieuGiamGiaView getInstance() { 
        if (instance == null) { 
            instance = new InuhaPhieuGiamGiaView();
        }
        return instance;
    }
	
    /** Creates new form InuhaPhieuGiamGiaView */
    public InuhaPhieuGiamGiaView() {
	initComponents();
	
	datePicker.setEditor(txtThoiGian);
	datePicker.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
	datePicker.setSeparator("  tới ngày  ");
	datePicker.setUsePanelOption(true);
	datePicker.setDateSelectionAble(localDate -> !localDate.isAfter(LocalDate.now()));
	datePicker.setCloseAfterSelected(true);
	
	pnlSearchBox.setPlaceholder("Nhập tên hoặc mã giảm giấ ...");
        txtTuKhoa = pnlSearchBox.getKeyword();
	
	btnSearch.setIcon(ResourceUtils.getSVG("/svg/search.svg", new Dimension(20, 20)));
	btnClear.setBackground(ColorUtils.BUTTON_GRAY);
	
	lblFilter.setIcon(ResourceUtils.getSVG("/svg/filter.svg", new Dimension(20, 20)));
	lblDanhSach.setIcon(ResourceUtils.getSVG("/svg/list.svg", new Dimension(20, 20)));
	
	txtTuKhoa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
                    handleClickButtonSearch();
                }
            }
        });
	
	cboTrangThai.removeAllItems();
        cboTrangThai.addItem(new ComboBoxItem<>("-- Tất cả trạng thái --", -1));
        cboTrangThai.addItem(new ComboBoxItem<>("Đang diễn ra", TrangThaiPhieuGiamGiaConstant.DANG_DIEN_RA));
        cboTrangThai.addItem(new ComboBoxItem<>("Sắp diễn ra", TrangThaiPhieuGiamGiaConstant.SAP_DIEN_RA));
	cboTrangThai.addItem(new ComboBoxItem<>("Đã diễn ra", TrangThaiPhieuGiamGiaConstant.DA_DIEN_RA));
		
	Dimension cboSize = new Dimension(150, 36);
	cboTrangThai.setPreferredSize(cboSize);
	
	setupTable(tblDanhSach);
        loadDataPage(1);
        setupPagination();
    }

    private void setupTable(JTable table) { 
	pnlList.setBackground(ColorUtils.BACKGROUND_DASHBOARD);
	pnlDanhSach.setBackground(ColorUtils.BACKGROUND_TABLE);
        TableCustomUI.apply(scrDanhSach, TableCustomUI.TableType.DEFAULT);

        tblDanhSach.setBackground(ColorUtils.BACKGROUND_TABLE);
        tblDanhSach.getTableHeader().setBackground(ColorUtils.BACKGROUND_TABLE);
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
            
	    ComboBoxItem<Integer> trangThai = (ComboBoxItem<Integer>) cboTrangThai.getSelectedItem();
	    
            InuhaFilterPhieuGiamGiaRequest request = new InuhaFilterPhieuGiamGiaRequest();
            request.setSize(sizePage);
	    
            int totalPages = phieuGiamGiaService.getTotalPage(request);
            if (totalPages < page) { 
                page = totalPages;
            }
            
            request.setPage(page);

           
            dataItems = phieuGiamGiaService.getPage(request);
            
            for(InuhaPhieuGiamGiaModel m: dataItems) { 
                model.addRow(m.toDataRowBanHang());
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
		    loadDataPage(1);
		    loading.dispose();
		});
                loading.setVisible(true);
            }

            @Override
            public void onClickPage(int page) {
		LoadingDialog loading = new LoadingDialog();
		executorService.submit(() -> { 
		    loadDataPage(page);
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
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFilter = new com.app.views.UI.panel.RoundPanel();
        lblFilter = new javax.swing.JLabel();
        splitLine2 = new com.app.views.UI.label.SplitLine();
        pnlSearchBox = new com.app.views.UI.panel.SearchBox();
        cboTrangThai = new javax.swing.JComboBox();
        txtThoiGian = new javax.swing.JFormattedTextField();
        btnSearch = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        pnlList = new com.app.views.UI.panel.RoundPanel();
        lblDanhSach = new javax.swing.JLabel();
        splitLine1 = new com.app.views.UI.label.SplitLine();
        pnlDanhSach = new javax.swing.JPanel();
        scrDanhSach = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        pnlPhanTrang = new javax.swing.JPanel();

        lblFilter.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblFilter.setText("Bộ lọc");

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

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSearch.setText("Tìm kiếm");
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnClear.setText("Huỷ");
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout pnlFilterLayout = new javax.swing.GroupLayout(pnlFilter);
        pnlFilter.setLayout(pnlFilterLayout);
        pnlFilterLayout.setHorizontalGroup(
            pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitLine2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlFilterLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFilterLayout.createSequentialGroup()
                        .addComponent(lblFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20))
                    .addGroup(pnlFilterLayout.createSequentialGroup()
                        .addComponent(pnlSearchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(101, Short.MAX_VALUE))))
        );
        pnlFilterLayout.setVerticalGroup(
            pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilterLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblFilter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitLine2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlSearchBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboTrangThai)
                    .addComponent(txtThoiGian)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        lblDanhSach.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblDanhSach.setText("Danh sách phiếu giảm giá");

        javax.swing.GroupLayout splitLine1Layout = new javax.swing.GroupLayout(splitLine1);
        splitLine1.setLayout(splitLine1Layout);
        splitLine1Layout.setHorizontalGroup(
            splitLine1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        splitLine1Layout.setVerticalGroup(
            splitLine1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        tblDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Mã", "Tên", "Số lượng", "Kiểu giảm", "Giá trị giảm", "Giảm tối đa", "Đơn tối thiểu", "Ngày bắt đầu", "ngày kết thúc", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrDanhSach.setViewportView(tblDanhSach);
        if (tblDanhSach.getColumnModel().getColumnCount() > 0) {
            tblDanhSach.getColumnModel().getColumn(1).setMinWidth(100);
            tblDanhSach.getColumnModel().getColumn(2).setMinWidth(100);
        }

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrDanhSach)
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(scrDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
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

        javax.swing.GroupLayout pnlListLayout = new javax.swing.GroupLayout(pnlList);
        pnlList.setLayout(pnlListLayout);
        pnlListLayout.setHorizontalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 736, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlPhanTrang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(splitLine1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlDanhSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlListLayout.setVerticalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitLine1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlPhanTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(pnlFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox cboTrangThai;
    private javax.swing.JLabel lblDanhSach;
    private javax.swing.JLabel lblFilter;
    private javax.swing.JPanel pnlDanhSach;
    private com.app.views.UI.panel.RoundPanel pnlFilter;
    private com.app.views.UI.panel.RoundPanel pnlList;
    private javax.swing.JPanel pnlPhanTrang;
    private com.app.views.UI.panel.SearchBox pnlSearchBox;
    private javax.swing.JScrollPane scrDanhSach;
    private com.app.views.UI.label.SplitLine splitLine1;
    private com.app.views.UI.label.SplitLine splitLine2;
    private javax.swing.JTable tblDanhSach;
    private javax.swing.JFormattedTextField txtThoiGian;
    // End of variables declaration//GEN-END:variables

    private void handleClickButtonSearch() {
	
    }
}
