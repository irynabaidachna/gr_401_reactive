package ua.com.reactive.gr_401_reactive.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_401_reactive.entity.Client;
import ua.com.reactive.gr_401_reactive.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public Flux<Client> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Client>> getById(@PathVariable Long id) {
        return clientService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Client> create(@RequestBody Client client) {
        return clientService.create(client);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Client>> update(
            @PathVariable Long id,
            @RequestBody Client client) {

        return clientService.update(id, client)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return clientService.getById(id)
                .flatMap(client -> clientService.delete(id)
                        .then(Mono.just(
                                ResponseEntity.noContent().<Void>build()
                        )))
                .switchIfEmpty(Mono.just(
                        ResponseEntity.notFound().<Void>build()
                ));
    }


}
