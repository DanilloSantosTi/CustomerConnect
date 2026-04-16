package danillomdsti.com.CustomerConnect.controller;

import danillomdsti.com.CustomerConnect.controller.dto.ApiResponse;
import danillomdsti.com.CustomerConnect.controller.dto.CustomersDTO;
import danillomdsti.com.CustomerConnect.controller.dto.PaginationResponse;
import danillomdsti.com.CustomerConnect.controller.dto.UpdateCustomerDTO;
import danillomdsti.com.CustomerConnect.entity.CustomerEntity;
import danillomdsti.com.CustomerConnect.service.CustomerService;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class UserController {

    private final CustomerService customerService;

    public UserController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody CustomersDTO customersDTO) {
        var customer = customerService.createCustomer(customersDTO);

        return ResponseEntity.created(URI.create("/customers/" + customer.getIdCustomer())).build();
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CustomerEntity>> getAllCustomers(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                       @RequestParam(name = "orderBy", defaultValue = "desc") String orderBy,
                                                                       @RequestParam(name = "cpf", required = false) String cpf,
                                                                       @RequestParam(name = "email", required = false) String email) {

        var pageResponse = customerService.findAllCustomers(page, pageSize, orderBy, cpf, email);

        return ResponseEntity.ok(new ApiResponse<>(
                pageResponse.getContent(),
                new PaginationResponse(
                        pageResponse.getNumber(),
                        pageResponse.getSize(),
                        pageResponse.getTotalElements(),
                        pageResponse.getTotalPages()
                )
        ));
    }

    @PutMapping(path = "/{customerId}")
    public ResponseEntity<Void> updateCustomer(
            @PathVariable("customerId") Long customerId,
            @RequestBody UpdateCustomerDTO updateCustomerDTO
    ) {
        var customer = customerService.updateById(customerId, updateCustomerDTO);

        return customer.isPresent() ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") Long customerId) {
        var customerDeleted = customerService.deleteById(customerId);

        return customerDeleted ?
                ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
