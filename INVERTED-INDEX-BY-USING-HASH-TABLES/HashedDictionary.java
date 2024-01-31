import java.util.Iterator;

public class HashedDictionary<K, V> implements DictionaryInterface<K, V> {

	private int numberOfEntries;
	private static final int DEFAULT_CAPACITY = 5;
	private static final int MAX_CAPACITY = 10000;

	private Node<K, V>[] hashTable;
	private int tableSize;
	private static final int MAX_SIZE = 2 * MAX_CAPACITY;
	private boolean initialized = false;
	private static double MAX_LOAD_FACTOR = 0.5;
	private static int collisionCount = 0;

	private boolean isHashFunctionSSF;
	private boolean isCollisionHandlingLP;

	public HashedDictionary(boolean isLoadFactor50, boolean isHashFunctionSSF, boolean isCollisionHandlingLP) {
		this(DEFAULT_CAPACITY, isLoadFactor50, isHashFunctionSSF, isCollisionHandlingLP);
	}

	public HashedDictionary(int initialCapacity, boolean isLoadFactor50, boolean isHashFunctionSSF,
			boolean isCollisionHandlingLP) {
		checkCapacity(initialCapacity);// *******************
		numberOfEntries = 0;
		int tableSize = getNextPrime(initialCapacity);
		this.isHashFunctionSSF = isHashFunctionSSF;
		this.isCollisionHandlingLP = isCollisionHandlingLP;

		@SuppressWarnings("unchecked")
		Node<K, V>[] temp = (Node<K, V>[]) new Node[tableSize];
		hashTable = temp;
		initialized = true;
		if (!isLoadFactor50) {
			MAX_LOAD_FACTOR = 0.8;
		}
	}

	public void getValue(K key) {
		Node currentNode = locate(key);// 0 olmamlý
		System.out.println("> Search: " + key + "\r\n");
		if (currentNode != null) {
			Node temp = currentNode;
			int count = 0;
			while (temp != null) {
				count++;
				temp = temp.getNext();
			}
			System.out.println(count + " documents found");
			while (currentNode != null) {
				System.out.println(currentNode.getCount() + "-" + currentNode.getValue().toString());
				currentNode = currentNode.getNext();

			}

		} else {
			System.out.println("Not found!");

		}

	}

	@Override
	public V remove(K key) {
		checkInitialization();// *********************
		V removedValue = null;

		Node currentNode = null;
		for (int i = 0; i < hashTable.length; i++) {
			if (hashTable[i] != null && key.equals(hashTable[i].getKey())) {
				currentNode = hashTable[i];
				hashTable[i] = null;
			}
		}

		if (currentNode != null) {
			removedValue = (V) currentNode.getValue();
			currentNode.setToRemoved();
			numberOfEntries--;
			return removedValue;
		} else {
			return null;
		}

	}

	private Node locate(K key) {

		for (int i = 0; i < hashTable.length; i++) {
			if (hashTable[i] != null && key.equals(hashTable[i].getKey())) {
				return hashTable[i];
			}
		}

		return null;
	}

	private Node locate2(int index, V value) {

		boolean found = false;
		Node currentNode = null;
		Node temp = hashTable[index];
		while (!found && temp != null) {
			if (temp.isIn() && temp.getValue().equals(value)) {
				found = true;
				currentNode = temp;

			} else {
				temp = temp.getNext();
			}

		}

		return currentNode;

	}

	public int whichLetter(char c) {
		char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		for (int i = 0; i < alphabet.length; i++) {
			if (String.valueOf(c).equalsIgnoreCase(String.valueOf(alphabet[i]))) {
				return i + 1;
			}
		}
		return 0;
	}

	public int SSF(K s) {
		int sum = 0;
		for (int i = 0; i < s.toString().length(); i++) {
			sum += whichLetter(s.toString().charAt(i));
		}
		while (sum < 0) {
			sum += hashTable.length;
		}
		return sum % hashTable.length;
	}

	public int PAF(K s) {
		int sum = 0;
		int len = s.toString().length();
		for (int i = 0; i < s.toString().length(); i++) {
			sum += Math.pow(31, len - 1) * whichLetter(s.toString().charAt(i));
			len--;
		}
		while (sum < 0) {
			sum += hashTable.length;
		}
		return sum % hashTable.length;
	}

	public int linearProbing(int index, K key) {
		while (true) {

			if (hashTable[index] == null || hashTable[index].getKey().equals(key)) {
				break;
			} else {
				index++;
				index = index % hashTable.length;
			}

		}
		collisionCount++;
		return index;
	}

	public int doubleHashing(int k, K key) {// ? k=0 dq=7
		int q = 5;
		int dq = q - (k % q);

		int hk = k % hashTable.length;
		int b = hashTable.length;

		int index = hk;
		int count = 1;
		int a = 0;// **
		while (true) {
			if (hashTable[index] == null || hashTable[index].getKey().equals(key)) {
				break;
			} else {
				index = (int) (hk + dq * count);
				index = index % hashTable.length;
				count++;
				a++;
			}
			if (a == 50) {// **
				a = 5;
			}

		}
		collisionCount++;
		return index;
	}

	public int getNextPrime(int x) {

		boolean prime = false;
		boolean found = false;

		while (!found) {
			x++;
			prime = true;
			if (x == 2) {
				break;
			}
			for (int i = 2; i < x; i++) {
				if (x % i == 0) {
					prime = false;
					break;
				}

			}
			if (prime) {
				break;
			}

		}

		return x;
	}

	public void put(K key, V value) {// key="Ad" value =001.txt +++++++++++++++++++++++++++++++++++++++++++++++++++++

		checkInitialization();// ********************
		int index;
		if (isHashFunctionSSF) {
			index = SSF(key);// SSF yada PAF gelecek
		} else {
			index = PAF(key);// SSF yada PAF gelecek
		}

		while (!(hashTable[index] == null || hashTable[index].getKey().equals(key))) {

			if (isCollisionHandlingLP) {
				index = linearProbing(index, key);// linearProbing or doubleHashing
			} else {
				index = doubleHashing(index, key);// linearProbing or doubleHashing
			}

		}

		if (hashTable[index] == null) {

			hashTable[index] = new Node(key, "");
			Node newNode = new Node("", value);
			hashTable[index].setNext(newNode);

			numberOfEntries++;

		} else {

			Node currentNode = locate2(index, value);

			if (currentNode != null) {

				currentNode.setCount(currentNode.getCount() + 1);

			} else {
				Node newNode = new Node("", value);
				Node temp = hashTable[index];
				while (temp.getNext() != null) {
					temp = temp.getNext();

				}

				temp.setNext(newNode);

			}

		}
		if (isHashTableTooFull()) {
			resize();
		}

	}

	private void resize() {
		Node<K, V>[] oldTable = hashTable;
		int oldSize = hashTable.length;
		int newSize = getNextPrime(oldSize * 2);
		;

		@SuppressWarnings("unchecked")
		Node<K, V>[] temp = (Node<K, V>[]) new Node[newSize];
		hashTable = temp;
		numberOfEntries = 0;

		for (int index = 0; index < oldSize; index++) {
			if (oldTable[index] != null && oldTable[index].isIn()) {
				put(oldTable[index].getKey(), oldTable[index].getValue());
				Node tempNode = oldTable[index].getNext();
				Node newNode = locate(oldTable[index].getKey());//
				while (tempNode != null) {
					newNode.setNext(tempNode);
					tempNode = tempNode.getNext();
					newNode = newNode.getNext();
				}

			}
		}
	}

	private boolean isHashTableTooFull() {

		return (numberOfEntries / hashTable.length) >= MAX_LOAD_FACTOR;
	}

	private void checkCapacity(int capacity) {
		if (capacity > MAX_CAPACITY) {
			throw new IllegalStateException(
					"Attempt to create a bag whose " + "capacity exeeds allowed " + "maximum of " + MAX_CAPACITY);
		}
	}

	private void checkInitialization() {
		if (!initialized)
			throw new SecurityException("ArrayBag object is not initialized " + "properly.");
	}

	public void search(K key) {

		for (int i = 0; i < hashTable.length; i++) {
			if (hashTable[i] != null && hashTable[i].equals(key)) {
				break;
			}
		}
	}

	public static int getCollisionCount() {
		return collisionCount;
	}

}
