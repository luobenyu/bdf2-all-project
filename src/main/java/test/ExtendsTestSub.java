package test;


public class ExtendsTestSub extends ExtendsTestSuper{
	
	protected String str = "sub";
	
	public ExtendsTestSub() {
		System.out.println("sub Conctructer");
	}
	
	@Override
	protected void customize() {
		System.out.println(this.str);
		System.out.println("sub customize...");
	}
	
	public static void main(String[] args) {
		ExtendsTestSuper sub = new ExtendsTestSub();
		sub.init();
		System.out.println(sub.str);
	}
}
