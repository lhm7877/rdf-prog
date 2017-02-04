//https://en.wikipedia.org/wiki/Inheritance_(object-oriented_programming)

class A{
	public
		 void DoSomethingALike() {System.out.print("A\n");}
};

class B extends A 
{ public
   void DoSomethingBLike() {System.out.print("B\n");}
};




public class testInheritance2 {
	void UseAnA(A  some_A)
	{
	   some_A.DoSomethingALike();
	}
	void SomeFunc()
	{
	   B b=new B();
	   UseAnA((A)b); // b can be substituted for an A.
	}
	public testInheritance2() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testInheritance2 tInheri = new testInheritance2();
		tInheri.SomeFunc();
	}

}
