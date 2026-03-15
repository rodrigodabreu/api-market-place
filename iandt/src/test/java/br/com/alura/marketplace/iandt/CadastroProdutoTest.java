package br.com.alura.marketplace.iandt;

import static br.com.alura.marketplace.application.v1.dto.factory.ProdutoDtoFactory.criarProdutoDtoRequest;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;

import br.com.alura.marketplace.application.Application;
import br.com.alura.marketplace.domain.setup.LocalStackSetup;
import br.com.alura.marketplace.domain.setup.PostgresSetup;
import br.com.alura.marketplace.domain.setup.RabbitMqSetup;
import br.com.alura.marketplace.domain.setup.WireMockSetup;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petstore.model.PetDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = Application.class)//essa configuração de contexto se faz necessária por se tratar de um projeto modular. Em aplicaçõe não modulares não é necessário configurar.
@Testcontainers
public class CadastroProdutoTest implements LocalStackSetup, WireMockSetup, PostgresSetup, RabbitMqSetup {

  @LocalServerPort
  Integer port;

  ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void beforeEach(){
    RestAssured.baseURI = "http://localhost:" + port + "/api";
  }

  @DisplayName("Quando cadastrar um produto")
  @Nested
  class CadastrarProduto{

    @DisplayName("Deve cadastrar com sucesso")
    @Nested
    class Sucesso {

      @BeforeEach
      void beforeEach() throws JsonProcessingException {
        var petDto = new PetDto("name of my pet", Collections.emptyList());
        WIRE_MOCK.stubFor(post("/petstore/pet")
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(objectMapper.writeValueAsString(petDto))));
      }

      @DisplayName("Dado um produto válido")
      @Test
      void teste1() throws JsonProcessingException {
        //given
        var produto = criarProdutoDtoRequest().comTodosOsCampos();

        //when
        var resultado = RestAssured.given()
            .log().all()
            .header("Correlation-Id", "b569e51e-851b-447b-853b-7785f5f15d24")
            .contentType(ContentType.JSON)
            .body(objectMapper.writeValueAsString(produto))
            .post("/v1/produtos")
            .then()
            .log()
            .all()
            .extract()
            .response();

        //then
          Assertions.assertThat(resultado.statusCode())
              .isEqualTo(201);
      }
    }
  }

}
