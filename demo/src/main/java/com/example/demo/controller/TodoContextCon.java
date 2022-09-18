package com.example.demo.controller;

import com.example.demo.domain.TodoContext;
import com.example.demo.service.TodoContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TodoContextCon {

    private TodoContextService todoContextService;

    @Autowired
    public TodoContextCon(TodoContextService todoContextService) {
        this.todoContextService = todoContextService;
    }

    /**
     * 메인 페이지
     * @return
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }

    /**
     * 등록 페이지
     * @return
     */
    @GetMapping(value = "/todo/new")
    public String createForm() {
        return "todo/createTodoForm";
    }

    /**
     * 등록 로직
     * @param form
     * @return
     */
    @PostMapping(value = "/todo/new")
    public String create(TodoForm form) {

        TodoContext context = new TodoContext();
        context.setPriority(form.getPriority());
        context.setTodoContext(form.getContext());

        todoContextService.regist(context);

        return "redirect:/";
    }

    /**
     * 조회 기능
     */
    @GetMapping(value = "/todos")
    public String list(Model model) {
        List<TodoContext> todos = todoContextService.findByTodoAllContexts();
        model.addAttribute("todos", todos);
        return "todo/todoListPage";
    }

    /**
     * 우선순위 별 조회
     */
    @GetMapping(value = "/sortedTodos")
    public String sortedList(@RequestParam("sorted") String sorted, Model model) {
        List<TodoContext> sortedTodos = todoContextService.findTodoContextsBySorted(sorted);
        model.addAttribute("todos", sortedTodos);
        return "todo/todoListPage";
    }

    /**
     * 내용별 조회
     */
    @PostMapping(value = "/searchTodo")
    public String searchTodo(@RequestParam("context") String context, Model model) {
        List<TodoContext> byTodoContext = todoContextService.findByTodoContext(context);
        model.addAttribute("todos", byTodoContext);
        return "todo/todoListPage";
    }

}
