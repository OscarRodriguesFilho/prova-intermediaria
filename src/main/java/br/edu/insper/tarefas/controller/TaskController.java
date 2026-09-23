package br.edu.insper.tarefas.controller;

import br.edu.insper.tarefas.dto.CreateTaskRequest;
import br.edu.insper.tarefas.dto.TaskResponse;
import br.edu.insper.tarefas.dto.UpdateTaskRequest;
import br.edu.insper.tarefas.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@RequestBody CreateTaskRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<TaskResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public TaskResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id, @RequestBody UpdateTaskRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
