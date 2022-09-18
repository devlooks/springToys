package com.example.demo.service;

import com.example.demo.domain.TodoContext;
import com.example.demo.repository.MemoryTodoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoContextServiceTest {

    TodoContextService todoContextService;
    MemoryTodoRepository todoRepository;

    @BeforeEach
    public void beforeEach() {
        todoRepository = new MemoryTodoRepository();
        todoContextService = new TodoContextService(todoRepository);
    }

    @AfterEach
    public void afterEach() {
        todoRepository.clearStore();
    }

    @Test
    void 할일_등록() {

        // Given
        TodoContext todoContext = new TodoContext();
        todoContext.setTodoContext("첫번째, 무엇을 할지 생각하라");
        todoContext.setPriority(0);

        // when
        Long registid = todoContextService.regist(todoContext);

        // then
        TodoContext result = todoRepository.findById(registid).get();
        assertEquals(todoContext.getTodoContext(), result.getTodoContext());

        // print
        System.out.println(todoContext.getTodoContext());
        System.out.println(result.getTodoContext());

    }

    @Test
    void 우선순위_중복_체크() {

        // Given
        TodoContext todoContext = new TodoContext();
        todoContext.setPriority(1);

        TodoContext todoContext1 = new TodoContext();
        todoContext1.setPriority(1);

        // when
        todoContextService.regist(todoContext);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> todoContextService.regist(todoContext1));

        // then
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 우선순위 입니다.");
    }

}