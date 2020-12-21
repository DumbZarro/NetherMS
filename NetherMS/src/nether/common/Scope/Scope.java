package nether.common.Scope;

/**
 * 权限分配器
 * @author TGB
 * 由权限表、一棵哈夫曼树和一棵普普通通的树（权限树）组成
 * 所有权限的关系通过 static 初始化固定在权限树中，其父节点拥有子节点权限
 * 用户的权限则以字符串的形式存储在其权限表中，存储时存储在数据库里
 * 由于选用的是 SQLite 数据库不支持数组存储，故而将所有权限通过哈夫曼表存储
 * 再将用户权限编码成哈夫曼编码存储
 */

import java.util.ArrayList;

/**
 * 权限表，用于分配用户权限
 * @author TGB
 *
 */
public class Scope {
	
	public final static ScopeTree Scopes;	// 权限树
	public final static ScopeHuffman ScopeHuffmanTable;		// 哈夫曼树
	static {
		
		Scopes = new ScopeTree("super", 10);		// 最高权限
		Scopes.addChild("super", "admin", 70);		// 管理员权限
		Scopes.addChild("admin", "ghost", 90);		// 一般权限
		Scopes.addChild("admin", "mortal", 90);		// 最低权限
		
		ScopeHuffmanTable = new ScopeHuffman(Scopes);
		ScopeHuffmanTable.createTree();
	}
	
	/**
	 * 构造函数
	 */
	public Scope() {
		this.addScope("mortal");
	}
	
	/**
	 * 通过权限初始化
	 * @param s 权限
	 */
	public Scope(String s){
		this.addScope(s);
	}
	
	private ArrayList<String> scope = new ArrayList<String>();		// 用于给用户存储scopes
	
	/**
	 * 判断是否有这个权限
	 * @param scope	请求权限
	 * @return boolean
	 */
	public boolean hasScope(String scope) {
		ScopeTree scpnode;
		
		// 遍历已有权限 scp
		for(String scp : this.scope) {
			
			// 找出已有权限的对应节点 scpnode
			scpnode = Scopes.findScope(scp);
			if(null == scpnode)
				continue;
			
			// 已有权限的对应节点是否包含所请求的权限
			if(scpnode.hasChild(scope))
				return true;
			
		}
		return false;
	}
	
	/**
	 * 添加权限
	 * @param scope 请求权限
	 * @return boolean
	 */
	public boolean addScope(String scope) {
		if(Scopes.hasChild(scope)) {
			this.scope.add(scope);
			return true;
		}
		return false;
	}
	
	/**
	 * 删除权限
	 * @param scope 请求权限
	 * @return boolean
	 */
	public boolean delScope(String scope) {
		if(this.scope.contains(scope)) {
			this.scope.remove(scope);
			return true;
		}
		return false;
	}
	
	/**
	 * 将权限表转换成哈夫曼编码
	 * @return 哈夫曼编码
	 */
	public String toHuffmanCode() {
		return ScopeHuffmanTable.encodeHuffman(this.scope);
	}
	
	/**
	 * 将哈夫曼编码转换成权限表
	 * @param codes 哈夫曼编码
	 */
	public void fromHuffmanCode(String codes) {
		this.scope = ScopeHuffmanTable.decodeHuffman(codes);
	}
	
}
