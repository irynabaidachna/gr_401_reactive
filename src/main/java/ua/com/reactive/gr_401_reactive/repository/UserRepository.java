package ua.com.reactive.gr_401_reactive.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ua.com.reactive.gr_401_reactive.entity.User;
import reactor.core.publisher.Mono;

public interface UserRepository
        extends ReactiveCrudRepository<User, Long> {

    @Query("SELECT id, username, password FROM users WHERE username = :username")
    Mono<User> findByUsername(String username);
}
