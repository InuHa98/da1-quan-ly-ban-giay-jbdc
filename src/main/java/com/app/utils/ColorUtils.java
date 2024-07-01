/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.utils;

import java.awt.Color;

/**
 *
 * @author inuHa
 */
public class ColorUtils {

    public static Color PRIMARY_COLOR = new Color(100, 110, 177);

    public static Color PRIMARY_TEXT = new Color(189, 189, 189);

    public static Color DANGER_COLOR = new Color(255, 0, 98);

    public static Color INFO_COLOR = new Color(0, 153, 255);

    public static Color SUCCESS_COLOR = new Color(74, 175, 9);

    public static Color WARNING_COLOR = new Color(253, 174, 0);

    public static Color BACKGROUND_PRIMARY = new Color(43, 43, 43);

    public static Color BACKGROUND_HOVER = new Color(65, 65, 65);

    public static Color BACKGROUND_DASHBOARD = new Color(32, 33, 37);
	
    public static Color BACKGROUND_TABLE = new Color(43, 45, 49);
    
    public static Color BACKGROUND_TABLE_SELECTION = new Color(63, 66, 72);
    
    /**
     * Chuyển đổi mã hex thành color
     * @param hex VD: #FFFFFF
     * @return java.awt.Color
     */
    public static Color hexToColor(String hex) {
        hex = hex.replace("#", "");

        int length = hex.length();
        if (length == 6) {
            int r = Integer.valueOf(hex.substring(0, 2), 16);
            int g = Integer.valueOf(hex.substring(2, 4), 16);
            int b = Integer.valueOf(hex.substring(4, 6), 16);
            return new Color(r, g, b);
        } else if (length == 8) {
            int r = Integer.valueOf(hex.substring(0, 2), 16);
            int g = Integer.valueOf(hex.substring(2, 4), 16);
            int b = Integer.valueOf(hex.substring(4, 6), 16);
            int a = Integer.valueOf(hex.substring(6, 8), 16);
            return new Color(r, g, b, a);
        } else {
            throw new IllegalArgumentException("Địng dạng hex không hợp lệ: " + hex);
        }
    }

}
