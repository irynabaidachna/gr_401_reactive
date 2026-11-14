package ua.com.reactive.gr_401_reactive.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("accounts")
public class Account {

    @Id
    private Long id;

    private Long clientId;
    private BigDecimal balance;
    private boolean blocked;
}
