package com.example.client_service.service;

import com.example.client_service.dto.ContactCreateRequest;
import com.example.client_service.dto.ContactResponse;
import com.example.client_service.entity.Client;
import com.example.client_service.entity.Contact;
import com.example.client_service.exception.ResourceNotFoundException;
import com.example.client_service.repository.ClientRepository;
import com.example.client_service.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ContactService contactService;

    private Client client;
    private Contact contact;
    private ContactCreateRequest request;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setClientId(1L);
        client.setName("Artem");
        client.setLastname("Grigorev");
        client.setContacts(new ArrayList<>());

        contact = new Contact();
        contact.setId(1L);
        contact.setPhone("89999999999");
        contact.setEmail("artem@mail.ru");
        contact.setClient(client);

        request = new ContactCreateRequest("89000000000", "ARTEM@mail.ru", 1L);
    }

    @Test
    void getContactById_shouldReturnContact_whenExists() {
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        ContactResponse result = contactService.getContactById(1L);

        assertEquals(contact.getPhone(), result.getPhone());
        assertEquals(contact.getEmail(), result.getEmail());
    }

    @Test
    void getContactById_shouldThrow_whenNotFound() {
        when(contactRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> contactService.getContactById(99L));
    }

    @Test
    void getContactsByClientId_shouldThrow_whenClientNotFound() {
        when(clientRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,() -> contactService.getContactsByClientId(99L));
    }

    @Test
    void createContact_shouldSaveAndReturnContact() {
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        ContactResponse result = contactService.createContact(request);

        assertEquals(contact.getPhone(), result.getPhone());
        assertEquals(contact.getEmail(), result.getEmail());
        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void updateContact_shouldUpdateFields_whenExists() {
        ContactCreateRequest updateRequest = new ContactCreateRequest("11111111111", "example@gmail.com", 1L);
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        ContactResponse result = contactService.updateContact(1L, updateRequest);

        assertEquals(updateRequest.getPhone(), result.getPhone());
        assertEquals(updateRequest.getEmail(), result.getEmail());

        assertEquals("11111111111", contact.getPhone());
        assertEquals("example@gmail.com", contact.getEmail());
    }

    @Test
    void deleteContact_shouldThrow_whenNotFound() {
        when(contactRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> contactService.deleteContact(99L));
    }
}
