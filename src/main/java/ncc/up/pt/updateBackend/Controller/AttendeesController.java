package ncc.up.pt.updateBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ncc.up.pt.updateBackend.Repository.*;
import ncc.up.pt.updateBackend.Model.*;

import ncc.up.pt.updateBackend.Common.ResponseDTO;
import ncc.up.pt.updateBackend.Common.RecaptchaValidator;;

@RestController
@RequestMapping("/process")
public class AttendeesController {

    private final AttendeesRepository attendeesRepository;
    private final AttendeesPointsRepository attendeesPointsRepository;

    @Autowired
    public AttendeesController(AttendeesRepository attendeesRepository,
            AttendeesPointsRepository attendeePointsRepository) {
        this.attendeesRepository = attendeesRepository;
        this.attendeesPointsRepository = attendeePointsRepository;
    }

    @GetMapping("/add-points/{id}/{points}")
    public ResponseEntity<?> addPointsToAttendee(@PathVariable Long id, @PathVariable int points) {
        Optional<Attendees> attendeeOptional = attendeesRepository.findById(id);

        if (!attendeeOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Attendees attendee = attendeeOptional.get();

        // Check if AttendeePoints exists for this attendee
        Optional<AttendeesPoints> attendeePointsOptional = attendeesPointsRepository.findByAttendee(attendee);

        AttendeesPoints attendeePoints;
        if (attendeePointsOptional.isPresent()) {
            // If exists, update points
            attendeePoints = attendeePointsOptional.get();
            attendeePoints.setPoints(attendeePoints.getPoints() + points);
        } else {
            // If not exists, create new AttendeePoints
            attendeePoints = new AttendeesPoints();
            attendeePoints.setAttendee(attendee);
            attendeePoints.setPoints(points);
        }

        attendeesPointsRepository.save(attendeePoints);
        return ResponseEntity.ok(attendeePoints);
    }

    @PostMapping
    public ResponseEntity<Object> processAttendee(@RequestBody Attendees attendee,
            @RequestParam("token") String token) {
        // Validate the token
        boolean isTokenValid = RecaptchaValidator.validate(token);
        if (!isTokenValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO("1", "Invalid reCAPTCHA token", null));
        }
        System.out.println("VALID TOKEN");
        try {

            Attendees savedAttendee = attendeesRepository.save(attendee);

            // Generate QR code containing the cellphone number
            String qrCodeContent = savedAttendee.getCellphoneNumber().toString();
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, 200, 200);

            // Save QR code image locally
            String filePath = "QRCode_" + savedAttendee.getCellphoneNumber() + ".png";
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            // Convert QR code to Base64
            java.io.ByteArrayOutputStream pngOutputStream = new java.io.ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();
            String base64QRCode = java.util.Base64.getEncoder().encodeToString(pngData);

            // Create response JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(
                    new ResponseDTO("0", "Success", base64QRCode));

            return ResponseEntity.ok(jsonResponse);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("1", "Failed", null));
        }
    }

}
