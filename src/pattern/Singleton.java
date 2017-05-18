package pattern;

// 单例模式
public class Singleton {
	
	public static void main(String[] args) {
		Singleton a = Singleton.getInstance();
		Singleton b = Singleton.getInstance();
		System.out.println(a.getNum()+ " " + b.getNum());
		
		a.setNum(4);
		System.out.print(b.getNum());
	}
	
	private Singleton(){		
	}
	
	private static final Singleton Single = new Singleton();
	
	public static Singleton getInstance() {
		return Single;
		
	}
	
	private int num = 5;
	
	public int getNum(){
		return this.num;
	}
	
	public void setNum(int num){
		this.num = num;
	}
    
}
