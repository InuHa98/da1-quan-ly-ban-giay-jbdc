package com.app.core.inuha.views.quanly.components.thuonghieu;

import com.app.Application;
import com.app.common.helper.MessageModal;
import com.app.common.helper.MessageToast;
import com.app.common.helper.Pagination;
import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.common.infrastructure.request.FillterRequest;
import com.app.core.inuha.models.sanpham.InuhaThuongHieuModel;
import com.app.core.inuha.services.InuhaThuongHieuService;
import com.app.core.inuha.views.quanly.components.sanpham.InuhaAddSanPhamView;
import com.app.core.inuha.views.quanly.components.table.thuoctinhsanpham.InuhaThuocTinhTableActionCellEditor;
import com.app.core.inuha.views.quanly.components.table.thuoctinhsanpham.InuhaThuocTinhTableActionCellRender;
import com.app.utils.ColorUtils;
import com.app.utils.ResourceUtils;
import com.app.views.UI.dialog.LoadingDialog;
import com.app.views.UI.table.TableCustomUI;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;
import com.app.views.UI.table.ITableActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author InuHa
 */
public class InuhaListThuongHieuView extends javax.swing.JPanel {

    private static InuhaListThuongHieuView instance;
    
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    
    private final InuhaThuongHieuService thuongHieuService = new InuhaThuongHieuService();
    
    public Pagination pagination = new Pagination();
    
    private int sizePage = pagination.getLimitItem();
    
    private List<InuhaThuongHieuModel> dataItems = new ArrayList<>();
    
    public final static String MODAL_ID_CREATE = "modal_create_danh_muc";
        
    public final static String MODAL_ID_EDIT = "modal_edit_danh_muc";
        
    public static InuhaListThuongHieuView getInstance() { 
        if (instance == null) { 
            instance = new InuhaListThuongHieuView();
        }
        return instance;
    }
    
    /**
     * Creates new form InuhaQuanLyThuongHieuView
     */
    public InuhaListThuongHieuView() {
        initComponents();
        instance = this;

        btnAdd.setBackground(ColorUtils.PRIMARY_COLOR);
        btnAdd.setIcon(ResourceUtils.getSVG("/svg/plus.svg", new Dimension(20, 20)));
        
        setupTable(tblDanhSach);
        loadDataPage(1);
        setupPagination();
    }

    private void setupTable(JTable table) { 
        
        ITableActionEvent event = new ITableActionEvent() {
            @Override
            public void onEdit(int row) {
                InuhaThuongHieuModel item = dataItems.get(row);
                ModalDialog.showModal(instance, new SimpleModalBorder(new InuhaEditThuongHieuView(item), "Chỉnh sửa thương hiệu"), MODAL_ID_EDIT);
            }

            @Override
            public void onDelete(int row) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                InuhaThuongHieuModel item = dataItems.get(row);
                
                LoadingDialog loadingDialog = new LoadingDialog();

                executorService.submit(() -> {
                    if (MessageModal.confirmWarning("Xoá: " + item.getTen(), "Bạn thực sự muốn xoá thương hiệu này?")) {
                        executorService.submit(() -> {
                            try {
                                thuongHieuService.delete(item.getId());
                                loadingDialog.dispose();
                                InuhaAddSanPhamView.getInstance().loadDataThuongHieu();
                                loadDataPage();
                                MessageToast.success("Xoá thành công thương hiệu: " + item.getTen());
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
            }
        };
        
        TableCustomUI.apply(scrDanhSach, TableCustomUI.TableType.DEFAULT);
        pnlDanhSach.setBackground(ColorUtils.BACKGROUND_GRAY);
        tblDanhSach.setBackground(ColorUtils.BACKGROUND_GRAY);
        tblDanhSach.getTableHeader().setBackground(ColorUtils.BACKGROUND_GRAY);
        
        table.getColumnModel().getColumn(4).setCellRenderer(new InuhaThuocTinhTableActionCellRender(table));
        table.getColumnModel().getColumn(4).setCellEditor(new InuhaThuocTinhTableActionCellEditor(event));
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
            
            FillterRequest request = new FillterRequest();
            
            int totalPages = thuongHieuService.getTotalPage(request);
            if (totalPages < page) { 
                page = totalPages;
            }
            
            request.setPage(page);
            request.setSize(sizePage);
           
            dataItems = thuongHieuService.getPage(request);
            
            for(InuhaThuongHieuModel m: dataItems) { 
                model.addRow(m.toDataRow());
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
        pagination.setCurrentPage(currentPage);
        pagination.setTotalPages(totalPages);
        pagination.renderListPage();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAdd = new javax.swing.JButton();
        pnlDanhSach = new com.app.views.UI.panel.RoundPanel();
        scrDanhSach = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        pnlPhanTrang = new javax.swing.JPanel();

        btnAdd.setText("Thêm thương hiệu mới");
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        tblDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Tên thương hiệu", "Ngày tạo", "Cập nhật cuối", "hành động"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSach.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDanhSach.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrDanhSach.setViewportView(tblDanhSach);
        if (tblDanhSach.getColumnModel().getColumnCount() > 0) {
            tblDanhSach.getColumnModel().getColumn(0).setMaxWidth(50);
            tblDanhSach.getColumnModel().getColumn(1).setMinWidth(200);
        }

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(scrDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pnlPhanTrang.setOpaque(false);

        javax.swing.GroupLayout pnlPhanTrangLayout = new javax.swing.GroupLayout(pnlPhanTrang);
        pnlPhanTrang.setLayout(pnlPhanTrangLayout);
        pnlPhanTrangLayout.setHorizontalGroup(
            pnlPhanTrangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlPhanTrangLayout.setVerticalGroup(
            pnlPhanTrangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlPhanTrang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAdd)))
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(pnlDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlPhanTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        handleClickButtonAdd();
    }//GEN-LAST:event_btnAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private com.app.views.UI.panel.RoundPanel pnlDanhSach;
    private javax.swing.JPanel pnlPhanTrang;
    private javax.swing.JScrollPane scrDanhSach;
    private javax.swing.JTable tblDanhSach;
    // End of variables declaration//GEN-END:variables

    
    private void handleClickButtonAdd() {
        ModalDialog.showModal(this, new SimpleModalBorder(new InuhaAddThuongHieuView(), "Thêm thương hiệu mới"), MODAL_ID_CREATE);
    }
}
