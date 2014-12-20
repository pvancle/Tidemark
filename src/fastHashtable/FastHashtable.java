package fastHashtable;

import java.lang.System;


public class FastHashtable {
	
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
	
	private class Bucket {
		public Node Head;
		public int  Count;
		public Bucket() {
			this.Head = null;
			this.Count = 0;
		}
	}

	private int _N = 10;
	private float _lf_upper = 0.75f;
	private float _lf_lower = 0.25f;
	private Bucket[] _buckets;
	private int _size = 0;

	private void _init() {
		this._size = 0;
		this._buckets = new Bucket[this._N];
		for(int i=0;i<this._N;++i) {
			this._buckets[i] = new Bucket();
		}
	}

	private void _increase_buckets() {
		int old_N = this._N;
		Bucket[] old_buckets = this._buckets.clone();
		this._N = 2 * this._N;
		this._init();
		for (int i = 0; i < old_N; ++i) {
			for (Node n = old_buckets[i].Head; n != null; n = n.Next) {
				this.add(n.Key, n.Value);
			}
		}
	}

	private void _decrease_buckets() {
		int old_N = this._N;
		Bucket[] old_buckets = this._buckets.clone();
		this._N = this._N / 2;
		this._init();
		for (int i = 0; i < old_N; ++i) {
			for (Node n = old_buckets[i].Head; n != null; n = n.Next) {
				this.add(n.Key, n.Value);
			}
		}
	}

	private int _computeBucket(int p_key) {
		return p_key % this._N;
	}

	public FastHashtable() {
		this._init();
	}

	public FastHashtable(int p_n) {
		this._N = p_n;
		this._init();
	}

	public FastHashtable(int p_n, float p_lf_lower, float p_lf_upper) {
		this._N = p_n;
		this._lf_lower = p_lf_lower;
		this._lf_upper = p_lf_upper;
		this._init();
	}

	public int getSize() {
		return this._size;
	}

	public void add(int p_key, long p_value) {
		int bucket = this._computeBucket(p_key);
		if (this._buckets[bucket].Head == null) {
			this._buckets[bucket].Head = new Node(p_key, p_value);
			this._buckets[bucket].Count++;
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
			this._buckets[bucket].Count++;
			this._size++;
			if (this._size / this._N > _lf_upper) {
				this._increase_buckets();
			}

		}
	}

	public boolean containsKey(int p_key) {
		int bucket = this._computeBucket(p_key);

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
		int bucket = this._computeBucket(p_key);
		Node n = this._buckets[bucket].Head;
		while(n!=null) {
			if(n.Key==p_key) {
				return new Long(n.Value);
			}
			n = n.Next;
		}
		return null;
	}
	
	public boolean delete(int p_key) {
		int bucket = this._computeBucket(p_key);
		Node p = null;
		Node n = this._buckets[bucket].Head;
		while(n!=null) {
			if(n.Key==p_key) {
				if(n!=this._buckets[bucket].Head) {
					p.Next = n.Next;
				} else {
					this._buckets[bucket].Head = this._buckets[bucket].Head.Next;
				}
				this._buckets[bucket].Count--;
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
			sb.append('/');
			sb.append(this._buckets[i].Count);
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

	public static void main(String[] args) {
		System.out.println("Hello world");

		java.util.Hashtable lh = new java.util.Hashtable<>(10, 0.75f);

		FastHashtable ft = new FastHashtable(10);
		for (int i = 0; i < 100; ++i) {
			for (int k = 0; k < 7; ++k) {
				ft.add(i, k);
			}
		}
		System.out.println(ft.toString());
		for (int i = 0; i < (20 + 1); ++i) {
			System.out.println(ft.containsKey(i));
		}
		for (int i = 0; i < (20 + 1); i += 2) {
			ft.delete(i);
		}
		for (int i = 0; i < (20 + 1); ++i) {
			System.out.println(ft.containsKey(i));
		}
		for (int i = 0; i < (20 + 1); ++i) {
			System.out.println(ft.containsKey(i));
			System.out.println(ft.get(i));
			ft.delete(i);
		}
		System.out.println(ft.toString());
		for (int i = 0; i < 20; ++i) {
			ft.add(i, i * 2);
		}
		System.out.println(ft.toString());
		int v = 10;
		for (int j = 0; j < 10; ++j) {
			for (int k = 0; k < 20; ++k) {
				ft.add(k, v);
			}
			v += 10;
		}
		System.out.println(ft.toString());
	}

}
