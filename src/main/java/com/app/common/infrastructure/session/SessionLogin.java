package com.app.common.infrastructure.session;

import com.app.common.controller.ApplicationController;
import com.app.common.helper.MessageModal;
import com.app.common.infrastructure.exceptions.ServiceResponseException;
import com.app.models.INhanVienDTO;
import com.app.services.NhanVienService;
import com.app.utils.ContextUtils;
import com.app.views.common.LoginView;
import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SessionLogin {

    private static SessionLogin instance;

    private NhanVienService nhanVienService = ContextUtils.getBean(NhanVienService.class);

    private String username = null;

    private String password = null;

    @Getter
    private INhanVienDTO data = null;

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

    public void create(String username, String password, INhanVienDTO data) {
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
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            if (MessageModal.confirmInfo("Bạn thực sự muốn đăng xuất?")) {
                clear();
                ApplicationController.getInstance().show(new LoginView());
            }
        });
        executorService.shutdown();
    }

}
