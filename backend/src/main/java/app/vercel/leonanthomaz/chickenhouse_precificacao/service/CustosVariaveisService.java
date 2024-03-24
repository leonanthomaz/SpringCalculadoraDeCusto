package app.vercel.leonanthomaz.chickenhouse_precificacao.service;

import app.vercel.leonanthomaz.chickenhouse_precificacao.model.CustosVariaveis;
import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Produto;
import app.vercel.leonanthomaz.chickenhouse_precificacao.repository.CustosVariaveisRepository;
import app.vercel.leonanthomaz.chickenhouse_precificacao.repository.ProdutoRepository;
import app.vercel.leonanthomaz.chickenhouse_precificacao.components.Calculadora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustosVariaveisService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private Calculadora calculadora;
    @Autowired
    private CustosVariaveisRepository custosVariaveisRepository;

    public List<CustosVariaveis> findAll() {
        return custosVariaveisRepository.findAll();
    }

    public Produto adicionarCustosVariaveis(Long produtoId, CustosVariaveis custosVariaveis) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        custosVariaveis.setProduto(produto);
        produto.getCustosVariaveis().add(custosVariaveis);

        calculadora.calcularMontagemDoLancheBruto(produto);
        calculadora.calcularDespesas(produto);
        calculadora.calcularMontagemLanchePorMedidas(produto);
        calculadora.calcularProdutoFinal(produto);

        return produtoRepository.save(produto);
    }
}
