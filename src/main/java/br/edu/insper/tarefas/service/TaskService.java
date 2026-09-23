package br.edu.insper.tarefas.service;

import br.edu.insper.tarefas.dto.CreateTaskRequest;
import br.edu.insper.tarefas.dto.TaskResponse;
import br.edu.insper.tarefas.dto.UpdateTaskRequest;
import br.edu.insper.tarefas.entity.Task;
import br.edu.insper.tarefas.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public TaskResponse create(CreateTaskRequest request) {
        validateTitle(request.title());
        return TaskResponse.from(repository.save(new Task(request.title().trim(), request.description())));
    }

    public List<TaskResponse> list() {
        return repository.findAll().stream().map(TaskResponse::from).toList();
    }

    public TaskResponse get(Long id) {
        return TaskResponse.from(find(id));
    }

    public TaskResponse update(Long id, UpdateTaskRequest request) {
        validateTitle(request.title());
        if (request.status() == null) {
            throw new IllegalArgumentException("Status é obrigatório");
        }
        Task task = find(id);
        task.update(request.title().trim(), request.description(), request.status());
        return TaskResponse.from(repository.save(task));
    }

    public void delete(Long id) {
        repository.delete(find(id));
    }

    private Task find(Long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Título é obrigatório");
        }
    }
}
