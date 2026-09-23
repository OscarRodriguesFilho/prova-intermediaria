package br.edu.insper.tarefas.service;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Tarefa " + id + " não encontrada");
    }
}
