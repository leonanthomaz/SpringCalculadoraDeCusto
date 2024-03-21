package app.vercel.leonanthomaz.chickenhouse_precificacao.service;

import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Produto;
import app.vercel.leonanthomaz.chickenhouse_precificacao.repository.ProdutoRepository;
import app.vercel.leonanthomaz.chickenhouse_precificacao.components.Calculadora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private Calculadora calculadora;
    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

}