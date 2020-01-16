package testapp.android.com.echartslearn.reflect.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HelloInvocationHandler implements InvocationHandler {
	Object target;
	public HelloInvocationHandler(Object target)  {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("这里是动态的代理：开始");
		Object result = method.invoke(target, args);
		System.out.println("这里是动态的代理：结束");
		return result;
	}
}	
