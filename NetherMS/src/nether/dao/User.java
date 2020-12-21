package nether.dao;

import java.util.Date;

import nether.common.DateUtil;
import nether.common.MD5Util;
import nether.common.Scope.Scope;

/**
 * 用户模型
 * @author TGB
 *
 */
public class User {
	private Scope scope;
	private Integer id;
	private String username;
	private String password;	// password 应该以 MD5 / Hash 形式存储
	private Date birthdate;
	private String deathCause;
	private String reincarnation;

	public static IdGenerater idGen;
	static {
		idGen = new IdGenerater();
		new UserDBUtil();
	}
	
	public User() {
		this.scope = new Scope("mortal");
		this.id = idGen.genID();
		this.username = "defualtUserName";
		this.birthdate = new Date();
		this.deathCause = "死因未明";
		this.reincarnation = "无处安放";
	}
	
	public User(String user, String pass, String scope, Date date) {
		this.id = idGen.genID();
		this.scope = new Scope(scope);
		this.username = user;
		this.password = MD5Util.md5(pass);
		this.birthdate = date==null?new Date():date;
		this.deathCause = "死因未明";
		this.reincarnation = "无处安放";
	}
	
	/**
	 * 数据库用的User模型
	 * @param id
	 * @param username
	 * @param password 此处密码应该是已经经过加密了的
	 * @param scope
	 * @param birthdate
	 * @param deathCause
	 * @param reincarnation
	 */
	@SuppressWarnings("deprecation")
	public User(int id, String username, String password, String scope,
			String birthdate, String deathCause, String reincarnation) {
		this.id = Integer.valueOf(id);
		this.scope = new Scope();
			this.scope.fromHuffmanCode(scope);
		this.username = username;
		this.password = password;
		this.deathCause = deathCause;
		this.reincarnation = reincarnation;
		this.birthdate = DateUtil.String2Date(birthdate);
	}
	
	/**
	 * 检查本用户是否有此权限
	 * @param scope
	 * @return boolean
	 */
	public boolean hasScope(String scope) {
		return this.scope.hasScope(scope);
	}
	
	/**
	 * 作为超管给一般成员添加权限
	 * @param user
	 * @param scope
	 * @return 是否成功添加
	 */
	public boolean addScopeOfUser(User user, String scope) {
		
		if(this != user && this.hasScope("super") && !user.hasScope("super")) {
			user.scope.addScope(scope);
			return true;
		}
		return false;
	}
	
	/**
	 * 作为超管给一般成员删除权限
	 * @param user
	 * @param scope
	 * @return 是否删除添加
	 */
	public boolean delScopeOfUser(User user, String scope) {
		
		if(this != user && this.hasScope("super") 
				&& user.hasScope(scope) && !user.hasScope("super")) {
			user.scope.delScope(scope);
			return true;
		}		
		return false;
	}
	
	/**
	 * 通过用户名和密码到数据库查询数据构造对象
	 * @param user
	 * @param pass
	 */
	public User(String user, String pass) {
		this.username = user;
		this.password = MD5Util.md5(pass);

	}
	
	/**
	 * 判断密码是否正确
	 * @param password
	 * @return 是否正确
	 */
	public boolean checkPassword(String password) {
//		System.out.println("MD5：" + MD5Util.md5(password));
//		System.out.println("RAW：" + this.password);
		return MD5Util.md5(password).equals(this.password);
	}
	
	@Override
	public String toString() {
		return String.format("%d %s %s %s %s %s", 
				this.id,
				this.username,
				scope.toHuffmanCode(),
				DateUtil.Date2String(birthdate),
				deathCause.equals("")?"死因不明":deathCause,
				reincarnation.equals("")?"未能投胎":reincarnation);
	}
	
	// ==== getter == & == setter ============
	
	public String getUsername() {
		return this.username;
	}
	public Scope getScope() {
		return scope;
	}
	public void setScope(Scope scope) {
		this.scope = scope;
	}
	public Integer getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = MD5Util.md5(password);
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDeathCause() {
		return deathCause;
	}
	public void setDeathCause(String deathCause) {
		this.deathCause = deathCause;
	}
	public String getReincarnation() {
		return reincarnation;
	}
	public void setReincarnation(String reincarnation) {
		this.reincarnation = reincarnation;
	}
	
	
	public void setID() {
		this.id = 100000;//设置阎王ID
	}
	// ==== getter == & == setter ============
}
