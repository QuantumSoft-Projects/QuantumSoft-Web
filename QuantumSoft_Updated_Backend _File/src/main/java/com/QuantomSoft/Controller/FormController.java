//form controller
package com.QuantomSoft.Controller;

import ch.qos.logback.core.model.Model;
import com.QuantomSoft.Entity.Admin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.QuantomSoft.Entity.Form;
import com.QuantomSoft.Service.FormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/forms")
@CrossOrigin("*")
public class FormController {

    private static final Logger logger= LoggerFactory.getLogger(FormController.class);

    @Autowired
    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @PostMapping("/form/saveForm")
    public ResponseEntity<String> saveUser(@RequestPart("formData") String formData,
                                           @RequestPart(value = "cv", required = false) MultipartFile multipartFile) throws JsonProcessingException {
        logger.info("Received request to save user");
        ObjectMapper objectMapper = new ObjectMapper();
        Form user = objectMapper.readValue(formData, Form.class);

        // Manually set Admin (assuming adminId comes in JSON)
        if (user.getAdmin() == null || user.getAdmin().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin ID is required.");
        }

        Admin admin = new Admin();
        admin.setId(user.getAdmin().getId()); // only ID needed for persistence
        user.setAdmin(admin);

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileType = multipartFile.getContentType();
            if ("application/pdf".equals(fileType) || "image/jpeg".equals(fileType) || "image/jpg".equals(fileType)) {
                try {
                    user.setCv(multipartFile.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("Only PDF and JPEG/JPG files are allowed.");
            }
        } else {
            user.setCv(null);
        }

        String message = formService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("User successfully saved");
    }

}
