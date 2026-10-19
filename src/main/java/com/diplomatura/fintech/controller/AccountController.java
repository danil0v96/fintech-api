package com.diplomatura.fintech.controller;

import com.diplomatura.fintech.dto.CreateAccountRequest;
import com.diplomatura.fintech.entity.Account;
import com.diplomatura.fintech.exception.AccountNotFoundException;
import com.diplomatura.fintech.exception.CbuAlreadyExistsException;
import com.diplomatura.fintech.exception.UserNotFoundException;
import com.diplomatura.fintech.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@Tag(
        name = "Cuentas",
        description = "Operaciones para crear y consultar cuentas fintech"
)
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(
            summary = "Lista todas las cuentas",
            description = "Devuelve todas las cuentas registradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista obtenida correctamente"
    )
    @GetMapping
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @Operation(
            summary = "Busca una cuenta por ID",
            description = "Devuelve la cuenta correspondiente al ID indicado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cuenta encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "La cuenta no existe"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(accountService.findById(id));
        } catch (AccountNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }

    @Operation(
            summary = "Crea una cuenta",
            description = "Crea una cuenta activa, con saldo inicial cero, asociada a un usuario existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Cuenta creada correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Los datos enviados no son válidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El usuario indicado no existe"
            )
    })
    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody CreateAccountRequest request
    ) {
        try {
            Account account = accountService.create(
                    request.getCbu(),
                    request.getAlias(),
                    request.getUserId()
            );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(account);

        } catch (UserNotFoundException exception) {
            return ResponseEntity.notFound().build();

        } catch (CbuAlreadyExistsException exception) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(exception.getMessage());
        }
    }

    @Operation(
            summary = "Desactivar una cuenta",
            description = "Realiza un borrado lógico cambiando el estado de la cuenta a DEACTIVATED"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Cuenta desactivada correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "La cuenta no existe"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDeleteById(@PathVariable Long id) {
        try {
            accountService.softDeleteById(id);
            return ResponseEntity.noContent().build();
        } catch (AccountNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }

    @Operation(
            summary = "Eliminar físicamente una cuenta",
            description = "Elimina definitivamente de la base de datos la cuenta indicada"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Cuenta eliminada definitivamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "La cuenta no existe"
            )
    })
    @DeleteMapping("/{id}/fisico")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            accountService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (AccountNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }
}
