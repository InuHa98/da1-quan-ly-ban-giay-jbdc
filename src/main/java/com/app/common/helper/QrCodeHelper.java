package com.app.common.helper;

import com.app.Application;
import com.app.utils.QrCodeUtils;
import com.app.views.UI.panel.qrcode.IQRCodeScanEvent;
import com.app.views.UI.panel.qrcode.WebcamQRCodeScanPanel;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import java.io.File;
import java.io.IOException;
import jnafilechooser.api.JnaFileChooser;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;

/**
 *
 * @author InuHa
 */
public class QrCodeHelper {
    
    public static void showWebcam(IQRCodeScanEvent event) { 
        showWebcam(null, event);
    }
     
    public static void showWebcam(String title, IQRCodeScanEvent event) { 
        IQRCodeScanEvent callback = new IQRCodeScanEvent() { 
            @Override
            public void onScanning(Result result) {
                event.onScanning(result);
                closeWebcam();
            }
        };
        
        ModalDialog.showModal(Application.app, new SimpleModalBorder(WebcamQRCodeScanPanel.getInstance(callback), title), QrCodeUtils.MODAL_SCAN_ID);
    }
    
    public static void closeWebcam() { 
        ModalDialog.closeModal(QrCodeUtils.MODAL_SCAN_ID);
        WebcamQRCodeScanPanel.dispose();
    }
    
    public static void save(String code, String fileName) {
        JnaFileChooser ch = new JnaFileChooser();
        ch.setMode(JnaFileChooser.Mode.Directories);
        boolean act = ch.showOpenDialog(Application.app);
        if (act) {
            File folder = ch.getSelectedFile();
            File file = new File(folder, fileName + "." + QrCodeUtils.IMAGE_FORMAT.toLowerCase());
            try {
                QrCodeUtils.generateQRCodeImage(code, file);
                MessageToast.success("Lưu QR Code thành công!");
            } catch (WriterException | IOException e) {
                e.printStackTrace();
                MessageToast.error("Không thể lưu QR Code!!!!");
            }
        }
    }
    
}
