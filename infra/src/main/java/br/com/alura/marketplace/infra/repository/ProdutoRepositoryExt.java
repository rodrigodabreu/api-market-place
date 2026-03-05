package br.com.alura.marketplace.infra.repository;

import br.com.alura.marketplace.domain.entity.Produto;
import br.com.alura.marketplace.domain.repository.ProdutoRepository;
import java.util.Optional;

public interface ProdutoRepositoryExt extends ProdutoRepository {

  Optional<Produto> findByNome(String nome);

}
