public class Lugar {

	private final int numero;
	private int escalao;
	private final int escalaoOriginal;
	private Funcionario dono;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param numero
	 * 
	 * 
	 * @param escalao
	 * 
	 * 
	 */

	public Lugar(int numero, int escalao) {
		this.numero = numero;
		this.escalao = escalao;
		this.escalaoOriginal = escalao;
		this.dono = null;
	}

	public int obterEscalao() {
		return this.escalao;
	}
	
	public int obterEscalaoOriginal() {
		return this.escalaoOriginal;
	}

	public int obterNumero() {
		return this.numero;
	}
	
	public void setEscalao(int a) {
		this.escalao = a;
	}

	public String obterDono() {
		return this.dono == null ? "Lugar não atribuido" : this.dono.obterNome();
	}

	public void setDono(Funcionario f, int funcionarioEscalao, int estrategia) {
        this.dono = f;
        if (estrategia == 2 && funcionarioEscalao != escalaoOriginal) {
            this.escalao = funcionarioEscalao;
        }
    }

	public void removerDono() {
		this.dono = null;
		if(this.escalao != this.escalaoOriginal) {
			this.escalao = this.escalaoOriginal;
		}
	}

	public boolean ocupado() {
		return this.dono != null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Lugar)) {
			return false;
		}
		Lugar lugar = (Lugar) obj;
		if (this.dono == null && lugar.dono == null)
			return true;
		if (this.dono == null || lugar.dono == null)
			return false;
		return this.numero == lugar.numero && this.escalao == lugar.escalao && this.dono.equals(lugar.dono);

	}

	@Override
	public int hashCode() {
		return Integer.hashCode(numero);
	}
}