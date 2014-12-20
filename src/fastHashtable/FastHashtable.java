package fastHashtable;

import java.lang.System;


public class FastHashtable {
	
	private class Node {
		public int Key;
		public long Value;
		public Node Next;
		public Node(int p_key,long p_value) {
			this.Key = p_key;
			this.Value = p_value;
			this.Next = null;
		}
		public Node(Node p_predecessor, int p_key, long p_value) {
			this.Key = p_key;
			this.Value = p_value;
			this.Next = p_predecessor;
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

	private int _N=10;
	private float _lf=0.75f;
	private Bucket[] _buckets;
	private void _init() {
		this._buckets = new Bucket[this._N];
		for(int i=0;i<this._N;++i) {
			this._buckets[i] = new Bucket();
		}
	}
	
	private int _computeBucket(int p_key) {
		return p_key % this._N;
	}
	public FastHashtable() {
		this._init();
	}
	public FastHashtable(int p_n)
	{
		this._N = p_n;
		this._init();
	}
	
	public FastHashtable(int p_n,float p_load_factor)
	{
		this._N = p_n;
		this._lf = p_load_factor;
		this._init();
	}
	
	public void add(int p_key,long p_value) {
		int bucket = this._computeBucket(p_key);
		if(this._buckets[bucket].Count==0) {
			this._buckets[bucket].Head = new Node(p_key,p_value);
			this._buckets[bucket].Count++;
		} else {
			this._buckets[bucket].Head = new Node(this._buckets[bucket].Head,p_key,p_value);
			this._buckets[bucket].Count++;
		}
	}
	
	public boolean containsKey(int p_key) {
		int bucket = this._computeBucket(p_key);
		Node p = this._buckets[bucket].Head;
		while(p!=null) {
			if(p.Key==p_key) {
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
				return true;
			}
			p = n;
			n = p.Next;
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println("Hello world");

		java.util.Hashtable lh = new java.util.Hashtable<>(10,0.75f);
		
		FastHashtable ft = new FastHashtable(10);
		for(int i=0;i<20;++i) {
			ft.add(i, i*2);
		}
		for(int i=0;i<(20+1);++i) {
			System.out.println(ft.containsKey(i));
		}
		for(int i=0;i<(20+1);i+=2) {
			ft.delete(i);
		}
		for(int i=0;i<(20+1);++i) {
			System.out.println(ft.containsKey(i));
		}
		for(int i=0;i<(20+1);++i) {
			System.out.println(ft.get(i));
		}
	}

}
