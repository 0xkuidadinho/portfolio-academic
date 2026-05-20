import java.util.HashMap;

import java.util.LinkedList;

import java.util.Queue;

public class Gestor {

	private int nrLugares;
	private int nrEscaloes;
	private int estrategia;
	private int numeroLugaresTotal;
	private BidirectionalHashMap<Lugar, Funcionario> atribuicoes;
	private HashMap<String, Funcionario> funcionarios;
	private HashMap<Integer, Lugar> lugaresPorEscalao;

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param nrEscaloes
	 * 
	 * 
	 * @param nrLugares
	 * 
	 * 
	 * @param estrategia
	 * 
	 * 
	 */

	public Gestor(int nrEscaloes, int nrLugares, int estrategia) {
		this.nrEscaloes = nrEscaloes;
		this.nrLugares = nrLugares;
		this.estrategia = estrategia;
		this.atribuicoes = new BidirectionalHashMap<>();
		this.funcionarios = new HashMap<>();
		this.lugaresPorEscalao = new HashMap<>();
		this.numeroLugaresTotal = this.nrLugares / this.nrEscaloes;
		int lugarAtual = 1;
		for (int escalao = nrEscaloes; escalao >= 1; escalao--) {
			for (int i = 0; i < numeroLugaresTotal; i++) {
				Lugar fila = new Lugar(lugarAtual, escalao);
				this.lugaresPorEscalao.put(lugarAtual, fila);
				lugarAtual++;
			}
		}
	}

	public int totalAtribuidos() {
		return this.atribuicoes.size();
	}

	public int atribuidosNoEscalao(int escalao) {
		int count = 0;
		for (Lugar l : this.lugaresPorEscalao.values()) {
			if (l.obterEscalao() == escalao && this.atribuicoes.containsKey(l)) {
				count++;
			}
		}
		return count;
	}

	public boolean registar(String nome, int escalao) {
		if (this.funcionarios.containsKey(nome)) {
			return false;
		} else {
			Funcionario f = new Funcionario(nome, escalao);
			this.funcionarios.put(nome, f);
			return true;
		}
	}

	public boolean atribuir(String nome) {
	    Funcionario f = this.funcionarios.get(nome);

	    if (!this.funcionarios.containsKey(nome) || this.atribuicoes.containsValue(f)) {
	        return false;
	    }

	    int escalao = f.obterEscalao();

	    if (this.estrategia == 1) {
	        return tryAssignToEscalao(f, escalao);
	    }

	    if (this.estrategia == 2) {
	        if (escalao == 1) {
	            return tryAssignToEscalao(f, 1);
	        }

	        if (tryAssignToEscalao(f, escalao)) {
	            return true;
	        }

	        if (tryAssignToEscalao(f, 1)) {
	            return true;
	        }

	        for (int i = 2; i <= nrEscaloes; i++) {
	            if (i != escalao && tryAssignToEscalao(f, i)) {
	            	
	                return true;
	            }
	        }
	    }
	    return false;
	}


	private boolean tryAssignToEscalao(Funcionario f, int escalao) {
		for (Lugar l : lugaresPorEscalao.values()) {
			if (l.obterEscalao() == escalao && !l.ocupado()) {
				l.setDono(f, f.obterEscalao(), this.estrategia);
				this.atribuicoes.put(l, f);
				return true;
			}
		}
		return false;
	}

	public Funcionario obterDono(int numero) {
		Lugar l = this.lugaresPorEscalao.get(numero);
		if (l == null || !atribuicoes.containsKey(l))
			return null;
		return this.atribuicoes.getValue(l);
	}

	public Lugar removerAtribuicaoPorNome(String nome) {
		if (this.atribuicoes.containsValue(this.funcionarios.get(nome))) {
			Lugar l = this.atribuicoes.removeByValue(this.funcionarios.get(nome));
			this.atribuicoes.removeByKey(l);
			l.removerDono();
			return l;
		}
		return null;
	}

	public Lugar removerAtribuicaoPorNumero(int numero) {
		Lugar l = this.lugaresPorEscalao.get(numero);
		if (l == null || !this.atribuicoes.containsKey(l)) {
			return null;
		}
		this.atribuicoes.removeByKey(l);
		l.removerDono();
		return l;
	}

	public int obterNumero(String nome) {
		Lugar lugar = this.atribuicoes.getKey(this.funcionarios.get(nome));
		if (lugar == null)
			return -1;
		return lugar.obterNumero();
	}

}
