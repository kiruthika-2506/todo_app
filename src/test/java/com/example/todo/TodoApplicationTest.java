package com.example.todo;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoApplicationTests {

    @Autowired
    private TodoRepository repo;

    @Test
    void testCRUD() {

        Todo todo = new Todo();
        todo.setTask("Test Task");
        todo.setCompleted(false);

        Todo saved = repo.save(todo);
        assertNotNull(saved.getId());

        saved.setTask("Updated Task");
        Todo updated = repo.save(saved);
        assertEquals("Updated Task", updated.getTask());

        repo.deleteById(saved.getId());
        assertFalse(repo.findById(saved.getId()).isPresent());
    }
}