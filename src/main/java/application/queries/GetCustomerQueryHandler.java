package application.queries;

import domain.Customer;
import interfaces.dto.CustomerResponseDTO;
import interfaces.dto.PaginatedResponse;
import interfaces.mappers.CustomerMapper;
import infrastructure.repository.CustomerPanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import mediator.QueryHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class GetCustomerQueryHandler implements QueryHandler<GetCustomersQuery, PaginatedResponse<CustomerResponseDTO>> {
    private final CustomerPanacheRepository customerRepository;

    @Inject
    public GetCustomerQueryHandler(CustomerPanacheRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Class<GetCustomersQuery> getType() {
        return GetCustomersQuery.class;
    }


    @Override
    public PaginatedResponse<CustomerResponseDTO> apply(GetCustomersQuery getCustomersQuery) {

        Map<String, Object> params = new HashMap<>();
        params.put("country", getCustomersQuery.getCountry());

        params = params.entrySet().stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


        String query = params.keySet().stream()
                .map(o -> o + "=:" + o)
                .collect(Collectors.joining(" and "));

        PanacheQuery<Customer> filteredCustomerQuery = customerRepository
                .find(query, params)
                .page(Page.of(getCustomersQuery.getPage() - 1, getCustomersQuery.getLimit()));


        return toPaginatedResponse(filteredCustomerQuery);
    }

    private PaginatedResponse<CustomerResponseDTO> toPaginatedResponse(PanacheQuery<Customer> customerQuery) {

        final long totalPages = (long) Math.ceil((double) customerQuery.count() / customerQuery.page().size);
        final long currentPage = (long) customerQuery.page().index + 1;
        List<CustomerResponseDTO> customers = customerQuery.list().stream()
                .map(CustomerMapper::toDTO)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(customers, customers.size(), totalPages, currentPage, customerQuery.hasNextPage());
    }
}
