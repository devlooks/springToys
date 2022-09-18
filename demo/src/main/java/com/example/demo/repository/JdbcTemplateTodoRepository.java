package com.example.demo.repository;

import com.example.demo.domain.TodoContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JdbcTemplateTodoRepository implements TodoRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateTodoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TodoContext save(TodoContext todoContext) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todotbl").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", todoContext.getId());
        parameters.put("context", todoContext.getTodoContext());
        parameters.put("priority", todoContext.getPriority());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        todoContext.setId(key.longValue());
        return todoContext;
    }

    @Override
    public Optional<TodoContext> findById(Long id) {
        List<TodoContext> result = jdbcTemplate.query("select * from todotbl where id = ?", todoContextRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<TodoContext> findByContext(String context) {
        List<TodoContext> result = jdbcTemplate.query("select * from todotbl where context like '%" + context + "%'", todoContextRowMapper());
        return result.stream().collect(Collectors.toList());
    }

    @Override
    public List<TodoContext> findAll() {
        return jdbcTemplate.query("select * from todotbl", todoContextRowMapper());
    }

    @Override
    public List<TodoContext> sortedByPriority(String sort) {
        List<TodoContext> result = jdbcTemplate.query("select * from todotbl order by id " + sort, todoContextRowMapper());
        return result;
    }

    private RowMapper<TodoContext> todoContextRowMapper() {
        return (rs, rowNum) -> {
            TodoContext context = new TodoContext();
            context.setId(rs.getLong("id"));
            context.setTodoContext(rs.getString("context"));
            context.setPriority(rs.getInt("priority"));
            return context;
        };
    }
}
