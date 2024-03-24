package app.vercel.leonanthomaz.chickenhouse_precificacao.service;

import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Ingrediente;
import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Produto;
import app.vercel.leonanthomaz.chickenhouse_precificacao.repository.IngredienteRepository;
import app.vercel.leonanthomaz.chickenhouse_precificacao.repository.ProdutoRepository;
import app.vercel.leonanthomaz.chickenhouse_precificacao.components.Calculadora;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class IngredienteService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private Calculadora calculadora;
    @Autowired
    private IngredienteRepository ingredienteRepository;

    public List<Ingrediente> findAll() {
        return ingredienteRepository.findAll();
    }

    public Produto adicionarIngrediente(Long produtoId, Ingrediente ingrediente) {

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ingrediente.setProduto(produto);
        produto.getIngredientes().add(ingrediente);

        calculadora.calcularMontagemDoLancheBruto(produto);
        calculadora.calcularDespesas(produto);
        calculadora.calcularMontagemLanchePorMedidas(produto);
        calculadora.calcularProdutoFinal(produto);
        return produtoRepository.save(produto);
    }

}

