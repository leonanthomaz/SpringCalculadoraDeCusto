package app.vercel.leonanthomaz.chickenhouse_precificacao.service;

import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Embalagem;
import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Produto;
import app.vercel.leonanthomaz.chickenhouse_precificacao.repository.EmbalagemRepository;
import app.vercel.leonanthomaz.chickenhouse_precificacao.repository.ProdutoRepository;
import app.vercel.leonanthomaz.chickenhouse_precificacao.components.Calculadora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbalagemService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private Calculadora calculadora;
    @Autowired
    private EmbalagemRepository embalagemRepository;

    public List<Embalagem> findAll() {
        return embalagemRepository.findAll();
    }

    public Produto adicionarEmbalagem(Long produtoId, Embalagem embalagem) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        embalagem.setProduto(produto);
        produto.getEmbalagens().add(embalagem);

        calculadora.calcularMontagemDoLancheBruto(produto);
        calculadora.calcularDespesas(produto);
        calculadora.calcularMontagemLanchePorMedidas(produto);
        calculadora.calcularProdutoFinal(produto);

        return produtoRepository.save(produto);
    }

}

