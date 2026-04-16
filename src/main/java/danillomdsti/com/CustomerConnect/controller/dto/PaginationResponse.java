package danillomdsti.com.CustomerConnect.controller.dto;

public record PaginationResponse(
        Integer page,
        Integer pageSize,
        Long totalElements,
        Integer totalPages
) {
}
