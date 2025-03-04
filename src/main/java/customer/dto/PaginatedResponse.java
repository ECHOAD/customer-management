package customer.dto;


import lombok.Data;

import java.util.List;

/**
 * Represents a paginated response with metadata.
 *
 * @param <T> the type of data in the response
 */
@Data
public class PaginatedResponse<T> {
    private List<T> data;
    private long totalItems;
    private long totalPages;
    private long currentPage;
    private boolean hasNextPage;

    /**
     * Constructs a new PaginatedResponse.
     *
     * @param data        the list of data items
     * @param totalItems  the total number of items available
     * @param totalPages  the total number of pages available
     * @param currentPage the current page number
     * @param hasNextPage whether there is a next page available
     */
    public PaginatedResponse(List<T> data, long totalItems, long totalPages, long currentPage, boolean hasNextPage) {
        this.data = data;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.hasNextPage = hasNextPage;
    }

    public static PaginatedResponse<?> empty() {
        return new PaginatedResponse<>(null, 0, 0, 0, false);
    }
}
