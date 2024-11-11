package org.example.springbootdeveloper.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostTodoRequestDto {
    @NotNull
    private String task;

    @NotNull
    private String category;

    private String description;

    @NotNull
    private Boolean status;
}
