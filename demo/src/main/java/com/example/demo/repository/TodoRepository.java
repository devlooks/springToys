package com.example.demo.repository;

import com.example.demo.domain.TodoContext;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    TodoContext save(TodoContext todoContext);
    Optional<TodoContext> findById(Long id);
    List<TodoContext> findByContext(String context);
    List<TodoContext> findAll();
    List<TodoContext> sortedByPriority(String sort);

}
