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

    public static Color BACKGROUND_PRIMARY = new Color(30, 31, 34);

    public static Color BACKGROUND_GRAY = new Color(49, 51, 56);

    public static Color BACKGROUND_DARK = new Color(43, 45, 49);
	
    public static Color BACKGROUND_HOVER = new Color(65, 69, 89);

    public static Color BACKGROUND_DASHBOARD = new Color(35, 36, 40);
	
    public static Color BACKGROUND_TABLE = new Color(43, 45, 49);
    
    public static Color BACKGROUND_TABLE_ODD = new Color(63, 66, 72);
        
    public static Color TEXT_TABLE = new Color(181, 186, 193);
        
    public static Color BACKGROUND_TABLE_SELECTION = new Color(63, 66, 72);
    
    public static Color BUTTON_PRIMARY = new Color(98, 93, 147);

    public static Color BUTTON_GRAY = new Color(34, 35, 38);
        
    public static Color INPUT_PRIMARY = new Color(43, 45, 49);

    public static Color SELECT_PRIMARY = new Color(57, 60, 65);
    
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

    /**
     * Làm tối màu từ màu chỉ định
     * @param color VD: new Color(250, 250, 250)
     * @param factor càng thấp càng tối (từ 0.0f -> 1.0f) VD: 0.3f
     * @return java.awt.Color
     */
    public static Color darken(Color color, float factor) {
        int r = Math.max((int)(color.getRed() * factor), 0);
        int g = Math.max((int)(color.getGreen() * factor), 0);
        int b = Math.max((int)(color.getBlue() * factor), 0);
        return new Color(r, g, b, color.getAlpha());
    }

    /**
     * Làm sáng màu từ màu chỉ định
     * @param color VD: new Color(0, 0, 0)
     * @param factor càng cao càng sáng (từ 0.0f -> 1.0f) VD: 0.3f
     * @return java.awt.Color
     */
    public static Color lighten(Color color, float factor) {
        int r = Math.min((int)(color.getRed() + (255 - color.getRed()) * factor), 255);
        int g = Math.min((int)(color.getGreen() + (255 - color.getGreen()) * factor), 255);
        int b = Math.min((int)(color.getBlue() + (255 - color.getBlue()) * factor), 255);
        return new Color(r, g, b, color.getAlpha());
    }

    /**
     * Làm tối màu từ màu chỉ định
     * @param hex VD: #ffffff
     * @param factor càng thấp càng tối (từ 0.0f -> 1.0f) VD: 0.3f
     * @return java.awt.Color
     */
    public static Color darken(String hex, float factor) {
        return darken(hexToColor(hex), factor);
    }

    /**
     * Làm sáng màu từ màu chỉ định
     * @param hex VD: #000000
     * @param factor càng cao càng sáng (từ 0.0f -> 1.0f) VD: 0.3f
     * @return java.awt.Color
     */
    public static Color lighten(String hex, float factor) {
        return lighten(hexToColor(hex), factor);
    }

}
