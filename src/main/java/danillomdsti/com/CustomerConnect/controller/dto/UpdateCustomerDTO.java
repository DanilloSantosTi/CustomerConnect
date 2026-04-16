package danillomdsti.com.CustomerConnect.controller.dto;

public record UpdateCustomerDTO(
        String cpf,
        String name,
        String email,
        String phoneNumber
) {
}
