package org.example.springbootdeveloper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class PagedResponseDto<T> {
    private List<T> content;
    private int currentPage;
    private int totalPages;
    private long totalElements;
}
