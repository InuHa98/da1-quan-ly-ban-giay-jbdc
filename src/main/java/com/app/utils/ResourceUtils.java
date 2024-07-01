/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.utils;

import java.io.InputStream;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author inuHa
 */
public class ResourceUtils {

    public static final String DIR_ASSETS = "assets";

    /**
     * Lấy ra tệp tin trong thư mục resources
     * @param path địa chỉ tệp trong thư mục resources
     * @return trả về InputStream của tệp
     * @throws NullPointerException nếu path null
     */
    public static InputStream getDataFile(String path) throws NullPointerException {
        return ResourceUtils.class.getClassLoader().getResourceAsStream(path);
    }

    /**
     * Lấy ra địa chỉ URL của tệp tin trong thư mục resources
     * @param path đường dẫn tệp tin (VD: common/images/logo.png)
     * @return java.net.URL
     */
    public static URL getUrlFile(String path) {
        path = path.replaceAll("^/+", "").replaceAll("/+$", "");
        URL url = null;
        try {
            url = ResourceUtils.class.getResource("/" + path);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Lấy ra data hình ảnh trong thư mục resources
     * @param path đường dẫn tệp tin (VD: common/images/logo.png)
     * @return javax.swing.ImageIcon
     */
    public static ImageIcon getImage(String path) throws NullPointerException {
        try {
            return new ImageIcon(getUrlFile(path));
        } catch (NullPointerException e) {
            throw new NullPointerException("Không tìm thấy hình ảnh: " + path);
        }
    }

    /**
     * Lấy ra data hình ảnh trong thư mục resources/assets
     * @param path đường dẫn tệp tin (VD: images/logo.png)
     * @return javax.swing.ImageIcon
     */
    public static ImageIcon getImageAssets(String path) {
        return new ImageIcon(getUrlFile(DIR_ASSETS + "/" + path));
    }

}
