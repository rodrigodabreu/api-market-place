package br.com.alura.marketplace.infra.entity.factory;

import static lombok.AccessLevel.PRIVATE;

import br.com.alura.marketplace.domain.entity.Produto;
import br.com.alura.marketplace.domain.entity.Produto.Status;
import java.math.BigDecimal;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class ProdutoFactory {

  public static Builder criarProduto() {
    return new Builder(Produto.builder());
  }

  @RequiredArgsConstructor(access = PRIVATE)
  public static class Builder {

    private final Produto.ProdutoBuilder builder;

    public Produto comTodosOsCampos() {
      return builder
          .nome("Produto Teste")
          .categoria("Categoria Teste")
          .status(Status.AVAILABLE)
          .descricao("Descrição do produto de teste")
          .valor(BigDecimal.valueOf(99.99))
          .build();
    }

    public Produto comNome(String nome) {
      return builder
          .nome(nome)
          .categoria("Categoria Teste")
          .status(Status.AVAILABLE)
          .descricao("Descrição do produto de teste")
          .valor(BigDecimal.valueOf(99.99))
          .build();
    }

    public Builder nome(String nome) {
      builder.nome(nome);
      return this;
    }

    public Builder categoria(String categoria) {
      builder.categoria(categoria);
      return this;
    }

    public Builder status(Status status) {
      builder.status(status);
      return this;
    }

    public Builder descricao(String descricao) {
      builder.descricao(descricao);
      return this;
    }

    public Builder valor(BigDecimal valor) {
      builder.valor(valor);
      return this;
    }

    public Produto build() {
      return builder.build();
    }
  }
}
