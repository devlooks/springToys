package com.example.demo.repository;

import com.example.demo.domain.TodoContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaTodoRepository extends JpaRepository<TodoContext, Long>, TodoRepository {


}
