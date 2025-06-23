package com.example.demo.repository;

import com.example.demo.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findTopByEmailOrderByCreatedAtAsc(String email);
    Optional<Contact> findTopByPhoneNumberOrderByCreatedAtAsc(String phoneNumber);

    // ✅ This is the missing method:
    List<Contact> findAllByLinkedIdOrId(Long linkedId, Long id);
    List<Contact> findByEmailOrPhoneNumber(String email, String phoneNumber);
    List<Contact> findByLinkedId(Long linkedId);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
