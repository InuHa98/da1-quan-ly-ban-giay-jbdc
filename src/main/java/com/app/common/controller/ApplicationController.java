package com.app.common.controller;

import com.app.Application;
import com.app.views.UI.dialog.LoadingDialog;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class ApplicationController {

    private Application context;

    private static ApplicationController instance;

    private ApplicationController() {
    }

    public static ApplicationController getInstance() {
        if (instance == null) {
            instance = new ApplicationController();
        }
        return instance;
    }

    public Application getContext() {
        return context;
    }

    public void setContext(Application context) {
        this.context = context;
    }

    public void show(JComponent component) {
        if (this.context == null) {
            System.out.println("Context not found");
            return;
        }

        //EventQueue.invokeLater(() -> {
            //FlatAnimatedLafChange.showSnapshot();
            context.setContentPane(component);
            context.revalidate();
            context.repaint();
	        context.pack();
            //FlatAnimatedLafChange.hideSnapshotWithAnimation();
        //});
    }

}
