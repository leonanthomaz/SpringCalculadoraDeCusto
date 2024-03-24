package app.vercel.leonanthomaz.chickenhouse_precificacao.controller;

import app.vercel.leonanthomaz.chickenhouse_precificacao.model.CustosVariaveis;
import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Embalagem;
import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Produto;
import app.vercel.leonanthomaz.chickenhouse_precificacao.service.CustosVariaveisService;
import app.vercel.leonanthomaz.chickenhouse_precificacao.service.EmbalagemService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/custosvariaveis")
@Log4j2
public class CustosVariaveisController {

    @Autowired
    private CustosVariaveisService custosVariaveisService;

    @GetMapping("listar")
    public ResponseEntity<List<CustosVariaveis>> listarCustosVariaveis() {
        List<CustosVariaveis> custosVariaveis = custosVariaveisService.findAll();
        return ResponseEntity.ok(custosVariaveis);
    }

    @PostMapping("/criar/{produtoId}")
    public ResponseEntity<Produto> addCustosVariaveis(@PathVariable Long produtoId, @RequestBody CustosVariaveis custosVariaveis) {
        Produto produtoAtualizado = custosVariaveisService.adicionarCustosVariaveis(produtoId, custosVariaveis);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoAtualizado);
    }
}
