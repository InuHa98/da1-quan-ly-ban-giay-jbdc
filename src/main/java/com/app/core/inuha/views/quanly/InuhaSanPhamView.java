package com.app.core.inuha.views.quanly;

import com.app.Application;
import com.app.common.helper.ExcelHelper;
import com.app.common.helper.MessageModal;
import com.app.common.helper.MessageToast;
import com.app.common.helper.Pagination;
import com.app.common.helper.QrCodeHelper;
import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
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
import com.app.core.inuha.request.InuhaFilterSanPhamRequest;
import com.app.core.inuha.services.InuhaChatLieuService;
import com.app.core.inuha.services.InuhaDanhMucService;
import com.app.core.inuha.services.InuhaDeGiayService;
import com.app.core.inuha.services.InuhaKichCoService;
import com.app.core.inuha.services.InuhaKieuDangService;
import com.app.core.inuha.services.InuhaMauSacService;
import com.app.core.inuha.services.InuhaSanPhamChiTietService;
import com.app.core.inuha.services.InuhaSanPhamService;
import com.app.core.inuha.services.InuhaThuongHieuService;
import com.app.core.inuha.services.InuhaXuatXuService;
import com.app.core.inuha.views.quanly.components.table.trangthai.InuhaTrangThaiSanPhamTableCellRender;
import com.app.core.inuha.views.quanly.sanpham.InuhaAddSanPhamView;
import com.app.core.inuha.views.quanly.sanpham.InuhaDetailSanPhamChiTietView;
import com.app.core.inuha.views.quanly.sanpham.InuhaDetailSanPhamView;
import static com.app.core.inuha.views.quanly.sanpham.InuhaDetailSanPhamView.ID_MODAL_DEAIL;
import com.app.utils.ColorUtils;
import com.app.utils.CurrencyUtils;
import com.app.utils.ProductUtils;
import com.app.utils.QrCodeUtils;
import com.app.utils.ResourceUtils;
import com.app.utils.TimeUtils;
import com.app.views.UI.combobox.ComboBoxItem;
import com.app.views.UI.dialog.LoadingDialog;
import com.app.views.UI.panel.RoundPanel;
import com.app.views.UI.panel.qrcode.IQRCodeScanEvent;
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
import com.app.views.UI.table.celll.TableImageCellRender;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import jnafilechooser.api.JnaFileChooser;

/**
 *
 * @author inuHa
 */
public class InuhaSanPhamView extends RoundPanel {

    private static InuhaSanPhamView instance;
        
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    
    private final static InuhaSanPhamService sanPhamService = new InuhaSanPhamService();
    
    private final static InuhaSanPhamChiTietService sanPhamChiTietService = new InuhaSanPhamChiTietService();
	
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
    
    private JTextField txtTuKhoa2;
    
    public Pagination pagination = new Pagination();
    
    public Pagination paginationSPCT = new Pagination();
    
    private int sizePage = pagination.getLimitItem();
    
    private List<InuhaSanPhamModel> dataItems = new ArrayList<>();
    
    private List<InuhaSanPhamChiTietModel> dataItemsSPCT = new ArrayList<>();
    
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
        
	pnlSearchBox2.setPlaceholder("Nhập tên hoặc mã sản phẩm ...");
        txtTuKhoa2 = pnlSearchBox2.getKeyword();
	
        txtTuKhoa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
                    handleClickButtonSearch();
                }
            }
        });
        
	txtTuKhoa2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
                    handleClickButtonSeachChiTiet();
                }
            }
        });
		
        pnlContainer.setBackground(ColorUtils.BACKGROUND_PRIMARY);
        tbpTab.setBackground(ColorUtils.BACKGROUND_PRIMARY);
        lblFilter.setIcon(ResourceUtils.getSVG("/svg/filter.svg", new Dimension(20, 20)));
	lblFilter2.setIcon(ResourceUtils.getSVG("/svg/filter.svg", new Dimension(20, 20)));
        lblList.setIcon(ResourceUtils.getSVG("/svg/list.svg", new Dimension(20, 20)));
	lblList2.setIcon(ResourceUtils.getSVG("/svg/list.svg", new Dimension(20, 20)));
        btnThemSanPham.setIcon(ResourceUtils.getSVG("/svg/plus.svg", new Dimension(20, 20)));
        btnXoaSanPham.setIcon(ResourceUtils.getSVG("/svg/trash.svg", new Dimension(20, 20)));
        btnSearch.setIcon(ResourceUtils.getSVG("/svg/search.svg", new Dimension(20, 20)));
        btnSearch2.setIcon(ResourceUtils.getSVG("/svg/search.svg", new Dimension(20, 20)));
	btnScanQR.setIcon(ResourceUtils.getSVG("/svg/qr.svg", new Dimension(20, 20)));
	btnScanQRChiTiet.setIcon(ResourceUtils.getSVG("/svg/qr.svg", new Dimension(20, 20)));
	btnExport.setIcon(ResourceUtils.getSVG("/svg/export.svg", new Dimension(20, 20)));
	btnImport.setIcon(ResourceUtils.getSVG("/svg/import.svg", new Dimension(20, 20)));
	btnSaveAllQR.setIcon(ResourceUtils.getSVG("/svg/save.svg", new Dimension(20, 20)));
	
        btnClear.setBackground(ColorUtils.BUTTON_GRAY);
	btnClear2.setBackground(ColorUtils.BUTTON_GRAY);
        btnThemSanPham.setBackground(ColorUtils.BUTTON_PRIMARY);
        
        cboTrangThai.removeAllItems();
        cboTrangThai.addItem(new ComboBoxItem<>("-- Tất cả trạng thái --", -1));
        cboTrangThai.addItem(new ComboBoxItem<>("Đang bán", 1));
        cboTrangThai.addItem(new ComboBoxItem<>("Ngừng bán", 0));
        
	cboTrangThai2.removeAllItems();
        cboTrangThai2.addItem(new ComboBoxItem<>("-- Tất cả trạng thái --", -1));
        cboTrangThai2.addItem(new ComboBoxItem<>("Đang bán", 1));
        cboTrangThai2.addItem(new ComboBoxItem<>("Ngừng bán", 0));
	
	Dimension cboSize = new Dimension(150, 36);
	cboDanhMuc.setPreferredSize(cboSize);
	cboThuongHieu.setPreferredSize(cboSize);
	cboTrangThai.setPreferredSize(cboSize);
	cboDanhMuc2.setPreferredSize(cboSize);
	cboThuongHieu2.setPreferredSize(cboSize);
	cboTrangThai2.setPreferredSize(cboSize);
	cboXuatXu.setPreferredSize(cboSize);
	cboKieuDang.setPreferredSize(cboSize);
	cboChatLieu.setPreferredSize(cboSize);
	cboDeGiay.setPreferredSize(cboSize);
	cboKichCo.setPreferredSize(cboSize);
	cboMauSac.setPreferredSize(cboSize);
	
        setupTable(tblDanhSach);
	setupTableSPCT(tblDanhSachChiTiet);
        setupPagination();
	setupPaginationSPCT();
	
	loadDataDanhMuc();
	loadDataThuongHieu();
	loadDataXuatXu();
	loadDataKieuDang();
	loadDataChatLieu();
	loadDataDeGiay();
	loadDataKichCo();
	loadDataMauSac();

	loadDataPage(1);
	loadDataPageSPCT(1);
    }
    
    private void setupTable(JTable table) { 
        
        ITableActionEvent event = new ITableActionEvent() {
            @Override
            public void onEdit(int row) {
                InuhaSanPhamModel item = dataItems.get(row);
                showEditSanPham(item);
            }

            @Override
            public void onDelete(int row) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                InuhaSanPhamModel item = dataItems.get(row);
                
                LoadingDialog loadingDialog = new LoadingDialog();

                executorService.submit(() -> {
                    if (MessageModal.confirmWarning("Xoá: " + item.getTen(), "Bạn thực sự muốn xoá sản phẩm này?")) {
                        executorService.submit(() -> {
                            try {
                                sanPhamService.delete(item.getId());
                                loadingDialog.dispose();
                                loadDataPage();
                                MessageToast.success("Xoá thành công sản phẩm: " + item.getTen());
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
                InuhaSanPhamModel item = dataItems.get(row);
                showDetailSanPham(item);
            }

        };
        
        pnlDanhSach.setBackground(ColorUtils.BACKGROUND_TABLE);
        TableCustomUI.apply(scrDanhSach, TableCustomUI.TableType.DEFAULT);
	
        table.setRowHeight(50);
        table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(table, 0));
        table.getColumnModel().getColumn(3).setCellRenderer(new TableImageCellRender(table));
	table.getColumnModel().getColumn(9).setCellRenderer(new InuhaTrangThaiSanPhamTableCellRender(table));
        table.getColumnModel().getColumn(10).setCellRenderer(new TableActionCellRender(table));
        table.getColumnModel().getColumn(10).setCellEditor(new TableActionCellEditor(event));
    }
    
    private void setupTableSPCT(JTable table) { 
        
        ITableActionEvent event = new ITableActionEvent() {
            @Override
            public void onEdit(int row) {
                InuhaSanPhamChiTietModel item = dataItemsSPCT.get(row);

            }

            @Override
            public void onDelete(int row) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                InuhaSanPhamChiTietModel item = dataItemsSPCT.get(row);
                
                LoadingDialog loading = new LoadingDialog();

                executorService.submit(() -> {

                });
            }

            @Override
            public void onView(int row) {
                InuhaSanPhamChiTietModel item = dataItemsSPCT.get(row);

            }

        };
        
        pnlDanhSachChiTiet.setBackground(ColorUtils.BACKGROUND_TABLE);
        TableCustomUI.apply(scrDanhSachChiTiet, TableCustomUI.TableType.DEFAULT);
        TableCustomUI.resizeColumnHeader(table);
	
        table.setRowHeight(50);
        table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxTableHeaderRenderer(table, 0));
        table.getColumnModel().getColumn(4).setCellRenderer(new TableImageCellRender(table));
	table.getColumnModel().getColumn(17).setCellRenderer(new InuhaTrangThaiSanPhamTableCellRender(table));
    }
    
    public void loadDataPage() { 
        loadDataPage(pagination.getCurrentPage());
    }
    
    public void loadDataPageSPCT() { 
        loadDataPageSPCT(paginationSPCT.getCurrentPage());
    }
	
    public void loadDataPage(int page) { 
        try {
            DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
            if (tblDanhSach.isEditing()) {
                tblDanhSach.getCellEditor().stopCellEditing();
            }
            
            model.setRowCount(0);
            
            String keyword = txtTuKhoa.getText().trim();
            keyword = keyword.replaceAll("\\s+", " ");
        
            ComboBoxItem<Integer> danhMuc = (ComboBoxItem<Integer>) cboDanhMuc.getSelectedItem();
            ComboBoxItem<Integer> thuongHieu = (ComboBoxItem<Integer>) cboThuongHieu.getSelectedItem();
            ComboBoxItem<Integer> trangThai = (ComboBoxItem<Integer>) cboTrangThai.getSelectedItem();

            InuhaFilterSanPhamRequest request = new InuhaFilterSanPhamRequest();
	    request.setKeyword(keyword);
	    request.setDanhMuc(danhMuc);
	    request.setThuongHieu(thuongHieu);
	    request.setTrangThai(trangThai);
            request.setSize(sizePage);
	    
            int totalPages = sanPhamService.getTotalPage(request);
            
            if (totalPages < page) { 
                page = totalPages;
            }
            
            request.setPage(page);

           
            dataItems = sanPhamService.getPage(request);
            
            for(InuhaSanPhamModel m: dataItems) { 
                model.addRow(m.toDataRowSanPham());
            }

            rerenderPagination(page, totalPages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    public void loadDataPageSPCT(int page) { 
        try {
            DefaultTableModel model = (DefaultTableModel) tblDanhSachChiTiet.getModel();
            if (tblDanhSachChiTiet.isEditing()) {
                tblDanhSachChiTiet.getCellEditor().stopCellEditing();
            }
            
            model.setRowCount(0);
            
            String keyword = txtTuKhoa2.getText().trim();
            keyword = keyword.replaceAll("\\s+", " ");
        
            ComboBoxItem<Integer> danhMuc = (ComboBoxItem<Integer>) cboDanhMuc2.getSelectedItem();
            ComboBoxItem<Integer> thuongHieu = (ComboBoxItem<Integer>) cboThuongHieu2.getSelectedItem();
	    ComboBoxItem<Integer> xuatXu = (ComboBoxItem<Integer>) cboXuatXu.getSelectedItem();
	    ComboBoxItem<Integer> kieuDang = (ComboBoxItem<Integer>) cboKieuDang.getSelectedItem();
	    ComboBoxItem<Integer> chatLieu = (ComboBoxItem<Integer>) cboChatLieu.getSelectedItem();
	    ComboBoxItem<Integer> deGiay = (ComboBoxItem<Integer>) cboDeGiay.getSelectedItem();
	    ComboBoxItem<Integer> kichCo = (ComboBoxItem<Integer>) cboKichCo.getSelectedItem();
	    ComboBoxItem<Integer> mauSac = (ComboBoxItem<Integer>) cboMauSac.getSelectedItem();
            ComboBoxItem<Integer> trangThai = (ComboBoxItem<Integer>) cboTrangThai2.getSelectedItem();

            InuhaFilterSanPhamChiTietRequest request = new InuhaFilterSanPhamChiTietRequest();
	    request.setKeyword(keyword);
	    request.setDanhMuc(danhMuc);
	    request.setThuongHieu(thuongHieu);
	    request.setXuatXu(xuatXu);
	    request.setKieuDang(kieuDang);
	    request.setChatLieu(chatLieu);
	    request.setDeGiay(deGiay);
	    request.setKichCo(kichCo);
	    request.setMauSac(mauSac);
	    request.setTrangThai(trangThai);
            request.setSize(sizePage);
	    
            int totalPages = sanPhamChiTietService.getTotalPage(request);
            
            if (totalPages < page) { 
                page = totalPages;
            }
            
            request.setPage(page);

           
            dataItemsSPCT = sanPhamChiTietService.getPage(request);
            
            for(InuhaSanPhamChiTietModel m: dataItemsSPCT) { 
                model.addRow(m.toDataRowAllSanPhamChiTiet());
            }

            rerenderPaginationSPCT(page, totalPages);
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
     
    private void setupPaginationSPCT() { 
        paginationSPCT.setPanel(pnlPhanTrangChiTiet);
        paginationSPCT.setCallback(new Pagination.Callback() {
            @Override
            public void onChangeLimitItem(JComboBox<Integer> comboBox) {
                sizePage = (int) comboBox.getSelectedItem();
		LoadingDialog loading = new LoadingDialog();
		executorService.submit(() -> { 
		    loadDataPageSPCT(1);
		    loading.dispose();
		});
                loading.setVisible(true);
            }

            @Override
            public void onClickPage(int page) {
		LoadingDialog loading = new LoadingDialog();
		executorService.submit(() -> { 
		    loadDataPageSPCT(page);
		    loading.dispose();
		});
                loading.setVisible(true);
            }
        });
        paginationSPCT.render();
    }
	
    private void rerenderPagination(int currentPage, int totalPages) { 
	pagination.rerender(currentPage, totalPages);
    }
    
    private void rerenderPaginationSPCT(int currentPage, int totalPages) { 
	paginationSPCT.rerender(currentPage, totalPages);
    }
    
    public void loadDataDanhMuc() { 
        dataDanhMuc = danhMucService.getAll();
        cboDanhMuc.removeAllItems();
        cboDanhMuc2.removeAllItems();
	
        cboDanhMuc.addItem(new ComboBoxItem<>("-- Tất cả danh mục --", -1));
	cboDanhMuc2.addItem(new ComboBoxItem<>("-- Tất cả danh mục --", -1));
        for(InuhaDanhMucModel m: dataDanhMuc) { 
            cboDanhMuc.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
	    cboDanhMuc2.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }

    public void loadDataThuongHieu() { 
        dataThuongHieu = thuongHieuService.getAll();
        cboThuongHieu.removeAllItems();
        cboThuongHieu2.removeAllItems();
	
        cboThuongHieu.addItem(new ComboBoxItem<>("-- Tất cả thương hiệu --", -1));
	cboThuongHieu2.addItem(new ComboBoxItem<>("-- Tất cả thương hiệu --", -1));
        for(InuhaThuongHieuModel m: dataThuongHieu) { 
            cboThuongHieu.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
	    cboThuongHieu2.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }

    public void loadDataXuatXu() { 
        dataXuatXu = xuatXuService.getAll();
        cboXuatXu.removeAllItems();
	
        cboXuatXu.addItem(new ComboBoxItem<>("-- Tất cả xuất xứ --", -1));
        for(InuhaXuatXuModel m: dataXuatXu) { 
            cboXuatXu.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }

    public void loadDataKieuDang() { 
        dataKieuDang = kieuDangService.getAll();
        cboKieuDang.removeAllItems();
	
        cboKieuDang.addItem(new ComboBoxItem<>("-- Tất cả kiểu dáng --", -1));
        for(InuhaKieuDangModel m: dataKieuDang) { 
            cboKieuDang.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }
	
    public void loadDataChatLieu() { 
        dataChatLieu = chatLieuService.getAll();
        cboChatLieu.removeAllItems();
	
        cboChatLieu.addItem(new ComboBoxItem<>("-- Tất cả chất liệu --", -1));
        for(InuhaChatLieuModel m: dataChatLieu) { 
            cboChatLieu.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }
	    
    public void loadDataDeGiay() { 
        dataDeGiay = deGiayService.getAll();
        cboDeGiay.removeAllItems();
	
        cboDeGiay.addItem(new ComboBoxItem<>("-- Tất cả đế giày --", -1));
        for(InuhaDeGiayModel m: dataDeGiay) { 
            cboDeGiay.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }
		
    public void loadDataKichCo() { 
        dataKichCo = kichCoService.getAll();
        cboKichCo.removeAllItems();
	
        cboKichCo.addItem(new ComboBoxItem<>("-- Tất cả kích cỡ --", -1));
        for(InuhaKichCoModel m: dataKichCo) { 
            cboKichCo.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
        }
    }
		    
    public void loadDataMauSac() { 
        dataMauSac = mauSacService.getAll();
        cboMauSac.removeAllItems();
	
        cboMauSac.addItem(new ComboBoxItem<>("-- Tất cả màu sắc --", -1));
        for(InuhaMauSacModel m: dataMauSac) { 
            cboMauSac.addItem(new ComboBoxItem<>(m.getTen(), m.getId()));
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
        cboTrangThai = new javax.swing.JComboBox();
        btnClear = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        lblFilter = new javax.swing.JLabel();
        splitLine1 = new com.app.views.UI.label.SplitLine();
        cboDanhMuc = new javax.swing.JComboBox();
        pnlList = new com.app.views.UI.panel.RoundPanel();
        lblList = new javax.swing.JLabel();
        btnThemSanPham = new javax.swing.JButton();
        splitLine2 = new com.app.views.UI.label.SplitLine();
        btnXoaSanPham = new javax.swing.JButton();
        pnlPhanTrang = new javax.swing.JPanel();
        pnlDanhSach = new com.app.views.UI.panel.RoundPanel();
        scrDanhSach = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        btnScanQR = new javax.swing.JButton();
        pnlDanhSachSanPhamChiTiet = new javax.swing.JPanel();
        roundPanel1 = new com.app.views.UI.panel.RoundPanel();
        lblList2 = new javax.swing.JLabel();
        splitLine3 = new com.app.views.UI.label.SplitLine();
        btnSaveAllQR = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        pnlDanhSachChiTiet = new com.app.views.UI.panel.RoundPanel();
        scrDanhSachChiTiet = new javax.swing.JScrollPane();
        tblDanhSachChiTiet = new javax.swing.JTable();
        pnlPhanTrangChiTiet = new javax.swing.JPanel();
        btnScanQRChiTiet = new javax.swing.JButton();
        roundPanel2 = new com.app.views.UI.panel.RoundPanel();
        lblFilter2 = new javax.swing.JLabel();
        splitLine4 = new com.app.views.UI.label.SplitLine();
        cboDanhMuc2 = new javax.swing.JComboBox();
        cboThuongHieu2 = new javax.swing.JComboBox();
        cboTrangThai2 = new javax.swing.JComboBox();
        cboXuatXu = new javax.swing.JComboBox();
        cboKieuDang = new javax.swing.JComboBox();
        cboChatLieu = new javax.swing.JComboBox();
        cboDeGiay = new javax.swing.JComboBox();
        cboKichCo = new javax.swing.JComboBox();
        cboMauSac = new javax.swing.JComboBox();
        pnlSearchBox2 = new com.app.views.UI.panel.SearchBox();
        btnSearch2 = new javax.swing.JButton();
        btnClear2 = new javax.swing.JButton();

        pnlDanhSachSanPham.setOpaque(false);

        pnlSearchBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pnlSearchBoxKeyPressed(evt);
            }
        });

        cboThuongHieu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả thương hiệu --" }));

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả trạng thái --" }));

        btnClear.setText("Huỷ lọc");
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnSearch.setText("Tìm kiếm");
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

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
                                .addComponent(pnlSearchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cboDanhMuc, 0, 151, Short.MAX_VALUE)
                                .addGap(20, 20, 20)
                                .addComponent(cboThuongHieu, 0, 163, Short.MAX_VALUE)
                                .addGap(20, 20, 20)
                                .addComponent(cboTrangThai, 0, 151, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSearchBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlFilterLayout.createSequentialGroup()
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                "", "#", "Mã", "", "Tên", "Danh mục", "Thương hiệu", "Số lượng", "Giá bán", "Trạng thái", "Hành động"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSach.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
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
            tblDanhSach.getColumnModel().getColumn(2).setMinWidth(50);
            tblDanhSach.getColumnModel().getColumn(3).setMinWidth(60);
            tblDanhSach.getColumnModel().getColumn(3).setMaxWidth(60);
            tblDanhSach.getColumnModel().getColumn(4).setMinWidth(100);
            tblDanhSach.getColumnModel().getColumn(10).setMinWidth(120);
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
                .addComponent(scrDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        btnScanQR.setText("Quét QR");
        btnScanQR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnScanQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScanQRActionPerformed(evt);
            }
        });

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
                    .addComponent(pnlDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPhanTrang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlListLayout.createSequentialGroup()
                        .addComponent(lblList, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                        .addComponent(btnXoaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnScanQR)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                    .addComponent(btnXoaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnScanQR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitLine2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnlPhanTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
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

        tbpTab.addTab("Danh sách sản phẩm", pnlDanhSachSanPham);

        pnlDanhSachSanPhamChiTiet.setOpaque(false);

        lblList2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblList2.setText("Danh sách chi tiết sản phẩm");

        javax.swing.GroupLayout splitLine3Layout = new javax.swing.GroupLayout(splitLine3);
        splitLine3.setLayout(splitLine3Layout);
        splitLine3Layout.setHorizontalGroup(
            splitLine3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        splitLine3Layout.setVerticalGroup(
            splitLine3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        btnSaveAllQR.setText("Tải danh sách QR Code");
        btnSaveAllQR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSaveAllQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAllQRActionPerformed(evt);
            }
        });

        btnImport.setText("Nhập Excel");
        btnImport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        btnExport.setText("Xuất Excel");
        btnExport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        tblDanhSachChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "#", "Mã chi tiết", "Mã sản phẩm", "", "Tên sản phẩm", "Danh mục", "Thương hiệu", "Xuất xứ", "Kiểu dáng", "Chất liệu", "Đế giày", "Kích cỡ", "Màu sắc", "Giá nhập", "Giá bán", "Số lượng", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachChiTiet.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDanhSachChiTiet.setAutoscrolls(false);
        tblDanhSachChiTiet.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDanhSachChiTiet.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDanhSachChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachChiTietMouseClicked(evt);
            }
        });
        scrDanhSachChiTiet.setViewportView(tblDanhSachChiTiet);
        if (tblDanhSachChiTiet.getColumnModel().getColumnCount() > 0) {
            tblDanhSachChiTiet.getColumnModel().getColumn(0).setMaxWidth(50);
            tblDanhSachChiTiet.getColumnModel().getColumn(4).setMinWidth(60);
            tblDanhSachChiTiet.getColumnModel().getColumn(4).setMaxWidth(60);
        }

        javax.swing.GroupLayout pnlDanhSachChiTietLayout = new javax.swing.GroupLayout(pnlDanhSachChiTiet);
        pnlDanhSachChiTiet.setLayout(pnlDanhSachChiTietLayout);
        pnlDanhSachChiTietLayout.setHorizontalGroup(
            pnlDanhSachChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrDanhSachChiTiet)
        );
        pnlDanhSachChiTietLayout.setVerticalGroup(
            pnlDanhSachChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachChiTietLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrDanhSachChiTiet, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlPhanTrangChiTietLayout = new javax.swing.GroupLayout(pnlPhanTrangChiTiet);
        pnlPhanTrangChiTiet.setLayout(pnlPhanTrangChiTietLayout);
        pnlPhanTrangChiTietLayout.setHorizontalGroup(
            pnlPhanTrangChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlPhanTrangChiTietLayout.setVerticalGroup(
            pnlPhanTrangChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );

        btnScanQRChiTiet.setText("Quét QR");
        btnScanQRChiTiet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnScanQRChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScanQRChiTietActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitLine3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(lblList2, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnScanQRChiTiet)
                        .addGap(18, 18, 18)
                        .addComponent(btnExport)
                        .addGap(18, 18, 18)
                        .addComponent(btnImport)
                        .addGap(18, 18, 18)
                        .addComponent(btnSaveAllQR))
                    .addComponent(pnlDanhSachChiTiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPhanTrangChiTiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnScanQRChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSaveAllQR, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(lblList2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitLine3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDanhSachChiTiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPhanTrangChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblFilter2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblFilter2.setText("Bộ lọc");

        javax.swing.GroupLayout splitLine4Layout = new javax.swing.GroupLayout(splitLine4);
        splitLine4.setLayout(splitLine4Layout);
        splitLine4Layout.setHorizontalGroup(
            splitLine4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        splitLine4Layout.setVerticalGroup(
            splitLine4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        cboDanhMuc2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả danh mục --" }));

        cboThuongHieu2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả thương hiệu --" }));
        cboThuongHieu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboThuongHieu2ActionPerformed(evt);
            }
        });

        cboTrangThai2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả trạng thái --" }));

        cboXuatXu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả xuất xứ --" }));
        cboXuatXu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboXuatXuActionPerformed(evt);
            }
        });

        cboKieuDang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả kiểu dáng --" }));
        cboKieuDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKieuDangActionPerformed(evt);
            }
        });

        cboChatLieu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả chất liệu --" }));

        cboDeGiay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả đế giày --" }));

        cboKichCo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả kích cỡ --" }));
        cboKichCo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKichCoActionPerformed(evt);
            }
        });

        cboMauSac.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Tất cả màu sắc --" }));
        cboMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauSacActionPerformed(evt);
            }
        });

        pnlSearchBox2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pnlSearchBox2KeyPressed(evt);
            }
        });

        btnSearch2.setText("Tìm kiếm");
        btnSearch2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch2ActionPerformed(evt);
            }
        });

        btnClear2.setText("Huỷ lọc");
        btnClear2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClear2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitLine4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFilter2, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboKieuDang, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSearchBox2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboTrangThai2, 0, 143, Short.MAX_VALUE)
                            .addComponent(cboChatLieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboDanhMuc2, 0, 142, Short.MAX_VALUE)
                            .addComponent(cboDeGiay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboThuongHieu2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboKichCo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboXuatXu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboMauSac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblFilter2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(splitLine4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboDanhMuc2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboThuongHieu2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboTrangThai2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlSearchBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboKichCo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout pnlDanhSachSanPhamChiTietLayout = new javax.swing.GroupLayout(pnlDanhSachSanPhamChiTiet);
        pnlDanhSachSanPhamChiTiet.setLayout(pnlDanhSachSanPhamChiTietLayout);
        pnlDanhSachSanPhamChiTietLayout.setHorizontalGroup(
            pnlDanhSachSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlDanhSachSanPhamChiTietLayout.setVerticalGroup(
            pnlDanhSachSanPhamChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachSanPhamChiTietLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
	handleClickRowTable(evt);
    }//GEN-LAST:event_tblDanhSachMouseClicked

    private void btnXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPhamActionPerformed
        // TODO add your handling code here:
        handleClickButtonDelete();
    }//GEN-LAST:event_btnXoaSanPhamActionPerformed

    private void btnThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamActionPerformed
        // TODO add your handling code here:
        handleClickButtonAdd();
    }//GEN-LAST:event_btnThemSanPhamActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        handleClickButtonSearch();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        handleClickButtonClear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void pnlSearchBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pnlSearchBoxKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlSearchBoxKeyPressed

    private void btnScanQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanQRActionPerformed
        // TODO add your handling code here:
        handleClickButtonScanQrCode();
    }//GEN-LAST:event_btnScanQRActionPerformed

    private void btnSaveAllQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAllQRActionPerformed
        // TODO add your handling code here:
	handleClickButtonSaveAllQR();
    }//GEN-LAST:event_btnSaveAllQRActionPerformed

    private void cboThuongHieu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThuongHieu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboThuongHieu2ActionPerformed

    private void cboXuatXuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboXuatXuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboXuatXuActionPerformed

    private void cboKieuDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKieuDangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboKieuDangActionPerformed

    private void cboKichCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKichCoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboKichCoActionPerformed

    private void cboMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauSacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMauSacActionPerformed

    private void pnlSearchBox2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pnlSearchBox2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlSearchBox2KeyPressed

    private void btnClear2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear2ActionPerformed
        // TODO add your handling code here:
	handleClickButtonClearChiTiet();
    }//GEN-LAST:event_btnClear2ActionPerformed

    private void btnSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch2ActionPerformed
        // TODO add your handling code here:
	handleClickButtonSeachChiTiet();
    }//GEN-LAST:event_btnSearch2ActionPerformed

    private void tblDanhSachChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachChiTietMouseClicked
        // TODO add your handling code here:
	handleClickRowTableChiTiet(evt);
    }//GEN-LAST:event_tblDanhSachChiTietMouseClicked

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:
	handleClickXuatExcel();
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnScanQRChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanQRChiTietActionPerformed
        // TODO add your handling code here:
	handleClickButtonScanQrCodeChiTiet();
    }//GEN-LAST:event_btnScanQRChiTietActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
        // TODO add your handling code here:
	handleClickButtonImport();
    }//GEN-LAST:event_btnImportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClear2;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnSaveAllQR;
    private javax.swing.JButton btnScanQR;
    private javax.swing.JButton btnScanQRChiTiet;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearch2;
    private javax.swing.JButton btnThemSanPham;
    private javax.swing.JButton btnXoaSanPham;
    private javax.swing.JComboBox cboChatLieu;
    private javax.swing.JComboBox cboDanhMuc;
    private javax.swing.JComboBox cboDanhMuc2;
    private javax.swing.JComboBox cboDeGiay;
    private javax.swing.JComboBox cboKichCo;
    private javax.swing.JComboBox cboKieuDang;
    private javax.swing.JComboBox cboMauSac;
    private javax.swing.JComboBox cboThuongHieu;
    private javax.swing.JComboBox cboThuongHieu2;
    private javax.swing.JComboBox cboTrangThai;
    private javax.swing.JComboBox cboTrangThai2;
    private javax.swing.JComboBox cboXuatXu;
    private javax.swing.JLabel lblFilter;
    private javax.swing.JLabel lblFilter2;
    private javax.swing.JLabel lblList;
    private javax.swing.JLabel lblList2;
    private com.app.views.UI.panel.RoundPanel pnlContainer;
    private com.app.views.UI.panel.RoundPanel pnlDanhSach;
    private com.app.views.UI.panel.RoundPanel pnlDanhSachChiTiet;
    private javax.swing.JPanel pnlDanhSachSanPham;
    private javax.swing.JPanel pnlDanhSachSanPhamChiTiet;
    private com.app.views.UI.panel.RoundPanel pnlFilter;
    private com.app.views.UI.panel.RoundPanel pnlList;
    private javax.swing.JPanel pnlPhanTrang;
    private javax.swing.JPanel pnlPhanTrangChiTiet;
    private com.app.views.UI.panel.SearchBox pnlSearchBox;
    private com.app.views.UI.panel.SearchBox pnlSearchBox2;
    private com.app.views.UI.panel.RoundPanel roundPanel1;
    private com.app.views.UI.panel.RoundPanel roundPanel2;
    private javax.swing.JScrollPane scrDanhSach;
    private javax.swing.JScrollPane scrDanhSachChiTiet;
    private com.app.views.UI.label.SplitLine splitLine1;
    private com.app.views.UI.label.SplitLine splitLine2;
    private com.app.views.UI.label.SplitLine splitLine3;
    private com.app.views.UI.label.SplitLine splitLine4;
    private javax.swing.JTable tblDanhSach;
    private javax.swing.JTable tblDanhSachChiTiet;
    private javax.swing.JTabbedPane tbpTab;
    // End of variables declaration//GEN-END:variables

    private void handleClickButtonDelete() {
        List<Integer> ids = findSelectedSanPhamIds(tblDanhSach);
        if (ids.isEmpty()) { 
            MessageModal.error("Vui lòng chọn ít nhất một sản phẩm muốn xoá!!!");
            return;
        }
        
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            if (MessageModal.confirmError("Xoá sản phẩm đã chọn ", "Bạn thực sự muốn xoá những sản phẩm này?")) {
                executorService.submit(() -> {
                    try {
                        sanPhamService.deleteAll(ids);
                        loading.dispose();
                        loadDataPage();
                        MessageToast.success("Xoá thành công " + ids.size() + " sản phẩm đã chọn");
                    } catch (ServiceResponseException e) {
                        loading.dispose();
                        MessageToast.error(e.getMessage());
                    } catch (Exception e) {
                        loading.dispose();
                        MessageModal.error(ErrorConstant.DEFAULT_ERROR);
                    }  
                });
                loading.setVisible(true);
            }
        });

    }
    
    private List<Integer> findSelectedSanPhamIds(JTable table) {
        List<Integer> ids = new ArrayList<>();
        for (int row = 0; row < table.getRowCount(); row++) {
            Boolean value = (Boolean) table.getValueAt(row, 0);
            if (Boolean.TRUE.equals(value)) {
                ids.add(dataItems.get(row).getId());
            }
        }
        return ids;
    }

    private List<InuhaSanPhamChiTietModel> findSelectedSanPhamChiTietIds(JTable table) {
        List<InuhaSanPhamChiTietModel> rows = new ArrayList<>();
        for (int row = 0; row < table.getRowCount(); row++) {
            Boolean value = (Boolean) table.getValueAt(row, 0);
            if (Boolean.TRUE.equals(value)) {
                rows.add(dataItemsSPCT.get(row));
            }
        }
        return rows;
    }
	
    private void handleClickButtonAdd() {
        ModalDialog.showModal(this, new SimpleModalBorder(new InuhaAddSanPhamView(), "Thêm sản phẩm"));
    }

    private void handleClickButtonSearch() {
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            loadDataPage();
            loading.dispose();
        });
        loading.setVisible(true);
    }

    private void handleClickButtonSeachChiTiet() {
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            loadDataPageSPCT();
            loading.dispose();
        });
        loading.setVisible(true);
    }

    private void handleClickButtonClearChiTiet() {
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            txtTuKhoa2.setText(null);
            cboDanhMuc2.setSelectedIndex(0);
            cboThuongHieu2.setSelectedIndex(0);
            cboTrangThai2.setSelectedIndex(0);
	    cboXuatXu.setSelectedIndex(0);
	    cboKieuDang.setSelectedIndex(0);
	    cboChatLieu.setSelectedIndex(0);
	    cboDeGiay.setSelectedIndex(0);
	    cboKichCo.setSelectedIndex(0);
	    cboMauSac.setSelectedIndex(0);
            loadDataPageSPCT();
            loading.dispose();
        });
        loading.setVisible(true);
    }
    
    private void handleClickButtonClear() {
        LoadingDialog loading = new LoadingDialog();
        executorService.submit(() -> {
            txtTuKhoa.setText(null);
            cboDanhMuc.setSelectedIndex(0);
            cboThuongHieu.setSelectedIndex(0);
            cboTrangThai.setSelectedIndex(0);
            loadDataPage();
            loading.dispose();
        });
        loading.setVisible(true);
    }

    private void handleClickButtonScanQrCode() {
        QrCodeHelper.showWebcam("Tìm kiếm sản phẩm bằng QR", result -> {

            LoadingDialog loading = new LoadingDialog();
            executorService.submit(() -> {
                try {
                    String code = result.getText();

                    InuhaSanPhamModel sanPhamModel = null;
                    int id;
                    if ((id = QrCodeUtils.getIdSanPham(code)) > 0) {
                        sanPhamModel = sanPhamService.getById(id);
                    } else if((id = QrCodeUtils.getIdSanPhamChiTiet(code)) > 0) { 
			InuhaSanPhamChiTietService sanPhamChiTietService = new InuhaSanPhamChiTietService();
			InuhaSanPhamChiTietModel sanPhamChiTietModel = sanPhamChiTietService.getById(id);
			sanPhamModel = sanPhamChiTietModel.getSanPham();
                    } else { 
                        loading.dispose();
                        MessageToast.error("QRCode không hợp lệ!!!");
                        return;
                    }
                    loading.dispose();
                    showEditSanPham(sanPhamModel);                
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

    private void showEditSanPham(InuhaSanPhamModel item) {
	ModalDialog.closeAllModal();
        ModalDialog.showModal(instance, new SimpleModalBorder(new InuhaAddSanPhamView(item), "Chỉnh sửa sản phẩm"));
    }
    
    private void showDetailSanPham(InuhaSanPhamModel item) {
	ModalDialog.closeAllModal();
        ModalDialog.showModal(instance, new SimpleModalBorder(new InuhaDetailSanPhamView(item), null));
    }

    private void showDetailChiTiet(InuhaSanPhamChiTietModel item) {
        ModalDialog.showModal(instance, new SimpleModalBorder(new InuhaDetailSanPhamChiTietView(item), null), ID_MODAL_DEAIL);
    }
    
    private void handleClickRowTable(MouseEvent evt) {
	if (evt.getClickCount() > 1) { 
	    InuhaSanPhamModel sanPhamModel = dataItems.get(tblDanhSach.getSelectedRow());
	    showDetailSanPham(sanPhamModel);
	    return;
	}
	
        List<Integer> columns = List.of(0, 10);
        if (SwingUtilities.isLeftMouseButton(evt)) { 
            int row = tblDanhSach.rowAtPoint(evt.getPoint());
            int col = tblDanhSach.columnAtPoint(evt.getPoint());
            boolean isSelected = (boolean) tblDanhSach.getValueAt(row, 0);
            if (!columns.contains(col)) { 
                tblDanhSach.setValueAt(!isSelected, row, 0);
            }
        }
    }
    
    private void handleClickRowTableChiTiet(MouseEvent evt) {
	if (evt.getClickCount() > 1) { 
	    InuhaSanPhamChiTietModel sanPhamChiTietModel = dataItemsSPCT.get(tblDanhSachChiTiet.getSelectedRow());
	    showDetailChiTiet(sanPhamChiTietModel);
	    return;
	}
	
        List<Integer> columns = List.of(0);
        if (SwingUtilities.isLeftMouseButton(evt)) { 
            int row = tblDanhSachChiTiet.rowAtPoint(evt.getPoint());
            int col = tblDanhSachChiTiet.columnAtPoint(evt.getPoint());
            boolean isSelected = (boolean) tblDanhSachChiTiet.getValueAt(row, 0);
            if (!columns.contains(col)) { 
                tblDanhSachChiTiet.setValueAt(!isSelected, row, 0);
            }
        }
    }

    private void handleClickXuatExcel() {
	String fileName = "SanPhamChiTiet-" + TimeUtils.now("dd_MM_yyyy__hh_mm_a");
	String[] headers = new String[] {
	    "STT",
	    "Mã sản phẩm",
	    "Mã chi tiết",
	    "Tên sản phẩm",
	    "Danh mục",
	    "Thương hiệu",
	    "Xuất xứ",
	    "Kiểu dáng",
	    "Chất liệu",
	    "Đế giày",
	    "Kích cỡ",
	    "Màu sắc",
	    "Giá nhập",
	    "Giá bán",
	    "Số lượng",
	    "Trạng thái"
	};

	LoadingDialog loading = new LoadingDialog();
	executorService.submit(() -> { 
	    List<InuhaSanPhamChiTietModel> items = findSelectedSanPhamChiTietIds(tblDanhSachChiTiet);
	    try {
		if (items.isEmpty()) { 
		    items = sanPhamChiTietService.getAll();
		}
		
		List<String[]> rows = new ArrayList<>();
		int i = 1;
		for(InuhaSanPhamChiTietModel item: items) { 
		    rows.add(new String[] { 
			String.valueOf(i++),
			item.getSanPham().getMa(),
			item.getMa(),
			item.getSanPham().getTen(),
			item.getSanPham().getDanhMuc().getTen(),
			item.getSanPham().getThuongHieu().getTen(),
			item.getSanPham().getXuatXu().getTen(),
			item.getSanPham().getKieuDang().getTen(),
			item.getSanPham().getChatLieu().getTen(),
			item.getSanPham().getDeGiay().getTen(),
			item.getKichCo().getTen(),
			item.getMauSac().getTen(),
			CurrencyUtils.parseString(item.getSanPham().getGiaNhap()),
			CurrencyUtils.parseString(item.getSanPham().getGiaBan()),
			CurrencyUtils.parseTextField(item.getSoLuong()),
			ProductUtils.getTrangThai(item.getTrangThai())
		    });
		}
		
		loading.dispose();
		
		ExcelHelper.writeFile(fileName, headers, rows);
	    } catch (ServiceResponseException e) {
		e.printStackTrace();
		loading.dispose();
		MessageModal.error(e.getMessage());
	    } catch (Exception e) {
		e.printStackTrace();
		loading.dispose();
		MessageModal.error(ErrorConstant.DEFAULT_ERROR);
	    }
	});
	loading.setVisible(true);
    }

    private void handleClickButtonSaveAllQR() {
        JnaFileChooser ch = new JnaFileChooser();
        ch.setMode(JnaFileChooser.Mode.Directories);
        boolean act = ch.showOpenDialog(Application.app);
        if (act) {
            File folder = ch.getSelectedFile();
            File dir = new File(folder, "SanPhamChiTiet-QRCode-" + TimeUtils.now("dd_MM_yyyy__hh_mm_a"));
	    boolean checkExists = dir.isDirectory();

	    if (!checkExists) {
		dir.mkdirs();
	    }
	    
	    LoadingDialog loading = new LoadingDialog();
	    executorService.submit(() -> { 
		List<InuhaSanPhamChiTietModel> items = findSelectedSanPhamChiTietIds(tblDanhSachChiTiet);
		try {
		    if (items.isEmpty()) { 
			items = sanPhamChiTietService.getAll();
		    }

		    int results = 0;
		    for(InuhaSanPhamChiTietModel item: items) { 
			try {
			    File fileName = new File(dir, item.getMa() + "." + QrCodeUtils.IMAGE_FORMAT.toLowerCase());
			    QrCodeUtils.generateQRCodeImage(QrCodeUtils.generateCodeSanPhamChiTiet(item.getId()), fileName);
			    results++;
			} catch (WriterException | IOException e) {
			    e.printStackTrace();
			}
		    }
		    MessageToast.success("Lưu danh sách QR Code thành công!!!");
		} catch (ServiceResponseException e) {
		    e.printStackTrace();
		    MessageModal.error(e.getMessage());
		} catch (Exception e) {
		    e.printStackTrace();
		    MessageModal.error(ErrorConstant.DEFAULT_ERROR);
		} finally {
		    loading.dispose();
		}
	    });
	    loading.setVisible(true);
        }
    }

    private void handleClickButtonScanQrCodeChiTiet() {
	QrCodeHelper.showWebcam("Tìm kiếm sản phẩm chi tiết", (result) -> { 
	    String code = result.getText();
	    LoadingDialog loading = new LoadingDialog();
	    executorService.submit(() -> { 
		try {
		    int id = QrCodeUtils.getIdSanPhamChiTiet(code);
		    if (id > 0) { 
			InuhaSanPhamChiTietModel model = sanPhamChiTietService.getById(id);
			showDetailChiTiet(model);
		    } else { 
			MessageToast.error("Mã QR không hợp lệ!!!");
		    }
		} catch (ServiceResponseException e) { 
		    e.printStackTrace();
		    MessageToast.error(e.getMessage());
		} catch (Exception e) { 
		    e.printStackTrace();
		    MessageToast.error(ErrorConstant.DEFAULT_ERROR);
		} finally { 
		    loading.dispose();
		}
	    });
	    loading.setVisible(true);
	});
    }

    private void handleClickButtonImport() {
	File fileExcel = ExcelHelper.selectFile();
	if (fileExcel == null) {
	    return;
	}
	
	LoadingDialog loading = new LoadingDialog();
	executorService.submit(() -> { 
	    if (MessageModal.confirmWarning("Cảnh báo", "Dữ liệu chưa tồn tại thì sẽ được thêm vào còn dữ liệu đã tồn tại sẽ được cập nhật?")) { 
		executorService.submit(() -> {
		    List<String[]> rows = new ArrayList<>();
		    try {
			rows = ExcelHelper.readFile(fileExcel, false);
		    } catch (Exception e) { 
			e.printStackTrace();
			loading.dispose();
		    }
		    int results = 0;

		    List<String> keywords = Arrays.asList("Mã sản phẩm", "Mã chi tiết", "Kích cỡ", "Màu sắc", "Số lượng", "Trạng thái");
		    String[] headers = rows.get(0);
		    Map<String, Integer> keywordPositions = new HashMap<>();
		    for (int i = 0; i < headers.length; i++) {
			keywordPositions.put(headers[i], i);
		    }

		    List<Integer> positions = keywords.stream()
			.map(keyword -> keywordPositions.getOrDefault(keyword, -1))
			.collect(Collectors.toList());

		    int posSanPham = positions.get(0);
		    int posChiTiet = positions.get(1);
		    int posKichCo = positions.get(2);
		    int posMauSac = positions.get(3);
		    int posSoLuong = positions.get(4);
		    int posTrangThai = positions.get(5);

		    for(String[] row : rows) { 
			try {
			    String maSanPham = posSanPham != -1 && posSanPham < row.length ? row[posSanPham] : null;
			    String maChiTiet = posChiTiet != -1 && posChiTiet < row.length ? row[posChiTiet] : null;
			    String tenKichCo = posKichCo != -1 && posKichCo < row.length ? row[posKichCo] : null;
			    String tenMauSac = posMauSac != -1 && posMauSac < row.length ? row[posMauSac] : null;
			    Integer soLuong = posSoLuong != -1 && posSoLuong < row.length ? (int) CurrencyUtils.parseNumber(row[posSoLuong]) : null;
			    Boolean trangThai = posTrangThai != -1 && posTrangThai < row.length ? row[posTrangThai].equalsIgnoreCase("Đang bán") : null;

			    if ((maSanPham == null && maChiTiet == null) || tenKichCo == null || tenMauSac == null || soLuong == null || trangThai == null) { 
				continue;
			    }

			    InuhaKichCoModel kichCo = kichCoService.insertByExcel(tenKichCo);
			    InuhaMauSacModel mauSac = mauSacService.insertByExcel(tenMauSac);

			    InuhaSanPhamModel sanPham = new InuhaSanPhamModel();
			    sanPham.setMa(maSanPham);

			    InuhaSanPhamChiTietModel sanPhamChiTiet = new InuhaSanPhamChiTietModel();
			    sanPhamChiTiet.setMa(maChiTiet);
			    sanPhamChiTiet.setSoLuong(soLuong);
			    sanPhamChiTiet.setTrangThai(trangThai);
			    sanPhamChiTiet.setKichCo(kichCo);
			    sanPhamChiTiet.setMauSac(mauSac);
			    sanPhamChiTiet.setSanPham(sanPham);

			    if (sanPhamChiTietService.insertByExcel(sanPhamChiTiet)) {
				results++;
			    }
			} catch (Exception e) { 
			}
		    }


		    if (results > 0) { 
			MessageToast.success(results + " hàng dữ liệu chịu tác động");
			loadDataPageSPCT(1);
		    } else {
			MessageToast.warning("Không có hàng dữ liệu nào chịu tác động");
		    }
		    loading.dispose();
		});
		loading.setVisible(true);
	    }
	    
	});
    }




}
