package app.vercel.leonanthomaz.chickenhouse_precificacao.controller;

import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Produto;
import app.vercel.leonanthomaz.chickenhouse_precificacao.service.ProdutoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Log4j2
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listar")
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @PostMapping("/criar")
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produtoRequest) {
        // Construa o objeto Produto com todos os campos necessários
        Produto produto = new Produto();
        produto.setNome(produtoRequest.getNome());
        produto.setIngredientes(produtoRequest.getIngredientes());
        produto.setEmbalagens(produtoRequest.getEmbalagens());
        produto.setCustosFixos(produtoRequest.getCustosFixos());
        produto.setCustosVariaveis(produtoRequest.getCustosVariaveis());

        // Crie o produto e faça os cálculos de preço
        Produto produtoCriado = produtoService.criarProduto(produto);
        log.info("Produto criado: {}", produtoCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    @PostMapping("/criarpornome")
    public ResponseEntity<Produto> criarProdutoPorNome(@RequestBody Produto produtoRequest) {
        Produto build = Produto.builder().nome(produtoRequest.getNome()).build();
        Produto produtoCriado = produtoService.criarProduto(build);
        log.info("Produto criado: {}", produtoCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }
}
