package com.example.demo.service;

import com.example.demo.model.Contact;
import com.example.demo.model.LinkPrecedence;
import com.example.demo.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public Map<String, Object> identifyOrCreateContact(String email, String phoneNumber) {
        // Validate input
        if ((email == null || email.isBlank()) && (phoneNumber == null || phoneNumber.isBlank())) {
            throw new IllegalArgumentException("At least one of email or phoneNumber must be provided");
        }

        // Find all contacts matching either email or phone number
        List<Contact> matchedContacts = contactRepository.findByEmailOrPhoneNumber(email, phoneNumber);

        // Case 1: No existing contact - create primary
        if (matchedContacts.isEmpty()) {
            Contact newPrimary = createPrimaryContact(email, phoneNumber);
            return buildResponse(newPrimary, Collections.emptyList());
        }

        // Find or determine the primary contact
        Contact primary = determinePrimaryContact(matchedContacts);

        // Update secondary contacts if needed
        updateSecondaryContacts(matchedContacts, primary);

        // Create secondary contact if new information is provided
        createSecondaryIfNewInfo(email, phoneNumber, primary, matchedContacts);

        // Build and return response
        return buildResponse(primary, contactRepository.findByLinkedId(primary.getId()));
    }

    private Contact createPrimaryContact(String email, String phoneNumber) {
        Contact newPrimary = new Contact();
        newPrimary.setEmail(email);
        newPrimary.setPhoneNumber(phoneNumber);
        newPrimary.setLinkPrecedence(LinkPrecedence.PRIMARY);
        newPrimary.setCreatedAt(LocalDateTime.now());
        newPrimary.setUpdatedAt(LocalDateTime.now());
        return contactRepository.save(newPrimary);
    }

    private Contact determinePrimaryContact(List<Contact> contacts) {
        // Find existing primary contacts
        Optional<Contact> oldestPrimary = contacts.stream()
                .filter(c -> c.getLinkPrecedence() == LinkPrecedence.PRIMARY)
                .min(Comparator.comparing(Contact::getCreatedAt));

        if (oldestPrimary.isPresent()) {
            return oldestPrimary.get();
        }

        // No primary found, convert the oldest contact to primary
        Contact oldest = contacts.stream()
                .min(Comparator.comparing(Contact::getCreatedAt))
                .orElseThrow();

        oldest.setLinkPrecedence(LinkPrecedence.PRIMARY);
        oldest.setUpdatedAt(LocalDateTime.now());
        return contactRepository.save(oldest);
    }

    private void updateSecondaryContacts(List<Contact> contacts, Contact primary) {
        contacts.forEach(contact -> {
            if (!contact.getId().equals(primary.getId()) &&
                    (contact.getLinkPrecedence() != LinkPrecedence.SECONDARY ||
                            !contact.getLinkedId().equals(primary.getId()))) {
                contact.setLinkPrecedence(LinkPrecedence.SECONDARY);
                contact.setLinkedId(primary.getId());
                contact.setUpdatedAt(LocalDateTime.now());
                contactRepository.save(contact);
            }
        });
    }

    private void createSecondaryIfNewInfo(String email, String phoneNumber, Contact primary, List<Contact> existingContacts) {
        boolean emailExists = email != null && existingContacts.stream()
                .anyMatch(c -> email.equals(c.getEmail()));

        boolean phoneExists = phoneNumber != null && existingContacts.stream()
                .anyMatch(c -> phoneNumber.equals(c.getPhoneNumber()));

        if ((email != null && !emailExists) || (phoneNumber != null && !phoneExists)) {
            Contact secondary = new Contact();
            secondary.setEmail(email);
            secondary.setPhoneNumber(phoneNumber);
            secondary.setLinkPrecedence(LinkPrecedence.SECONDARY);
            secondary.setLinkedId(primary.getId());
            secondary.setCreatedAt(LocalDateTime.now());
            secondary.setUpdatedAt(LocalDateTime.now());
            contactRepository.save(secondary);
        }
    }

    private Map<String, Object> buildResponse(Contact primary, List<Contact> secondaries) {
        Set<String> emails = new HashSet<>();
        Set<String> phoneNumbers = new HashSet<>();
        List<Long> secondaryIds = new ArrayList<>();

        // Add primary contact info
        if (primary.getEmail() != null) emails.add(primary.getEmail());
        if (primary.getPhoneNumber() != null) phoneNumbers.add(primary.getPhoneNumber());

        // Add secondary contacts info
        secondaries.forEach(c -> {
            if (c.getEmail() != null) emails.add(c.getEmail());
            if (c.getPhoneNumber() != null) phoneNumbers.add(c.getPhoneNumber());
            secondaryIds.add(c.getId());
        });

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("primaryContactId", primary.getId());
        response.put("emails", new ArrayList<>(emails));
        response.put("phoneNumbers", new ArrayList<>(phoneNumbers));
        response.put("secondaryContactIds", secondaryIds);

        return response;
    }
}