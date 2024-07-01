package com.app.views.components.sidebarmenu;

import com.app.Application;
import com.app.common.controller.DashboardController;
import com.app.common.helper.MessageModal;
import com.app.common.helper.MessageToast;
import com.app.common.infrastructure.constants.ErrorConstant;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.common.infrastructure.router.NhanVienRoute;
import com.app.common.infrastructure.router.QuanLyRoute;
import com.app.common.infrastructure.session.AvatarUpload;
import com.app.common.infrastructure.session.SessionLogin;
import com.app.models.responseDTO.INhanVienDTO;
import com.app.services.NhanVienService;
import com.app.utils.*;
import com.app.views.UI.ImageRound;
import com.app.views.UI.dialog.LoadingDialog;
import com.app.views.UI.scroll.ScrollBarCustom;
import com.app.views.common.DashboardView;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.Animator;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SidebarMenu extends JPanel {

    private final NhanVienService nhanVienService = ContextUtils.getBean(NhanVienService.class);

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private ImageRound lbAvatar;

    private JLabel lbUsername;

    private JLabel lbEmail;

    private JLabel lbRole;

    private JPanel plMenu;

    private SidebarMenuButton selectedMenu;

    private SidebarMenuButton unSelectedMenu;

    private Animator animator;

    private ISidebarMenuEvent menuEvent;

    private final List<SidebarMenuItem> itemsMenu = SessionUtils.isManager() ? QuanLyRoute.getInstance().getItemSideMenu() : NhanVienRoute.getInstance().getItemSideMenu();

    public SidebarMenu() {
        initComponents();
        this.initMenu((index) -> {

            Optional<SidebarMenuItem> item = itemsMenu.stream().filter(o -> o.getIndex() == index).findFirst();
            if (item.isEmpty()) {
                throw new RuntimeException("Không tìm thấy SidebarMenuItem");
            }

            DashboardController.getInstance().show(item.get().getComponent());
        });

        int maxLengthText = 23;

        INhanVienDTO authUser = SessionLogin.getInstance().getData();

        String username = ComponentUtils.hiddenText(authUser.getUsername(), maxLengthText);
        String email = ComponentUtils.hiddenText(authUser.getEmail(), maxLengthText);

        lbUsername.setText(username);
        lbEmail.setText(email);
        lbRole.setText(SessionUtils.isManager() ? "Quản lý" : "Nhân viên");
    }

    private void initComponents() {
        setLayout(new MigLayout("wrap 1, insets 10 0 10 10, fill", "fill, 240:240", "[grow,fill]"));
        setOpaque(false);

        JPanel infoPanel = new JPanel(new MigLayout("wrap 1"));
        infoPanel.setOpaque(false);

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportBorder(null);
        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setVerticalScrollBar(new ScrollBarCustom());

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
    }

    private void handleChangeAvatar(MouseEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            LoadingDialog loading = new LoadingDialog(DashboardView.context);

            executor.submit(() -> {
                if (MessageModal.confirmInfo("Cập nhật ảnh đại diện mới?")) {

                    ExecutorService executorUpload = Executors.newSingleThreadExecutor();

                    executorUpload.submit(() -> {

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

                            SwingUtilities.invokeLater(() -> {
                                lbAvatar.setImage(avatarUpload.getDataImage());
                                MessageToast.success("Cập nhật ảnh đại diện thành công.");
                            });
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
        menuButton.setText("   " + text);
        menuButton.addActionListener((e) -> {
            if (!animator.isRunning()) {
                if (menuButton != selectedMenu) {
                    if (menuButton.getCallback() != null) {
                        menuButton.getCallback().onClick(menuButton);
                    } else {
                        unSelectedMenu = selectedMenu;
                        selectedMenu = menuButton;
                        animator.start();
                        menuEvent.menuSelected(menuButton.getIndex());
                    }
                }
            }
        });

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
