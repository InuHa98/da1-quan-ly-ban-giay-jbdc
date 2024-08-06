package com.app.utils;

import com.app.core.inuha.services.InuhaSanPhamChiTietService;
import com.app.core.inuha.services.InuhaSanPhamService;
import javax.swing.*;

/**
 *
 * @author inuHa
 */
public class ProductUtils {

    private static final String PREFIX_CODE_SANPHAM = "SP-";
    
    private static final String PREFIX_CODE_SANPHAMCHITIET = "SPCT-";
	
    private static final int LENGTH_CODE = 6;

    private static final String IMAGE_NAME_FORMAT = "product_%s";

    public static final int MAX_WIDTH_UPLOAD = 200;

    public static final int MAX_HEIGHT_UPLOAD = 200;

    public static String generateCodeSanPham() {
        String lastCode = InuhaSanPhamService.getInstance().getLastCode();
        int number = 1;
        
        if (lastCode != null && !lastCode.equalsIgnoreCase("null")) { 
            int indexOfDash = lastCode.indexOf('-');
            String numberPart = lastCode.substring(indexOfDash + 1);
            number = Integer.parseInt(numberPart);
        }

        return PREFIX_CODE_SANPHAM + CurrencyUtils.startPad(String.valueOf(++number), LENGTH_CODE, '0');
    }

    public static String generateCodeSanPhamChiTiet() {
        String lastCode = InuhaSanPhamChiTietService.getInstance().getLastCode();
        
        int number = 1;
        
        if (lastCode != null && !lastCode.equalsIgnoreCase("null")) { 
            int indexOfDash = lastCode.indexOf('-');
            String numberPart = lastCode.substring(indexOfDash + 1);
            number = Integer.parseInt(numberPart);
        }

        return PREFIX_CODE_SANPHAMCHITIET + CurrencyUtils.startPad(String.valueOf(++number), LENGTH_CODE, '0');
    }
	
    public static String uploadImage(String code, String pathImage) {
        ImageIcon resizeImage = ComponentUtils.resizeImage(new ImageIcon(pathImage), MAX_WIDTH_UPLOAD, MAX_HEIGHT_UPLOAD);
        String fileName = StorageUtils.uploadProduct(resizeImage, String.format(IMAGE_NAME_FORMAT, code));
        return fileName;
    }

    public static ImageIcon getImage(String image) {
	if (image == null) {
	    return null;
	}

        if (!image.trim().isEmpty()) {
            try {
                return StorageUtils.getProduct(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResourceUtils.getImageAssets("images/no-product.jpeg");
    }
    
    public static String getUrlImageProduct(String image) { 
        return StorageUtils.getUrlImageProduct(image);
    }
    
    public static String getTrangThai(boolean trangThai) { 
	return trangThai ? "Đang bán" : "Ngừng bán";
    }
    
    public static boolean removeImageProduct(String image) { 
        return StorageUtils.deleteFile(getUrlImageProduct(image));
    }

}
