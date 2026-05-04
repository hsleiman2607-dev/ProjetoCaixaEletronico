import java.util. *;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.EventQueue;


public class CaixaEletronico implements ICaixaEletronico{
	
// Armazena o valor acumulado de saques efetuados 
    private double totalSacadoNaSessao = 0;
    
// valor total de saldo disponivel para efetuar saque 
    private double saldoDisponivel = 32750.00;
    
// Objeto que armazena textualmente todos os saques para gerar o extrato final
    private StringBuilder historicoSaques = new StringBuilder("--- EXTRATO DE SAQUES ---\n");
    
// valor minimo que caixa deve manter 
    private int cotaMinima = 1000;
    
// Matriz que representa o estoque: [valor da nota][quantidade disponível]
    private final Integer[][] estoqueCedulas;

    public CaixaEletronico() {
    	//// Inicialização do estoque com notas de 100, 50, 20, 10, 5 e 2 reais
        this.estoqueCedulas = new Integer[][] {
        	
                {100, 100},
                {50,  200},
                {20,  300},
                {10,  350},
                {5,   450},
                {2,   500}
        };
        
    }

// metodo de gerar o relatorio de estoque atual de cedulas e total de sacado na sessao  
    public String pegaRelatorioCedulas() {
    	
// Inicializa o StringBuilder,construi a string, com o título do relatório
        StringBuilder relatorio = new StringBuilder("Relatório de Cédulas:\n");
        
// Percorre a matriz de estoque para listar cada valor de nota e sua quantidade disponível
        for (Integer[] par : estoqueCedulas) {
        	
        	// par[0] contém o valor da nota (ex: 100) e par[1] a quantidade
            relatorio.append(String.format("Nota R$ %d - Qtd: %d\n", par[0], par[1]));
        }
        
        relatorio.append("\n-------------------------------------------------------------------------\n");
        
// Adiciona o valor acumulado de saques realizados na sessão
        relatorio.append(String.format("TOTAL SACADO NESTA SESSÃO: R$ %,.2f\n", totalSacadoNaSessao));
        relatorio.append("-------------------------------------------------------------------------\n");
        return relatorio.toString();

    }
// metodo para mostrar o valor total disponivel no caixa eletronico
    public String pegaValorTotalDisponivel() {
        int i, soma=0;
        String resposta = "Valor Total é " + soma;
        
// logica de pega o valor total disponivel no caixa eletronio
        for(i = 0; i < estoqueCedulas.length; i++) {
            int valorPorNota = estoqueCedulas[i][0] * estoqueCedulas[i][1];
            soma += valorPorNota;
        }
        JOptionPane.showMessageDialog(null, String.format("Valor total no caixa: R$ %,.2f", (double)soma));
        return resposta;
    }


// metodo para repor cedulas do caixa 
    public String reposicaoCedulas(Integer cedula, Integer quantidade) {

        for(int i = 0; i < estoqueCedulas.length; i++) {
// Verifica se a nota na linha i (coluna 0) é a nota que queremos repor
            if (estoqueCedulas[i][0].equals(cedula)) {

// Adiciona a nova quantidade ao estoque existente (coluna 1)
                estoqueCedulas[i][1] += quantidade;

                return "Reposição concluída! Novo estoque de R$" + cedula + ": " + estoqueCedulas[i][1];
            }

        }

// logica de fazer a reposicao de cedulas e criar uma mensagem(resposta) ao usuario
        return "Reposição negada! Cedula Inválida";
    }

// Método que verifica se a Nota existe mesmo, na hora da reposição
    public boolean existeCedula(int cedula) {
        for (Integer[] linha : estoqueCedulas) {
            if (linha[0] == cedula) {
                return true; // Tem lá!
            }
        }
        return false; // Não existe
    }

// calcula soma total disponivel no caixa somando todas cedulas
    private int calcularSomaTotal() {
    	
// variavel acumuladora para armazenar o valor total em reais 
        int soma = 0;
        
// Percorre cada linha da matriz estoqueCedulas
        for(int i = 0; i < estoqueCedulas.length; i++) {
        	
/* Multiplica o valor da nota (índice [i][0])
pela quantidade disponível (índice [i][1]) e soma ao total acumulado*/
            soma += estoqueCedulas[i][0] * estoqueCedulas[i][1];
        }
// retorna a soma
        return soma;
    }


// Variável acumuladora ----?
    public String sacar(Integer valor) { 
        int valorDisponivel = calcularSomaTotal();
        
// validação que impede saques se o caixa estiver abaixo da reserva(Cota Minima)
        if (valorDisponivel < this.cotaMinima) {
            return "Caixa Vazio: Chame o Operador";
        }
        int valorRestante = valor;
        JOptionPane.showMessageDialog(null, String.format("Processando saque de %d", valor));

// primeira regra impedi valores negativos
        if (valor <= 0) {
            return "Erro: Valor de saque deve ser maior que zero.";
        }
        
//segunda regra tratamento de valores impossíveis (como 1 ou 3 reais)
        if (valor == 1 || valor == 3) {
            return "Erro: Não existem notas para sacar valor de 1 ou tres reais";
        }

// Array para armazenar temporariamente a quantidade de cada nota que será entregue
        int[] notasParaEntregar = new int[estoqueCedulas.length];
        
// Contador para validar o limite físico de saída de notas para efetuar saque
        int totalDeNotasSendoEntregues = 0;
        
        
// --- falta comentarios ---
// O Loop que percorre as notas
        for (int i = 0; i < estoqueCedulas.length; i++) {
            int valorNota = estoqueCedulas[i][0];
            int qtdNotas = getQtdNotas(i, valorRestante, valorNota);

            notasParaEntregar[i] = qtdNotas;
            valorRestante -= (qtdNotas * valorNota);
               
           totalDeNotasSendoEntregues += qtdNotas;

        }
        
        if (totalDeNotasSendoEntregues > 30) {
            JOptionPane.showMessageDialog(null, 
                "Limite de notas excedido! O saque resultaria em " + totalDeNotasSendoEntregues + " notas.\n" +
                "O limite máximo permitido é de 30 notas.", "Erro no Saque", JOptionPane.ERROR_MESSAGE);
                
            return "Erro"; // Esse 'return' para o método e impede o saque.
        }
        
        
// Validação final verifica se caixa consiguiu zerar  o valor do saque com as notas disponíveis
        if (valorRestante == 0) {
        	
        	this.saldoDisponivel -= valor; // Subtrai o valor sacado do saldo total
        	
// Acumula o valor no total sacado durante a sessão atual para o relatório de cédulas
        	this.totalSacadoNaSessao += valor; //somar o valor sacado do totalSacadoNaSessao
        	
// registro de mensagem do saque efetuado 
            StringBuilder mensagem = new StringBuilder("Saque realizado com sucesso!\n");
            
// addiciona o registro deste saque ao historico de saques para extrato de encarramento 
            historicoSaques.append(String.format("Saque: R$ %d,00 | Saldo Atual: R$ %.2f\n", valor, this.saldoDisponivel));
            
// Percorre o array temporário de notas selecionadas para atualizar a matriz estoqueCedulas
            for (int i = 0; i < estoqueCedulas.length; i++) {
                
                if (notasParaEntregar[i] > 0) {
// mensagem detalhando quais notas o cliente está recebendo
                    mensagem.append(String.format("%d nota(s) de R$%d\n", notasParaEntregar[i],  estoqueCedulas[i][0]));
                    
// Atualiza o estoque real (SET)
                    estoqueCedulas[i][1] -= notasParaEntregar[i];
                }
            }
            
            JOptionPane.showMessageDialog(null, mensagem.toString());

        }
        else {
        	
// Caso o valorRestante não seja zero, as notas disponíveis não foram suficientes para compor o valor exato
            JOptionPane.showMessageDialog(null, "Erro: O caixa não possui notas disponíveis para compor este valor exato.");
        }
        System.out.println("-----------------------------------");

        return "";

    } // fim sacar

// metodo que calcula  quantidade ideal de notas  em determinado saque
    private int getQtdNotas(int i, int valorRestante, int valorNota) {
    	
// Consulta a quantidade física disponível desta nota específica no estoque
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

// Método para o relatório buscar esse valor sacado 
    public double getTotalSacado() {
        return totalSacadoNaSessao;
    }
    
// Metodo para  gerar o extrato detalhado consolidando o histórico de saques e o saldo final
    public String extrato() {
    	
        // 1. Começamos direto com o cabeçalho
        StringBuilder relatorio = new StringBuilder();  
        
        // 2. Verificamos se há histórico
        if (historicoSaques.length() == 0) { 
        	
//relatorio.append("--- EXTRATO  ---\n\n");
            relatorio.append("Nenhum saque realizado nesta sessão.\n");
        } else {
// Adiciona o título do histórico e os dados salvos
           
            relatorio.append(historicoSaques.toString());
        }
        
        // 3. Adicionamos o rodapé com o Saldo Atualizado
        relatorio.append("\n----------------------------\n");
        relatorio.append(String.format("SALDO ATUALIZADO: R$ %.2f", this.saldoDisponivel));
        
        return relatorio.toString();
    }
    
//logica de armazenar a cota minima para saque e criar um //mensagem(resposta)ao usuario
    public String armazenaCotaMinima(Integer minimo) {
        this.cotaMinima = minimo;
        return "Cota mínima definida para R$" + minimo + ",00";
    }
   

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

