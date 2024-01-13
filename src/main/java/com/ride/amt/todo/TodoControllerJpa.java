package com.ride.amt.todo;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("name")
public class TodoControllerJpa {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public TodoControllerJpa(TodoService todoService) {
        this.todoService = todoService;
    }

    private TodoService todoService;

    @RequestMapping("list-todos")
    public String listAllTodos(ModelMap model) {
        String username = getLoginUsername();
        logger.debug("list all todos \nusername: {}", username);

        List<Todo> todos = todoService.listAllTodos(username);
        logger.debug("list all todos: {}", todos.toString());
        model.addAttribute("todos", todos);

        return "listTodos";
    }

    @RequestMapping(value = "add-todo", method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap model) {
        String username = getLoginUsername();
        logger.debug("show new todo page\nusername: {}", username);
        Todo todo = new Todo(0, username, "", LocalDate.now(), false);
        model.put("todo", todo);
        return "todo";
    }

    @RequestMapping(value = "add-todo", method = RequestMethod.POST)
    public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            return "todo";
        }
        String username = getLoginUsername();
        String description = todo.getDescription();
        LocalDate targetDate = todo.getTargetDate();
        boolean done = todo.isDone();

        todo.setUsername(username);

        logger.debug("add new todo\nusername: {}\ndescription: {}\ntarget date: {}\ndone: {}", username, description, targetDate, done);

        todoService.addNewTodo(todo);
        return "redirect:list-todos";
    }

    @RequestMapping("delete-todo")
    public String deleteTodo(@RequestParam int id) {
        logger.debug("delete todo\nid: {}", id);
        todoService.deleteTodo(id);
        return "redirect:list-todos";
    }

    @RequestMapping(value = "update-todo", method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
        Todo todo = todoService.showTodo(id);
        logger.debug("show update todo page\nid: {}\ntodo: {}", id, todo);
        model.addAttribute("todo", todo);
        return "todo";
    }

    @RequestMapping(value = "update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            return "todo";
        }
        String username = getLoginUsername();
        todo.setUsername(username);
        logger.debug("update todo\ntodo: {}", todo);
        todoService.updateTodo(todo);
        return "redirect:list-todos";
    }

    private String getLoginUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        logger.debug("get login username \nusername: {}", username);
        return username;
    }

}
