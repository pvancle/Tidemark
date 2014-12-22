/*
 * This mini test harness has a framework for adding new unit tests. The main function automatically
 * runs each test that is loaded into the tests list.
 */

package FHTP;

import fastHashtable.FastHashtable;

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
	 * Test where the key does not exist in the container.
	 */
	public class TestForMissingKey extends TestAbstract {
		public boolean run() {
			FastHashtable ft = new FastHashtable();
			boolean r;
			/*
			 * the container is empty,
			 */
			r = !ft.containsKey(0);
			assert r;
			/*
			 * the container has entries but for key equals 1.
			 */
			ft.put(0, 1);
			r = !ft.containsKey(1);
			assert r;
			/*
			 * try to remove a key that does not exist.
			 */
			r = !ft.remove(1);
			assert r;
			return r;
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
		System.out.println("failure count: "+failure_count+" of "+tests.size()+" unit tests");

	}

}
