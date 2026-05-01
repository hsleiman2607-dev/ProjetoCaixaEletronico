import java.awt.EventQueue;
import java.util. *;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Scanner;
public class CaixaEletronico implements ICaixaEletronico{
    private double totalSacadoNaSessao = 0;
    private int cotaMinima = 1000;
    private final Integer[][] estoqueCedulas;

    public CaixaEletronico() {
        //this.totalSacadoNaSessao = totalSacadoNaSessao;
        this.estoqueCedulas = new Integer[][] {
                {100, 100},
                {50, 200},
                {20, 300},
                {10, 350},
                {5, 450},
                {2, 500}
        };
    }



    public String pegaRelatorioCedulas() {
        StringBuilder relatorio = new StringBuilder("Relatório de Cédulas:\n");
        for (Integer[] par : estoqueCedulas) {
            relatorio.append(String.format("Nota R$ %d - Qtd: %d\n", par[0], par[1]));
        }
        relatorio.append("\n-------------------------------------------------------------------------\n");
        // ADICIONE ESTA LINHA:
        relatorio.append(String.format("TOTAL SACADO NESTA SESSÃO: R$ %,.2f\n", totalSacadoNaSessao));
        relatorio.append("-------------------------------------------------------------------------\n");
        return relatorio.toString();

    }
    public String pegaValorTotalDisponivel() {
        int i, soma=0;
        String resposta = "Valor Total é " + soma;
        //logica de pega o valor total disponivel no caixa eletronio

        for(i = 0; i < estoqueCedulas.length; i++) {
            int valorPorNota = estoqueCedulas[i][0] * estoqueCedulas[i][1];
            soma += valorPorNota;
        }
        JOptionPane.showMessageDialog(null, String.format("Valor total no caixa: R$ %,.2f", (double)soma));
        return resposta;
    }




    public String reposicaoCedulas(Integer cedula, Integer quantidade) {

        for(int i = 0; i < estoqueCedulas.length; i++) {
            // Verifica se a nota na linha i (coluna 0) é a nota que queremos repor
            if (estoqueCedulas[i][0].equals(cedula)) {

                // Adiciona a nova quantidade ao estoque existente (coluna 1)
                estoqueCedulas[i][1] += quantidade;

                return "Reposição concluída! Novo estoque de R$" + cedula + ": " + estoqueCedulas[i][1];
            }

        }

        //logica de fazer a reposicao de cedulas e criar uma mensagem(resposta) ao usuario
        return "Reposição negada! Cedula Inválida";
    }

    //Método que verifica se a Nota existe mesmo, na hora da reposição
    public boolean existeCedula(int cedula) {
        for (Integer[] linha : estoqueCedulas) {
            if (linha[0] == cedula) {
                return true; // Tem lá!
            }
        }
        return false; // Não existe
    }


    private int calcularSomaTotal() {
        int soma = 0;
        for(int i = 0; i < estoqueCedulas.length; i++) {
            soma += estoqueCedulas[i][0] * estoqueCedulas[i][1];
        }
        return soma;
    }



    // Variável acumuladora
    public String sacar(Integer valor) {
        int valorDisponivel = calcularSomaTotal();
        if (valorDisponivel < this.cotaMinima) {
            return "Caixa Vazio: Chame o Operador";
        }

        int valorRestante = valor;


        JOptionPane.showMessageDialog(null, String.format("Processando saque de %d", valor));


        // primeira regra
        if (valor <= 0) {
            return "Erro: Valor de saque deve ser maior que zero.";
        }
        //segunda regra
        if (valor == 1 || valor == 3) {
            return "Erro: Não existem notas para sacar valor de 1 ou tres reais";
        }




        int[] notasParaEntregar = new int[estoqueCedulas.length];

        // O Loop que percorre as notas
        for (int i = 0; i < estoqueCedulas.length; i++) {
            int valorNota = estoqueCedulas[i][0];
            int qtdNotas = getQtdNotas(i, valorRestante, valorNota);

            notasParaEntregar[i] = qtdNotas;
            valorRestante -= (qtdNotas * valorNota);
        }






        // Validação final
        if (valorRestante == 0) {
            StringBuilder mensagem = new StringBuilder("Saque realizado com sucesso!\n");
            for (int i = 0; i < estoqueCedulas.length; i++) {
                //this.totalSacadoNaSessao += valor;
                if (notasParaEntregar[i] > 0) {
                    mensagem.append(String.format("%d nota(s) de R$%d\n", notasParaEntregar[i],  estoqueCedulas[i][0]));
                    // Atualiza o estoque real (SET)
                    estoqueCedulas[i][1] -= notasParaEntregar[i];
                }
            }
            this.totalSacadoNaSessao += valor;
            JOptionPane.showMessageDialog(null, mensagem.toString());

        }
        else {
            JOptionPane.showMessageDialog(null, "Erro: O caixa não possui notas disponíveis para compor este valor exato.");
        }
        System.out.println("-----------------------------------");




        return "";

    }//fim sacar

    private int getQtdNotas(int i, int valorRestante, int valorNota) {
        int qtdDisponivel = estoqueCedulas[i][1];

        // Calcula quantas notas caberiam
        int qtdNotas = valorRestante / valorNota;

        // --- TRAVA DE SEGURANÇA PARA 1, 3, 11, 21... ---
        // Se tirar essas notas deixar um resto 1 ou 3, tiramos uma nota a menos
        if (valorNota > 5) {
            int restoSeTirarTudo = valorRestante - (qtdNotas * valorNota);
            if ((restoSeTirarTudo == 1 || restoSeTirarTudo == 3) && qtdNotas > 0) {
                qtdNotas--;
            }
        }

        // Lógica especial para a nota de 5: garantir que o que sobrar seja par (para a nota de 2)
        if (valorNota == 5) {
            if (valorRestante % 2 != 0 && valorRestante >= 5) {
                qtdNotas = 1; // Pega uma nota de 5 para tornar o resto par
            } else {
                qtdNotas = (valorRestante / 10) * 2; // Pega notas de 5 em pares (R$ 10)
            }
        }

        // Verifica se tem estoque suficiente
        if (qtdNotas > qtdDisponivel) {
            qtdNotas = qtdDisponivel;
        }
        return qtdNotas;
    }


    // Método para o relatório buscar esse valor
    public double getTotalSacado() {
        return totalSacadoNaSessao;
    }


    public String armazenaCotaMinima(Integer minimo) {
        this.cotaMinima = minimo;
        return "Cota mínima definida para R$" + minimo + ",00";
    }
    //logica de armazenar a cota minima para saque e criar um //mensagem(resposta)ao usuario





    public static void main(String arg[]){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI frame = new GUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}