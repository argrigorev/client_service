package com.example.client_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    private Long clientId;
    private String name;
    private String lastname;
    private List<ContactResponse> contacts;
}
