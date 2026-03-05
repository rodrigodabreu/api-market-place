package br.com.alura.marketplace.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.alura.marketplace.domain.entity.Produto;
import br.com.alura.marketplace.infra.config.JpaConfig;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@DataJpaTest
@ContextConfiguration(classes = JpaConfig.class)
class ProdutoRepositoryExtTest {

  @Autowired
  TestEntityManager entityManager;

  @Autowired
  ProdutoRepositoryExt repository;

  @DisplayName("Quando buscar um produto por nome")
  @Nested
  class FindByNome {

    @DisplayName("Então deve encontrar com sucesso")
    @Nested
    class Sucesso {

      @Test
      @DisplayName("Dado um produto existente no banco de dados com nome válido")
      void deveBuscarProdutoPorNomeComSucesso() {
        //given
        String nome = "Produto Teste";
        Produto produto = Produto.builder()
            .nome(nome)
            .categoria("Categoria Teste")
            .status(Produto.Status.AVAILABLE)
            .descricao("Descrição do produto de teste")
            .valor(BigDecimal.valueOf(99.99))
            .build();

        repository.save(produto);
        entityManager.flush();

        //when
        Optional<Produto> resultado = repository.findByNome(nome);

        //then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNome()).isEqualTo(nome);
      }
    }

    @DisplayName("Então não deve encontrar o produto")
    @Nested
    class NaoEncontrado {

      @Test
      @DisplayName("Dado um nome de produto que não existe no banco de dados")
      void naoDeveBuscarProdutoInexistente() {
        //given
        String nomeInexistente = "Produto Inexistente";

        //when
        Optional<Produto> resultado = repository.findByNome(nomeInexistente);

        //then
        assertThat(resultado).isEmpty();
      }
    }

  }

}