
class A {
	static {
		System.out.println("A:����ľ�̬�������������ص�");
	}
	
	A() {
		System.out.println("A:����Ĺ�������new����ʵ��ʱ������");
	}
}

public class B extends A {
	static {
		System.out.println("B:��̬�����ֻ����һ��");
	}
	
	B() {
		System.out.println("B:���캯��");
	}
}

class C extends A {
	static {
		System.out.println("C:��̬������������ڸ��๹����");
	}
	
	C() {
		System.out.println("C:���캯��");
	}
}
