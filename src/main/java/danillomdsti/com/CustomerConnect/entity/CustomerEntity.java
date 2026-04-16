package danillomdsti.com.CustomerConnect.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "tb_customers",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_customer_cpf", columnNames = "cpf_customer"),
        @UniqueConstraint(name = "uk_customer_email", columnNames = "email_customer")
    }
)
public class CustomerEntity {

    public CustomerEntity() {}

    @Id
    @Column(name = "id_customer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCustomer;

    @Column(name = "name_customer")
    private String nameCustomer;

    @Column(name = "cpf_customer", nullable = false, unique = true)
    private String cpfCustomer;

    @Column(name = "email_customer", nullable = false, unique = true)
    private String emailCustomer;

    @Column(name = "cellphoneNumber_customer")
    private String cellphoneNumberCustomer;

    @Column(name = "createAt_customer")
    private LocalDateTime createAtCustomer;

    @Column(name = "updateAt_customer")
    private LocalDateTime updateAtCustomer;

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getCpfCustomer() {
        return cpfCustomer;
    }

    public void setCpfCustomer(String cpfCustomer) {
        this.cpfCustomer = cpfCustomer;
    }

    public String getEmailCustomer() {
        return emailCustomer;
    }

    public void setEmailCustomer(String emailCustomer) {
        this.emailCustomer = emailCustomer;
    }

    public String getCellphoneNumberCustomer() {
        return cellphoneNumberCustomer;
    }

    public void setCellphoneNumberCustomer(String cellphoneNumberCustomer) {
        this.cellphoneNumberCustomer = cellphoneNumberCustomer;
    }

    public LocalDateTime getCreateAtCustomer() {
        return createAtCustomer;
    }

    public void setCreateAtCustomer(LocalDateTime createAtCustomer) {
        this.createAtCustomer = createAtCustomer;
    }

    public LocalDateTime getUpdateAtCustomer() {
        return updateAtCustomer;
    }

    public void setUpdateAtCustomer(LocalDateTime updateAtCustomer) {
        this.updateAtCustomer = updateAtCustomer;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }
}
