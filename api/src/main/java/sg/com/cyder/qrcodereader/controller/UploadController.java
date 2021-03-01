/**
 * UploadController
 * <p>
 * Handles QR Code image file upload and returns the QR text
 * <p>
 * Author: Fernando Karnagi
 */
package sg.com.cyder.qrcodereader.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sg.com.cyder.qrcodereader.entity.QRTextResponseEntity;
import sg.com.cyder.qrcodereader.library.BoofCVQRCodeReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UploadController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/tmp/";

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping(path = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QRTextResponseEntity> singleFileUpload(@RequestParam("qr_base64") MultipartFile file,
                                                                 RedirectAttributes redirectAttributes) {

        // there has to be a valid file
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "file not valid"
            );
        }

        try {
            // get the file and save it somewhere
            byte[] bytes = file.getBytes();

            // write the file
            String fileName = UPLOADED_FOLDER + file.getOriginalFilename();
            Path path = Paths.get(fileName);
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

            // try to detect QR code and return its text
            String qrText = BoofCVQRCodeReader.read(fileName);

            // return error if text is empty, means it is an valid QR code
            if (StringUtils.isEmpty(qrText)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "no QR Code detected"
                );
            }
            // return qrText JSON
            return ResponseEntity.ok(new QRTextResponseEntity(qrText));

        } catch (IOException e) {
            e.printStackTrace();
            // Unable to process the image, it is a bad request
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }


    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
}
