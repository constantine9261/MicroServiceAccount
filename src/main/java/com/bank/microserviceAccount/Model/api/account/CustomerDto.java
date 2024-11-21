package com.bank.microserviceAccount.Model.api.account;

import lombok.Data;

@Data
public class CustomerDto {
    private String id;            // ID del cliente
    private String type;          // Tipo de cliente ("PERSONAL", "BUSINESS")
    private String name;          // Nombre del cliente
    private String address;       // Dirección del cliente
    private String identificationNumber; // Número de identificación (para clientes personales)
    private String businessName;  // Nombre de la empresa (para clientes empresariales)
    private String registrationNumber; // Número de registro (para clientes empresariales)
    private String contactName;   // Contacto principal (para clientes empresariales)
}