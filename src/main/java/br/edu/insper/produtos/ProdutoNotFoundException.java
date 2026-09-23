package br.edu.insper.produtos;

public class ProdutoNotFoundException extends RuntimeException {
    public ProdutoNotFoundException(Long id) {
        super("Produto " + id + " não encontrado");
    }
}
