package com.example.client_service.controller;

import com.example.client_service.dto.ContactCreateRequest;
import com.example.client_service.dto.ContactResponse;
import com.example.client_service.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Contacts", description = "API для управления контактами")
@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @Operation(summary = "Получение всех контактов")
    @GetMapping
    public ResponseEntity<List<ContactResponse>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @Operation(summary = "Получение контакта по ID")
    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> getContactById(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    @Operation(summary = "Получение контактов пользователя по его ID")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ContactResponse>> getContactsByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(contactService.getContactsByClientId(clientId));
    }

    @Operation(summary = "Создание контакта")
    @PostMapping
    public ResponseEntity<ContactResponse> createContact(@RequestBody ContactCreateRequest request) {
        return ResponseEntity.ok(contactService.createContact(request));
    }

    @Operation(summary = "Обновление информации о контакте")
    @PutMapping("/{id}")
    public ResponseEntity<ContactResponse> updateContact(@PathVariable Long id, @RequestBody ContactCreateRequest request) {
        return ResponseEntity.ok(contactService.updateContact(id, request));
    }

    @Operation(summary = "Удаление контакта")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
