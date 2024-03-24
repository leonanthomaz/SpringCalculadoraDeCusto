package app.vercel.leonanthomaz.chickenhouse_precificacao.controller;


import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Embalagem;
import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Ingrediente;
import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Produto;
import app.vercel.leonanthomaz.chickenhouse_precificacao.service.EmbalagemService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/embalagens")
@Log4j2
public class EmbalagemController {

    @Autowired
    private EmbalagemService embalagemService;

    @GetMapping("listar")
    public ResponseEntity<List<Embalagem>> listarEmbalagem() {
        List<Embalagem> embalagens = embalagemService.findAll();
        return ResponseEntity.ok(embalagens);
    }

    @PostMapping("/criar/{produtoId}")
    public ResponseEntity<Produto> addEmbalagem(@PathVariable Long produtoId, @RequestBody Embalagem embalagem) {
        Produto produtoAtualizado = embalagemService.adicionarEmbalagem(produtoId, embalagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoAtualizado);
    }
}
