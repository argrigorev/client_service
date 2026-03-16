package com.example.client_service.service;

import com.example.client_service.dto.ContactCreateRequest;
import com.example.client_service.dto.ContactResponse;
import com.example.client_service.entity.Contact;
import com.example.client_service.exception.ResourceNotFoundException;
import com.example.client_service.repository.ClientRepository;
import com.example.client_service.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final ClientRepository clientRepository;

    public List<ContactResponse> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ContactResponse getContactById(Long id) {
        return toResponse(contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with id " + id + " not found")));
    }

    public List<ContactResponse> getContactsByClientId(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Client with id " + clientId + " not found");
        }
        return contactRepository.findByClient_ClientId(clientId).stream()
                .map(this::toResponse)
                .toList();
    }

    public ContactResponse createContact(ContactCreateRequest request) {
        Contact savedContact = contactRepository.save(toEntity(request));
        return toResponse(savedContact);
    }

    public ContactResponse updateContact(Long id, ContactCreateRequest request) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with id " + id + " not found"));

        existingContact.setPhone(request.getPhone());
        existingContact.setEmail(request.getEmail());

        return toResponse(contactRepository.save(existingContact));
    }

    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contact with id " + id + " not found");
        }
        contactRepository.deleteById(id);
    }

    private ContactResponse toResponse(Contact contact) {
        return new ContactResponse(
                contact.getId(),
                contact.getPhone(),
                contact.getEmail()
        );
    }

    private Contact toEntity(ContactCreateRequest request) {
        Contact contact = new Contact();
        contact.setPhone(request.getPhone());
        contact.setEmail(request.getEmail());
        contact.setClient(clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client with id " + request.getClientId() + " not found")));
        return contact;
    }
}
