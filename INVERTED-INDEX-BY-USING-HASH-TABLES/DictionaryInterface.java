import java.util.Iterator;

public interface DictionaryInterface<K,V> {
	
	public V remove(K key);
	
	
	
	
	public void getValue(K key);
	private Node locate(K key) {
		return null;
	}
	private Node locate3(int index, V value) {
		return null;
	}
	public int whichLetter(char c);
	public int SSF(K s);
	public int PAF(K s);
	public int linearProbing(int index, K key);
	public int doubleHashing(int k, K key);
	public int getNextPrime(int x);
	public void put(K key, V value);
	private void resize() {
	} 
	private boolean isHashTableTooFull() {
		return false;
	}
}
