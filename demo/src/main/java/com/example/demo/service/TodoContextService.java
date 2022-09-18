package com.example.demo.service;

import com.example.demo.domain.TodoContext;
import com.example.demo.repository.TodoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class TodoContextService {

    // private final TodoRepository todoRepository = new MemoryTodoRepository();

    private final TodoRepository repository;

    public TodoContextService(TodoRepository repository) {
        this.repository = repository;
    }

    /**
     * 등록
     */
    public Long regist(TodoContext todoContext) {
        validationPriorityContext(todoContext);
        repository.save(todoContext);
        return todoContext.getId();
    }

    /**
     * 전체 내역 조회
     */
    public List<TodoContext> findByTodoAllContexts() {
        return repository.findAll();
    }

    /**
     * context 내용 조회
     */
    public List<TodoContext> findByTodoContext(String context) {
        return repository.findByContext(context);
    }

    /**
     * id로 조회하기
     */
    public Optional<TodoContext> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * sorting 하기
     * param( desc or asc)
     */
    public List<TodoContext> findTodoContextsBySorted(String sorted) {
        return repository.sortedByPriority(sorted);
    }

    /**
     * 우선 순위 중복 체크
     * @param todoContext
     */
    private void validationPriorityContext(TodoContext todoContext) {
        repository.findAll().forEach(obj -> {
            if (todoContext.getPriority() == obj.getPriority()) {

                throw new IllegalStateException("이미 존재하는 우선순위 입니다.");
            }
        });
    }


}
