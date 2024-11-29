package com.bank.microserviceAccount.Model.api.account;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
@Data
@Builder
public class BankAccountDto {
    @Id
    private String id; // Identificador único generado por MongoDB
    private String accountNumber; // Número de cuenta único
    private String customerId; // ID del cliente asociado
    private String type; // Tipo de cuenta: "SAVINGS", "CURRENT", "FIXED"
    private double balance; // Saldo de la cuenta
    private int maxTransactions; // Límite de transacciones mensuales (para cuentas de ahorro)
    private double monthlyFee; // Comisión mensual (para cuentas corrientes)
    private String allowedWithdrawalDate; // Fecha permitida para retiro (para cuentas a plazo fijo)
    private boolean debitCardLinked; // Agregar este campo al DTO

}
