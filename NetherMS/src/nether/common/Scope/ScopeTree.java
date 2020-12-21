package nether.common.Scope;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 用于描述权限之间的关系
 * @author TGB
 *
 */
public class ScopeTree {
	private String ScopeName;
	private ArrayList<ScopeTree> child;
	private int rank;
	
	ScopeTree(String scpname, int rank) {
		this.ScopeName = scpname;
		this.child = new ArrayList<ScopeTree>();
		this.rank = rank;
	}
	
	// ==== getter == & == setter ============
	public String getScopeName() {
		return this.ScopeName;
	}
	
	public int getRank() {
		return this.rank;
	}
	// ==== getter == & == setter ============
	
	/**
	 * 查找并返回有对应Scope的引用
	 * @param scope
	 * @return 有对应Scope的ScopeTree
	 */
	public ScopeTree findScope(String scope) {
		if(this.ScopeName == scope)
			return this;
		else if(null == this.child || 0 == this.child.size())
			return null;
		
		// 向下递归遍历子树
		for(ScopeTree st : child) {
			ScopeTree res = st.findScope(scope);
			if(null != res)
				return res;
		}
		
		return null;
	}
	
	/**
	 * 是否含有特定子结点
	 * 判断自身时也返回true
	 * @param scope
	 * @return boolean
	 */
	public boolean hasChild(String scope) {
		return null != findScope(scope);
	}
	
	/**
	 * 对对应Scope结点添加更低权限的结点
	 * @param masterscope
	 * @param scope
	 * @param rank
	 * @return 是否添加成功
	 */
	public boolean addChild(String masterscope, String scope, int rank) {
		ScopeTree sp = findScope(masterscope);
		
		// 父节点不能为空且添加节点不能与现存的同名
		if(null == sp || null != findScope(scope))
			return false;
		
		sp.child.add(new ScopeTree(scope, rank));
		return true;
	}
	
	/**
	 * 通过层次遍历，将ScopeTree转换成动态数组
	 * 以用于哈夫曼树的构建
	 * @return 包含所有节点的数组
	 */
	public ArrayList<ScopeTree> ScopestoArrayList() {
		ArrayList<ScopeTree> scopeArr = new ArrayList<ScopeTree>();
		Queue<ScopeTree> scopeQue = new LinkedList<ScopeTree>();
		
		// 层次遍历
		for(scopeQue.offer(this); !scopeQue.isEmpty(); ) {
			ScopeTree scp = scopeQue.poll();
			if(null == scp)
				continue;
			
			scopeArr.add(scp);
			if(null != scp.child && 0 != scp.child.size()) {
				for(ScopeTree child : scp.child) {
					scopeQue.offer(child);
				}
			}
		}
		
		return scopeArr;
	}
	
	
}