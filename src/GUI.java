
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    CaixaEletronico grafico = new CaixaEletronico();

    // MAIN
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            try { GUI frame = new GUI();
            
            frame.setVisible(true);

            } catch (Exception e) {e.printStackTrace();
            
            }
            
        });
    }

    // CONSTRUTOR
    public GUI() {

        setTitle("Caixa Eletrônico");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBounds(500, 200, 300, 380);

        contentPane = new JPanel();

        contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));

        contentPane.setLayout(null);

        setContentPane(contentPane);


        // BOTÃO SACAR

        JButton btnSacar = new JButton("Efetuar Saque");

        btnSacar.setFont(new Font("Tahoma", Font.PLAIN, 14));

        btnSacar.setBounds(47, 33, 190, 30);

        btnSacar.addActionListener(e -> {

            try {

                String valor = JOptionPane.showInputDialog(
                        "Informe o valor do saque:"
                );

                if (valor == null || valor.isEmpty()) {
                    return;
                }

                Integer valorParaSaque = Integer.parseInt(valor);

                String resultado = grafico.sacar(valorParaSaque);

                JOptionPane.showMessageDialog(null, resultado);

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(null, "Digite apenas números inteiros.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
            
        });

        contentPane.add(btnSacar);


        // RELATÓRIO

        JButton btnRelatorio = new JButton("Relatório de Cédulas");

        btnRelatorio.setFont(new Font("Tahoma", Font.PLAIN, 14));

        btnRelatorio.setBounds(47, 97, 190, 30);

        btnRelatorio.addActionListener(e -> {

            String relatorio = grafico.pegaRelatorioCedulas();

            JOptionPane.showMessageDialog(null, relatorio, "Relatório",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        contentPane.add(btnRelatorio);


        // VALOR TOTAL

        JButton btnValorTotal = new JButton("Valor Total Disponível");

        btnValorTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));

        btnValorTotal.setBounds(47, 138, 190, 30);

        btnValorTotal.addActionListener(e -> {

            String total = grafico.pegaValorTotalDisponivel();

            JOptionPane.showMessageDialog(null, total, "Saldo do Caixa",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        contentPane.add(btnValorTotal);


        // REPOSIÇÃO

        JButton btnReposicao = new JButton("Reposição de Cédulas");

        btnReposicao.setFont(new Font("Tahoma", Font.PLAIN, 14));

        btnReposicao.setBounds(47, 179, 190, 30);

        btnReposicao.addActionListener(e -> {

            try {

                String txtCedula = JOptionPane.showInputDialog("Qual nota deseja repor?");

                if (txtCedula == null || txtCedula.isEmpty()) {
                    return;
                }

                Integer cedula = Integer.parseInt(txtCedula);

                if (!grafico.existeCedula(cedula)) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Cédula inválida," + " " + "Notas Disponiveis 2,5,10,20,50,100",
                            "Erro", JOptionPane.ERROR_MESSAGE);

                    return;
                }

                String Quantidade = JOptionPane.showInputDialog("Quantidade de notas:");

                if (Quantidade == null || Quantidade.isEmpty()) {
                    return;
                }

                Integer qtd = Integer.parseInt(Quantidade);

                String msg = grafico.reposicaoCedulas(cedula, qtd);

                JOptionPane.showMessageDialog(null,msg);

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(null,
                        "Digite apenas números inteiros.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        contentPane.add(btnReposicao);


        // COTA MÍNIMA

        JButton btnCotaMinima = new JButton("Cota Mínima");

        btnCotaMinima.setFont(new Font("Tahoma", Font.PLAIN, 14));

        btnCotaMinima.setBounds(47, 220, 190, 30);

        btnCotaMinima.addActionListener(e -> {

            try {String cota = JOptionPane.showInputDialog("Informe a nova cota mínima:");

                if (cota == null || cota.isEmpty()) {
                    return;
                }

                Integer minimo = Integer.parseInt(cota);

                String msg = grafico.armazenaCotaMinima(minimo);

                JOptionPane.showMessageDialog(null,msg);

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(null,"Digite apenas números inteiros.","Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        contentPane.add(btnCotaMinima);


        // SAIR

        JButton btnSair = new JButton("Sair");

        btnSair.setFont(new Font("Tahoma", Font.PLAIN, 14));

        btnSair.setBounds(47, 280, 190, 30);

        btnSair.addActionListener(e -> {

            String extratoFinal = grafico.extrato();

            JOptionPane.showMessageDialog(null, extratoFinal, "Extrato Final",
                    JOptionPane.INFORMATION_MESSAGE);

            System.exit(0);
        });

        contentPane.add(btnSair);


        // LABELS

        JLabel lblCliente = new JLabel("Módulo do Cliente:");

        lblCliente.setForeground(SystemColor.desktop);

        lblCliente.setBounds(47, 15, 150, 14);

        contentPane.add(lblCliente);

        JLabel lblAdmin = new JLabel("Módulo do Administrador:");

        lblAdmin.setBounds(47, 75, 170, 14);

        contentPane.add(lblAdmin);

        JLabel lblAmbos = new JLabel("Módulo Geral:");

        lblAmbos.setBounds(47, 261, 170, 14);

        contentPane.add(lblAmbos);
    }
}

