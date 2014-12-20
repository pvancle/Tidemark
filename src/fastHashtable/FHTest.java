package fastHashtable;

import java.util.ArrayList;
public class FHTest {
	
	private abstract class TestInterface {
		public abstract boolean run();
	}
	
	public class Test1 extends TestInterface {
		public boolean run() {
			FastHashtable ft = new FastHashtable();
			ft.add(0, 1);
			return ft.containsKey(0);
		}
	}
	public class TestForMissingKey extends TestInterface {
		public boolean run() {
			FastHashtable ft = new FastHashtable();
			ft.add(0, 1);
			boolean r = !ft.containsKey(1);
			assert r;
			return r;
		}
	}
	public FHTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		int failure_count = 0;
		ArrayList<TestInterface> tests = new ArrayList<TestInterface>();
		FHTest test = new FHTest();
		tests.add(test.new Test1());
		tests.add(test.new TestForMissingKey());
		TestInterface ti = null;
		for(int i=0;i<tests.size();++i) {
			try {
				ti = tests.get(i);
				boolean r = ti.run();
				assert r;
			}
			catch(AssertionError e) {
				++failure_count;
				System.out.println("failure: "+ti.getClass());
				
			}
		}
		System.out.println("failure count: "+failure_count);

	}

}
