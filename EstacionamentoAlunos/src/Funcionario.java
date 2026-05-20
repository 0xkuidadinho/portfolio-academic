public class Funcionario {

	private String nome;
	private int escalao;
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param nome
	 * 
	 * 
	 * @param escalao
	 * 
	 * 
	 */

	public Funcionario(String nome, int escalao) {
		this.nome = nome;
		this.escalao = escalao;
	}

	public String obterNome() {
		return this.nome;
	}

	public int obterEscalao() {
		return this.escalao;
	}
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Funcionario)) {
			return false;
		}

		Funcionario f = (Funcionario) obj;
		return this.nome.equals(f.nome) && this.escalao == f.escalao;
	}
    
	@Override
	public int hashCode() {
		return this.nome.hashCode();
	}
}
