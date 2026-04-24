package Demontraçao;

// usa uma lista duplamente vinculada para percorrer os itens 
// na mesma ordem em que foram adicionados
// LinkedHashMap: Diferente de um HashMap comum, 
// ele mantém a ordem em que os itens foram inseridos.
import java.util.LinkedHashMap;

// é uma interface no Java que representa 
// uma estrutura de dados de chave-valor (mapeamento),
// onde cada chave única está vinculada a um valor específico
import java.util.Map;


// No topo, definimos o que o caixa "sabe" sobre si mesmo.
public class ICaixaEletronico {
    
    
// Map<Integer, Integer> estoqueNotas: Imagine como uma gaveta organizada.
    private Map<Integer, Integer> estoqueNotas;
    
// Armazena a quantidade de cada nota: Nota -> Quantidade    
    private double cotaMinima;

// cedulasDisponiveis: Um array fixo que define quais notas o banco aceita (de 100 a 2).
// Lógica Algorítmica (Sempre com as maiores notas possíveis)
// Isso é crucial aqui para que o sistema sempre tente usar as notas maiores primeiro.
    private final int[] cedulasDisponiveis = {100, 50, 20, 10, 5, 2};
    
 
// O Construtor (public CaixaEletronico):
    
// Ele Prepara o estoque
    public ICaixaEletronico() {
        estoqueNotas = new LinkedHashMap<>();
        for (int nota : cedulasDisponiveis) {
        	
// Percorre o array de cédulas e coloca "zero" em todas elas.
            estoqueNotas.put(nota, 0);
        }

// Valor padrão inicial ele para de funcionar por segurança:
        
// Define uma cota mínima (R$ 100,00). Se o caixa tiver menos que isso no total,      
// A "chave" é o valor da nota (ex: 100) e o "valor" é quantas notas existem (ex: 10).
        this.cotaMinima = 100.0;  
    }

// Reposição:
    
// São os métodos para quem faz a manutenção do caixa:
// de uma nota ao que já existe no estoque
    
// reporNotas: Adiciona uma quantidade específica
// de uma nota ao que já existe no estoque
    public void reporNotas(int nota, int quantidade) {
        if (estoqueNotas.containsKey(nota)) {
            estoqueNotas.put(nota, estoqueNotas.get(nota) + quantidade);     
        }
    }

// Definir Cota Mínima:
    
// setCotaMinima: Altera o valor limite de segurança.
    public void setCotaMinima(double valor) {
        this.cotaMinima = valor;
    }

// Módulo do Administrador: Valor Total:
    
// getValorTotalDisponivel: Soma tudo o que há no caixa 
// (valor×quantidade) para saber o montante total.
    
    public double getValorTotalDisponivel() {
        double total = 0;
        for (Map.Entry<Integer, Integer> entry : estoqueNotas.entrySet()) {
            total += entry.getKey() * entry.getValue();
        }
        return total;
    }

// Módulo do Cliente:
    
// Efetuar Saque
    public String efetuarSaque(int valorSaque) {
// 1. Verificação de Cota Mínima
        if (getValorTotalDisponivel() < cotaMinima) {
            return "Caixa Vazio: Chame o Operador";
        }

// O Cálculo das Notas:

// Ele cria um mapa temporário (notasParaEntregar).
// Ele percorre as notas disponíveis (da maior para a menor).
        Map<Integer, Integer> notasParaEntregar = new LinkedHashMap<>();
        int restante = valorSaque;
        
        for (int nota : cedulasDisponiveis) {
            int qtdDisponivel = estoqueNotas.get(nota);
// A conta mágica: restante / nota. Se você quer R$ 130 e a nota é 100, o resultado é 1. 
// Ele verifica se tem essa 1 nota no estoque.
            
            int qtdNecessaria = restante / nota;
            
         // Se o saque for possível, subtraímos do estoque
            int qtdASerEntregue = Math.min(qtdNecessaria, qtdDisponivel);
            
// Ele subtrai o valor do "restante" e passa para a próxima nota (50, 20...).
            if (qtdASerEntregue > 0) {
                notasParaEntregar.put(nota, qtdASerEntregue);
                restante -= (qtdASerEntregue * nota);
            }
        }

// Validação final do saque:
        
// Se ao final o restante for 0, o saque deu certo! 
        if (restante == 0) {

// Ele chama o atualizarEstoque
            atualizarEstoque(notasParaEntregar);
            return formatarRecibo(notasParaEntregar);
        } else {
            return "Não Temos Notas Para Este Saque";
        }
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
    
// Se sobrar algo (ex: tentou sacar R$ 7 com notas de 10 e 5), 
// ele avisa que não tem notas disponíveis.
    public String gerarRelatorioCedulas() {
        StringBuilder sb = new StringBuilder("Relatório de Estoque:\n");
        for (int nota : cedulasDisponiveis) {
            sb.append("R$ ").append(nota).append
            (": ").append(estoqueNotas.get(nota)).append(" unidades\n");
        }
        return sb.toString();
    }
}



