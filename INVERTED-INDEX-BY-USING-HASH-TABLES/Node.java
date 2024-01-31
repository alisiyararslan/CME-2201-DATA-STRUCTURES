

public class Node<K, V> {
	private K key;
	private V value;
	private Node<K, V> next;
	private States state;
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public States getState() {
		return state;
	}
	private enum States {
		CURRENT, REMOVED
	}
	public boolean isRemoved() {
		return state == States.REMOVED;
	}

	public void setToRemoved() {
		setState(States.REMOVED);
		;

	}
	public boolean isIn() {
		return state == States.CURRENT;
	}

	public void setToIn() {
		setState(States.CURRENT);
		
	}
	
	public void setState(States state) {
		this.state = state;
	}
	
	public Node(K key, V value) {
		
		this.key = key;
		this.value = value;
		state = States.CURRENT;
		count=1;
		
	}
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	public Node<K, V> getNext() {
		return next;
	}
	public void setNext(Node<K, V> next) {
		this.next = next;
	}
	
	

}
