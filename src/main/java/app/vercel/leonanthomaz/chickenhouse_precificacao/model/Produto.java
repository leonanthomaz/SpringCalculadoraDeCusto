package app.vercel.leonanthomaz.chickenhouse_precificacao.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Entity
@Table(name = "tb_produto")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double custoDeMontagem;
    private double custoDeDespesas;
    private double valorDeVenda;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Ingrediente> ingredientes;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Embalagem> embalagens;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CustosFixos> custosFixos;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CustosVariaveis> custosVariaveis;

}

