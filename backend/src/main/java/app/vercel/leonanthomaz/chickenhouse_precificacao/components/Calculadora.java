package app.vercel.leonanthomaz.chickenhouse_precificacao.components;

import app.vercel.leonanthomaz.chickenhouse_precificacao.model.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class Calculadora {

    private static final double MARGEM_LUCRO = 0.5; // Margem de lucro de 50%
    private static double valorMontagem;
    private static double valorDespesas;

//    public double calcularPrecoFinal(Produto produto) {
//        double custoBruto, double custoFixo, double custoVariavel, int quantidade
//
//        double custoTotal = custoBruto + custoFixo + custoVariavel;
//        double precoFinal = custoTotal / quantidade;
//        return precoFinal * (1 + MARGEM_LUCRO); // Aplicando a margem de lucro
//
//        // Exemplo de uso do método calcularPrecoFinal
////        double custoBruto = 145.0;
////        double custoFixo = 100.0; // Energia
////        double custoVariavel = 130.0; // Mensalidade do Ifood
////        int quantidade = 100; // Quantidade de unidades
////
////        double precoFinal = service.calcularPrecoFinal(custoBruto, custoFixo, custoVariavel, quantidade);
////        System.out.println("Preço final por unidade: R$" + precoFinal);
//    }

    public void calcularMontagemDoLancheBruto(Produto produto) {
        double ingrediente = produto.getIngredientes().stream().mapToDouble(Ingrediente::getCustoBruto).sum();
        double embalagem = produto.getEmbalagens().stream().mapToDouble(Embalagem::getCustoBruto).sum();
        double total = ingrediente + embalagem;
        produto.setCustoDeMontagem(total);
        log.info("MONTAGEM DO LANCHE (BRUTO): {}", total);
    }

    public void calcularDespesas(Produto produto) {
        double custoFixo = produto.getCustosFixos().stream().mapToDouble(CustosFixos::getCustoBruto).sum();
        double custoVariavel = produto.getCustosVariaveis().stream().mapToDouble(CustosVariaveis::getCustoBruto).sum();
        double total = custoFixo + custoVariavel;
        produto.setCustoDeDespesas(total);
        valorDespesas = total;
        log.info("DESPESAS FIXAS E VARIAVEIS: {}", total);
    }

    public double calcularProdutoFinal(Produto produto) {
        // Calcular o custo total de montagem considerando a perda
        double custoMontagem = calcularCustoTotalComPerda(produto.getIngredientes(), produto.getEmbalagens());

        // Calcular o custo total de despesas
        double custoDespesas = calcularCustoTotalDespesas(produto.getCustosFixos(), produto.getCustosVariaveis());

        // Somar os custos total de montagem e despesas
        double custoTotal = custoMontagem + custoDespesas;

        // Definir o preço de venda como o custo total do produto
        produto.setValorDeVenda(custoTotal);

        log.info("VALOR FINAL: {}", custoTotal);
        return custoTotal;
    }

    private double calcularCustoTotalComPerda(List<Ingrediente> ingredientes, List<Embalagem> embalagens) {
        double custoTotalIngredientes = ingredientes.stream()
                .mapToDouble(ingrediente -> CalculoPorMedida.calcularCustoPorMedida(
                        ingrediente.getCustoBruto(),
                        ingrediente.getQuantidade(),
                        ingrediente.getPorcentagemDePerda(),
                        ingrediente.getUnidadeMedida()))
                .sum();

        double custoTotalEmbalagens = embalagens.stream()
                .mapToDouble(embalagem -> CalculoPorMedida.calcularCustoPorMedida(
                        embalagem.getCustoBruto(),
                        embalagem.getQuantidade(),
                        embalagem.getPorcentagemDePerda(),
                        embalagem.getUnidadeMedida()))
                .sum();

        return custoTotalIngredientes + custoTotalEmbalagens;
    }

    private double calcularCustoTotalDespesas(List<CustosFixos> custosFixos, List<CustosVariaveis> custosVariaveis) {
        double custoTotalFixos = custosFixos.stream()
                .mapToDouble(fixo -> CalculoPorMedida.calcularCustoPorMedida(
                        fixo.getCustoBruto(),
                        fixo.getQuantidade(),
                        fixo.getPorcentagemDePerda(),
                        fixo.getUnidadeMedida()))
                .sum();

        double custoTotalVariaveis = custosVariaveis.stream()
                .mapToDouble(variavel -> CalculoPorMedida.calcularCustoPorMedida(
                        variavel.getCustoBruto(),
                        variavel.getQuantidade(),
                        variavel.getPorcentagemDePerda(),
                        variavel.getUnidadeMedida()))
                .sum();

        return custoTotalFixos + custoTotalVariaveis;
    }

    public double calcularMontagemLanchePorMedidas(Produto produto) {
        double custoIngredientes = calcularCustoIngredientes(produto);
        double custoEmbalagens = calcularCustoEmbalagens(produto);
        double custoFixos = calcularCustoFixos(produto);
        double custoVariaveis = calcularCustoVariaveis(produto);
        double total = custoIngredientes + custoEmbalagens + custoFixos + custoVariaveis;
        log.info("VALOR COM MEDIDAS: {}", total);
        valorMontagem = total;
        return total;
    }

    private double calcularCustoIngredientes(Produto produto) {
        double totalQuantidadeIngredientes = produto.getIngredientes().stream().mapToDouble(Ingrediente::getQuantidade).sum();

        return produto.getIngredientes().stream()
                .mapToDouble(ingrediente -> {
                    double proporcao = ingrediente.getQuantidade() / totalQuantidadeIngredientes;
                    return proporcao * ingrediente.calcularCustoIngredientesUnitario();
                }).sum();
    }

    private double calcularCustoEmbalagens(Produto produto) {
        double totalQuantidadeEmbalagens = produto.getEmbalagens().stream().mapToDouble(Embalagem::getQuantidade).sum();

        return produto.getEmbalagens().stream()
                .mapToDouble(embalagem -> {
                    double proporcao = embalagem.getQuantidade() / totalQuantidadeEmbalagens;
                    return proporcao * embalagem.calcularCustoEmbalagemUnitario();
                }).sum();
    }

    private double calcularCustoFixos(Produto produto) {
        double totalQuantidadeProdutos = calcularCustoIngredientes(produto) + calcularCustoEmbalagens(produto);

        return produto.getCustosFixos().stream()
                .mapToDouble(custoFixo -> custoFixo.calcularCustoFixosUnitario() / totalQuantidadeProdutos)
                .sum();
    }

    private double calcularCustoVariaveis(Produto produto) {
        double totalQuantidadeProdutos = calcularCustoIngredientes(produto) + calcularCustoEmbalagens(produto);

        return produto.getCustosVariaveis().stream()
                .mapToDouble(custoVariavel -> custoVariavel.calcularCustoVariaveisUnitario() / totalQuantidadeProdutos)
                .sum();
    }
}
