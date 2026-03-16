package com.example.client_service.service;

import com.example.client_service.dto.ClientCreateRequest;
import com.example.client_service.dto.ClientResponse;
import com.example.client_service.entity.Client;
import com.example.client_service.exception.ResourceNotFoundException;
import com.example.client_service.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;
    private ClientCreateRequest request;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setClientId(1L);
        client.setName("Artem");
        client.setLastname("Grigorev");
        client.setContacts(new ArrayList<>());

        request = new ClientCreateRequest("Petr", "Petrov");
    }

    @Test
    void getAllClients_shouldReturnList() {
        when(clientRepository.findAll()).thenReturn(List.of(client));

        List<ClientResponse> result = clientService.getAllClients();

        assertEquals(1, result.size());
        assertEquals("Artem", result.get(0).getName());
    }

    @Test
    void getClientById_shouldReturnClient_whenExists() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        ClientResponse result = clientService.getClientById(1L);

        assertEquals(client.getName(), result.getName());
        assertEquals(client.getLastname(), result.getLastname());
    }

    @Test
    void getClientById_shouldThrow_whenNotFound() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.getClientById(99L));
    }

    @Test
    void createClient_shouldSaveAndReturnClient() {
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientResponse result = clientService.createClient(request);

        assertEquals(client.getName(), result.getName());
        assertEquals(client.getLastname(), result.getLastname());
        verify(clientRepository, times(1)).save(any(Client.class));

    }

    @Test
    void updateClient_shouldUpdateFields_whenExists() {
        ClientCreateRequest updateRequest = new ClientCreateRequest("Ivan", "Ivanov");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientResponse result = clientService.updateClient(1L, updateRequest);

        assertEquals(updateRequest.getName(), result.getName());
        assertEquals(updateRequest.getLastname(), result.getLastname());
    }

    @Test
    void updateClient_shouldThrow_whenNotFound() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.updateClient(99L, request));
    }

    @Test
    void deleteClient_shouldDelete_whenExists() {
        when(clientRepository.existsById(1L)).thenReturn(true);

        clientService.deleteClient(1L);

        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteClient_shouldThrow_whenNotFound() {
        when(clientRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> clientService.deleteClient(99L));
    }
}
