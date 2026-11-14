package ua.com.reactive.gr_401_reactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ua.com.reactive.gr_401_reactive.entity.Account;

public interface AccountRepository
        extends ReactiveCrudRepository<Account, Long> {
}
