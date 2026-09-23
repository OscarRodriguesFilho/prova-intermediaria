package br.edu.insper.transacoes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransactionServiceTest {
    private final TransactionRepository repository = mock(TransactionRepository.class);
    private final TransactionService service = new TransactionService(repository, "http://localhost:8090");

    @Test
    void calculatesTotalValueInBackend() {
        Transaction transaction = transaction();

        assertEquals(new BigDecimal("61.00"), transaction.getTotalValue());
    }

    @Test
    void listsAllTransactionsWithoutClientFilter() {
        when(repository.findAll()).thenReturn(List.of(transaction()));

        List<TransactionResponse> result = service.list(null);

        assertEquals(1, result.size());
        assertEquals("PETR4", result.getFirst().stockCode());
        verify(repository).findAll();
    }

    @Test
    void filtersTransactionsByClientId() {
        when(repository.findByClientId(7L)).thenReturn(List.of(transaction()));

        assertEquals(1, service.list(7L).size());
        verify(repository).findByClientId(7L);
    }

    @Test
    void deletesExistingTransaction() {
        Transaction transaction = transaction();
        when(repository.findById(3L)).thenReturn(Optional.of(transaction));

        service.delete(3L);

        verify(repository).delete(transaction);
    }

    @Test
    void returnsNotFoundWhenDeletingUnknownTransaction() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TransactionService.TransactionMissing.class, () -> service.delete(99L));
    }

    private Transaction transaction() {
        return new Transaction(7L, "cliente@exemplo.com", "PETR4", 2,
                new BigDecimal("30.50"), LocalDateTime.of(2026, 9, 23, 10, 0));
    }
}
