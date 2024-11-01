package org.example.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.common.constant.ResponseMessage;
import org.example.springbootdeveloper.dto.request.PostTodoRequestDto;
import org.example.springbootdeveloper.dto.request.PutTodoRequestDto;
import org.example.springbootdeveloper.dto.response.*;
import org.example.springbootdeveloper.entity.Todo;
import org.example.springbootdeveloper.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public ResponseDto<List<GetTodoListResponseDto>> getTodos() {
        List<GetTodoListResponseDto> data = null;

        try {
            List<Todo> todos = todoRepository.findAll();

            data = todos.stream()
                    .map(GetTodoListResponseDto::new)
                    .collect(Collectors.toList());

        } catch(Exception e) {
            e.printStackTrace();
            ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    public ResponseDto<PostTodoResponseDto> createTodo(PostTodoRequestDto dto) {
        PostTodoResponseDto data = null;

        try {
            Todo todo = Todo.builder()
                    .task(dto.getTask())
                    .status(dto.getStatus())
                    .build();

            todoRepository.save(todo);
            data = new PostTodoResponseDto(todo);

        }catch(Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    public ResponseDto<Void> updateTodo(Long id, PutTodoRequestDto dto) {
        Long todoId = id;

        try {
            Optional<Todo> optionalTodo = todoRepository.findById(todoId);
            if(optionalTodo.isPresent()) {
                Todo todo = optionalTodo.get();
                todo.setStatus(dto.getStatus());

                todoRepository.save(todo);
            } else {
                ResponseDto.setFailed(ResponseMessage.NOT_EXIST_DATA);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
    }

    public ResponseDto<Void> deleteTodo(Long id) {
        Long todoId = id;

        try{
        if(!todoRepository.existsById(todoId)) {
            return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_DATA);
        }
        todoRepository.deleteById(todoId);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
        }catch(Exception e) {
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }
}
