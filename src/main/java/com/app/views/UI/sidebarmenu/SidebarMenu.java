package com.app.views.UI.sidebarmenu;

import com.app.common.controller.DashboardController;
import com.app.common.helper.MessageModal;
import com.app.common.helper.MessageToast;
import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.common.infrastructure.router.NhanVienRoute;
import com.app.common.infrastructure.router.QuanLyRoute;
import com.app.common.infrastructure.session.AvatarUpload;
import com.app.common.infrastructure.session.SessionLogin;
import com.app.core.inuha.models.InuhaTaiKhoanModel;
import com.app.core.inuha.services.InuhaTaiKhoanService;
import com.app.utils.*;
import com.app.views.UI.ImageRound;
import com.app.views.UI.dialog.LoadingDialog;
import com.app.views.UI.scroll.ScrollBarCustomUI;
import com.app.views.DashboardView;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.Animator;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jnafilechooser.api.JnaFileChooser;

/**
 *
 * @author InuHa
 */
public class SidebarMenu extends JPanel {
    
    private final static int MIN_WIDTH = 80;
    
    private final static int MAX_WIDTH = 240;

    private final InuhaTaiKhoanService nhanVienService = new InuhaTaiKhoanService();

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    private static SidebarMenu instance;
    
    private ImageRound lbAvatar;

    private JLabel lbUsername;

    private JLabel lbEmail;

    private JLabel lbRole;

    private JPanel plMenu;

    private SidebarMenuButton selectedMenu;

    private SidebarMenuButton unSelectedMenu;

    private Animator animator;

    private ISidebarMenuEvent menuEvent;

    private MouseAdapter eventHoverMenu;
    
    private final List<SidebarMenuItem> itemsMenu = SessionUtils.isManager() ? QuanLyRoute.getInstance().getItemSideMenu() : NhanVienRoute.getInstance().getItemSideMenu();

    public SidebarMenu() {
	instance = this;
        initComponents();
	ISidebarMenuEvent event = (index) -> {

            Optional<SidebarMenuItem> item = itemsMenu.stream().filter(o -> o.getIndex() == index).findFirst();
            if (item.isEmpty()) {
                return;
            }

	    String className = item.get().getPackageComponent();
	    
	    if (className == null || className.isEmpty()) {
		System.err.println("Không tìm thấy route");
		return;
	    }
	    
            try {
                Class<?> loadClass = Class.forName(className);
                DashboardController.getInstance().show((JComponent) loadClass.getDeclaredConstructor().newInstance());
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                InvocationTargetException e) {
                throw new RuntimeException(e);
            }

        };
	
        this.initMenu(event);

        int maxLengthText = 23;

        InuhaTaiKhoanModel authUser = SessionLogin.getInstance().getData();

        String username = authUser != null ? ComponentUtils.hiddenText(authUser.getUsername(), maxLengthText) : null;
        String email = authUser != null ? ComponentUtils.hiddenText(authUser.getEmail(), maxLengthText) : null;

        lbUsername.setText(username);
        lbEmail.setText(email);
        lbRole.setText(SessionUtils.isManager() ? "Quản lý" : "Nhân viên");
	
	setMaximumSize(new Dimension(MIN_WIDTH, getPreferredSize().height));
    }

    private void initComponents() {
        setLayout(new MigLayout("wrap 1, insets 10 0 10 10, fill", "fill, " + MAX_WIDTH + ":" + MAX_WIDTH, "[grow,fill]"));
        setOpaque(false);

        JPanel infoPanel = new JPanel(new MigLayout("wrap 1"));
        infoPanel.setOpaque(false);

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportBorder(null);
        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUI(new ScrollBarCustomUI());

        plMenu = new JPanel(new MigLayout("wrap 1, insets 0 0 10 10, fill", "fill"));
        plMenu.setOpaque(false);

        scroll.setViewportView(plMenu);

        lbAvatar = new ImageRound();
        lbUsername = new JLabel();
        lbEmail = new JLabel();
        lbRole = new JLabel();

        lbUsername.setForeground(ColorUtils.PRIMARY_COLOR);

        lbEmail.setForeground(ColorUtils.PRIMARY_TEXT);

        Dimension avatarSize = new Dimension(64, 64);
        lbAvatar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbAvatar.setPreferredSize(avatarSize);
        lbAvatar.setMinimumSize(avatarSize);
        lbAvatar.setBorderSize(2);
        lbAvatar.setBorderSpace(2);
        lbAvatar.setImage(SessionUtils.getAvatar(SessionLogin.getInstance().getData()));
        lbAvatar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleChangeAvatar(e);
            }
        });

        lbUsername.putClientProperty(FlatClientProperties.STYLE, "font:bold +5");
        infoPanel.add(lbAvatar, "dock west, gapx 10 10");
        infoPanel.add(lbUsername);
        infoPanel.add(lbEmail);
        infoPanel.add(lbRole);

        this.add(infoPanel, "gapy 20 20");
        this.add(scroll);
	
	eventHoverMenu = new MouseAdapter() {
            private int i = MIN_WIDTH;
            private Dimension d = new Dimension();
            private java.util.Timer tm1;
            private java.util.Timer tm2;

            @Override
            public void mouseEntered(MouseEvent e) {
                if (tm2 != null) tm2.cancel();
                if (i < MAX_WIDTH) {
                    tm1 = new java.util.Timer();
                    tm1.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            d.setSize(i++, instance.getHeight());
			    instance.setMaximumSize(d);
                            instance.setPreferredSize(d);
                            instance.revalidate();
                            if (i >= MAX_WIDTH) tm1.cancel();
                        }
                    }, 0, 1);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (tm1 != null) tm1.cancel();
                tm2 = new java.util.Timer();
                tm2.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        d.setSize(i--, instance.getHeight());
			instance.setMaximumSize(d);
                        instance.setPreferredSize(d);
                        instance.revalidate();
                        if (i <= MIN_WIDTH) tm2.cancel();
                    }
                }, 0, 1);
            }
        };

        addMouseListener(eventHoverMenu); 
	
	infoPanel.addMouseListener(eventHoverMenu); 
	lbAvatar.addMouseListener(eventHoverMenu); 
	plMenu.addMouseListener(eventHoverMenu); 
    }
    

    private void handleChangeAvatar(MouseEvent event) {
        JnaFileChooser ch = new JnaFileChooser();
        ch.addFilter("Hình ảnh", "png", "jpg", "jpeg");
        boolean act = ch.showOpenDialog(SwingUtilities.getWindowAncestor(this));
        if (act) {
            File selectedFile = ch.getSelectedFile();

            LoadingDialog loading = new LoadingDialog();

            executor.submit(() -> {
                if (MessageModal.confirmInfo("Cập nhật ảnh đại diện mới?")) {

                    executor.submit(() -> {

                        AvatarUpload avatarUpload = SessionUtils.uploadAvatar(SessionLogin.getInstance().getData(), selectedFile.getAbsolutePath());
                        MessageToast.clearAll();

                        if (avatarUpload.getFileName() == null) {
                            loading.dispose();
                            MessageToast.error("Không thể upload hình ảnh!");
                            return;
                        }

                        try {
                            nhanVienService.changeAvatar(avatarUpload.getFileName());
                            loading.dispose();

                            lbAvatar.setImage(avatarUpload.getDataImage());
                            MessageToast.success("Cập nhật ảnh đại diện thành công.");

                        } catch (ServiceResponseException e) {
                            loading.dispose();
                            MessageToast.error(e.getMessage());
                        } catch (Exception e) {
                            loading.dispose();
                            MessageToast.error(ErrorConstant.DEFAULT_ERROR);
                        }
                    });

                    loading.setVisible(true);
		    
                }
            });




        }
    }

    private void initMenu(ISidebarMenuEvent event) {
        menuEvent = event;
        animator = new Animator(3000, (fraction) -> {
            selectedMenu.setAnimate(fraction);
            if (unSelectedMenu != null) {
                unSelectedMenu.setAnimate(1f - fraction);
            }
        });
        animator.setDuration(200);
        animator.setResolution(1);

        for (SidebarMenuItem item: itemsMenu) {
            this.addMenu(item.getIndex(), item.getIcon(), item.getLabel(), item.getCallback());
        }
    }

    private void addMenu(int index, String icon, String text, ISidebarMenuButtonCallback callback) {
        SidebarMenuButton menuButton = new SidebarMenuButton(index, callback);
        setFont(menuButton.getFont().deriveFont(Font.PLAIN, 14));
        menuButton.setIcon(ComponentUtils.resizeImage(ResourceUtils.getImageAssets("sidemenu/" + icon + ".png"), 24, 24));
        menuButton.setText("        " + text);
        menuButton.addActionListener((e) -> {
            if (!animator.isRunning()) {
                if (menuButton != selectedMenu) {
                    if (menuButton.getCallback() != null) {
                        menuButton.getCallback().onClick(menuButton);
                    } else {
                        unSelectedMenu = selectedMenu;
                        selectedMenu = menuButton;
                        animator.start();
			LoadingDialog loading = new LoadingDialog();
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			executorService.submit(() -> {
			    menuEvent.menuSelected(menuButton.getIndex());
			    loading.dispose();
			    executorService.shutdown();
			});
                        loading.setVisible(true);
                    }
                }
            }
        });
	menuButton.addMouseListener(eventHoverMenu); 
        plMenu.add(menuButton);
    }

    public void setSelected(int index) {
        for(Component c: plMenu.getComponents()) {
            if (!(c instanceof SidebarMenuButton menu)) {
                continue;
            }
            if (menu.getIndex() == index) {
                if (menu != selectedMenu) {
                    unSelectedMenu = selectedMenu;
                    selectedMenu = menu;
                    animator.start();
                    menuEvent.menuSelected(index);
                }
                break;
            }
        }
    }

}
