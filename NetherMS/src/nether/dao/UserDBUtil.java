package nether.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import nether.common.DateUtil;

/**
 * 数据库操作工具类
 * @author TGB
 *
 */
public class UserDBUtil {
	static DB db;
	private static Statement statement;
	private static final String dbname = "users";
	
	static {
		db = DB.getGlobalDB();	// 使用全局的数据库
		statement = db.getStatement();
		
		createTable();
		if(getMaxID() > 0)	{ // 设置自增初始值 
			User.idGen = new IdGenerater(getMaxID()+1);
			System.out.println(User.idGen.genID());
		}
		if(getMaxID()<=100000) {
			User YanWang = new User("YanWang", "123123123", "super", new Date());
			YanWang.setID();
			storeUser(YanWang);
		}
		
//		AlertBox.display(String.valueOf(YanWang.getId()), "欢迎你回来!阎王大人", "跪安吧!");
		

	}
	
	public UserDBUtil() {
	}
	
	/**
	 * 新建有关于 users 的表
	 */
	private static void createTable() {
		try {
			
			ResultSet rs = statement.executeQuery(
					"select count(*) from sqlite_master "
					+ "where type = 'table' and name = '"
					+ dbname +"'");
			if(rs.next() && rs.getBoolean(1)) {		// 已经存在表
				System.out.println("已经存在users，使用已存在的表");
				return;
			}
			
			statement.executeUpdate("CREATE TABLE "+dbname+"("
					+ "id 				INTEGER NOT NULL	PRIMARY KEY,"
					+ "username 		TEXT 	NOT NULL,"
					+ "password 		TEXT 	NOT NULL,"
					+ "scope 			TEXT 	NOT NULL,"
					+ "birthdate 		TEXT,"
					+ "deathcause		TEXT,"
					+ "reincarnation	TEXT"
					+ ")");
		} catch (Exception e) {
			System.out.println("While create users table: " + e.getMessage());
		}
	}
	
	/**
	 * 获取ID的最大值，用于自增
	 * @return 数据库中ID最大值
	 */
	private static int getMaxID() {
		try {
			if(null == db.getStatement())
				return -1;
			
			ResultSet rs = db.getStatement().executeQuery("select MAX(id) from " + dbname);
			if(rs.next()) {
				System.out.println(String.format("User initial ID max: %d", rs.getInt(1)));
				return rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("While get Max ID: " + e.getMessage());
			e.printStackTrace();
		}
		
		return -1;
	}
	
	/**
	 * 存储用户数据
	 * @param user
	 * @return 是否存储成功
	 */
	public static boolean storeUser(User user) {
		
		try {
			if(null == statement)
				return false;
			String params = String.format("%d, '%s', '%s', '%s', '%s', '%s', '%s'", 
					user.getId(),
					user.getUsername(),
					user.getPassword(),
					user.getScope().toHuffmanCode(),
					DateUtil.Date2String(user.getBirthdate()),
					user.getDeathCause(),
					user.getReincarnation());
			statement.executeUpdate(String.format("INSERT INTO %s VALUES (%s)", dbname, params));
			
			// TODO 将成功与否与上面的 executeUpdate 语句挂钩
			return true;

		} catch (SQLException e) {
			System.out.println("While store user data: " + e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 更新用户数据
	 * @param user
	 */
	public static void updateUser(User user) {
		try {
			if(null == statement)
				return;
			
			String params = String.format("id = %d, username = '%s', password = '%s', "
					+ "scope = '%s', birthdate = '%s', deathcause = '%s', reincarnation = '%s'", 
					user.getId(),
					user.getUsername(),
					user.getPassword(),
					user.getScope().toHuffmanCode(),
					DateUtil.Date2String(user.getBirthdate()),
					user.getDeathCause(),
					user.getReincarnation());
			statement.executeUpdate(String.format("UPDATE %s SET %s WHERE id = '%d'", dbname, params, user.getId()));

		} catch (SQLException e) {
			System.out.println("While update user: " + e.getMessage());
		}
	}
	
	/**
	 * 通过ID和未加密的密码获取User对象
	 * @param id
	 * @return User对象，如果密码错误或者其他错误则返回null
	 */
	public static User getUserByID(Integer id) {
		
		try {
			
			ResultSet rs = statement.executeQuery(String.format("SELECT * FROM %s WHERE id = '%d'", dbname, id.intValue()));
			if(rs.next()) {
				return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("scope"), rs.getString("birthdate"), rs.getString("deathcause") , rs.getString("reincarnation"));
			}
			
		} catch (SQLException e) {
			System.out.println("While get user by ID: " + e.getMessage());
			return null;
		}
		
		return null;
	}
	
	/**
	 * 返回所有用户
	 * @return 返回所有用户的ArrayList，若出现错误返回null
	 */
	public static ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<User>();
		
		try {
			
			ResultSet rs = statement.executeQuery(String.format("SELECT * FROM %s", dbname));
			while(rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
							rs.getString("scope"), rs.getString("birthdate"), rs.getString("deathcause") , rs.getString("reincarnation"));
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			System.out.println("While get all users: " + e.getMessage());
			return null;
		}

	}
}
