package br.com.alura.marketplace.application.v1.dto.factory;

import static lombok.AccessLevel.PRIVATE;

import br.com.alura.marketplace.application.v1.dto.ProdutoDto;
import br.com.alura.marketplace.domain.entity.Produto.Status;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//não vai ser herdada por nenhum outra classe
@NoArgsConstructor(access = PRIVATE)
public final class ProdutoDtoFactory {

  public static Request criarProdutoDtoRequest() {
    return new Request(ProdutoDto.Request.builder());
  }

  @RequiredArgsConstructor(access = PRIVATE)
  public static class Request {

    private final ProdutoDto.Request.RequestBuilder builder;

    public ProdutoDto.Request comTodosOsCampos() {
      return builder
          .nome("Produto 1")
          .descricao("Descrição Produto 1")
          .status(Status.AVAILABLE)
          .build();
    }
  }

}