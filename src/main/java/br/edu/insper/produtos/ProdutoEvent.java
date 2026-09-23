package br.edu.insper.produtos;

public record ProdutoEvent(Long produtoId, AuditOperation operation, int quantidade) { }
