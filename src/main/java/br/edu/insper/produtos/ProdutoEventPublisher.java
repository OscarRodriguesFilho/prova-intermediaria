package br.edu.insper.produtos;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ProdutoEventPublisher {
    private final List<ProdutoObserver> observers;

    public ProdutoEventPublisher(List<ProdutoObserver> observers) {
        this.observers = observers;
    }

    public void publish(ProdutoEvent event) {
        observers.forEach(observer -> observer.onProdutoEvent(event));
    }
}
