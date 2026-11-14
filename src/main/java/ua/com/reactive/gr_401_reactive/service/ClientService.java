package ua.com.reactive.gr_401_reactive.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_401_reactive.entity.Client;
import ua.com.reactive.gr_401_reactive.repository.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Flux<Client> getAll() {
        return clientRepository.findAll();
    }

    public Mono<Client> getById(Long id) {
        return clientRepository.findById(id);
    }

    public Mono<Client> create(Client client) {
        client.setId(null);
        return clientRepository.save(client);
    }

    public Mono<Client> update(Long id, Client updatedClient) {
        return clientRepository.findById(id)
                .flatMap(client -> {
                    client.setFirstName(updatedClient.getFirstName());
                    client.setLastName(updatedClient.getLastName());
                    client.setAge(updatedClient.getAge());
                    return clientRepository.save(client);
                });
    }

    public Mono<Void> delete(Long id) {
        return clientRepository.deleteById(id);
    }
}

