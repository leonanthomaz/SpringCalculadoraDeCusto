package app.vercel.leonanthomaz.chickenhouse_precificacao.model;

import app.vercel.leonanthomaz.chickenhouse_precificacao.enums.UnidadeDeMedida;
import app.vercel.leonanthomaz.chickenhouse_precificacao.components.CalculoPorMedida;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_custosfixos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustosFixos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double porcentagemDePerda;
    private double custoBruto;
    private double custoPorUnidade;
    private double quantidade;

    @Enumerated(EnumType.STRING)
    private UnidadeDeMedida unidadeMedida;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    @JsonBackReference
    private Produto produto;

    public double calcularCustoFixosUnitario() {
        double custoPorMedida = CalculoPorMedida.calcularCustoPorMedida(custoBruto, quantidade, porcentagemDePerda, unidadeMedida);
        return custoPorUnidade = custoPorMedida;
    }
}
