/*
 * This mini test harness has a framework for adding new unit tests. The main function automatically
 * runs each test that is loaded into the tests list.
 */

package fastHashtable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class FHTest {

	/*
	 * Base class for unit tests.
	 */
	private abstract class TestAbstract {
		public abstract boolean run();
	}
	
	/*
	 * Test put & containsKey
	 */
	public class TestPut extends TestAbstract {
		public boolean run() {
			FastHashtable ft = new FastHashtable();
			ft.put(0, 1);
			return ft.containsKey(0);
		}
	}
	
	/*
	 * Test for collision. Collisions occur given
	 * the initial capacity and the number of key/value pairs.
	 */
	public class TestCollision extends TestAbstract {
		public boolean run() {
			//...
			//...initial number of buckets is 2. 4 pairs
			//...are entered, so, there will be collisions.
			//...
			FastHashtable ft = new FastHashtable(2,10);
			for(int i=0;i<4;++i) {
				ft.put(i, i);
			}
			for(int i=0;i<4;++i) {
				long j = ft.get(i);
				assert j==(long)i;
			}
			
			return true;
		}
	}
	
	/*
	 * Tests that a rehash is done when the
	 * ratio of entries to buckets exceeds the
	 * threshold.
	 * This test causes 2 rehash's.
	 */
	public class TestRehash extends TestAbstract {
		public boolean run() {
			/*
			 * initial number of buckets is 10 and
			 * load factor is five. there will be
			 * a rehash when there are 51 entries, and,
			 * 101 entries.
			 */
			final int N=10;
			final int M=11;
			FastHashtable ft = new FastHashtable(N,5f);
			for(int i=0;i<M*N;++i) {
				ft.put(i, i);
			}
			/*
			 * verify that all entries can be retrieved.
			 */
			for(int i=0;i<M*N;++i) {
				long j = ft.get(i);
				assert j==(long)i;
			}
			
			return true;
		}
	}
	
	/*
	 * 
	 */
	public class TestForMissingKey extends TestAbstract {
		public boolean run() {
			FastHashtable ft = new FastHashtable();
			ft.put(0, 1);
			boolean r0 = !ft.containsKey(1);
			boolean r1 = ft.containsKey(0);
			return r0&r1;
		}
	}
	
	/*
	 * Compares the performance time of FastHashtable to java.util.Hashtable.
	 */
	public class TestRelativeComputationTime extends TestAbstract {
		public boolean run() {
			
			final int N = 1048576;
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
			ht_total_time += (stop_time.getTime()-start_time.getTime());
			return ft_total_time < ht_total_time;
		}
		
	}
	


	/*
	 * Returns the list of unit tests to run. Add new tests here.
	 */
	public ArrayList<TestAbstract> getTestsList() {
		
		ArrayList<TestAbstract> tests = new ArrayList<TestAbstract>();
		
		tests.add(new TestRehash());
		tests.add(new TestPut());
		tests.add(new TestCollision());
		tests.add(new TestForMissingKey());
  		tests.add(new TestRelativeComputationTime());
		
		return tests;
		
	}
	
	public static void main(String[] args) {

		/*
		 * iterate over all of the unit tests and
		 * count the number of failures, then, report
		 * the result.
		 */
		int failure_count = 0;
		FHTest test = new FHTest();
		ArrayList<TestAbstract> tests = test.getTestsList();
		
		TestAbstract ti = null;

		for (int i = 0; i < tests.size(); ++i) {
			try {
				ti = tests.get(i);
				boolean r = ti.run();
				assert r;
			} catch (AssertionError e) {
				++failure_count;
				System.out.println("failure: " + ti.getClass());

			}
		}
		System.out.println("failure count: "+failure_count);

	}

}
