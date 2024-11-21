package com.bank.microserviceAccount.controller;


import com.bank.microserviceAccount.Model.api.account.BankAccountDto;
import com.bank.microserviceAccount.Model.api.account.BankAccountRequest;
import com.bank.microserviceAccount.Model.api.shared.ResponseDto;
import com.bank.microserviceAccount.Model.api.shared.ResponseDtoBuilder;
import com.bank.microserviceAccount.business.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {


    private final IAccountService bankAccountService;
    @Operation(summary = "Crear una cuenta bancaria", description = "Crea una nueva cuenta bancaria con los datos proporcionados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta bancaria creada con éxito",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos de solicitud no válidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/accounts")
    public Mono<ResponseDto<BankAccountDto>> createBankAccount(@RequestBody BankAccountRequest request) {
        return bankAccountService.createBankAccount(request)
                .map(account -> ResponseDtoBuilder.success(account, "Cuenta bancaria creada con éxito"))
                .onErrorResume(error -> Mono.just(ResponseDtoBuilder.error(error.getMessage())));
    }

    @Operation(summary = "Obtener una cuenta bancaria por ID", description = "Obtiene los detalles de una cuenta bancaria específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta bancaria encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Cuenta bancaria no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public Mono<ResponseDto<BankAccountDto>> getAccountById(@PathVariable String id) {
        return bankAccountService.findById(id)
                .map(account -> ResponseDtoBuilder.success(account, "Cuenta bancaria encontrada"))
                .defaultIfEmpty(ResponseDtoBuilder.notFound("Cuenta bancaria no encontrada"));
    }

    @Operation(summary = "Obtener todas las cuentas bancarias", description = "Obtiene una lista de todas las cuentas bancarias disponibles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de cuentas bancarias obtenida",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public Flux<ResponseDto<BankAccountDto>> getAllAccounts() {
        return bankAccountService.findAll()
                .map(account -> ResponseDtoBuilder.success(account, "Cuenta bancaria obtenida"));
    }

    @Operation(summary = "Actualizar una cuenta bancaria", description = "Actualiza los detalles de una cuenta bancaria existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta bancaria actualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Cuenta bancaria no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos de solicitud no válidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public Mono<ResponseDto<BankAccountDto>> updateAccount(@PathVariable String id, @RequestBody BankAccountRequest request) {
        return bankAccountService.updateBankAccount(id, request)
                .map(updatedAccount -> ResponseDtoBuilder.success(updatedAccount, "Cuenta bancaria actualizada"))
                .defaultIfEmpty(ResponseDtoBuilder.notFound("Cuenta bancaria no encontrada"))
                .onErrorResume(e -> Mono.just(ResponseDtoBuilder.error(e.getMessage())));
    }

    @Operation(summary = "Eliminar una cuenta bancaria", description = "Elimina una cuenta bancaria específica por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta bancaria eliminada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Cuenta bancaria no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseDto<Object>> deleteAccount(@PathVariable String id) {
        return bankAccountService.deleteById(id)
                .then(Mono.just(ResponseDtoBuilder.success(null, "Cuenta bancaria eliminada")))
                .defaultIfEmpty(ResponseDtoBuilder.notFound("Cuenta bancaria no encontrada"));
    }


    @Operation(summary = "Crear cuenta VIP", description = "Crea una cuenta de ahorro VIP. Requiere cliente personal con tarjeta de crédito activa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta VIP creada con éxito."),
            @ApiResponse(responseCode = "400", description = "Validación fallida para la cuenta VIP."),
            @ApiResponse(responseCode = "500", description = "Error interno.")
    })
    @PostMapping("/vip")
    public Mono<ResponseDto<BankAccountDto>> createVipAccount(@RequestBody BankAccountRequest request) {
        return bankAccountService.createVipAccount(request)
                .map(account -> ResponseDtoBuilder.success(account, "Cuenta VIP creada con éxito."))
                .onErrorResume(e -> Mono.just(ResponseDtoBuilder.error(e.getMessage())));
    }

    @Operation(summary = "Crear cuenta PYME", description = "Crea una cuenta corriente PYME. Requiere cliente empresarial con tarjeta de crédito activa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta PYME creada con éxito."),
            @ApiResponse(responseCode = "400", description = "Validación fallida para la cuenta PYME."),
            @ApiResponse(responseCode = "500", description = "Error interno.")
    })
    @PostMapping("/pyme")
    public Mono<ResponseDto<BankAccountDto>> createPymeAccount(@RequestBody BankAccountRequest request) {
        return bankAccountService.createPymeAccount(request)
                .map(account -> ResponseDtoBuilder.success(account, "Cuenta PYME creada con éxito."))
                .onErrorResume(e -> Mono.just(ResponseDtoBuilder.error(e.getMessage())));
    }

}
