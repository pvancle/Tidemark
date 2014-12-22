package fastHashtable;

/*
 * A hashtable for int keys and long values. This class has enough of the methods of Java's Hashtable
 * to allow a performance comparison.
 */
public class FastHashtable {
	
	/*
	 * The nodes of the singly linked list in
	 * each bucket.
	 */
	private class Node {
		public int Key;
		public long Value;
		public Node Next;
		public Node(int p_key, long p_value) {
			this.Key = p_key;
			this.Value = p_value;
			this.Next = null;
		}
	}

	/*
	 * Each bucket has the head node.
	 */
	private class Bucket {
		public Node Head;
		public Bucket() {
			this.Head = null;
		}
	}
	
	/*
	 * The array of buckets.
	 */
	private Bucket[] _buckets;

	/*
	 * The size of the bucket array. The size will grow
	 * if there are "too many" collisons.
	 */
	private int _N = 16;
	/*
	 * The number of key/value pairs in the hash table.
	 */
	private int _size = 0;
	
	/*
	 * Rehash with a larger value of this._N if
	 * the ratio of entries to buckets exceeds _load_factor.
	 */
	private float _load_factor = 5.0f;


	private void _init() {
		this._size = 0;
		this._buckets = new Bucket[this._N];
		for(int i=0;i<this._N;++i) {
			this._buckets[i] = new Bucket();
		}
	}

	private void _rehash() {
		int old_N = this._N;
		Bucket[] old_buckets = this._buckets.clone();
		this._N = 2 * this._N;
		this._init();
		for (int i = 0; i < old_N; ++i) {
			for (Node n = old_buckets[i].Head; n != null; n = n.Next) {
				this.put(n.Key, n.Value);
			}
		}
	}

	public FastHashtable() {
		this._init();
	}

	public FastHashtable(int p_n) {
		this._N = p_n;
		this._init();
	}

	public FastHashtable(int p_n, float p_lf_upper) {
		this._N = p_n;
		this._load_factor = p_lf_upper;
		this._init();
	}

	public int size() {
		return this._size;
	}

	public void put(int p_key, long p_value) {
		int bucket = p_key % this._N;
		if (this._buckets[bucket].Head == null) {
			this._buckets[bucket].Head = new Node(p_key, p_value);
			this._size++;
		} else {
			Node n = this._buckets[bucket].Head;
			Node tail = null;
			while (n != null) {
				if (n.Key == p_key) {
					n.Value = p_value;
					return;
				}
				tail = n;
				n = tail.Next;
			}
			tail.Next = new Node(p_key, p_value);
			this._size++;
			if ((this._size / (float)this._N) > _load_factor) {
				this._rehash();
			}

		}
	}

	public boolean containsKey(int p_key) {
		int bucket = p_key % this._N;

		Node p = this._buckets[bucket].Head;
		while (p != null) {
			if (p.Key == p_key) {
				return true;
			}
			p = p.Next;
			
		}
		return false;
	}
	
	public Long get(int p_key) {
		int bucket = p_key % this._N;
		Node n = this._buckets[bucket].Head;
		while(n!=null) {
			if(n.Key==p_key) {
				return new Long(n.Value);
			}
			n = n.Next;
		}
		return null;
	}
	
	public boolean remove(int p_key) {
		int bucket = p_key % this._N;
		Node p = null;
		Node n = this._buckets[bucket].Head;
		while(n!=null) {
			if(n.Key==p_key) {
				if(n!=this._buckets[bucket].Head) {
					p.Next = n.Next;
				} else {
					this._buckets[bucket].Head = this._buckets[bucket].Head.Next;
				}
				this._size--;
				return true;
			}
			p = n;
			n = p.Next;
		}
		return false;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		for (int i = 0; i < this._N; ++i) {

			sb.append(i);
			sb.append(':');
			for (Node n = this._buckets[i].Head; n != null; n = n.Next) {
				sb.append('(');
				sb.append(n.Key);
				sb.append(',');
				sb.append(n.Value);
				sb.append("),");
			}
		}
		sb.append(')');
		return sb.toString();
	}


}
