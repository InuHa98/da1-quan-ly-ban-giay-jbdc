package com.app;

import com.app.common.controller.ApplicationController;
import com.app.common.helper.Pagination;
import com.app.common.helper.TestConnection;
import com.app.utils.ContextUtils;
import com.app.utils.ResourceUtils;
import com.app.views.UI.dialog.ModalDialog;
import com.app.views.guest.ChangePasswordView;
import com.app.views.guest.LoginView;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import io.github.cdimascio.dotenv.Dotenv;
import raven.popup.GlassPanePopup;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Application extends JFrame {

    public static Application app;

    private final static int MIN_WIDTH = 1400;

    private final static int MIN_HEIGHT = 800;
    
    public static void main(String[] args) {

        Dotenv.configure().systemProperties().load();

        SwingUtilities.invokeLater(() -> {
            try {
                FlatRobotoFont.install();
                FlatLaf.registerCustomDefaultsSource("themes");
                UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
                UIManager.setLookAndFeel(new FlatMacDarkLaf());
            } catch (UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }

            ContextUtils.getBean(TestConnection.class);
	
            ApplicationController.getInstance().setContext(new Application());
            ApplicationController.getInstance().show(new LoginView());
	    
            app = ApplicationController.getInstance().getContext();
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            app.setTitle(System.getProperty("APP_NAME"));
            app.setIconImage(ResourceUtils.getImageAssets("/icons/logo.png").getImage());
            app.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
            app.setLocationRelativeTo(null);
            app.setVisible(true);

            GlassPanePopup.install(app);
            Notifications.getInstance().setJFrame(app);

        });

    }

}
