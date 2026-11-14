package ua.com.reactive.gr_401_reactive.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_401_reactive.service.RegistrationService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    public record RegistrationRequest(
            String username,
            String password
    ) {}

    @PostMapping("/registration")
    public Mono<ResponseEntity<Map<String, String>>> register(
            @RequestBody RegistrationRequest request) {

        return registrationService
                .register(request.username(), request.password())
                .map(user -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(Map.of(
                                "message", "User registered successfully",
                                "username", user.getUsername()
                        )))
                .onErrorResume(IllegalArgumentException.class, error -> {
                    HttpStatus status = "Username already exists".equals(error.getMessage())
                            ? HttpStatus.CONFLICT
                            : HttpStatus.BAD_REQUEST;

                    return Mono.just(ResponseEntity
                            .status(status)
                            .body(Map.of("error", error.getMessage())));
                });
    }
}

