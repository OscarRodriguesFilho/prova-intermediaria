package br.edu.insper.transacoes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TransacoesApplication.class)
class TransactionRepositoryIT {
    @Autowired TransactionRepository repository;

    @Test
    void persistsTransactionInPostgres() {
        Transaction saved = repository.save(new Transaction(1L, "cliente@exemplo.com", "PETR4", 2,
                new BigDecimal("30.50"), LocalDateTime.of(2026, 9, 23, 10, 0)));
        assertNotNull(saved.getId());
        assertEquals(new BigDecimal("61.00"), saved.getTotalValue());
    }
}
