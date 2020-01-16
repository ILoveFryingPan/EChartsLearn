package testapp.android.com.echartslearn.reflect.proxy;


import testapp.android.com.echartslearn.reflect.proxy.dynamic.HelloInvocationHandler;
import testapp.android.com.echartslearn.reflect.proxy.staticProxy.Hello;
import testapp.android.com.echartslearn.reflect.proxy.staticProxy.IHello;
import testapp.android.com.echartslearn.reflect.proxy.staticProxy.StaticProxy;

public class Proxy {
	
	
	public static void main(String[] args) {
		Hello hello = new Hello();
		StaticProxy staticProxy = new StaticProxy();
		staticProxy.setIHello(hello);
		staticProxy.sayHello();

		HelloInvocationHandler handler = new HelloInvocationHandler(hello);
		IHello ihello = (IHello) java.lang.reflect.Proxy.newProxyInstance(IHello.class.getClassLoader(), new Class[]{IHello.class}, handler);
		ihello.sayHello();
	}
}
