package app.vercel.leonanthomaz.chickenhouse_precificacao.controller;


import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Ingrediente;
import app.vercel.leonanthomaz.chickenhouse_precificacao.model.Produto;
import app.vercel.leonanthomaz.chickenhouse_precificacao.service.IngredienteService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
@Log4j2
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping("listar")
    public ResponseEntity<List<Ingrediente>> listarIngredientes() {
        List<Ingrediente> ingredientes = ingredienteService.findAll();
        return ResponseEntity.ok(ingredientes);
    }

    @PostMapping("/criar/{produtoId}")
    public ResponseEntity<Produto> addIngrediente(@PathVariable Long produtoId, @RequestBody Ingrediente ingrediente) {
        Produto produtoAtualizado = ingredienteService.adicionarIngrediente(produtoId, ingrediente);
        return ResponseEntity.ok(produtoAtualizado);
    }

}
