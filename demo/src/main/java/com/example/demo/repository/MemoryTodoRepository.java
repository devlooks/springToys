package com.example.demo.repository;

import com.example.demo.domain.TodoContext;

import java.util.*;
import java.util.stream.Collectors;


public class MemoryTodoRepository implements TodoRepository{

    private static Map<Long, TodoContext> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public TodoContext save(TodoContext todoContext) {
        todoContext.setId(++sequence);
        store.put(todoContext.getId(), todoContext);
        return todoContext;
    }

    @Override
    public Optional<TodoContext> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<TodoContext> findByContext(String context) {
        return store.values().stream()
                .filter(todoContext -> todoContext.getTodoContext().contains(context))
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoContext> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<TodoContext> sortedByPriority(String sort) {

        List<TodoContext> collect = store.values()
                .stream().sorted(Comparator.comparing(TodoContext::getPriority).reversed())
                .collect(Collectors.toList());

        List<TodoContext> collect1 = store.values()
                .stream().sorted(Comparator.comparing(TodoContext::getPriority))
                .collect(Collectors.toList());

        if ("desc".equals(sort)) {
            return collect;
        } else {
            return collect1;
        }
    }

    public void clearStore() {
        store.clear();
    }
}
