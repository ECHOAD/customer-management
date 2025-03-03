package customer.domain;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * This class represents a customer
 */
@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "first_last_name", nullable = false)
    private String firstLastName;

    @Column(name = "second_last_name")
    private String secondLastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "demonym", nullable = false)
    private String demonym;

}
