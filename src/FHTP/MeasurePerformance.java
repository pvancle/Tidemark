package FHTP;

import java.util.Date;
import java.util.Hashtable;

import fastHashtable.FastHashtable;

public class MeasurePerformance {

	public MeasurePerformance() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final int N = 1<<16;
		final int K = 8;
		long s = 0;
		Date start_time = null;
		Date stop_time  = null;
		double ft_total_time = 0;
		double ht_total_time = 0;
		//...
		//...initialize both FastHashtable & Hashtable
		//...
		FastHashtable ft = new FastHashtable(1024,5.0f);
		Hashtable<Integer,Long> ht = new Hashtable<Integer,Long>(1024,0.75f);
		
		/*
		 * for each type of hash table, put, get, remove many pairs, N, K times. Use wall clock
		 * time to measure performance.
		 */
		/*
		 * first run the FastHashtable.
		 */
		start_time = new Date();
		for(int j=0;j<K;++j) {
			for(int i=1;i<N;++i) {
				ft.put(i, i);
				s += ft.get(i);
			}
			assert ft.size()==(N-1); //test the size for this very large object.
			for(int i=1;i<N;++i) {
				ft.remove(i);
			}
			assert ft.size()==0;
		}
		stop_time = new Date();
		ft_total_time += (stop_time.getTime()-start_time.getTime());
	
		/*
		 * now run the java.util.Hashtable.
		 */
		start_time = new Date();
		for(int j=0;j<K;++j) {
			for(int i=1;i<N;++i) {
				Long v = new Long(i);
				ht.put(i, v);
				s += ht.get(i);
			}
			for(int i=1;i<N;++i) {
				ht.remove(i);
			}
		}
		stop_time = new Date();
		
		/*
		 * compare the elapsed times.
		 */
		ht_total_time += (stop_time.getTime()-start_time.getTime());
		System.out.println("runtime comparison: ft: "+ft_total_time+"ms, ht: "+ht_total_time+"ms, ft/ht: "+(ft_total_time/ht_total_time));


	}

}
