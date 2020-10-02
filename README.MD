# JDBC Notes


## 1. JDBC
- Java Data Base Connectivity, java database API
- J2SE Components
- Layer: App -> JDBC -> Database Driver -> Database

***


## 2. JDBC Implementation 
#### Step1:	Load driver class into .JVM
- Methodl: Recommend, driver register via java reflection.

```
Class.forName ("com.mysql.jdbc.Driver")
```
- Method2: Driver register via DriverManager.

```
DriverManager.registerDriver(com.mysql.jdbc.Driver)
```

- Method3: Not Recommend, set system properties.

```
System.setProperty("jdbc.drivers", "driver1:driver")
```

#### Step2:	Create connection

```
Connection conn = DriverManager.getConnection(url, user, password);
URL: JDBC:subProtocal://host:port/database?key=Value&
```
#### Step3: Create statement
```
Statement st = conn.createStatement();
st.executeQuery(sql);

String sql = "select * from table_name where col_name=?";
PreparedStatement ps = conn.preparedStatement(sql);
ps.setString(1, "col_value");
ps.executeQuery();

```

#### Step4: Handle Result
```
ResultSet rs = statement.executeQuery(sql);
While(rs.next()){
	rs.getString("col_name");
}
```

#### Step5: Close connection
```
ResultSet.close();
Statement.close();
Connection.close();
```
***		
		
## 3. SQL Injection and PrepareStatement
#### SQL Injection
- select * from user where user_id = <user_id> or 1
- All records are in the result
#### PrepareStatement
- Prevent SQL Injection
- Prevent overflow of compile SQL
- SQL optimization
- Index start from 1
	
	
## 4. Data Type	
#### Data
```
//Write:
new java.sql.Date(ts.getTime())
ps.setDate(i,d); 
ps.setTime(i,t); 
ps.setTimestamp(i, ts);

//Read:
rs.getDate(i); 
rs.getTime(i); 
rs.getTimestamp(i);
```

#### CLOB
```
//Write
File file = new File(<File_Name>);
Reader reader = new BufferedReader(new FileReader(file));
PrepareStatement.setCharacterStream(1, reader, (int) file.length());
int i = PrepareStatement.executeUpdate();

//Read
Clob clob = rs.getClob(1);
Reader reader = clob.getCharacterStream();
char[] buff = new char[1024];
for (int i = 0; (i = reader.read(buff)) > 0;) {
	System.out.print(new String(buff, 0, i));
}
```

#### BLOB
```
//Write
File file = new File("Image.jpg");
InputStream in = new BufferedInputStream(new FileInputStream(file));
ps.setBinaryStream(1, in, (int) file.length());
int i = PrepareStatement.executeUpdate();

//Read
Blob blob = rs.getBlob(1);
InputStream in = blob.getBinaryStream();
OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
byte[] buff = new byte[1024];
for (int i = 0; (i = in.read(buff)) > 0;) {
	System.out.print(new String(buff, 0, i));
}
```
***		

		
## 5. Dao
- Design interface to decouple business layer and persisent layer
#### Dao Exception
- Complie exception: chocked exception, system must handle
- Runtime exception: unchecked exception, system may not handle
- Catch the compile exception, throw runtime exceptions
#### Dao Factory
- Create Dao Object base on Configuration for POLO CRUD
- Load the property file in class path by classLoader
- Init the Dao Object via reflection

```
private DaoFactory() {
	try {
		Properties prop = new Properties();
		InputStream inStream = DaoFactory.class.getClassLoader().getResourceAsStream("daoconfig.properties");
		prop.load(inStream);
		String userDaoClass = prop.getProperty(UserDao.class.getSimpleName());
		Class<?> clazz = Class.forName(userDaoClass);
		userDao = (UserDao) clazz.newInstance();
	} catch (Throwable e) {
		throw new ExceptionInInitializerError(e);
	}
}
```
***

		
		
## 6. Trasaction
#### Properties
- Atomicity: 		All operation commit or all operation rollback
- Consistency:	Database in conisist status after transaction
- Isolcation:		Transaction wilL not affect another trasaction
- Durability:		Transaction persist data

```
	connection.setAutoConunit (false);
	connection.commit();
	connection.rollback();
```
#### Save Point
- Purpose: Rollback the operation after save point
- Commmit the operation before save point
 
``` 
	Savepoint sp = connection.setSavepoint;
	connection.rollback(sp);
	connection.cocnmit ()
```
#### Java Transaction API
- Manage trasaction for distributed database
- Two phase coimit:
	+ commit request phase: coordinator process attenpts comtit transaction
	+ commit phase: If one transaction rollback, rollback other, else commit all transaction

***



## 7. CalldbleStatement
- Inherit from prepare statement
- Set the in parameter
- Register the out parameter

```
String sql = "{ call addUser(?,?,?,?) } ";
cs = conn.prepareCall(sql);
cs.setString(1, "ps name");
cs.setDate(2, new java.sql.Date(System.currentTimeMillis()));
cs.setFloat(3, 100f);
cs.registerOutParameter(4, Types.INTEGER);
```
***
	
	
	
## 8. PreparaStatment Batch
- Processing batch job, fast than multiple insertion/update

```
	ProparedStatement pa = connection .prepareStatement (sql)
	For
		ps.setData(ldx, value);
		ps.addbatch();
	End
	ps.executeBatch();
```	
***	
	
	
## 9 Other API
#### Get Generated Key
```
ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
ps.executeUpdate();
rs = ps.getGeneratedKeys();
```
#### Rollable ResultSet

```
Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 	ResultSet.CONCUR_UPDATABLE);
ResultSet rs = st.executeQuery(sql);
rs.beforeFirst(); rs.afterLast();rs.first();rs.isFirst();rs.last();rs.isLast();
rs.absolute(9);rs.moveToInsertRow();
```
#### Updatable ResultSet	

```
conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
rs.updateString("col name", "new value");
rs.updateRow();
```
#### Pagation

```
select * from user limit 5, 10
```
***



## 10. Metadata
- Database

```
DatabaseMetaData dbmd = conn.getMetaData();
getDatabaseProductName());
supportsTransactions());
```
- Parameter

```
ParameterMetaData pmd = ps.getParameterMetaData();
pmd.getParameterClassName(i)
pmd.getParameterType(i)
pmd.getParameterTypeName(i)
```
- ResultSet

```
ResultSetMetaData rsmd = rs.getMetaData();
rsmd.getColumnName(i)
rsmd.getColumnLabel(i)
```
***	


## 11. DataSource
#### Connection Pool
- Build connection at beqining
- Get connection from connection pool
- Mark connection free and put it back to connection pool
- initConnectior(): init default number of connection
- getConnection()
	+ get the connection base on required
	+ if free connection, get free connection
	+ if no connection and less than LIMIT, create new onnection
	+ if excess LIMIT, no connection return
- freeconnection(): free Connection from dataSource
#### Proxy Connection
- Connection:	close real connection
- ProxyConn:	free the connection and put it back to connection pool
#### DataSource
- Get Connection from DataSource instead of DriverManager
- Get Connection fast
- Connection: Proxy Connection
- Connection Pool: visit DataSource instead of accessing connection pool
 
```
Class.forName("com.mysql.jdbc.Driver");
Properties prop = new Properties();
InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
prop.load(is);
dataSource = BasicDataSourceFactory.createDataSource(prop);
```
***
	
## 12. Generic Dao Implementation
#### Extend
- AbstractDao implemnt find() with abstract method rowMapper
- ConcreteDao extend AbstractDao to call find() with implmented method rowMapper

```
AbstractDao{
	public Object find(String sql, Object[] args) {
		...
		try {
			...
			if (rs.next()) {
				obj = rowMapper(rs);
			}
			return obj;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
	abstract protected Object rowMapper(ResultSet rs) throws SQLException;
}

public class UserDaoImpl extends AbstractDao {
	public String findUserName(int id) {
		String sql = "select  name from user where id=?";
		Object[] args = new Object[] { id };
		Object user = super.find(sql, args);
		return ((User) user).getName();
	}
	protected Object rowMapper(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setMoney(rs.getFloat("money"));
		user.setBirthday(rs.getDate("birthday"));
		return user;
	}
}
```
***

#### Strategy + Composite
- AbstractDao implemnt find() with RowMapper interface mapRow method
- ConcreteDao with AbstractDao reference to call find() with class implmented RowMapper interface

```	
public class DaoTemplate {
	public Object find(String sql, Object[] args, RowMapper rowMapper) {
		...
		try {
			Object obj = null;
			if (rs.next()) {
				obj = rowMapper.mapRow(rs);
			}
			return obj;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}

public class UserDaoImplAdv {
	DaoTemplate template = new DaoTemplate();
	public User findUser(String loginName, String password) {
		String sql = "select id, name, money, birthday  from user where name=?";
		Object[] args = new Object[] { loginName };
		RowMapper mapper = new UserRowMapper();
		Object user = this.template.find(sql, args, new RowMapper(){
			public Object mapRow(ResultSet rs) throws SQLException {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setMoney(rs.getFloat("money"));
				user.setBirthday(rs.getDate("birthday"));
				return user;
			}
		});
		return (User) user;
	}
}

```
***	
