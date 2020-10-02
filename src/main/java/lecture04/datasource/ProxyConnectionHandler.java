package lecture04.datasource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

class ProxyConnectionHandler implements InvocationHandler {
	private Connection realConnection;
	private Connection warpedConnection;
	private ProxyConnDataSource dataSource;

	private int maxUseCount = 5;
	private int currentUserCount = 0;

	ProxyConnectionHandler(ProxyConnDataSource dataSource, Connection realConn) {
		this.dataSource = dataSource;
		this.realConnection = realConn;
	}

	Connection bind(Connection realConn) {
		this.warpedConnection = (Connection) Proxy.newProxyInstance(this
				.getClass().getClassLoader(), new Class[] { Connection.class },
				this);
		return warpedConnection;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if ("close".equals(method.getName())) {
			this.currentUserCount++;
			if (this.currentUserCount < this.maxUseCount)
				this.dataSource.connectionsPool.addLast(this.warpedConnection);
			else {
				this.realConnection.close();
				this.dataSource.currentCount--;
			}
		}
		return method.invoke(this.realConnection, args);
	}

}
