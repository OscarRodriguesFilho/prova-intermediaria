package br.edu.insper.produtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public final class ProdutoRequests {
    private ProdutoRequests() { }

    public record Create(@NotBlank String nome, String descricao,
                         @DecimalMin(value = "0.0", inclusive = false) BigDecimal preco,
                         @Min(0) Integer quantidade) { }

    public record Response(Long id, String nome, String descricao, BigDecimal preco, Integer quantidade) {
        static Response from(Produto produto) {
            return new Response(produto.getId(), produto.getNome(), produto.getDescricao(),
                    produto.getPreco(), produto.getQuantidade());
        }
    }
}
