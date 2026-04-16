package danillomdsti.com.CustomerConnect.repository;

import danillomdsti.com.CustomerConnect.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    boolean existsByCpfCustomer(String cpfCustomer);

    boolean existsByEmailCustomer(String emailCustomer);

    boolean existsByCpfCustomerAndIdCustomerNot(String cpfCustomer, Long idCustomer);

    boolean existsByEmailCustomerAndIdCustomerNot(String emailCustomer, Long idCustomer);

    @Query("""
        SELECT c
        FROM CustomerEntity c
        WHERE (:cpf IS NULL OR c.cpfCustomer = :cpf)
          AND (:email IS NULL OR c.emailCustomer = :email)
    """)
    Page<CustomerEntity> findByCpfAndEmail(
        @Param("cpf") String cpf,
        @Param("email") String email,
        Pageable pageable
    );
}
