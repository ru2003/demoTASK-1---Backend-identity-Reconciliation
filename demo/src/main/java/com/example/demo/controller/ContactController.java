package com.example.demo.controller;

import com.example.demo.request.IdentifyRequest;
import com.example.demo.response.IdentifyResponse;
import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/identify")
    public ResponseEntity<?> identify(@RequestBody IdentifyRequest request) {
        String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();

        // ✅ Add validation to prevent saving null/empty inputs
        if ((email == null || email.trim().isEmpty()) &&
                (phoneNumber == null || phoneNumber.trim().isEmpty())) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Contact tracing matrix incomplete. Provide at least one valid identifier."));
        }

        return ResponseEntity.ok(contactService.identifyOrCreateContact(email, phoneNumber));
    }
}
