package CaixaNovo;



/* é uma API fundamental do Java para a criação de Interfaces Gráficas de Usuário (GUI)
em aplicações desktop import javax.swing Faz parte da biblioteca Swing Usada para criar interfaces gráficas (GUI)
Componentes mais modernos e flexíveis Exemplos de componentes: JFrame (janela),
JButton (botão), JLabel (texto) e o  JTextField (campo de texto)*/
import javax.swing.*;

/* é uma API (Application Programming Interface)
fundamental do Java usada para desenvolver Interfaces Gráficas de Usuário (GUI),
como janelas, botões, menus e campos de texto, para aplicações desktop*/
import java.awt.*;


/* Herança e Conexão (extends JFrame): public class InterfaceCaixa extends JFrame:

Ao dizer que ela estende JFrame, estamos transformando essa classe em uma janela de
aplicativo. Ela ganha automaticamente funções como minimizar, fechar e exibir título.*/

// esse é a interface do caixa eletronico
public class ICaixaEletronico extends JFrame {

//... 	A Conexão com o "Cérebro" ...

    /* private CaixaEletronico logica: Esta é a variável que guarda a inteligência do sistema.

    Composição: A interface precisa saber com quem ela vai falar.

    Você cria uma variável logica que vai guardar a inteligência do sistema que vimos anteriormente.
    Ela é inicializada no construtor com logica = new CaixaEletronico();.*/
    private CaixaEletronico logica;

    /* O "Design" da Janela (configurarJanela): Aqui é onde definimos onde cada botão fica.
    Imagine como se estivéssemos arrumando os móveis de uma sala.*/
    public ICaixaEletronico() {
        logica = new CaixaEletronico();
        configurarJanela();
    }

    /* define o título da janela, ou seja, o nome que aparece lá em cima*/
    private void configurarJanela() {
        setTitle("Caixa eletrônico");

        /* define o que acontece quando o usuário fecha a janela:EXIT_ON_CLOSE significa encerrar
         * completamente o programa.*/
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

/* setLayout(new BorderLayout()): Define a organização principal.
O BorderLayout divide a janela em Norte, Sul, Leste, Oeste e Centro.*/
        getContentPane().setLayout(new BorderLayout());



/* Painel Principal com Layout Vertical:


/* JPanel e BoxLayout: O painelPrincipal é uma "caixa" onde empilhamos os componentes.
O BoxLayout com Y_AXIS garante que os botões fiquem um embaixo do outro (eixo vertical).*/
        JPanel painelPrincipal = new JPanel();

/* setLayout(new BorderLayout()): Define a organização principal.
O BorderLayout divide a janela em Norte, Sul, Leste, Oeste e Centro.*/
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


/* Resumo do Fluxo: O usuário clica no botão "Efetuar Saque".

A interface abre um JOptionPane perguntando o valor.
A interface envia esse valor para o método logica.sacar(valor).

A Logica (classe A) calcula as notas e devolve um texto (String).
A Interface (classe B) mostra esse texto em uma janelinha de mensagem.*/

/* Interatividade: Os Ouvintes (ActionListener):
Os botões por si só não fazem nada. Para eles funcionarem, usamos os "Listeners" (Ouvintes):
Ela diz ao Java: "Quando o botão for clicado, execute o método acaoSaque".*/



 /*    ... Os Botões e a Interatividade (Listeners) ....

Botões Surdos: Um JButton sozinho é "surdo"; você pode clicar nele mil vezes e nada acontece.

addActionListener: É aqui que você dá "ouvidos" ao botão.

Expressão Lambda (e ->): É uma forma moderna e resumida do Java dizer:
"Quando ocorrer um evento (e) neste botão, aponte (->) para o método acaoSaque() e execute-o".*/



// ... Módulo do Cliente ...
        painelPrincipal.add(new JLabel("Módulo do Cliente:"));
        JButton btnSaque = criarBotaoLargo("Efetuar Saque");

/* btnSaque.addActionListener(e -> acaoSaque()):
Aqui usamos uma Expressão Lambda (esse símbolo ->).*/
        btnSaque.addActionListener(e -> acaoSaque());
        painelPrincipal.add(btnSaque);

/* Box.createRigidArea: É um espaçador invisível.
Serve para os botões não ficarem "grudados" uns nos outros, criando um respiro visual.*/
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));


        // ... Módulo do Administrador ...
        painelPrincipal.add(new JLabel ("Modulo do Administrador:"));
        JButton btnRelatorio = criarBotaoLargo("Relatorio de Cedulas");
        btnRelatorio.addActionListener(e -> exibirMensagem(logica.gerarRelatorioCedulas()));

        JButton btnTotal = criarBotaoLargo("Valor total disponivel");
        btnTotal.addActionListener(e -> exibirMensagem
                ("Total em Caixa: R$ " + logica.getValorTotalDisponivel()));

        JButton btnReposicao = criarBotaoLargo("Reposicao de Cedulas");
        btnReposicao.addActionListener(e -> acaoReposicao());

        JButton btnCota = criarBotaoLargo("Parcela Minima");
        btnCota.addActionListener(e -> acaoDefinirCota());

        painelPrincipal.add(btnRelatorio);
        painelPrincipal.add(btnTotal);
        painelPrincipal.add(btnReposicao);
        painelPrincipal.add(btnCota);

        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));


        // ... Módulo de Ambos ...
        painelPrincipal.add(new JLabel("Modulo de Ambos:"));
        JButton btnSair = criarBotaoLargo("Sair");
        btnSair.addActionListener(e -> System.exit(0));
        painelPrincipal.add(btnSair);

        getContentPane().add(painelPrincipal);

/* pack() e setLocationRelativeTo(null):
O pack ajusta o tamanho da janela para que tudo caiba perfeitamente,*/
        pack();

        /* o null faz com que a janela apareça bem no centro da sua tela.*/
        setLocationRelativeTo(null);
    }

    /* O programa não usa campos de texto fixos na janela principal, mas sim janelas flutuantes (pop-ups):*/

    private void acaoSaque() {

/* Caixas de Diálogo (JOptionPane), showInputDialog:
Abre uma caixinha para o usuário digitar algo (como o valor do saque).*/
        String input = JOptionPane.showInputDialog(this, "Digite o valor do saque:");
        if (input != null) {
            try {
                int valor = Integer.parseInt(input);

/* ... Métodos de Ação (Conectam com a Lógica): ...
 *
showMessageDialog: Abre uma caixinha apenas para mostrar um aviso ou o resultado final (o recibo).*/
                exibirMensagem(logica.sacar(valor));
            } catch (NumberFormatException e) {

                exibirMensagem("Erro: Digite um valor numérico válido.");
            }
        }
    }

/* ... Tratamento de Erros (try-catch): ...

Note que dentro de acaoSaque e acaoReposicao existe um bloco try-catch. Isso é vital.
Se o usuário digitar letras onde o programa espera números (como "cem" em vez de "100"), o Integer.

parseInt daria um erro crítico. o catch "segura" esse erro e mostra uma mensagem educada:
"Erro: Digite um valor numérico válido".*/

    private void acaoReposicao() {
        String notaStr = JOptionPane.showInputDialog(this, "Qual nota repor (2, 5, 10, 20, 50, 100)?");
        String qtdStr = JOptionPane.showInputDialog(this, "Quantidade de notas:");

        if (notaStr != null && qtdStr != null) {
            try {
                int nota = Integer.parseInt(notaStr);
                int qtd = Integer.parseInt(qtdStr);
                logica.reporNotas(nota, qtd);
                exibirMensagem("Reposição efetuada com sucesso!");
            } catch (Exception e) {
                exibirMensagem("Erro na reposição. Verifique os valores.");
            }
        }
    }

    private void acaoDefinirCota() {
        String input = JOptionPane.showInputDialog(this, "Defina o valor da Cota Mínima:");
        if (input != null) {
            logica.setCotaMinima(Double.parseDouble(input));
            exibirMensagem("Cota mínima atualizada.");
        }
    }

    private void exibirMensagem(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    /* Garante que todos tenham o mesmo tamanho*/
    private JButton criarBotaoLargo(String texto) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(300, 30));
        return btn;
    }

/*  ... Inicialização Segura (main) ...

SwingUtilities.invokeLater: As interfaces gráficas em Java rodam em uma linha de processamento específica (Thread).

Esse comando garante que a janela seja criada de forma segura,
evitando travamentos ou falhas visuais na hora de abrir o programa.*/

    public static void main(String[] args) {

// Executa a interface na Thread de Eventos do Swing
        SwingUtilities.invokeLater(() -> {
            new ICaixaEletronico().setVisible(true);
        });
    }
}



