package br.edu.insper.tarefas.service;

import br.edu.insper.tarefas.dto.CreateTaskRequest;
import br.edu.insper.tarefas.dto.TaskResponse;
import br.edu.insper.tarefas.dto.UpdateTaskRequest;
import br.edu.insper.tarefas.entity.Task;
import br.edu.insper.tarefas.entity.TaskStatus;
import br.edu.insper.tarefas.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskServiceTest {
    private final TaskRepository repository = mock(TaskRepository.class);
    private final TaskService service = new TaskService(repository);

    @Test
    void createsPendingTaskWithTrimmedTitle() {
        when(repository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponse response = service.create(new CreateTaskRequest("  Estudar Spring  ", "Revisar controllers"));

        assertEquals("Estudar Spring", response.title());
        assertEquals(TaskStatus.PENDING, response.status());
        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(repository).save(captor.capture());
        assertEquals("Estudar Spring", captor.getValue().getTitle());
    }

    @Test
    void rejectsTaskWithoutTitle() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.create(new CreateTaskRequest(" ", "descrição"))
        );

        assertEquals("Título é obrigatório", exception.getMessage());
    }

    @Test
    void updatesExistingTask() {
        Task task = new Task("Versão inicial", "descrição inicial");
        when(repository.findById(5L)).thenReturn(Optional.of(task));
        when(repository.save(task)).thenReturn(task);

        TaskResponse response = service.update(5L,
                new UpdateTaskRequest("Versão final", "descrição final", TaskStatus.DONE));

        assertEquals("Versão final", response.title());
        assertEquals(TaskStatus.DONE, response.status());
        verify(repository).save(task);
    }

    @Test
    void throwsWhenTaskDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> service.get(99L));
    }
}
