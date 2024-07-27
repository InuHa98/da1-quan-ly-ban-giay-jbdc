package com.app.common.infrastructure.session;

import com.app.Application;
import com.app.common.controller.ApplicationController;
import com.app.common.helper.MessageModal;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.core.inuha.models.InuhaTaiKhoanModel;
import com.app.core.inuha.services.InuhaTaiKhoanService;
import com.app.core.inuha.views.all.InuhaChangePasswordView;
import com.app.core.inuha.views.guest.LoginView;
import com.app.views.UI.dialog.LoadingDialog;
import lombok.Getter;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;

/**
 *
 * @author InuHa
 */
public class SessionLogin {

    private static SessionLogin instance;

    private final InuhaTaiKhoanService nhanVienService = new InuhaTaiKhoanService();

    private String username = null;

    private String password = null;

    @Getter
    private InuhaTaiKhoanModel data = null;

    private SessionLogin() {
    }

    public static SessionLogin getInstance() {
        if (instance == null) {
            instance = new SessionLogin();
        }
        return instance;
    }

    public boolean isLogin() {
        return username != null && password != null;
    }

    public SessionLogin validSession() {
        if (!isLogin()) {
            return this;
        }

        try {
            this.data = nhanVienService.login(username, password);
        } catch (ServiceResponseException e) {
            clear();
            MessageModal.closeAll();
            if (MessageModal.confirmError("Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại")) {
                ApplicationController.getInstance().show(new LoginView());
            }
        }
        return this;
    }

    public void create(String username, String password, InuhaTaiKhoanModel data) {
        this.username = username;
        this.password = password;
        this.data = data;
    }

    public void clear() {
        this.username = null;
        this.password = null;
        this.data = null;
    }

    public void logout() {
	LoadingDialog loading = new LoadingDialog();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            if (MessageModal.confirmInfo("Bạn thực sự muốn đăng xuất?")) {
		executorService.submit(() -> {
		    clear();
		    ApplicationController.getInstance().show(new LoginView());
		    loading.dispose();
		});
		loading.setVisible(true);
		executorService.shutdown();
            }
        });
    }

    public void changePassword() {
        ModalDialog.showModal(Application.app, new SimpleModalBorder(new InuhaChangePasswordView(), "Thay đổi mật khẩu"));
    }
}
