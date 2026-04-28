package CaixaEletronico;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {
	CaixaEletronico grafico = new CaixaEletronico();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
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
	public void realizarAbastecimentoInicial() {
	    int[] notas = {100, 50, 20, 10, 5, 2};
	    
	    for (int nota : notas) {
	        String qtdStr = JOptionPane.showInputDialog(this, 
	            "ABASTECIMENTO INICIAL\nInforme a quantidade de notas de R$ " + nota + ":", 
	            "Configuração do Caixa", 
	            JOptionPane.QUESTION_MESSAGE);
	            
	        try {
	            if (qtdStr != null && !qtdStr.isEmpty()) {
	                int qtd = Integer.parseInt(qtdStr);
	                // Chama o seu método de reposição para alimentar a matriz
	                grafico.reposicaoCedulas(nota, qtd);
	            } else {
	                // Se o usuário cancelar ou deixar vazio, assume 0 para essa nota
	                grafico.reposicaoCedulas(nota, 0);
	            }
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Valor inválido. Quantidade definida como 0 para esta nota.");
	            grafico.reposicaoCedulas(nota, 0);
	        }
	    }
	    
	    JOptionPane.showMessageDialog(this, "Abastecimento concluído com sucesso!");
	}
	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 775, 571);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(165, 16, 10, 10);
		contentPane.add(panel);
		
		JButton btnSacar = new JButton("Sacar");
		btnSacar.setBounds(32, 52, 688, 23);
		btnSacar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String valor = JOptionPane.showInputDialog("Informe o valor de saque:");
				// Verificação simples para evitar erro se o usuário cancelar o diálogo
		        if (valor != null && !valor.isEmpty()) {
		            try {
		                // 2. Converte a String para um Integer
		                Integer valorParaSaque = Integer.parseInt(valor);
		                
		                // 3. Chama o seu método 'sacar' e guarda a mensagem de retorno
		                // (Assumindo que o método 'sacar' está na mesma classe ou objeto acessível)
		                String resultado = grafico.sacar(valorParaSaque);
		                
		                // 4. Exibe o resultado para o usuário
		                JOptionPane.showMessageDialog(null, resultado);
		                
		            } catch (NumberFormatException ex) {
		                // Caso o usuário digite letras em vez de números
		                JOptionPane.showMessageDialog(null, "Por favor, insira um valor numérico válido.");
		            }
		        }
				
			}
		});
		contentPane.add(btnSacar);
		
		JButton btnRelatorio = new JButton("Relatório");
		btnRelatorio.setBounds(32, 119, 688, 23);
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String relatorioCompleto = grafico.pegaRelatorioCedulas();
				JOptionPane.showMessageDialog(null, relatorioCompleto, "Estoque de Notas", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		contentPane.add(btnRelatorio);
		
		JButton btnValorTotal = new JButton("Valor Total Disponivel");
		btnValorTotal.setBounds(32, 243, 688, 23);
		btnValorTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String ValorTotal = grafico.pegaValorTotalDisponivel();
			}
		});
		contentPane.add(btnValorTotal);
		
		JButton btnReposicaoCedulas = new JButton("Reposicao Cedulas");
		btnReposicaoCedulas.setBounds(32, 312, 688, 23);
		btnReposicaoCedulas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
		            String txtCedula = JOptionPane.showInputDialog("Qual nota deseja repor? (Ex: 20, 50, 100)");
		            String txtQtd = JOptionPane.showInputDialog("Quantidade de notas:");
		            
		            if (txtCedula != null && !txtCedula.isEmpty() && txtQtd != null&& !txtQtd.isEmpty()) {
		                Integer cedula = Integer.parseInt(txtCedula);
		                Integer qtd = Integer.parseInt(txtQtd);
		                
		                // Chama o método e mostra a resposta
		                String msg = grafico.reposicaoCedulas(cedula, qtd);
		                JOptionPane.showMessageDialog(null, msg);
		                
		             // 2. Chama o relatório logo em seguida para mostrar o novo estado
		                String relatorioAtualizado = grafico.pegaRelatorioCedulas();
		                JOptionPane.showMessageDialog(null, relatorioAtualizado, "Estoque Atualizado", JOptionPane.INFORMATION_MESSAGE);
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Digite apenas números inteiros!");
		        }
			}
		});
		contentPane.add(btnReposicaoCedulas);
		
		
		
		
		JButton btnCotaMinima = new JButton("Cota Miniman");
		btnCotaMinima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
		            String cota = JOptionPane.showInputDialog("Informe a Cota minima");
		            
		            
		            if (cota != null && !cota.isEmpty()) {
		                Integer minimo = Integer.parseInt(cota);
		                
		                
		                // Chama o método e mostra a resposta
		                String msg = grafico.armazenaCotaMinima(minimo);
		                JOptionPane.showMessageDialog(null, msg);
		                
		             // 2. Chama o relatório logo em seguida para mostrar o novo estado
		                String relatorioAtualizado = grafico.pegaRelatorioCedulas();
		                JOptionPane.showMessageDialog(null, relatorioAtualizado, "Estoque Atualizado", JOptionPane.INFORMATION_MESSAGE);
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Digite apenas números inteiros!");
		        }
				
			}
		});
		btnCotaMinima.setBounds(32, 394, 688, 23);
		contentPane.add(btnCotaMinima);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 1. Busca o relatório final (use o seu objeto global do caixa)
		        String extratoFinal = grafico.pegaRelatorioCedulas();
		        
		        // 2. Exibe o extrato em uma última janela
		        JOptionPane.showMessageDialog(null, extratoFinal, "Extrato Final de Encerramento", JOptionPane.INFORMATION_MESSAGE);
		        
		        // 3. Fecha o aplicativo completamente
		        System.exit(0);
			}
		});
		btnSair.setBounds(32, 474, 688, 23);
		contentPane.add(btnSair);

	}
}
