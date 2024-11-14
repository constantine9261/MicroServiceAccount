package com.bank.microserviceAccount.Model.api.account;

import lombok.Data;

@Data
public class BankAccountRequest {
    private String accountNumber; // Número de cuenta único
    private String customerId; // ID del cliente asociado
    private String type; // Tipo de cuenta: "SAVINGS", "CURRENT", "FIXED"
    private double balance; // Saldo de la cuenta
    private int maxTransactions; // Límite de transacciones mensuales (para cuentas de ahorro)
    private double monthlyFee; // Comisión mensual (para cuentas corrientes)
    private String allowedWithdrawalDate; // Fecha permitida para retiro (para cuentas a plazo fijo)
}
