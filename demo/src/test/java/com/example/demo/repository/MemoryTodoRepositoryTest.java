package com.example.demo.repository;

import com.example.demo.domain.TodoContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemoryTodoRepositoryTest {

    MemoryTodoRepository repository = new MemoryTodoRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    void save() {
        // given
        TodoContext todoContext = new TodoContext();
        todoContext.setPriority(1);
        todoContext.setTodoContext("뭘 만들지 생각하기");

        //when
        repository.save(todoContext);

        // then
        TodoContext result = repository.findById(todoContext.getId()).get();
        Assertions.assertThat(result).isEqualTo(todoContext);
    }

    @Test
    void findByContext(){
        // given
        TodoContext todoContext1 = new TodoContext();
        todoContext1.setTodoContext("뭘 만들지 하기");
        todoContext1.setPriority(0);
        repository.save(todoContext1);

        TodoContext todoContext2 = new TodoContext();
        todoContext2.setPriority(1);
        todoContext2.setTodoContext("생각하고 있니?");
        repository.save(todoContext2);

        // when
        List<TodoContext> result = repository.findByContext("생각");

        // print
        System.out.println(result.toString());

        // then
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findAll() {
        // given
        TodoContext todoContext1 = new TodoContext();
        todoContext1.setTodoContext("뭘 만들지 생각하기");
        todoContext1.setPriority(0);
        repository.save(todoContext1);

        TodoContext todoContext2 = new TodoContext();
        todoContext2.setTodoContext("단계별 구상");
        todoContext2.setPriority(0);
        repository.save(todoContext2);

        TodoContext todoContext3 = new TodoContext();
        todoContext3.setTodoContext("단계별 실행");
        todoContext3.setPriority(0);
        repository.save(todoContext3);

        // when
        List<TodoContext> result = repository.findAll();

        // print
        result.stream().forEach(System.out::println);

        // then
        Assertions.assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void findByPriority() {

        ArrayList<TodoContext> arr = new ArrayList<>();

        // given
        TodoContext todoContext0 = new TodoContext();
        todoContext0.setTodoContext("뭘 만들지 생각하기");
        todoContext0.setPriority(0);
        repository.save(todoContext0);

        TodoContext todoContext1 = new TodoContext();
        todoContext1.setTodoContext("단계별 구상");
        todoContext1.setPriority(1);
        repository.save(todoContext1);

        TodoContext todoContext2 = new TodoContext();
        todoContext2.setTodoContext("단계별 실행");
        todoContext2.setPriority(2);
        repository.save(todoContext2);

        TodoContext todoContext3 = new TodoContext();
        todoContext3.setTodoContext("글로 써보기");
        todoContext3.setPriority(3);
        repository.save(todoContext3);

        arr.add(todoContext3);
        arr.add(todoContext2);
        arr.add(todoContext1);
        arr.add(todoContext0);

        // when
        List<TodoContext> descObj = repository.sortedByPriority("desc");

        // print
        System.out.println("arr : " + arr);
        System.out.println("descObj : " + descObj);

        // then
        Assertions.assertThat(descObj).isEqualTo(arr);
    }
}