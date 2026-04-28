package CaixaVelho;

import java.util.Scanner;

public class CaixaEletronico {
    static void main(String[] args) {

        Conta[] contas = new Conta[100];
        Scanner scan = new Scanner(System.in);

        int contador = 0; // Responsável por buscar a conta para login
        boolean sair = false;

        do {
            menu();
            int opcao = scan.nextInt();
            scan.nextLine();
            printLinhas();

            switch (opcao) {
                case 1:
                    acessarConta(scan, contas, contador);

                    break;
                case 2:
                    contas[contador] = criarConta(scan, new Conta());
                    contador ++;
                    break;
                case 3:
                    System.out.println("Saindo...");
                    sair = true;
                    break;
            }
        } while (!sair);

    }

        /* Essa função vai pegar todos os dados do usuário, para criar uma
        * nova conta e vai os enviar para a Classe CaixaVelho.Conta como valores*/
        public static Conta criarConta(Scanner scan, Conta conta){
            System.out.println("Cadastro de CaixaVelho.Conta");
            printLinhas();

        // Recebendo os valores do usuário
            System.out.println("Digite seu nome completo:");
            String nome = scan.nextLine();

            System.out.println("Digite seu CPF:");
            String cpf = scan.nextLine();

            System.out.println("Crie uma senha:");
            String senha = scan.nextLine();

            System.out.println("Digite o valor inicial da conta:");
            double saldo = scan.nextDouble();

            scan.nextLine();

        // Enviando os valores recebidos para dentro do objeto CaixaVelho.Conta
            conta.setNome(nome);
            conta.setCpf(cpf);
            conta.setSenha(senha);
            conta.setSaldo(saldo);


            System.out.println("\nCaixaVelho.Conta cadastrada com Sucesso");
            printLinhas();

        // Retorna a conta criada para dentro do Vetor "contas"
            return conta;
        }

        public static void menu(){
            System.out.println("\nCaixa Eletrônico Java");
            printLinhas();
            System.out.println("1- Entrar na conta");
            System.out.println("2- Criar conta");
            System.out.println("3- Sair");
        }

        //Só uma prática de bom uso, vista no YouTube, deixar bonitinho
        public static void printLinhas(){
            System.out.println("---------------------------\n");
        }

        // Função para Login da conta
        public static void acessarConta(Scanner scan, Conta[] contas, int contador){
            while (true){
                System.out.println("\nAcessando sua conta");
                printLinhas();

                System.out.println("Digite seu CPF: ");
                String cpf = scan.nextLine();

                System.out.println("Digite sua senha: ");
                String senha = scan.nextLine();

                printLinhas();

            /* O responsável para ver se o CPF + Senha em questão
            * está dentro do Vetor "contas" */
                boolean acessoPermitido = false;

            // Rastreador da conta
                int indiceConta = -1;

            /* O laço aki vai percorrer o Vetor até o número de contas
                salvos e, se o CPF junta da Senha digitados bater com algum
                lá dentro, o login é permitido */
                for(int i = 0; i < contador; i++){
                    if (contas[i].getCpf().equals(cpf) &&
                        contas[i].getSenha().equals(senha)){

                        acessoPermitido = true;
                        indiceConta = i;
                break;
                    }
                }

            // Após a confirmação de Login
                if (acessoPermitido){
                    menuConta(scan, contas[indiceConta]);
                    printLinhas();
                } else {
                    System.out.println("\nAcesso Negado");
                    System.out.println("CPF ou Senha incorretos");
                    printLinhas();
                }


            }
        }

    /* Criando o Menu após o login da CaixaVelho.Conta, permitindo Sacar, Depositar,
    * Ver quanto de saldo tem na conta */
        public static void menuConta(Scanner scan, Conta conta){
            boolean sair = false;

            while (!sair){
                System.out.println("Menu da CaixaVelho.Conta:");
                printLinhas();

                System.out.println("1- Ver Saldo na CaixaVelho.Conta");
                System.out.println("2- Sacar Dinheiro");
                System.out.println("3- Depositar Dinheiro");
                System.out.println("4- Status da CaixaVelho.Conta");
                System.out.println("5- Sair");

                int opcao = scan.nextInt();
                scan.nextLine();
                printLinhas();

                // Estrutura do Menu.
                switch (opcao){
                    case 1:
                        System.out.println("Seu Saldo: " + conta.getSaldo());
                        printLinhas();
                        break;

                    case 2:
                        System.out.println("Insira o valor do saque: ");
                        double saque = scan.nextDouble();
                        scan.nextLine();
                        if (saque <= conta.getSaldo()) { // Garante que o saque só é realizado se tiver dinheiro na conta.
                            conta.setSaldo(conta.getSaldo() - saque);

                            System.out.println("Saque realizado com sucesso.");
                            System.out.println("Saldo atual: " + conta.getSaldo());
                            printLinhas();
                        } else {
                            System.out.println("Saldo insuficiente na conta.");
                            printLinhas();
                        }
                        break;

                    case 3:
                        System.out.println("Insira o valor do deposito: ");
                        double deposito = scan.nextDouble();
                        scan.nextLine();
                        conta.setSaldo(conta.getSaldo() + deposito);

                        System.out.println("Deposito realizado com sucesso.");
                        System.out.println("Saldo atual: " + conta.getSaldo());
                        printLinhas();
                        break;

                    case 4:
                        conta.status();
                        printLinhas();
                        break;

                    case 5:
                        sair = true;
                        break;

                    default:
                        System.out.println("Opção inválida, tente novamente.");
                }
            }
        }
}
