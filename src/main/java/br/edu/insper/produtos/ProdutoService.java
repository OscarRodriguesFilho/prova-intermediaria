package br.edu.insper.produtos;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ProdutoEventPublisher publisher;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoEventPublisher publisher) {
        this.produtoRepository = produtoRepository;
        this.publisher = publisher;
    }

    public ProdutoRequests.Response create(ProdutoRequests.Create request) {
        Produto produto = produtoRepository.save(new Produto(request.nome(), request.descricao(), request.preco(), request.quantidade()));
        publisher.publish(new ProdutoEvent(produto.getId(), AuditOperation.CREATE, produto.getQuantidade()));
        return ProdutoRequests.Response.from(produto);
    }

    @Transactional(readOnly = true)
    public List<ProdutoRequests.Response> list() {
        return produtoRepository.findAll().stream().map(ProdutoRequests.Response::from).toList();
    }

    @Transactional(readOnly = true)
    public ProdutoRequests.Response findById(Long id) {
        return ProdutoRequests.Response.from(findEntity(id));
    }

    public void delete(Long id) {
        Produto produto = findEntity(id);
        produtoRepository.delete(produto);
        publisher.publish(new ProdutoEvent(produto.getId(), AuditOperation.DELETE, produto.getQuantidade()));
    }

    private Produto findEntity(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
    }
}
