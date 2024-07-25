package com.app.utils;

import com.app.Application;
import com.app.common.helper.MessageToast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jnafilechooser.api.JnaFileChooser;


/**
 *
 * @author InuHa
 */
public class QrCodeUtils {
    
    public final static String MODAL_SCAN_ID = "modal-webcam-scan";
    
    public final static String PREFIX_CODE = "INUSHOES";
        
    public final static String SEPERATOR = "|";
    
    public final static String TYPE_SANPHAM = "product";
    
    public final static String TYPE_SANPHAMCHITIET = "productdetail";
    
    private static final int WIDTH = 600;
    
    private static final int HEIGHT = 600;
    
    public static final String IMAGE_FORMAT = "PNG";
    
    
    public static String generateCodeSanPham(int id) { 
        return PREFIX_CODE + SEPERATOR + TYPE_SANPHAM + SEPERATOR + id;
    }

    public static String generateCodeSanPhamChiTiet(int id) { 
        return PREFIX_CODE + SEPERATOR + TYPE_SANPHAMCHITIET + SEPERATOR + id;
    }
    
    public static int getIdSanPham(String code) { 
        String regex = String.format("^%s\\%s%s\\%s(\\d+)$", PREFIX_CODE, SEPERATOR, TYPE_SANPHAM, SEPERATOR);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        if (matcher.matches()) { 
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }
        
    public static int getIdSanPhamChiTiet(String code) { 
        String regex = String.format("^%s\\%s%s\\%s(\\d+)$", PREFIX_CODE, SEPERATOR, TYPE_SANPHAMCHITIET, SEPERATOR);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        if (matcher.matches()) { 
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }
       
    public static boolean isValidCanCuocCongDan(String code) { 
        String regex = "^\\d{12}\\|\\d{9}\\|[\\p{L}\\s]+\\|\\d{8}\\|(Nam|Nữ)\\|[\\p{L}\\s,]+\\|\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }
        
    public static void generateQRCodeImage(String text, File file) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
        MatrixToImageWriter.writeToPath(bitMatrix, IMAGE_FORMAT, file.toPath());
    }
    
}
