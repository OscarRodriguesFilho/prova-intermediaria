package br.edu.insper.produtos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LowStockObserver implements ProdutoObserver {
    private static final Logger log = LoggerFactory.getLogger(LowStockObserver.class);

    @Override
    public void onProdutoEvent(ProdutoEvent event) {
        if (event.operation() == AuditOperation.CREATE && event.quantidade() < 10) {
            log.warn("Estoque baixo para o produto {}: {} unidade(s) disponível(is)",
                    event.produtoId(), event.quantidade());
        }
    }
}
