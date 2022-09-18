package com.example.demo.config;

import com.example.demo.repository.JdbcTemplateTodoRepository;
import com.example.demo.repository.JdbcTodoRepository;
import com.example.demo.repository.JpaTodoRepository;
import com.example.demo.repository.TodoRepository;
import com.example.demo.service.TodoContextService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class WebConfig {

    private final DataSource dataSource;
    private final EntityManager em;

//    public WebConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    public WebConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }

    @Bean
    public TodoContextService todoContextService() {

        return new TodoContextService(todoRepository());
    }

    @Bean
    public TodoRepository todoRepository() {

        // return new MemoryTodoRepository();
        // return new JdbcTodoRepository(dataSource);
        // return new JdbcTemplateTodoRepository(dataSource);
        return new JpaTodoRepository(em);
    }
}
