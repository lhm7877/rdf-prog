import java.util.*;


class graph3{
	Stack st = new Stack();
}

class stacktest {
	static void showpush(Stack st, int a) {
		st.push(new Integer(a));
		System.out.println("push(" + a + ")");
		System.out.println("stack: " + st);
	}

	static void showpop(Stack st) {
		System.out.print("pop -> ");
		Integer a = (Integer) st.pop();
		System.out.println(a);
		System.out.println("stack: " + st);
	}

	public static void main(String args[]) {
		graph3 g1 = new graph3();
		graph3 g2 = new graph3();
		//Stack st = new Stack();
//		System.out.println("stack: " + st);
		
		stacktest st1 = new stacktest();
		stacktest st2 = new stacktest();
		
		
		st1.showpush(g1.st, 42);
		st1.showpush(g1.st, 66);
		st1.showpop(g1.st);
//		st1.showpop(graph3.st);

		st2.showpush(g2.st, 43);
		st2.showpush(g2.st, 67);
		st2.showpop(g2.st);
//		st2.showpop(graph3.st);
		
		try {
			showpop(g1.st);
		} catch (EmptyStackException e) {
			System.out.println("empty stack");
		}
	}
}
