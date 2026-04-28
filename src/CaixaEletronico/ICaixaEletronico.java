package CaixaEletronico;

public interface ICaixaEletronico {
	/**
	* Pega o valor total disponivel no caixa eletronico
	* @retorna uma string formatada com o valor total disponivel
	*/
	public String pegaValorTotalDisponivel();
	/**
	* Efetua o saque
	* @param valor a ser sacado
	* @retorna uma string formatada informando o resultado da operacao
	*/
	public String sacar(Integer valor);
	/**
	* Pega um relatorio informando as celulas e a quantidade de celula disponivel
	* @retorna uma string formatada com as celula e suas quantidades
	*/
	public String pegaRelatorioCedulas();
	/**
	* Efetua a reposicao de cedulas
	* @param cedula de reposicao
	* @param quantidade de cedulas para reposicao
	* @retorna uma string formatada informando o resultado da operacao
	*/
	public String reposicaoCedulas(Integer cedula, Integer quantidade);
	/**
	* Efetua a leitura da cota minima de atendimento
	* @param minimo
	* @retorna uma string formatada informando o resultado da operacao
	*/
	public String armazenaCotaMinima(Integer minimo);

}
