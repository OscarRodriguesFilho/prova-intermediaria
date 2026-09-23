package br.edu.insper.produtos;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Instant;

@Entity
public class AuditEvent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long produtoId;
    private Instant timestamp;
    @Enumerated(EnumType.STRING)
    private AuditOperation operation;

    protected AuditEvent() { }

    public AuditEvent(Long produtoId, AuditOperation operation) {
        this.produtoId = produtoId;
        this.operation = operation;
        this.timestamp = Instant.now();
    }

    public Long getId() { return id; }
    public Long getProdutoId() { return produtoId; }
    public Instant getTimestamp() { return timestamp; }
    public AuditOperation getOperation() { return operation; }
}
