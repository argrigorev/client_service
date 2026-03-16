package com.example.client_service.repository;

import com.example.client_service.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByClient_ClientId(Long clientId);
}
