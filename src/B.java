
class A {
	static {
		System.out.println("A:父类的静态代码块是最早加载的");
	}
	
	A() {
		System.out.println("A:父类的构造器在new子类实例时被调用");
	}
}

public class B extends A {
	static {
		System.out.println("B:静态代码块只加载一次");
	}
	
	B() {
		System.out.println("B:构造函数");
	}
}

class C extends A {
	static {
		System.out.println("C:静态代码块甚至早于父类构造器");
	}
	
	C() {
		System.out.println("C:构造函数");
	}
}
