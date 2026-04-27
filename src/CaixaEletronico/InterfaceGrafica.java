package CaixaEletronico;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

//import Demontraçao.ICaixaEletronico;
//import Demontraçao.InterfaceGráfica;

//import Demontraçao.ICaixaEletronico;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Window.Type;

public class InterfaceGrafica extends JFrame {

	private static final long serialVersionUID = 1L;
	//private JPanel CaixaEletronico;
	private JPanel contentPane;
	private ICaixaEletronico logica;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceGrafica frame = new InterfaceGrafica();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	/*public InterfaceGrafica() {
		setType(Type.POPUP);
		setTitle("Caixa Eletronico");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 406, 427);
		CaixaEletronico = new JPanel();
		CaixaEletronico.setBackground(new Color(192, 192, 192));
		CaixaEletronico.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(CaixaEletronico);
		
		JButton btnRelatorioCedulas = new JButton("Relatorio de Cedulas");
		btnRelatorioCedulas.setFont(new Font("Dialog", Font.PLAIN, 14));
		btnRelatorioCedulas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnValorTotalDisponivel = new JButton("Valor Total Disponivel");
		btnValorTotalDisponivel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton btnReposicaoCedulas = new JButton("Reposicao de Cedulas ");
		btnReposicaoCedulas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton btnNewButton_1_1_1_1 = new JButton("Cota Minima");
		btnNewButton_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnSair = new JButton("Sair");
		btnSair.setBackground(new Color(128, 128, 128));
		btnSair.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton btnEfetuarSaque = new JButton("Efetuar Saque");
		btnEfetuarSaque.setForeground(SystemColor.windowText);
		btnEfetuarSaque.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblNewLabel = new JLabel("Modulo do Cliente:");
		lblNewLabel.setForeground(SystemColor.desktop);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblNewLabel_1 = new JLabel("Modulo do Admistrador:");
		lblNewLabel_1.setForeground(SystemColor.activeCaptionText);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblNewLabel_2 = new JLabel("Modulo de Ambos:");
		lblNewLabel_2.setForeground(SystemColor.desktop);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout gl_caixaEletronico = new GroupLayout(CaixaEletronico);
		gl_caixaEletronico.setHorizontalGroup(
			gl_caixaEletronico.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_caixaEletronico.createSequentialGroup()
					.addGroup(gl_caixaEletronico.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_caixaEletronico.createSequentialGroup()
							.addGap(18)
							.addComponent(btnSair, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_caixaEletronico.createSequentialGroup()
							.addContainerGap(23, Short.MAX_VALUE)
							.addComponent(btnValorTotalDisponivel, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_caixaEletronico.createSequentialGroup()
							.addContainerGap(23, Short.MAX_VALUE)
							.addComponent(btnReposicaoCedulas, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_caixaEletronico.createSequentialGroup()
							.addGap(23)
							.addGroup(gl_caixaEletronico.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(btnRelatorioCedulas, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)))
						.addGroup(gl_caixaEletronico.createSequentialGroup()
							.addGap(23)
							.addGroup(gl_caixaEletronico.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addComponent(btnEfetuarSaque, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE)))
						.addGroup(Alignment.TRAILING, gl_caixaEletronico.createSequentialGroup()
							.addContainerGap(23, Short.MAX_VALUE)
							.addGroup(gl_caixaEletronico.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2)
								.addComponent(btnNewButton_1_1_1_1, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_caixaEletronico.setVerticalGroup(
			gl_caixaEletronico.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_caixaEletronico.createSequentialGroup()
					.addGap(18)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnEfetuarSaque, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_1)
					.addGap(9)
					.addComponent(btnRelatorioCedulas, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnValorTotalDisponivel, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnReposicaoCedulas, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton_1_1_1_1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_2)
					.addGap(15)
					.addComponent(btnSair, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		CaixaEletronico.setLayout(gl_caixaEletronico);

	}*/
	
	public InterfaceGrafica() {
		// --- INICIALIZAÇÃO DA LÓGICA ---
		logica = new ICaixaEletronico(); 
		// Adicionando algumas notas iniciais para teste
		logica.reporNotas(100, 10);
		logica.reporNotas(50, 20);
		logica.reporNotas(20, 30);
		// -------------------------------

		setType(Type.POPUP);
		setTitle("Caixa Eletronico");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 406, 450);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// 1. BOTÃO RELATÓRIO
		JButton btnRelatorioCedulas = new JButton("Relatorio de Cedulas");
		btnRelatorioCedulas.setFont(new Font("Dialog", Font.PLAIN, 14));
		btnRelatorioCedulas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String relatorio = logica.gerarRelatorioCedulas();
				JOptionPane.showMessageDialog(null, relatorio);
			}
		});
		
		// 2. BOTÃO VALOR TOTAL
		JButton btnValorTotalDisponivel = new JButton("Valor Total Disponivel");
		btnValorTotalDisponivel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnValorTotalDisponivel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double total = logica.getValorTotalDisponivel();
				JOptionPane.showMessageDialog(null, "Total no Caixa: R$ " + total);
			}
		});
		
		// 3. BOTÃO REPOSIÇÃO
		JButton btnReposicaoCedulas = new JButton("Reposicao de Cedulas ");
		btnReposicaoCedulas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReposicaoCedulas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String notaStr = JOptionPane.showInputDialog("Qual o valor da nota (2, 5, 10, 20, 50, 100)?");
				String qtdStr = JOptionPane.showInputDialog("Quantidade de notas:");
				try {
					int nota = Integer.parseInt(notaStr);
					int qtd = Integer.parseInt(qtdStr);
					logica.reporNotas(nota, qtd);
					JOptionPane.showMessageDialog(null, "Reposição efetuada com sucesso!");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro nos dados informados.");
				}
			}
		});
		
		// 4. BOTÃO COTA MÍNIMA
		JButton btnCotaMinima = new JButton("Cota Minima");
		btnCotaMinima.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCotaMinima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String valorStr = JOptionPane.showInputDialog("Defina o novo valor da cota mínima:");
				try {
					double valor = Double.parseDouble(valorStr);
					logica.setCotaMinima(valor);
					JOptionPane.showMessageDialog(null, "Cota mínima atualizada!");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Valor inválido.");
				}
			}
		});
		
		// 5. BOTÃO SAIR
		JButton btnSair = new JButton("Sair");
		btnSair.setBackground(new Color(128, 128, 128));
		btnSair.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSair.addActionListener(e -> System.exit(0));
		
		// 6. BOTÃO EFETUAR SAQUE (MÓDULO CLIENTE)
		JButton btnEfetuarSaque = new JButton("Efetuar Saque");
		btnEfetuarSaque.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEfetuarSaque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String valorStr = JOptionPane.showInputDialog("Quanto deseja sacar?");
				try {
					int valor = Integer.parseInt(valorStr);
					String resultado = logica.efetuarSaque(valor);
					JOptionPane.showMessageDialog(null, resultado);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Por favor, digite um valor inteiro.");
				}
			}
		});
		
		// --- ABAIXO SEGUE O SEU LAYOUT (GROUP LAYOUT) ---
		JLabel lblNewLabel = new JLabel("Modulo do Cliente:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblNewLabel_1 = new JLabel("Modulo do Admistrador:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblNewLabel_2 = new JLabel("Modulo de Ambos:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSair, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
						.addComponent(btnCotaMinima, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
						.addComponent(btnReposicaoCedulas, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
						.addComponent(btnValorTotalDisponivel, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
						.addComponent(btnRelatorioCedulas, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel)
						.addComponent(btnEfetuarSaque, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
						.addComponent(lblNewLabel_2))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(18)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnEfetuarSaque, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRelatorioCedulas, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnValorTotalDisponivel, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnReposicaoCedulas, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCotaMinima, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSair, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(30, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	
}
