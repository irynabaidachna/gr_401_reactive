package ua.com.reactive.gr_401_reactive.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("roles_has_users")
public class RolesHasUsers {

    @Id
    private Long id;

    private Long roleId;
    private Long userId;
}
