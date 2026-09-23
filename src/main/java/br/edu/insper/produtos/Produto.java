package br.edu.insper.produtos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String nome;
    private String descricao;
    @DecimalMin(value = "0.0", inclusive = false) private BigDecimal preco;
    @Min(0) private Integer quantidade;

    protected Produto() { }

    public Produto(String nome, String descricao, BigDecimal preco, Integer quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public BigDecimal getPreco() { return preco; }
    public Integer getQuantidade() { return quantidade; }
}
