package br.com.alura.marketplace.domain.entity.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.alura.marketplace.domain.entity.Produto;
import br.com.alura.marketplace.domain.entity.Produto.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProdutoAssertions {

  private final Produto resultado;

  public static ProdutoAssertions afirmaQue_Produto(Produto resultado) {
    return new ProdutoAssertions(resultado);
  }

  /**
   * Forma de
   * @see br.com.alura.marketplace.application.v1.dto.factory.ProdutoDtoFactory
   * .comTodosOsCampos()
   */
  public void foiConvertidoDe_ProdutoDto_Request() {
    assertThat(resultado.getNome()).isEqualTo("Produto 1");
    assertThat(resultado.getDescricao()).isEqualTo("Descrição Produto 1");
    assertThat(resultado.getStatus()).isEqualTo(Status.AVAILABLE);
  }
}