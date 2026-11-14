package ua.com.reactive.gr_401_reactive.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_401_reactive.entity.Account;
import ua.com.reactive.gr_401_reactive.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public Flux<Account> getAll() {
        return accountService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Account>> getById(@PathVariable Long id) {
        return accountService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Account> create(@RequestBody Account account) {
        return accountService.create(account);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Account>> update(
            @PathVariable Long id,
            @RequestBody Account account) {

        return accountService.update(id, account)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return accountService.getById(id)
                .flatMap(account -> accountService.delete(id)
                        .then(Mono.just(
                                ResponseEntity.noContent().<Void>build()
                        )))
                .switchIfEmpty(Mono.just(
                        ResponseEntity.notFound().<Void>build()
                ));
    }
}
