package br.edu.insper.produtos;

import org.springframework.stereotype.Component;

@Component
public class AuditObserver implements ProdutoObserver {
    private final AuditEventRepository repository;

    public AuditObserver(AuditEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onProdutoEvent(ProdutoEvent event) {
        repository.save(new AuditEvent(event.produtoId(), event.operation()));
    }
}
