
package ua.com.reactive.gr_401_reactive.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("payments")
public class Payment {

    @Id
    private Long id;

    private Long accountId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String status;
}
