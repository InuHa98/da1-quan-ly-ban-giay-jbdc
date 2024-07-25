package com.app.common.helper;

import com.app.Application;
import com.app.utils.QrCodeUtils;
import com.app.views.UI.panel.qrcode.IQRCodeScanEvent;
import com.app.views.UI.panel.qrcode.WebcamQRCodeScanPanel;
import com.google.zxing.Result;
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
    
}
