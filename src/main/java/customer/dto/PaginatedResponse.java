package customer.dto;


import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {
    private List<T> data;
    private long totalItems;
    private long totalPages;
    private long currentPage;
    private boolean hasNextPage;

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