package CaixaEletronico;
import java.awt.EventQueue;
import java.util. *;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Scanner;
public class CaixaEletronico implements ICaixaEletronico{
	private double totalSacadoNaSessao = 0;
	private int cotaMinima = 0;
	private int[][] estoqueCedulas;

    public CaixaEletronico() {
    	//this.totalSacadoNaSessao = totalSacadoNaSessao;
        this.estoqueCedulas = new int[][] {
            {100, 0},
            {50, 0},
            {20, 0},
            {10, 0},
            {5, 0},
            {2, 0}
        };
    }
    
    
    public int[][] getEstoqueCedulas() {
        return estoqueCedulas;
    }

    
    
    public void setEstoqueCedulas(int[][] estoqueCedulas) {
        this.estoqueCedulas = estoqueCedulas;
    }
    
    
    
	public String pegaRelatorioCedulas() {
		StringBuilder relatorio = new StringBuilder("Relatório de Cédulas:\n");
	    for (int[] par : estoqueCedulas) {
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
		int i = 0;
		String resposta = "Reposição concluída! Novo estoque de R$" + cedula + ": " + estoqueCedulas[i][1];
		
		for(i = 0; i < estoqueCedulas.length; i++) {
			// Verifica se a nota na linha i (posição 0) é a nota que queremos repor
	        if (estoqueCedulas[i][0] == cedula) {
	            
	            // Adiciona a nova quantidade ao estoque existente (posição 1)
	            estoqueCedulas[i][1] += quantidade; 
	            
	            return "Reposição concluída! Novo estoque de R$" + cedula + ": " + estoqueCedulas[i][1];
	        }
		}
		
		//logica de fazer a reposicao de cedulas e criar uma mensagem //(resposta)ao usuario
		return resposta;
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
		        	String resposta1 = "Erro: Valor de saque deve ser maior que zero.";
		        	return resposta1;
		        }
		        //segunda regra
		        if (valor == 1 || valor == 3) {
		        	String resposta2 = "Erro: Não existem notas para sacar valor de 1 ou tres reais";
		            return resposta2;
		        }
	       
		        
		  
        
		int[] notasParaEntregar = new int[estoqueCedulas.length];
		        
		// O Loop que percorre as notas
		for (int i = 0; i < estoqueCedulas.length; i++) {
			int valorNota = estoqueCedulas[i][0];
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
            return mensagem.toString();
        }
        else {
        	JOptionPane.showMessageDialog(null, "Erro: O caixa não possui notas disponíveis para compor este valor exato.");
        }
        System.out.println("-----------------------------------");
        
        
        
        
        return "";
        
    }//fim sacar

		
		
		
		// Método para o relatório buscar esse valor
	    public double getTotalSacado() {
	        return totalSacadoNaSessao;
	    }
   
		
		public String armazenaCotaMinima(Integer minimo) {
			String resposta = "Cota mínima definida para R$ " + minimo;
			this.cotaMinima = minimo;
			return resposta;
		}
		//logica de armazenar a cota minima para saque e criar um //mensagem(resposta)ao usuario
		
		
		
		
		
		public static void main(String arg[]){
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						GUI frame = new GUI();
						frame.setVisible(true);
						frame.realizarAbastecimentoInicial();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

}
