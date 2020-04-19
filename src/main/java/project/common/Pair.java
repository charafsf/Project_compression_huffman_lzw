package project.common;

public class Pair<K, V> {
    /** Key. */
    private final K key;
    /** Value. */
    private final V value;

    /**
     * Create an entry representing a mapping from the specified key to the
     * specified value.
     *
     * @param k Key (first element of the pair).
     * @param v Value (second element of the pair).
     */
    public Pair(K k, V v) {
        key = k;
        value = v;
    }

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}
    
	public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        } else {
            Pair<?, ?> oP = (Pair<?, ?>) o;
            return (key == null ?
                    oP.key == null :
                    key.equals(oP.key)) &&
                (value == null ?
                 oP.value == null :
                 value.equals(oP.value));
        }
    }
    
}
