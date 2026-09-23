package br.edu.insper.tarefas.repository;

import br.edu.insper.tarefas.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
