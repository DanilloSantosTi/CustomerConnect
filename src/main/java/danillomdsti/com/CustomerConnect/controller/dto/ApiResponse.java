package danillomdsti.com.CustomerConnect.controller.dto;

import java.util.List;

public record ApiResponse<T>(List<T> data, PaginationResponse paginationResponse) {
}
