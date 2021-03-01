/**
 * BoofCVQRCodeReader
 * <p>
 * QR Core reader by BoofCV.
 * This will read the first detected QR Code
 * <p>
 * Author: Fernando Karnagi
 */
package sg.com.cyder.qrcodereader.library;

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

    public static String read(String filePath) {

        // read the image to memory
        BufferedImage input = UtilImageIO.loadImage(filePath);

        // gray-out the image, easy to process
        GrayU8 gray = ConvertBufferedImage.convertFrom(input, (GrayU8) null);
        QrCodeDetector<GrayU8> detector = FactoryFiducial.qrcode(null, GrayU8.class);
        detector.process(gray);

        // get a list of all the qr codes it could successfully detect and decode
        List<QrCode> detections = detector.getDetections();

        Graphics2D g2 = input.createGraphics();
        int strokeWidth = Math.max(4, input.getWidth() / 200); // in large images the line can be too thin
        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(strokeWidth));

        // get the first detected QR code and return its text
        for (QrCode qr : detections) {
            // The message encoded in the marker
            System.out.println("found message: " + qr.message);
            return qr.message;
        }

        // not able to detect any QR code, return NULL
        return null;
    }

}
