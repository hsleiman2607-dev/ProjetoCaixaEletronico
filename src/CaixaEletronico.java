
import java.awt.EventQueue;

public class CaixaEletronico implements ICaixaEletronico {

    // Valor mínimo que o caixa deve manter
    private int cotaMinima = 1000;

    // Histórico de saques
    private final StringBuilder historicoSaques = new StringBuilder();

    // Total sacado durante a sessão
    private double totalSacadoNaSessao = 0;

    // Matriz: [valor da nota][quantidade]
    private final int[][] estoqueCedulas;
    

    public CaixaEletronico() {

        // Inicialização do estoque
        this.estoqueCedulas = new int[][] {
        	
                {100, 100},
                {50,  200},
                {20,  300},
                {10,  350},
                {5,   450},
                {2,   500}
        };
    }



    // RELATÓRIO DE CÉDULAS
    

    public String pegaRelatorioCedulas() {

        StringBuilder relatorio = new StringBuilder();

        relatorio.append("--- RELATÓRIO DE CÉDULAS ---\n\n");

        for (int[] par : estoqueCedulas) {
            relatorio.append(String.format(
                    "Nota R$ %d - Quantidade: %d\n",
                    par[0],
                    par[1]
            ));
        }

        relatorio.append("\n------------------------------------------\n");

        relatorio.append(String.format(
                "TOTAL SACADO NA SESSÃO: R$ %,.2f\n",
                totalSacadoNaSessao
        ));

        relatorio.append(String.format(
                "SALDO TOTAL EM CAIXA: R$ %,.2f\n",
                (double) calcularSomaTotal()
        ));

        relatorio.append("------------------------------------------\n");

        return relatorio.toString();
    }


   
    // VALOR TOTAL DISPONÍVEL


    public String pegaValorTotalDisponivel() {

        int soma = calcularSomaTotal();

        return String.format(
                "Valor total disponível no caixa: R$ %,.2f",
                (double) soma
        );
    }



    // REPOSIÇÃO DE CÉDULAS
   

    public String reposicaoCedulas(Integer cedula, Integer quantidade) {

        if (quantidade <= 0) {
            return "Erro: quantidade inválida.";
        }

        for (int i = 0; i < estoqueCedulas.length; i++) {

            if (estoqueCedulas[i][0] == cedula) {

                estoqueCedulas[i][1] += quantidade;

                return String.format(
                        "Reposição concluída. Nota R$ %d agora possui %d unidades.",
                        cedula,
                        estoqueCedulas[i][1]
                );
            }
        }

        return "Erro: cédula inválida.";
    }


  
    // VERIFICA EXISTÊNCIA DA CÉDULA
    

    public boolean existeCedula(int cedula) {

        for (int[] linha : estoqueCedulas) {

            if (linha[0] == cedula) {
                return true;
            }
        }

        return false;
    }


  
    // CALCULA SOMA TOTAL DO CAIXA
  

    private int calcularSomaTotal() {

        int soma = 0;

        for (int i = 0; i < estoqueCedulas.length; i++) {
            soma += estoqueCedulas[i][0] * estoqueCedulas[i][1];
        }

        return soma;
    }


  
    // SAQUE
    

    public String sacar(Integer valor) {

        // Validações iniciais
        String validacao = validarSaque(valor);

        if (validacao != null) {
            return validacao;
        }

        int valorRestante = valor;

        int[] notasParaEntregar = new int[estoqueCedulas.length];

        int totalDeNotas = 0;

        // Calcula notas
        for (int i = 0; i < estoqueCedulas.length; i++) {

            int valorNota = estoqueCedulas[i][0];

            int qtdNotas = calcularQtdNotas(i, valorRestante, valorNota);

            notasParaEntregar[i] = qtdNotas;

            valorRestante -= qtdNotas * valorNota;

            totalDeNotas += qtdNotas;
        }

        // Limite físico do caixa
        if (totalDeNotas > 30) {
            return "Erro: limite máximo de 30 notas excedido.";
        }

        // Não conseguiu montar o valor exato
        if (valorRestante != 0) {
            return "Erro: o caixa não possui notas suficientes para compor este valor.";
        }

        // Atualiza estoque
        atualizarEstoque(notasParaEntregar);

        // Atualiza histórico
        totalSacadoNaSessao += valor;

        historicoSaques.append(
                String.format(
                        "Saque: R$ %d,00 | Saldo restante: R$ %,.2f\n",
                        valor,
                        (double) calcularSomaTotal()
                )
        );

        // Gera comprovante
        return gerarMensagemSaque(notasParaEntregar, valor);
    }


 
    // VALIDA SAQUE
  

    private String validarSaque(Integer valor) {

        if (valor == null) {
            return "Erro: valor inválido.";
        }

        if (valor <= 0) {
            return "Erro: o valor deve ser maior que zero.";
        }

        if (valor == 1 || valor == 3) {
            return "Erro: impossível sacar R$ 1 ou R$ 3.";
        }

        int valorDisponivel = calcularSomaTotal();

        if ((valorDisponivel - valor) < cotaMinima) {
            return "Saque não realizado por falta de cédulas";
        }

        if (valor > valorDisponivel) {
            return "Erro: saldo insuficiente no caixa.";
        }

        return null;
    }


  
    // CALCULA QUANTIDADE DE NOTAS
  

    private int calcularQtdNotas(int indice, int valorRestante, int valorNota) {

        int qtdDisponivel = estoqueCedulas[indice][1];

        int qtdNotas = valorRestante / valorNota;


        // Evita restos impossíveis
        if (valorNota > 5) {

            int resto = valorRestante - (qtdNotas * valorNota);

            if ((resto == 1 || resto == 3) && qtdNotas > 0) {
                qtdNotas--;
            }
        }


        // Regra da nota de 5
        if (valorNota == 5) {

            qtdNotas = valorRestante / 5;

            // Se a sobra virar ímpar impossível para nota 2
            while (qtdNotas > 0 &&
                  ((valorRestante - (qtdNotas * 5)) % 2 != 0)) {

                qtdNotas--;
            }
        }


        // Verifica estoque
        if (qtdNotas > qtdDisponivel) {
            qtdNotas = qtdDisponivel;
        }

        return qtdNotas;
    }


   
    // ATUALIZA ESTOQUE
   

    private void atualizarEstoque(int[] notasParaEntregar) {

        for (int i = 0; i < estoqueCedulas.length; i++) {

            estoqueCedulas[i][1] -= notasParaEntregar[i];
        }
    }


  
    // GERA COMPROVANTE
   

    private String gerarMensagemSaque(int[] notasParaEntregar, int valor) {

        StringBuilder mensagem = new StringBuilder();

        mensagem.append("SAQUE REALIZADO COM SUCESSO\n\n");

        mensagem.append(String.format("Valor sacado: R$ %d,00\n\n", valor));

        for (int i = 0; i < estoqueCedulas.length; i++) {

            if (notasParaEntregar[i] > 0) {

                mensagem.append(
                        String.format(
                                "%d nota(s) para R$ %d\n",
                                notasParaEntregar[i],
                                estoqueCedulas[i][0]
                        )
                );
            }
        }

        mensagem.append(String.format(
                "\nSaldo restante no caixa: R$ %,.2f",
                (double) calcularSomaTotal()
        ));

        return mensagem.toString();
    }


   
    // TOTAL SACADO
    

    public double getTotalSacado() {
        return totalSacadoNaSessao;
    }


   
    // EXTRATO
 

    public String extrato() {

        StringBuilder relatorio = new StringBuilder();

        relatorio.append("--- EXTRATO DE SAQUES ---\n\n");

        if (historicoSaques.length() == 0) {

            relatorio.append("Nenhum saque realizado nesta sessão.\n");

        } else {

            relatorio.append(historicoSaques);
        }

        relatorio.append("\n--------------------------------\n");

        relatorio.append(
                String.format(
                        "SALDO ATUAL DO CAIXA: R$ %,.2f",
                        (double) calcularSomaTotal()
                )
        );

        return relatorio.toString();
    }


    
    // COTA MÍNIMA
    

    public String armazenaCotaMinima(Integer novoMinimo) {

        if (novoMinimo == null || novoMinimo < 0) {
            return "Erro: valor inválido para cota mínima.";
        }

        int valorTotalNoCaixa = calcularSomaTotal();

        if (novoMinimo > valorTotalNoCaixa) {

            return String.format(
                    "Caixa Vazio: Chame o Operador",
                    novoMinimo,
                    valorTotalNoCaixa
            );
        }

        this.cotaMinima = novoMinimo;

        return String.format(
                "Cota mínima atualizada para R$ %d,00",
                novoMinimo
        );
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            try {

                Gul frame = new Gul();

                frame.setVisible(true);

            } catch (Exception e) {

                e.printStackTrace();
            }
        });
    }
}
