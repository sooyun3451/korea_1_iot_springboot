package org.example.springbootdeveloper.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.dto.request.PostRequestDto;
import org.example.springbootdeveloper.dto.request.PostTodoRequestDto;
import org.example.springbootdeveloper.dto.request.PutTodoRequestDto;
import org.example.springbootdeveloper.dto.response.*;
import org.example.springbootdeveloper.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    public static final String TODO_UPDATE = "/{id}";
    public static final String TODO_DELETE = "/{id}";

    private final TodoService todoService;

    @GetMapping()
    public ResponseEntity<ResponseDto<List<GetTodoListResponseDto>>> getTodos() {
        ResponseDto<List<GetTodoListResponseDto>> result = todoService.getTodos();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping()
    public ResponseEntity<ResponseDto<PostTodoResponseDto>> createTodo(@Valid @RequestBody PostTodoRequestDto dto) {
        ResponseDto<PostTodoResponseDto> result = todoService.createTodo(dto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping(TODO_UPDATE)
    public ResponseEntity<ResponseDto<Void>> updateTodo(@PathVariable Long id, @Valid @RequestBody PutTodoRequestDto dto) {
        ResponseDto<Void> result = todoService.updateTodo(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping(TODO_DELETE)
    public ResponseEntity<ResponseDto<Void>> deleteTodo(@PathVariable Long id) {
        ResponseDto<Void> result = todoService.deleteTodo(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
