package app.vercel.leonanthomaz.chickenhouse_precificacao.repository;

import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}

