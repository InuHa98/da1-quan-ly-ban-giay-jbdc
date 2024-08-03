package com.app.utils;

import com.app.common.helper.TestConnection;
import com.app.common.infrastructure.constants.TrangThaiHoaDonConstant;
import com.app.core.inuha.services.InuhaHoaDonChiTietService;
import com.app.core.inuha.services.InuhaHoaDonService;
import io.github.cdimascio.dotenv.Dotenv;
import javax.swing.*;

/**
 *
 * @author inuHa
 */
public class BillUtils {

    private static final String PREFIX_CODE_HOADON = "HD-";
    
    private static final String PREFIX_CODE_HOADONCHITIET = "HDCT-";
	
    private static final int LENGTH_CODE = 6;

    public static String generateCodeHoaDon() {
        String lastCode = InuhaHoaDonService.getInstance().getLastCode();
        
        int number = 1;
        
        if (lastCode != null && !lastCode.equalsIgnoreCase("null")) { 
            int indexOfDash = lastCode.indexOf('-');
            String numberPart = lastCode.substring(indexOfDash + 1);
            number = Integer.parseInt(numberPart);
        }

        return PREFIX_CODE_HOADON + CurrencyUtils.startPad(String.valueOf(++number), LENGTH_CODE, '0');
    }

    public static String generateCodeHoaDonChiTiet() {
        String lastCode = InuhaHoaDonChiTietService.getInstance().getLastCode();
        
        int number = 1;
        
        if (lastCode != null && !lastCode.equalsIgnoreCase("null")) { 
            int indexOfDash = lastCode.indexOf('-');
            String numberPart = lastCode.substring(indexOfDash + 1);
            number = Integer.parseInt(numberPart);
        }

        return PREFIX_CODE_HOADONCHITIET + CurrencyUtils.startPad(String.valueOf(++number), LENGTH_CODE, '0');
    }

    
    public static String getTrangThai(int trangThai) { 
	return switch (trangThai) {
	    case TrangThaiHoaDonConstant.STATUS_DA_THANH_TOAN -> "Đã thanh toán";
	    case TrangThaiHoaDonConstant.STATUS_DA_HUY -> "Đã huỷ";
	    case TrangThaiHoaDonConstant.STATUS_CHO_THANH_TOAN -> "Chờ thanh toán";
	    default -> "Không xác định";
	};
    }

}
