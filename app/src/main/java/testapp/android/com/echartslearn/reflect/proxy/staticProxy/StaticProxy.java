package testapp.android.com.echartslearn.reflect.proxy.staticProxy;


public class StaticProxy implements IHello {

	private IHello hello;

	public void setIHello(IHello hello) {
		this.hello = hello;
	}

	@Override
	public void sayHello() {
		System.out.println("proxy the hello");
		hello.sayHello();
		System.out.println("proxy end");
	}
}
