package ua.com.reactive.gr_401_reactive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_401_reactive.entity.RolesHasUsers;
import ua.com.reactive.gr_401_reactive.entity.User;
import ua.com.reactive.gr_401_reactive.repository.RoleRepository;
import ua.com.reactive.gr_401_reactive.repository.RolesHasUsersRepository;
import ua.com.reactive.gr_401_reactive.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RolesHasUsersRepository rolesHasUsersRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> register(String username, String password) {

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return Mono.error(
                    new IllegalArgumentException("Username and password are required")
            );
        }

        return userRepository.findByUsername(username)
                .flatMap(existing -> Mono.<User>error(
                        new IllegalArgumentException("Username already exists")
                ))
                .switchIfEmpty(Mono.defer(() -> {

                    User user = new User(
                            null,
                            username,
                            passwordEncoder.encode(password)
                    );

                    return userRepository.save(user);
                }))
                .flatMap(savedUser ->
                        roleRepository.findAll()
                                .filter(role -> "ROLE_USER".equals(role.getName()))
                                .next()
                                .switchIfEmpty(Mono.error(
                                        new IllegalStateException("ROLE_USER not found")
                                ))
                                .flatMap(role -> {
                                    RolesHasUsers link = new RolesHasUsers(
                                            null,
                                            role.getId(),
                                            savedUser.getId()
                                    );

                                    return rolesHasUsersRepository.save(link);
                                })
                                .thenReturn(savedUser)
                );
    }
}
