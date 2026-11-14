package ua.com.reactive.gr_401_reactive.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_401_reactive.entity.Payment;
import ua.com.reactive.gr_401_reactive.repository.AccountRepository;
import ua.com.reactive.gr_401_reactive.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          AccountRepository accountRepository) {
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
    }

    public Flux<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public Mono<Payment> getById(Long id) {
        return paymentRepository.findById(id);
    }

    public Mono<Payment> create(Payment payment) {
        payment.setId(null);

        return accountRepository.existsById(payment.getAccountId())
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new IllegalArgumentException(
                                "Account does not exist"
                        ));
                    }

                    if (payment.getPaymentDate() == null) {
                        payment.setPaymentDate(java.time.LocalDateTime.now());
                    }

                    return paymentRepository.save(payment);
                });
    }

    public Mono<Payment> update(Long id, Payment updatedPayment) {
        return paymentRepository.findById(id)
                .flatMap(payment ->
                        accountRepository.existsById(
                                updatedPayment.getAccountId()
                        ).flatMap(exists -> {
                            if (!exists) {
                                return Mono.error(
                                        new IllegalArgumentException(
                                                "Account does not exist"
                                        )
                                );
                            }

                            payment.setAccountId(updatedPayment.getAccountId());
                            payment.setAmount(updatedPayment.getAmount());
                            payment.setPaymentDate(updatedPayment.getPaymentDate());
                            payment.setStatus(updatedPayment.getStatus());

                            return paymentRepository.save(payment);
                        })
                );
    }

    public Mono<Void> delete(Long id) {
        return paymentRepository.deleteById(id);
    }
}
