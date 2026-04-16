package danillomdsti.com.CustomerConnect.controller.dto;

import java.time.LocalDateTime;

public record CustomersDTO(
        String name,
        String cpf,
        String email,
        String phoneNumber,
        LocalDateTime createAt,
        LocalDateTime updateAt
) {
}
