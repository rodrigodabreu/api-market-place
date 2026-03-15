package br.com.alura.marketplace.iandt;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import br.com.alura.marketplace.application.Application;
import br.com.alura.marketplace.domain.repository.ProdutoRepository;
import br.com.alura.marketplace.domain.setup.LocalStackSetup;
import br.com.alura.marketplace.domain.setup.PostgresSetup;
import br.com.alura.marketplace.domain.setup.RedisSetup;
import br.com.alura.marketplace.infra.entity.factory.ProdutoFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = Application.class)
//essa configuração de contexto se faz necessária por se tratar de um projeto modular. Em aplicaçõe não modulares não é necessário configurar.
@Testcontainers
public class ConsultaProdutoTest implements LocalStackSetup, PostgresSetup, RedisSetup {

  @LocalServerPort
  Integer port;

  @Autowired
  ProdutoRepository produtoRepository;

  @BeforeEach
  void beforeEach() {
    RestAssured.baseURI = "http://localhost:" + port + "/api";
  }

  @AfterEach
  void afterEach() {
    //forma de manter a massa de dados integra para cada cenário de teste
    produtoRepository.deleteAll();
  }

  @DisplayName("Quando cadastrar um produto")
  @Nested
  class ConsultarProduto {

    @DisplayName("Deve consultar com sucesso")
    @Nested
    class Sucesso {


      @DisplayName("Dado um produto válido")
      @Test
      void teste1() throws JsonProcessingException {
        //given
        var produto = ProdutoFactory.criarProduto().comTodosOsCampos();
        produtoRepository.save(produto);

        //when
        var result = given()
            .log().all()
            .get("/v1/produtos/{id}", produto.getProdutoId())
            .then()
            .log().all()
            .extract()
            .response();

        //then
        assertThat(result.statusCode())
            .isEqualTo(200);

      }

      @DisplayName("Dado uma segunda chamada do mesmo produto válido")
      @Test
      void teste2() throws JsonProcessingException {
        //given
        var produto = ProdutoFactory.criarProduto().comTodosOsCampos();
        produtoRepository.save(produto);
        for (var i = 0; i < 2; i++) {

          //when
          var result = given()
              .log().all()
              .get("/v1/produtos/{id}", produto.getProdutoId())
              .then()
              .log().all()
              .extract()
              .response();

          //then
          assertThat(result.statusCode())
              .isEqualTo(200);
          produtoRepository.deleteAll();
        }
      }
    }
  }

}
