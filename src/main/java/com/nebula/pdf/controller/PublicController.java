package com.nebula.pdf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.nebula.pdf.service.PdfService;
import com.nebula.pdf.entity.Entity;

@RestController
public class PublicController {
    @Autowired PdfService service;

    @PostMapping("/public/pdf")
    public ResponseEntity<Entity> getPdf(@RequestBody Entity request) {
        try {
            Entity response = service.getPdfText(request.getBody());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Entity errorResponse = new Entity();
            errorResponse.setBody("Error: " + e.getMessage());
            errorResponse.setStatus(500);
            errorResponse.setError(true);
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
