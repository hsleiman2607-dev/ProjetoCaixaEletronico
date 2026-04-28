package CaixaNovo;



/* usa uma lista duplamente vinculada para percorrer os itens na mesma ordem em que foram adicionados.
LinkedHashMap: Diferente de um HashMap comum, ele mantém a ordem em que os itens foram inseridos.*/
import java.util.LinkedHashMap;

/* é uma interface no Java que representa estrutura de dados de chave-valor (mapeamento),
onde cada chave única está vinculada a um valor específico*/
import java.util.Map;


// No topo, definimos o que o caixa "sabe" sobre si mesmo.
public class CaixaEletronico {

    // Map<Integer, Integer> estoqueNotas: Imagine como uma gaveta organizada.
    private Map<Integer, Integer> estoqueNotas;

    // Armazena a quantidade de cada nota: Nota -> Quantidade
    private double cotaMinima;

    /* cedulasDisponiveis: Um array fixo que define quais notas o banco aceita (de 100 a 2).

    Lógica Algorítmica (Sempre com as maiores notas possíveis) Isso é crucial aqui para que o sistema
    sempre tente usar as notas maiores primeiro.*/
    private final int[] cedulasDisponiveis = {100, 50, 20, 10, 5, 2};



// ... O CONSTRUTOR (public CaixaEletronico) ...


    // Ele Prepara o estoque
    public CaixaEletronico() {
        estoqueNotas = new LinkedHashMap<>();
        for (int nota : cedulasDisponiveis) {

// Percorre o array de cédulas e coloca "zero" em todas elas.
            estoqueNotas.put(nota, 0);
        }

/* Valor padrão inicial ele para de funcionar por segurança: Define uma cota mínima (R$ 100,00).

Se o caixa tiver menos que isso no total.
A "chave" é o valor da nota (ex: 2) e o "valor" é quantas notas existem (ex: 10).*/
        this.cotaMinima = 2.0;
    }

//   ... Métodos Administrativos (Manutenção) ...


    /* Reposição: São os métodos para quem faz a manutenção do caixa:
    de uma nota ao que já existe no estoque.

    reporNotas: Adiciona uma quantidade específica de uma nota ao que já existe no estoque*/
    public void reporNotas(int nota, int quantidade) {if (estoqueNotas.containsKey(nota)) {
        estoqueNotas.put(nota, estoqueNotas.get(nota) + quantidade);
    }
    }

    // Definir Cota Mínima: setCotaMinima: Altera o valor limite de segurança.*/
    public void setCotaMinima(double valor) {
        this.cotaMinima = valor;
    }

    /* ... Módulo do Administrador: Valor Total ...

    getValorTotalDisponivel: Soma tudo o que há no caixa (valor×quantidade) para saber o montante total.*/
    public double getValorTotalDisponivel() {
        double total = 0;
        for (Map.Entry<Integer, Integer> entry : estoqueNotas.entrySet()) {
            total += entry.getKey() * entry.getValue();
        }
        return total;
    }

// ... Módulo do Cliente: Efetuar Saque ...

// A Lógica de Saque (sacar) - O Coração do Código

/* Trava de Segurança: Verifica se o getValorTotalDisponivel() é menor que a cotaMinima. Se for, recusa o saque.

    Cálculo (O Algoritmo): Cria um mapa temporário notasParaEntregar e uma variável restante (que começa com o valor que o cliente pediu).

    A Matemática: Ele divide o restante pelo valor da nota atual (restante / nota) para saber quantas notas daquele valor seriam necessárias.

    Disponibilidade (Math.min): Ele pega o menor valor entre o que é necessário e o que está disponível no estoque.
    (Ex: Preciso de 3 notas de 100, mas só tenho 2. Ele vai separar 2).

    Atualização do Restante: Subtrai o valor das notas separadas do restante e vai para a próxima nota (ex: vai para a de 50).*/

    public String sacar(int valorSaque) {
        if (getValorTotalDisponivel() < cotaMinima) {
            return "Saldo insuficiente no caixa.";
        }

        Map<Integer, Integer> notasParaEntregar  = new LinkedHashMap<>();

        // Chamamos o método auxiliar que tentará as combinações
        if (tentarCombinacao(valorSaque, 0, notasParaEntregar)) {
            // Se retornar true, o 'notasParaEntregar' contém as notas certas
            // Aqui você aplicaria a lógica de subtrair do estoque real
            atualizarEstoque(notasParaEntregar);
            return formatarRecibo(notasParaEntregar);
        } else {
            return "Valor indisponível para as cédulas atuais.";
        }
    }

    private boolean tentarCombinacao(int restante, int indiceNota, Map<Integer, Integer> notasParaEntregar) {
        // Caso base: conseguimos zerar o valor do saque
        if (restante == 0) return true;

        // Se percorremos todas as notas e ainda sobra valor, essa combinação falhou
        if (indiceNota >= cedulasDisponiveis.length) return false;

        int nota = cedulasDisponiveis[indiceNota];
        int qtdDisponivel = estoqueNotas.get(nota);
        int qtdMaximaPossivel = Math.min(restante / nota, qtdDisponivel);

        // Tentamos usar o máximo da nota atual e vamos diminuindo se der erro
        for (int qtd = qtdMaximaPossivel; qtd >= 0; qtd--) {
            if (qtd > 0) {
                notasParaEntregar.put(nota, qtd);
            }

            // Chamada recursiva para a próxima nota com o que sobrou
            if (tentarCombinacao(restante - (qtd * nota), indiceNota + 1, notasParaEntregar)) {
                return true;
            }

            // Se chegou aqui, a tentativa com 'qtd' notas falhou, removemos do mapa e tentamos com 'qtd - 1'
            notasParaEntregar.remove(nota);
        }

        return false;
    }



    // chama o atualizarEstoque das notas no repositorio
    private void atualizarEstoque(Map<Integer, Integer> saque) {
        for (Map.Entry<Integer, Integer> entry : saque.entrySet()) {

            int nota = entry.getKey();
            int qtdSaca = entry.getValue();
            estoqueNotas.put(nota, estoqueNotas.get(nota) - qtdSaca);
        }
    }

    // para tirar as notas da gaveta e gera o recibo.
    private String formatarRecibo(Map<Integer, Integer> notas) {
        StringBuilder sb = new StringBuilder("Saque realizado:\n");
        for (Map.Entry<Integer, Integer> entry : notas.entrySet()) {
            sb.append(entry.getValue()).append
                    (" nota(s) de R$ ").append(entry.getKey()).append("\n");
        }
        return sb.toString();
    }

// Relatório:

    /* Se sobrar algo (ex: tentou sacar R$ 7 com notas de 10 e 5), ele avisa que não tem notas disponíveis.*/
    public String gerarRelatorioCedulas() {
        StringBuilder sb = new StringBuilder("Relatório de Estoque:\n");
        for (int nota : cedulasDisponiveis) {
            sb.append("R$ ").append(nota).append
                    (": ").append(estoqueNotas.get(nota)).append(" unidades\n");
        }
        return sb.toString();
    }
}



