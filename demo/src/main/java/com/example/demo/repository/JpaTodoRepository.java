package com.example.demo.repository;

import com.example.demo.domain.TodoContext;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JpaTodoRepository implements TodoRepository{

    private final EntityManager em;

    public JpaTodoRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public TodoContext save(TodoContext todoContext) {
        em.persist(todoContext);
        return todoContext;
    }

    @Override
    public Optional<TodoContext> findById(Long id) {
        TodoContext todoContext = em.find(TodoContext.class, id);
        return Optional.ofNullable(todoContext);
    }

    @Override
    public List<TodoContext> findByContext(String context) {
        return em.createQuery("select t from todotbl t where t.todoContext = :context", TodoContext.class)
                .setParameter("context", context)
                .getResultList();
    }

    @Override
    public List<TodoContext> findAll() {
        return em.createQuery("select t from todotbl t", TodoContext.class)
                .getResultList();
    }

    @Override
    public List<TodoContext> sortedByPriority(String sort) {
        List<TodoContext> result = em.createQuery("select t from todotbl t", TodoContext.class)
                .getResultList();

        List<TodoContext> collect = null;

        if (sort == "desc") {
            collect = result.stream().sorted(Comparator.comparing(TodoContext::getPriority).reversed()).collect(Collectors.toList());
        } else {
            collect = result.stream().sorted(Comparator.comparing(TodoContext::getPriority)).collect(Collectors.toList());
        }
        
        return collect;
    }
}
