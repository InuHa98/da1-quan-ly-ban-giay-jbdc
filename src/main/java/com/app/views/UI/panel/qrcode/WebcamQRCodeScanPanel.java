package com.app.views.UI.panel.qrcode;

import com.app.common.helper.MessageModal;
import com.app.utils.ColorUtils;
import com.app.utils.QrCodeUtils;
import com.app.views.UI.dialog.LoadingDialog;
import com.app.views.UI.panel.RoundPanel;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import raven.modal.ModalDialog;

/**
 *
 * @author InuHa
 */
public class WebcamQRCodeScanPanel extends RoundPanel {

    private static WebcamQRCodeScanPanel instance;
    
    private static final long serialVersionUID = 6441489157408381878L;
        
    private Webcam webcam;
    
    private WebcamPanel panel;
    
    private Result result;
    
    private IQRCodeScanEvent event;

    private WebcamQRCodeScanPanel() {
        initComponents();
    }
     
    public static WebcamQRCodeScanPanel getInstance(IQRCodeScanEvent event) { 
        if (instance == null) { 
            instance = new WebcamQRCodeScanPanel();
        }
        LoadingDialog loading = new LoadingDialog();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> { 
            instance.event = event;
            instance.close();
            instance.initializeWebcam();
            loading.dispose();
        });
        loading.setVisible(true);

        return instance;
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(ColorUtils.BACKGROUND_GRAY);
    }

    private void initializeWebcam() {
        webcam = Webcam.getDefault();
        if (webcam == null) {
            MessageModal.error("Không tìm thấy webcam của máy tính");
            return;
        }
        
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        
	int check = 0;
	while (true) {
	    try {
		webcam.open();
		break;
	    } catch(Exception e) { 
		if (check > 3) {
		    MessageModal.error("Webcam đang bị tắt hoặc đã được chạy trên ứng dụng khác!!!");
		    return;		    
		}
	    }
	    try {
		Thread.sleep(500);
	    } catch (InterruptedException ex) {
		ex.printStackTrace();
	    }
	    check++;
	}

        panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(false);
        panel.setDisplayDebugInfo(false);
        panel.setImageSizeDisplayed(false);
        panel.setMirrored(true);
        removeAll();
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
        
        final Thread daemon = new Thread(new Runnable() {
            @Override
            public void run() {
                scanning();
            }
        });
        daemon.setDaemon(true);
        daemon.start();
    }

    private void scanning() {
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        while (true) {
            if (!isExists()) { 
                close();
                return;
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if (webcam == null || !webcam.isOpen()) {
                continue;
            }

            BufferedImage image = webcam.getImage();
            if (image == null) {
                continue;
            }

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            try {
                result = new MultiFormatReader().decode(bitmap);
                playSound();
                event.onScanning(result);
            } catch (NotFoundException e) {
            }
           
        }
    }
        
    private void close() {
        if (instance.webcam != null && instance.webcam.isOpen()) { 
            instance.webcam.close();
        }
    }

    public static void dispose() { 
        if (instance == null) { 
            return;
        }
        instance.close();
    }
        
    public boolean isExists() {
        if (instance == null || !instance.isVisible()) {
            return false;
        }

        try { 
            return ModalDialog.isIdExist(QrCodeUtils.MODAL_SCAN_ID);
        } catch (Exception e) { 
            return false;
        }
    }
    
    
    private void playSound() {
        try (InputStream audioSrc = getClass().getResourceAsStream("/assets/audio/beep.wav");
             AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc)) {
            
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
