package com.diplomatura.fintech.controller;

import com.diplomatura.fintech.dto.CreateIndividualUserRequest;
import com.diplomatura.fintech.dto.CreateLegalEntityUserRequest;
import com.diplomatura.fintech.entity.IndividualUser;
import com.diplomatura.fintech.entity.LegalEntityUser;
import com.diplomatura.fintech.entity.User;
import com.diplomatura.fintech.enums.UserType;
import com.diplomatura.fintech.exception.UserNotFoundException;
import com.diplomatura.fintech.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones para crear y consultar usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Listar todos los usuarios",
            description = "Devuelve todos los usuarios o permite filtrarlos por tipo"
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public List<User> findAll(@RequestParam(required = false) UserType tipo) {
        if (tipo == null) {
            return userService.findAll();
        }

        return userService.findAllByType(tipo);
    }

    @Operation(
            summary = "Buscar un usuario por ID",
            description = "Devuelve el usuario correspondiente al ID indicado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "El usuario no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.findById(id));
        } catch (UserNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }

    @Operation(
            summary = "Crear una persona física",
            description = "Registra una persona física y devuelve el ID generado por MySQL"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Persona física creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")
    })
    @PostMapping("/personas-fisicas")
    @ResponseStatus(HttpStatus.CREATED)
    public User createIndividual(@Valid @RequestBody CreateIndividualUserRequest request) {
        User user = new IndividualUser(
                request.getName(),
                request.getLastName(),
                request.getEmail(),
                request.getDocument()
        );

        return userService.create(user);
    }

    @Operation(
            summary = "Crear una persona jurídica",
            description = "Registra una persona jurídica y devuelve el ID generado por MySQL"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Persona jurídica creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos enviados no son válidos")
    })
    @PostMapping("/personas-juridicas")
    @ResponseStatus(HttpStatus.CREATED)
    public User createLegalEntity(@Valid @RequestBody CreateLegalEntityUserRequest request) {
        User user = new LegalEntityUser(
                request.getBusinessName(),
                request.getEmail(),
                request.getCuit()
        );

        return userService.create(user);
    }
}
