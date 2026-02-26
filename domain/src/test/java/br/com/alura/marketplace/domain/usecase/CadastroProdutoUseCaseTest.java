package br.com.alura.marketplace.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.alura.marketplace.domain.entity.Produto;
import br.com.alura.marketplace.domain.exception.BusinessException;
import br.com.alura.marketplace.domain.repository.BucketRepository;
import br.com.alura.marketplace.domain.repository.PetStoreRepository;
import br.com.alura.marketplace.domain.repository.ProdutoRepository;
import br.com.alura.marketplace.domain.repository.QueueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class CadastroProdutoUseCaseTest {

  private static final String UUID = "68634959-a61b-4e68-ba8a-6c75dff7e2ca";
  @InjectMocks
  CadastroProdutoUseCase useCase;

  @Mock
  ProdutoRepository produtoRepository;
  @Mock
  PetStoreRepository petStoreRepository;
  @Mock
  BucketRepository bucketRepository;
  @Mock
  QueueRepository queueRepository;

  @DisplayName("Quando cadastrar produto")
  @Nested
  class Cadastrar {

    @DisplayName("Então deve executar com sucesso")
    @Nested
    class Sucesso {

      @BeforeEach
      void beforeEach() {
        when(produtoRepository.save(any())).thenAnswer(invocationOnMock -> {
          Produto produto = invocationOnMock.getArgument(0);
          ReflectionTestUtils.setField(produto, "produtoId", java.util.UUID.fromString(UUID));
          return produto;
        });
      }

      @DisplayName("Dado um produto com todos os campos")
      @Test
      void teste1() {
        //given
        var produto = Produto.builder()
            .nome("Produto 1")
            .build();
        //when
        var resultado = useCase.cadastrar(produto);

        //then
        assertThat(resultado.getProdutoId()).isEqualTo(java.util.UUID.fromString(UUID));
      }

    }

    @DisplayName("Então deve retornar erro")
    @Nested
    class Falha {

      @DisplayName("Dado um produto com o nome começando com -")
      @Test
      void teste1() {
        //given
        var produto = Produto.builder()
            .nome("- Produto 1")
            .build();

        //when
        var resultado = assertThrows(BusinessException.class,
            () -> useCase.cadastrar(produto));

        //then
        assertThat(resultado).hasMessage("O nome do produto não pode iniciar com -");
      }

      //utilizando testes parametrizados
      @DisplayName("Dado um produto com um campo status igual a ${status}")
      @ParameterizedTest
      @EnumSource(Produto.Status.class)
      void teste2(Produto.Status status){
        //given
        var produto = Produto.builder()
            .nome("- Produto 1")
            .build();

        //when
        var resultado = useCase.cadastrar(produto);

        //then
        assertThat(resultado.getStatus()).isEqualTo(status);

      }
    }
  }
}