package br.edu.insper.produtos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProdutoServiceIT {
    @Autowired private ProdutoService service;
    @Autowired private AuditEventRepository auditRepository;

    @Test
    void persistsProductAndAuditsCreateAndDelete() {
        ProdutoRequests.Response produto = service.create(
                new ProdutoRequests.Create("Caderno", "Caderno universitário", new BigDecimal("19.90"), 5));

        assertNotNull(produto.id());
        assertEquals("Caderno", service.findById(produto.id()).nome());
        assertEquals(List.of(AuditOperation.CREATE), operations(produto.id()));

        service.delete(produto.id());

        assertEquals(List.of(AuditOperation.CREATE, AuditOperation.DELETE), operations(produto.id()));
    }

    private List<AuditOperation> operations(Long produtoId) {
        return auditRepository.findByProdutoIdOrderByTimestampAsc(produtoId)
                .stream().map(AuditEvent::getOperation).toList();
    }
}
