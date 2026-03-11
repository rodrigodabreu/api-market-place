import static br.com.alura.marketplace.application.v1.dto.factory.ProdutoDtoFactory.criarProdutoDtoRequest;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

import br.com.alura.marketplace.application.Application;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = Application.class)//essa configuração de contexto se faz necessária por se tratar de um projeto modular. Em aplicaçõe não modulares não é necessário configurar.
@Testcontainers
public class CadastroProdutoTest {

  //configuração para simular o uso do aws s3
  public final static LocalStackContainer LOCAL_STACK = new LocalStackContainer(
      DockerImageName.parse("localstack/localstack:3.5.0")
  ).withServices(S3);

  //configuração das properties
  @DynamicPropertySource
  static void localstackDynamicPropertySource(DynamicPropertyRegistry registry){
    registry.add("spring.cloud.aws.region.static", LOCAL_STACK ::getRegion);
    registry.add("spring.cloud.aws.credentials.access-key", LOCAL_STACK ::getAccessKey);
    registry.add("spring.cloud.aws.credentials.secret-key", LOCAL_STACK ::getSecretKey);
    registry.add("spring.cloud.aws.s3.endpoint", ()-> LOCAL_STACK.getEndpointOverride(S3));
  }
  @BeforeAll
  static void beforeAll(){
    LOCAL_STACK.start();
  }

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
