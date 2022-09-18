package com.example.demo.domain;

import javax.persistence.*;

@Entity(name = "todotbl")
public class TodoContext {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "context")
    private String todoContext;

    private int priority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTodoContext() {
        return todoContext;
    }

    public void setTodoContext(String todoContext) {
        this.todoContext = todoContext;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "TodoContext{" +
                "id=" + id +
                ", todoContext='" + todoContext + '\'' +
                ", Priority=" + priority +
                '}';
    }
}
