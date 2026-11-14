package ua.com.reactive.gr_401_reactive.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_401_reactive.entity.Message;
import ua.com.reactive.gr_401_reactive.service.MessageService;

@RestController
@RequestMapping("/controller")
public class MainController {

    private final MessageService messageService;

    public MainController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public Flux<Message> list() {
        return messageService.list();
    }

    @PostMapping
    public Mono<Message> create(@RequestBody Message message) {
        return messageService.addOne(message);
    }
}
