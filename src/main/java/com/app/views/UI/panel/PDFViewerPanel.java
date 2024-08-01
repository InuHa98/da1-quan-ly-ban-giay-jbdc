package com.app.views.UI.panel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.swing.JPanel;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 *
 * @author inuHa
 */
public class PDFViewerPanel extends JPanel {
    private BufferedImage pdfImage;

    public PDFViewerPanel(ByteArrayInputStream pdfStream) {
        try {
            try (PDDocument document = Loader.loadPDF(pdfStream.readAllBytes())) {
                PDFRenderer pdfRenderer = new PDFRenderer(document);
                pdfImage = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pdfImage != null) {
            g.drawImage(pdfImage, 0, 0, this);
        }
    }
    
}
