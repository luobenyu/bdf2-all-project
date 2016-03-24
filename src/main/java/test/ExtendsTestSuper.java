package test;

public class ExtendsTestSuper {

	protected String str = "super";

	public ExtendsTestSuper() {
		System.out.println("super Conctructer");
	}

	public void init() {
		System.out.println(this.str);
		System.out.println("super init...");
		customize();
	}

	protected void customize() {
		System.out.println("super customize...");
	}

}
