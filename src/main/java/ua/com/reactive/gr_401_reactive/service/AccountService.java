package ua.com.reactive.gr_401_reactive.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_401_reactive.entity.Account;
import ua.com.reactive.gr_401_reactive.repository.AccountRepository;
import ua.com.reactive.gr_401_reactive.repository.ClientRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountService(AccountRepository accountRepository,
                          ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public Flux<Account> getAll() {
        return accountRepository.findAll();
    }

    public Mono<Account> getById(Long id) {
        return accountRepository.findById(id);
    }

    public Mono<Account> create(Account account) {
        account.setId(null);

        return clientRepository.existsById(account.getClientId())
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(
                                new IllegalArgumentException(
                                        "Client does not exist"
                                )
                        );
                    }
                    return accountRepository.save(account);
                });
    }

    public Mono<Account> update(Long id, Account updatedAccount) {
        return accountRepository.findById(id)
                .flatMap(account ->
                        clientRepository.existsById(
                                updatedAccount.getClientId()
                        ).flatMap(exists -> {
                            if (!exists) {
                                return Mono.error(
                                        new IllegalArgumentException(
                                                "Client does not exist"
                                        )
                                );
                            }

                            account.setClientId(updatedAccount.getClientId());
                            account.setBalance(updatedAccount.getBalance());
                            account.setBlocked(updatedAccount.isBlocked());

                            return accountRepository.save(account);
                        })
                );
    }

    public Mono<Void> delete(Long id) {
        return accountRepository.deleteById(id);
    }
}
