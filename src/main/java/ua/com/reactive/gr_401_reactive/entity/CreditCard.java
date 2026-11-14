package ua.com.reactive.gr_401_reactive.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {

    private Long id;
    private Long clientId;
    private Long accountId;
    private String cardNumber;
    private String expirationDate;
}

