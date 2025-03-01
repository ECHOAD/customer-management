package customer.repository;

import customer.domain.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


/**
 * Implementation of customer repository
 * @see PanacheRepository
 */
@ApplicationScoped
public class CustomerPanacheRepository implements PanacheRepository<Customer> {
}
