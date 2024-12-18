package org.example.springbootdeveloper.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springbootdeveloper.entity.Todo;

@Data
@NoArgsConstructor
public class PostTodoResponseDto {
    private Long id;
    private String task;
    private String category;
    private String description;
    private Boolean status;

    public PostTodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.task = todo.getTask();
        this.category = todo.getCategory();
        this.description = todo.getDescription();
        this.status = todo.getStatus();
    }
}
