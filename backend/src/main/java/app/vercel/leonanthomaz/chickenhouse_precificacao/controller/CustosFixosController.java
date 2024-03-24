package app.vercel.leonanthomaz.chickenhouse_precificacao.controller;

import app.vercel.leonanthomaz.chickenhouse_precificacao.model.CustosFixos;
import app.vercel.leonanthomaz.chickenhouse_precificacao.model.CustosVariaveis;
import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Produto;
import app.vercel.leonanthomaz.chickenhouse_precificacao.service.CustosFixosService;
import app.vercel.leonanthomaz.chickenhouse_precificacao.service.CustosVariaveisService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/custosfixos")
@Log4j2
public class CustosFixosController {

    @Autowired
    private CustosFixosService custosFixosService;

    @GetMapping("listar")
    public ResponseEntity<List<CustosFixos>> listarCustosFixos() {
        List<CustosFixos> custosFixos = custosFixosService.findAll();
        return ResponseEntity.ok(custosFixos);
    }

    @PostMapping("/criar/{produtoId}")
    public ResponseEntity<Produto> addCustosFixos(@PathVariable Long produtoId, @RequestBody CustosFixos custosFixos) {
        Produto produtoAtualizado = custosFixosService.adicionarCustosFixos(produtoId, custosFixos);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoAtualizado);
    }
}
