package danillomdsti.com.CustomerConnect.service;

import danillomdsti.com.CustomerConnect.controller.dto.CustomersDTO;
import danillomdsti.com.CustomerConnect.controller.dto.UpdateCustomerDTO;
import danillomdsti.com.CustomerConnect.entity.CustomerEntity;
import danillomdsti.com.CustomerConnect.repository.CustomerRepository;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerEntity createCustomer(CustomersDTO customerDto) {
        if (customerRepository.existsByCpfCustomer(customerDto.cpf())) {
            throw new IllegalStateException("CPF ja cadastrado.");
        }

        if (customerRepository.existsByEmailCustomer(customerDto.email())) {
            throw new IllegalStateException("Email ja cadastrado.");
        }

        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setNameCustomer(customerDto.name());
        customerEntity.setCpfCustomer(customerDto.cpf());
        customerEntity.setEmailCustomer(customerDto.email());
        customerEntity.setCellphoneNumberCustomer(customerDto.phoneNumber());
        customerEntity.setCreateAtCustomer(LocalDateTime.now());
        customerEntity.setUpdateAtCustomer(LocalDateTime.now());

        return customerRepository.save(customerEntity);
    }

    public Page<CustomerEntity> findAllCustomers(
            Integer page,
            Integer pageSize,
            String orderBy,
            String cpf,
            String email
    ) {

        PageRequest pageRequest = getPageResquest(page, pageSize, orderBy);
        return findWithFilter(cpf, email, pageRequest);
    }

    public Optional<CustomerEntity> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<CustomerEntity> updateById(Long Id, UpdateCustomerDTO updateCustomerDTO) {
        var customer = findById(Id);

        if (customer.isPresent()) {
            validateUniqueFieldsOnUpdate(Id, updateCustomerDTO);
            updateFields(updateCustomerDTO, customer);
            customer.get().setUpdateAtCustomer(LocalDateTime.now());

            customerRepository.save(customer.get());
        }

        return customer;
    }

    public boolean deleteById(Long id) {
        var customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            return false;
        }

        customerRepository.delete(customer.get());
        return true;
    }

    private void validateUniqueFieldsOnUpdate(Long id, UpdateCustomerDTO updateCustomerDTO) {
        if (hasText(updateCustomerDTO.cpf())
                && customerRepository.existsByCpfCustomerAndIdCustomerNot(updateCustomerDTO.cpf(), id)) {
            throw new IllegalStateException("CPF ja cadastrado.");
        }

        if (hasText(updateCustomerDTO.email())
                && customerRepository.existsByEmailCustomerAndIdCustomerNot(updateCustomerDTO.email(), id)) {
            throw new IllegalStateException("Email ja cadastrado.");
        }
    }

    private static void updateFields(UpdateCustomerDTO updateCustomerDTO, Optional<CustomerEntity> customer) {
        if (hasText(updateCustomerDTO.name())) {
            customer.get().setNameCustomer(updateCustomerDTO.name());
        }
        if (hasText(updateCustomerDTO.cpf())) {
            customer.get().setCpfCustomer(updateCustomerDTO.cpf());
        }
        if (hasText(updateCustomerDTO.email())) {
            customer.get().setEmailCustomer(updateCustomerDTO.email());
        }
        if (hasText(updateCustomerDTO.phoneNumber())) {
            customer.get().setCellphoneNumberCustomer(updateCustomerDTO.phoneNumber());
        }
    }

    private Page<CustomerEntity> findWithFilter(String cpf, String email, PageRequest pageRequest) {
        String normalizedCpf = hasText(cpf) ? cpf : null;
        String normalizedEmail = hasText(email) ? email : null;

        return customerRepository.findByCpfAndEmail(normalizedCpf, normalizedEmail, pageRequest);
    }

    private PageRequest getPageResquest(Integer page, Integer pageSize, String orderBy) {
        if (page < 0) {
            throw new ValidationException("O parametro page deve ser maior ou igual a zero.");
        }

        if (pageSize < 1) {
            throw new ValidationException("O parametro pageSize deve ser maior que zero.");
        }

        if (!orderBy.equalsIgnoreCase("asc") && !orderBy.equalsIgnoreCase("desc")) {
            throw new ValidationException("O parametro orderBy deve ser 'asc' ou 'desc'.");
        }

        var direction = Sort.Direction.DESC;
        if (orderBy.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }

        return PageRequest.of(page, pageSize, direction, "createAtCustomer");
    }
}
