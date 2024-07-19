/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.utils;

import com.app.common.helper.MailerHelper;
import com.app.common.infrastructure.session.AvatarUpload;
import com.app.common.infrastructure.session.SessionLogin;
import com.app.core.inuha.models.InuhaTaiKhoanModel;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author inuHa
 */
public class SessionUtils {

    private static final String AVATAR_NAME_FORMAT = "user_%d";

    public static final int MAX_WIDTH_AVATAR_UPLOAD = 184;

    public static final int MAX_HEIGHT_AVATAR_UPLOAD = 184;

    public static String generateCode(int min, int max) {
        return Integer.toString((int) ((Math.random() * (max - min)) + min));
    }

    public static boolean sendOtp(String otp, String email) {
        String htmlContent = "";
        try {
            InputStream inputStream = ResourceUtils.getDataFile("templates/mail/forgot-password.html");
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                htmlContent = stringBuilder.toString();
                inputStream.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        htmlContent = htmlContent.replaceAll("\\{\\{CODE\\}\\}", otp);

        MailerHelper mailerHelper = new MailerHelper();

        return mailerHelper.send(email, "Quên mật khẩu", htmlContent);
    }

    public static AvatarUpload uploadAvatar(InuhaTaiKhoanModel user, String pathImage) {
        ImageIcon resizeImage = ComponentUtils.resizeImage(new ImageIcon(pathImage), MAX_WIDTH_AVATAR_UPLOAD, MAX_HEIGHT_AVATAR_UPLOAD);
        String fileName = StorageUtils.uploadAvatar(resizeImage, String.format(AVATAR_NAME_FORMAT, user.getId()));
        return new AvatarUpload(resizeImage, fileName);
    }

    public static ImageIcon getAvatar(InuhaTaiKhoanModel user) {
        String avatar = user.getAvatar();

        if (avatar != null && !avatar.trim().isEmpty()) {
            try {
                return StorageUtils.getAvatar(user.getAvatar());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResourceUtils.getImageAssets("images/noavatar.png");
    }

    public static boolean isManager() {
        return SessionLogin.getInstance().getData().isAdmin();
    }

    public static boolean isStaff() {
        return !SessionLogin.getInstance().getData().isAdmin();
    }

}
