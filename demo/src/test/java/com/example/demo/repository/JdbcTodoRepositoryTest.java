package com.example.demo.repository;

import com.example.demo.domain.TodoContext;
import com.example.demo.service.TodoContextService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JdbcTodoRepositoryTest {

    @Autowired
    TodoContextService todoContextService;
    
    @Autowired TodoRepository todoRepository;
    
    @Test
    void db_등록() {

        // Given
        TodoContext todoContext = new TodoContext();
        todoContext.setPriority(3);
        todoContext.setTodoContext("ㅅㄷㄴㅅ3");

        // When
        Long saveId = todoContextService.regist(todoContext);

        // Then
        TodoContext todoContext1 = todoRepository.findById(saveId).get();
        assertEquals(todoContext.getTodoContext(), todoContext.getTodoContext());

        // Print
        System.out.println(todoContext.toString());

    }

    @Test
    void db_id로조회() {
        // Given
        TodoContext todoContext = new TodoContext();
        todoContext.setId(1L);

        // When
        Optional<TodoContext> result = todoContextService.findById(todoContext.getId());

        // Then
        assertEquals(todoContext.getId(), result.get().getId());

        // Print
        System.out.println(todoContext);
        System.out.println(result.get().toString());
    }

    @Test
    void findAll() {

        //Given(No)

        // Just When
        List<TodoContext> byTodoAllContexts = todoContextService.findByTodoAllContexts();

        // Print
        System.out.println(byTodoAllContexts.toString());
    }

    @Test
    void 내용으로_검색() {

        // Given
        TodoContext test = new TodoContext();
        test.setTodoContext("2");

        // When
        List<TodoContext> byTodoContext = todoContextService.findByTodoContext(test.getTodoContext());

        // Then
        for (TodoContext todoContext : byTodoContext) {
            org.assertj.core.api.Assertions.assertThat(todoContext.getTodoContext()).contains(test.getTodoContext());
        }
    }

    @Test
    void 우선순위별_조회() {
        // Given
        String sort = "desc";

        // When
        List<TodoContext> byTodoAllContexts = todoContextService.findByTodoAllContexts()
                .stream()
                .sorted(Comparator.comparing(TodoContext::getPriority).reversed())
                .collect(Collectors.toList());
        List<TodoContext> todoContextsBySorted = todoContextService.findTodoContextsBySorted(sort);


        //Then
        for (int i = 0; i < byTodoAllContexts.size(); i++) {
            TodoContext todoContext1 = byTodoAllContexts.get(i);
            TodoContext todoContext2 = todoContextsBySorted.get(i);
            Assertions.assertEquals(todoContext1.getPriority(), todoContext2.getPriority());
        }

        //Print
        for (int i = 0; i < byTodoAllContexts.size(); i++) {
            TodoContext todoContext1 = byTodoAllContexts.get(i);
            TodoContext todoContext2 = todoContextsBySorted.get(i);
            System.out.println(todoContext1.getPriority() + " " + todoContext2.getPriority());
        }
    }
}