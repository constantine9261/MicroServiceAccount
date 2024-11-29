package com.bank.microserviceAccount.Model.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@Data
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
@Builder
@Document(collection = "bank_accounts") // Nombre de la colección en MongoDB
public class AccountEntity implements Serializable {

    @Id
    private String id; // Identificador único generado por MongoDB
    private String accountNumber; // Número de cuenta único
    private String customerId; // ID del cliente asociado
    private String type; // Tipo de cuenta: "SAVINGS", "CURRENT", "FIXED"
    private double balance; // Saldo de la cuenta
    private int maxTransactions; // Límite de transacciones mensuales (para cuentas de ahorro)
    private double monthlyFee; // Comisión mensual (para cuentas corrientes)
    private String allowedWithdrawalDate; // Fecha permitida para retiro (para cuentas a plazo fijo)
    private boolean debitCardLinked; // Indica si la cuenta está vinculada a una tarjeta de débito


}
