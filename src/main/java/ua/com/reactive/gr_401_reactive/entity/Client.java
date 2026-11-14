package ua.com.reactive.gr_401_reactive.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("clients")
public class Client {

    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private int age;
}