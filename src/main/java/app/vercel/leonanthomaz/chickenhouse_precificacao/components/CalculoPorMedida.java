package app.vercel.leonanthomaz.chickenhouse_precificacao.components;


import app.vercel.leonanthomaz.chickenhouse_precificacao.enums.UnidadeDeMedida;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Builder
@Log4j2
public class CalculoPorMedida {
    public static double calcularCustoPorMedida(double valor, double quantidade,
                                                double porcentagemDePerda, UnidadeDeMedida unidadeMedida) {
        double perda = porcentagemDePerda / 100.0; // Convertendo a porcentagem de perda para decimal

        double valorFinal;

        switch (unidadeMedida) {
            case QUILOGRAMA:
                valorFinal = valor / (quantidade * perda * 100);
                break;
            case GRAMA:
                valorFinal = valor / (quantidade * perda * 1000); // Convertendo para gramas
                break;
            case LITRO:
                valorFinal = valor / (quantidade * perda * 1000); // Mantendo o mesmo para litros
                break;
            case UNIDADE:
                valorFinal = valor / (quantidade * 100); // Mantendo o mesmo para unidades
                break;
            case MENSAL:
                // Ajustando para valor por mês
                valorFinal = valor / (quantidade * 30);
                break;
            default:
                throw new IllegalArgumentException("Unidade de medida inválida: " + unidadeMedida);
        }

        return valorFinal;
    }


}
