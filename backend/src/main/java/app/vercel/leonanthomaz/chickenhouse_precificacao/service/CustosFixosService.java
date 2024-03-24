package app.vercel.leonanthomaz.chickenhouse_precificacao.service;

import app.vercel.leonanthomaz.chickenhouse_precificacao.model.CustosFixos;
import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Produto;
import app.vercel.leonanthomaz.chickenhouse_precificacao.repository.CustosFixosRepository;
import app.vercel.leonanthomaz.chickenhouse_precificacao.repository.ProdutoRepository;
import app.vercel.leonanthomaz.chickenhouse_precificacao.components.Calculadora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustosFixosService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private Calculadora calculadora;

    @Autowired
    private CustosFixosRepository custosFixosRepository;

    public List<CustosFixos> findAll() {
        return custosFixosRepository.findAll();
    }

    public Produto adicionarCustosFixos(Long produtoId, CustosFixos custosFixos) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        custosFixos.setProduto(produto);
        produto.getCustosFixos().add(custosFixos);

        calculadora.calcularMontagemDoLancheBruto(produto);
        calculadora.calcularDespesas(produto);
        calculadora.calcularMontagemLanchePorMedidas(produto);
        calculadora.calcularProdutoFinal(produto);

        return produtoRepository.save(produto);
    }
}
