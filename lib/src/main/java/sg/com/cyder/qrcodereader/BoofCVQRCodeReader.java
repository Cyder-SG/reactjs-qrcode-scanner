package sg.com.cyder.qrcodereader;

import boofcv.abst.fiducial.QrCodeDetector;
import boofcv.alg.fiducial.qrcode.QrCode;
import boofcv.factory.fiducial.FactoryFiducial;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class BoofCVQRCodeReader {

    public static void main(String[] args) {
        BufferedImage input = UtilImageIO.loadImage("/tmp/qr_base64-1614520177202.png");
        GrayU8 gray = ConvertBufferedImage.convertFrom(input, (GrayU8) null);

        QrCodeDetector<GrayU8> detector = FactoryFiducial.qrcode(null, GrayU8.class);

        detector.process(gray);

        // Get's a list of all the qr codes it could successfully detect and decode
        List<QrCode> detections = detector.getDetections();

        Graphics2D g2 = input.createGraphics();
        int strokeWidth = Math.max(4, input.getWidth() / 200); // in large images the line can be too thin
        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(strokeWidth));
        for (QrCode qr : detections) {
            // The message encoded in the marker
            System.out.println("message: " + qr.message);
        }
    }

}