package com.example.client_service.service;

import com.example.client_service.dto.ClientCreateRequest;
import com.example.client_service.dto.ClientResponse;
import com.example.client_service.dto.ContactResponse;
import com.example.client_service.entity.Client;
import com.example.client_service.exception.ResourceNotFoundException;
import com.example.client_service.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<ClientResponse> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ClientResponse getClientById(Long id) {
        return toResponse(clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client with id " + id + " not found")));
    }

    public ClientResponse createClient(ClientCreateRequest request) {
        Client savedClient = clientRepository.save(toEntity(request));
        return toResponse(savedClient);
    }

    public ClientResponse updateClient(Long id, ClientCreateRequest request) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client with id " + id + " not found"));

        existingClient.setName(request.getName());
        existingClient.setLastname(request.getLastname());

        return toResponse(clientRepository.save(existingClient));
    }

    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client with id " + id + " not found");
        }
        clientRepository.deleteById(id);
    }

    private ClientResponse toResponse(Client client) {
        List<ContactResponse> contactResponses = client.getContacts().stream()
                .map(c -> new ContactResponse(c.getId(), c.getPhone(), c.getEmail()))
                .toList();
        return new ClientResponse(
                client.getClientId(),
                client.getName(),
                client.getLastname(),
                contactResponses
        );
    }

    private Client toEntity(ClientCreateRequest request) {
        Client client = new Client();
        client.setName(request.getName());
        client.setLastname(request.getLastname());
        return client;
    }
}
