import java.util.HashMap; //Dica

/**
 * 
 * 
 * Tabela bidirecional.
 * 
 * 
 */

public class BidirectionalHashMap<K, V> {

	private HashMap<K, V> forward;
	private HashMap<V, K> reverse;

	/**
	 * 
	 * 
	 * Construtor
	 * 
	 * 
	 */

	public BidirectionalHashMap() {
		this.forward = new HashMap<>();
		this.reverse = new HashMap<>();
	}

	/**
	 * 
	 * 
	 * Dado um valor, obter a chave correspondente
	 * 
	 * 
	 * @param value - o valor
	 * 
	 * 
	 * @return K - a chave
	 * 
	 * 
	 */

	public K getKey(V value) {
		K key = this.reverse.get(value);
		if (key != null) {
			return this.reverse.get(value);
		}
		return null;

	}

	/**
	 * 
	 * 
	 * Dada uma chave, obter o valor correspondente
	 * 
	 * 
	 * @param key - a chave
	 * 
	 * 
	 * @return V - o valor
	 * 
	 * 
	 */

	public V getValue(K key) {
		V value = this.forward.get(key);
		if (value != null) {
			return this.forward.get(key);
		}
		return null;
	}

	/**
	 * 
	 * 
	 * Adicionar um par chave-valor
	 * 
	 * 
	 * @param key   - a chave
	 * 
	 * 
	 * @param value - o valor
	 * 
	 * 
	 */

	public void put(K key, V value) {
		if (this.forward.containsKey(key)) {
			this.reverse.remove(this.forward.get(key));
		}

		if (this.reverse.containsKey(value)) {
			this.forward.remove(this.reverse.get(value));
		}
		
		this.forward.put(key, value);
		this.reverse.put(value, key);

	}

	/**
	 * 
	 * 
	 * Verificar se a tabela contem uma dada chave
	 * 
	 * 
	 * @param key - a chave
	 * 
	 * 
	 * @return true se contem a chave, false caso contrario
	 * 
	 * 
	 */

	public boolean containsKey(K key) {
		return this.forward.containsKey(key);
	}

	/**
	 * 
	 * 
	 * Verificar se a tabela contem um dado valor
	 * 
	 * 
	 * @param value - o valor
	 * 
	 * 
	 * @return true se contem o valor, false caso contrario
	 * 
	 * 
	 */

	public boolean containsValue(V value) {
		return this.reverse.containsKey(value);
	}

	/**
	 * 
	 * 
	 * Remover um par chave-valor, dada a chave
	 * 
	 * 
	 * @param key - a chave
	 * 
	 * 
	 * @return V - o valor previamente associado a chave
	 * 
	 * 
	 */

	public V removeByKey(K key) {
		V value = this.forward.remove(key);
		if (value != null) {
			this.reverse.remove(value);
		}
		return value;
	}

	/**
	 * 
	 * 
	 * Remover um par chave-valor, dado o valor
	 * 
	 * 
	 * @param value - o valor
	 * 
	 * 
	 * @return K - a chave previamente associada ao valor
	 * 
	 * 
	 */

	public K removeByValue(V value) {
		K key = this.reverse.remove(value);
		if (key != null) {
			this.forward.remove(key);
		}
		return key;

	}

	/**
	 * 
	 * 
	 * Retornar o tamanho da tabela (numero de pares chave-valor contidos)
	 * 
	 * 
	 * @return o tamanho da tabela
	 * 
	 * 
	 */

	public int size() {
		return this.forward.size();
	}
}