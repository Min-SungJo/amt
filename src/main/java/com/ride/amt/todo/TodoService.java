package com.ride.amt.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private TodoRepository todoRepository;

    public List<Todo> listAllTodos(String username) {
        return todoRepository.findByUsername(username);
    }

    public Todo showTodo(int id) {
        return todoRepository.findById(id).get();
    }

    public void addNewTodo(Todo todo) {
        todoRepository.save(todo);
    }

    public void deleteTodo(int id) {
        todoRepository.deleteById(id);
    }

    public void updateTodo(Todo todo) {
        todoRepository.save(todo);
    }


}
