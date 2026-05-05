import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;


// Define que esta classe é uma janela
public class GUI extends JFrame {
	
    CaixaEletronico grafico = new CaixaEletronico();
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

// É o ponto de entrada
    public static void main(String[] args) {

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

// define as características visuais 
    public GUI() {
    	setTitle("Caixa Eletronico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 200, 282, 372);
        contentPane = new JPanel();
        contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnSacar = new JButton("Efetuar Saque");
        btnSacar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSacar.setBounds(47, 33, 180, 30);
        btnSacar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String valor = JOptionPane.showInputDialog("Informe o valor de saque:");
                
// Verificação simples para evitar erro se o usuário cancelar o diálogo
                if (valor != null && !valor.isEmpty()) {
                    try {Integer valorParaSaque = Integer.parseInt(valor);

// Chama o seu método 'sacar' e guarda a mensagem de retorno,
                        String resultado = grafico.sacar(valorParaSaque);

// Exibe o resultado para o usuário
                    } catch (NumberFormatException ex) {
                    	
// Caso o usuário digite letras em vez de números
                        JOptionPane.showMessageDialog(null, "Por favor, insira um valor numérico válido.");
                    }
                }

            }
            
        });
        
// define a funcionalidade dos botões no painel
        contentPane.add(btnSacar);

        JButton btnRelatorio = new JButton("Relatório de Cedulas");
        btnRelatorio.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnRelatorio.setBounds(47, 97, 180, 30);
        btnRelatorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String relatorioCompleto = grafico.pegaRelatorioCedulas();
                JOptionPane.showMessageDialog(null, relatorioCompleto, "Estoque de Notas", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        contentPane.add(btnRelatorio);

        JButton btnValorTotal = new JButton("Valor Total Disponivel");
        btnValorTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnValorTotal.setBounds(47, 138, 180, 30);
        btnValorTotal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String ValorTotal = grafico.pegaValorTotalDisponivel();
            }
        });
        
        contentPane.add(btnValorTotal);

        JButton btnReposicaoCedulas = new JButton("Reposição de Cedulas");
        btnReposicaoCedulas.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnReposicaoCedulas.setBounds(47, 179, 180, 30);
        btnReposicaoCedulas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    String txtCedula = JOptionPane.showInputDialog("Qual nota deseja repor? (Ex: 20, 50, 100)");

                    if (!txtCedula.isEmpty()) { //Invoca o metodo de verificação de Cedula
                        Integer cedula = Integer.parseInt(txtCedula);

                        int valorCedula = Integer.parseInt(txtCedula);

                        // ... Validação Imedianta ...
                        if (!grafico.existeCedula(valorCedula)) {
                            JOptionPane.showMessageDialog
                            (null, "Reposição negada! Cedula Inválida", "Erro", JOptionPane.ERROR_MESSAGE);
                            
// mata a execução do botão, com isso a parte de inserir a quantidade nem é executada
                            return; 
                        }

                        String txtQtd = JOptionPane.showInputDialog("Quantidade de notas:");

                        if(!txtQtd.isEmpty()){
                            Integer qtd = Integer.parseInt(txtQtd);

                            // Chama o método e mostra a resposta
                            String msg = grafico.reposicaoCedulas(cedula, qtd);
                            JOptionPane.showMessageDialog(null, msg);

                            // 2. Chama o relatório logo em seguida para mostrar o novo estado
                            String relatorioAtualizado = grafico.pegaRelatorioCedulas();
                            JOptionPane.showMessageDialog
                            (null, relatorioAtualizado, "Estoque Atualizado", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Digite o valor da Cedula para repor");
                }
            }
        });
        
        contentPane.add(btnReposicaoCedulas);

        JButton btnCotaMinima = new JButton("Cota Minima");
        btnCotaMinima.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnCotaMinima.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    String cota = JOptionPane.showInputDialog("Informe a Cota minima");

                    if (cota != null && !cota.isEmpty()) {
                        Integer minimo = Integer.parseInt(cota);

                        // ... Chama o método e mostra a resposta ...
                        String msg = grafico.armazenaCotaMinima(minimo);
                        JOptionPane.showMessageDialog(null, msg);

                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Digite apenas números inteiros!");
                }

            }
        });
        
        btnCotaMinima.setBounds(47, 220, 180, 30);
        contentPane.add(btnCotaMinima);

        JButton btnSair = new JButton("Sair");
        btnSair.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                // ... Busca o relatório final (use o objeto global do caixa) ...
                String extratoFinal = grafico.extrato();

                // ... Exibe o extrato em uma última janela ...
                JOptionPane.showMessageDialog
                (null, extratoFinal, "Extrato Final de Encerramento", JOptionPane.INFORMATION_MESSAGE);

                // ... Fecha o aplicativo completamente ...
                System.exit(0);
            }
        });
        
        btnSair.setBounds(47, 280, 180, 30);
        contentPane.add(btnSair);
        
        JLabel lblNewLabel = new JLabel("Modulo do Cliente:");
        lblNewLabel.setForeground(SystemColor.desktop);
        lblNewLabel.setBounds(46, 16, 109, 14);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Modulo do Admistrador:");
        lblNewLabel_1.setBounds(47, 74, 170, 14);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Modulo de Ambos:");
        lblNewLabel_2.setBounds(47, 261, 170, 14);
        contentPane.add(lblNewLabel_2);

    }
}
