package br.edu.insper.tarefas;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.context.SpringBootTest; import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest class TaskServiceIT { @Autowired TaskService service; @Test void createsTaskUsingPostgres(){TaskRequests.Response task=service.create(new TaskRequests.Create("Estudar", "Teste de integração"));assertNotNull(task.id());assertEquals(TaskStatus.PENDING,task.status());} }
