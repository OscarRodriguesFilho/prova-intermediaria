package br.edu.insper.tarefas.dto;

import br.edu.insper.tarefas.entity.TaskStatus;

public record UpdateTaskRequest(String title, String description, TaskStatus status) {
}
