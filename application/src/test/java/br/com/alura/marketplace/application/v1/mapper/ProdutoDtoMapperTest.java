package br.com.alura.marketplace.application.v1.mapper;

import static br.com.alura.marketplace.domain.entity.assertions.ProdutoAssertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mapstruct.factory.Mappers.getMapper;

import br.com.alura.marketplace.application.v1.dto.ProdutoDto;
import br.com.alura.marketplace.application.v1.dto.factory.ProdutoDtoFactory;
import br.com.alura.marketplace.domain.entity.Produto.Status;
import br.com.alura.marketplace.domain.entity.assertions.ProdutoAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProdutoDtoMapperTest {

  protected ProdutoDtoMapper mapper = getMapper(ProdutoDtoMapper.class);

  @BeforeEach
  void beforeEach() {
    //desnecessário quando se tratar de uma classe mapper que utiliza o mapStruct
  }

  @DisplayName("Quando converter ProdutoDto.Request ")
  @Nested
  class Converter {

    @DisplayName("Então deve executar com sucesso")
    @Nested
    class Sucesso {

      @DisplayName("Dado um ProdutoDto.Request com todos os campos")
      @Test
      void test() {
        //given
        var dto = ProdutoDtoFactory.criarProdutoDtoRequest().comTodosOsCampos();
        //when
        var resultado = mapper.converter(dto);

        //then
        // Assertions podem ser feitas através do Junit 5 e/ou AssertJ
        // AssertJ traz maior facilidade para leitura
        afirmaQue_Produto(resultado).foiConvertidoDe_ProdutoDto_Request();
      }

    }
  }

}