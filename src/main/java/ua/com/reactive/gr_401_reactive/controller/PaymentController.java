package ua.com.reactive.gr_401_reactive.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_401_reactive.entity.Payment;
import ua.com.reactive.gr_401_reactive.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public Flux<Payment> getAll() {
        return paymentService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Payment>> getById(@PathVariable Long id) {
        return paymentService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Payment> create(@RequestBody Payment payment) {
        return paymentService.create(payment);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Payment>> update(
            @PathVariable Long id,
            @RequestBody Payment payment) {

        return paymentService.update(id, payment)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return paymentService.getById(id)
                .flatMap(payment -> paymentService.delete(id)
                        .then(Mono.just(
                                ResponseEntity.noContent().<Void>build()
                        )))
                .switchIfEmpty(Mono.just(
                        ResponseEntity.notFound().<Void>build()
                ));
    }
}
