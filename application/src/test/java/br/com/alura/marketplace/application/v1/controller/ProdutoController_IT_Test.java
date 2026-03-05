package br.com.alura.marketplace.application.v1.controller;

import static br.com.alura.marketplace.application.v1.dto.factory.ProdutoDtoFactory.criarProdutoDtoRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.alura.marketplace.application.v1.config.RestControllerTestConfig;
import br.com.alura.marketplace.domain.usecase.CadastroProdutoUseCase;
import br.com.alura.marketplace.domain.usecase.ConsultaProdutoUseCase;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = ProdutoController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(classes = RestControllerTestConfig.class)
class ProdutoController_IT_Test {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockitoBean
  CadastroProdutoUseCase cadastrarProdutoUseCase;

  @MockitoBean
  ConsultaProdutoUseCase consultaProdutoUseCase;

  @Nested
  @DisplayName("Quando cadastrar um produto")
  class CadastrarProduto {

    @DisplayName("Então deve cadastrar com sucesso")
    @Nested
    class Sucesso {

      @DisplayName("Dado um produto válido")
      @Test
      void teste1() throws Exception {
        //given
        var requestBody = criarProdutoDtoRequest().comTodosOsCampos();

        //when
        mockMvc.perform(post("/v1/produtos")
                .contentType("application/json")
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(objectMapper.writeValueAsString(requestBody)))
            .andDo(print())
            .andExpect(status().isCreated());
        //then
      }
    }
  }
}