package nether.common.Scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * 哈夫曼树结点模型，用于辅佐哈夫曼树计算
 * @author TGB
 *
 */
class HfmNode implements Comparable<HfmNode> {
	private ScopeTree node;
	private int weight;
	public HfmNode lchild, rchild;
	private String code;
	
	public HfmNode(ScopeTree scpt) {
		this.node = scpt;
		this.weight = scpt.getRank();
		this.code = new String();
	}
	
	/**
	 * 通过添加左右子树以及权值进行初始化
	 * @param weight
	 * @param lchild
	 * @param rchild
	 */
	public HfmNode(HfmNode lchild, HfmNode rchild) {
		this.node = null;
		this.lchild = lchild;
		this.rchild = rchild;
		this.code = new String();
		this.weight = lchild.getWeight() + rchild.getWeight();
	}
	
	// ================ Override ============
	
	@Override
	public int compareTo(HfmNode other) {
		if(this.weight < other.weight) {
			return -1;
		} else if(this.weight > other.weight) {
			return 1;
		} return 0;
	}
	
	@Override
	public String toString() {
		return (null!=this.node)?
				String.format("%s %d %s", this.node.getScopeName(), this.weight, this.code):
					String.format("NoScopeName %d %s", this.weight, this.code);
	}
	
	// ================ Override ============
	
	// ==== getter == & == setter ============
	
	public String getScopeName() {
		return this.node.getScopeName();
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		if(null == this.code)
			this.code = new String(code);
		else this.code = code;
	}
	
	// ==== getter == & == setter ============
	
	/**
	 * 节点是否包含东西还是单纯的权值节点
	 * @return
	 */
	public boolean hasContent() {
		return null!=this.node;
	}
}


/**
 * 哈夫曼树实现
 * 用于将权限对象编码存储进 SQLite 中
 * @author TGB
 *
 */
public class ScopeHuffman {
	private ScopeTree root;
	public HfmNode hfmRoot;
	private HashMap<String, String> codeToScope;
	private HashMap<String, String> scopeToCode;
	
	public ScopeHuffman(ScopeTree root) {
		this.root = root;
		this.codeToScope = new HashMap<String, String>();
		this.scopeToCode = new HashMap<String, String>();
	}
	
	/**
	 * 给哈夫曼节点列表排序
	 * 由于数据量不大所以用了经典冒泡
	 * @param HfmArr
	 */
	public static void sort(ArrayList<HfmNode> HfmArr) {
		for(int i = 0; i < HfmArr.size() - 1; i++) {
			for(int j = 0; j < HfmArr.size() - i - 1; j++) {
				if(1 == HfmArr.get(j).compareTo(HfmArr.get(j+1))) {
					HfmNode tmp = HfmArr.get(j);
					HfmArr.set(j, HfmArr.get(j+1));
					HfmArr.set(j+1, tmp);
				}
			}
		}
	}
	
	/**
	 * 从ScopesTree数组转换成哈夫曼数组
	 * @param scopesTreeArr	由权限树转换成的数组
	 * @return 哈夫曼数组
	 */
	public static ArrayList<HfmNode> fromScopeTrees(ArrayList<ScopeTree> scopesTreeArr) {
		ArrayList<HfmNode> HfmArr = new ArrayList<HfmNode>();
		for(ScopeTree scpt : scopesTreeArr) {
			HfmArr.add(new HfmNode(scpt));
		}
		
		return HfmArr;
	}
	
	/**
	 * 通过初始化进来的ScopeTree树来建立哈夫曼树
	 * 并完成编码，构造好的树存储在hfmRoot中
	 */
	public void createTree() {
		// 1. 建树
		ArrayList<HfmNode> hfmArr = fromScopeTrees(root.ScopestoArrayList());
		if(null == hfmArr)
			return;
		
		// 多于两个则继续建树 直到建立了根节点
		while(hfmArr.size() > 1) {
			HfmNode a = hfmArr.remove(0);
			HfmNode b = hfmArr.remove(0);
			if(null != a && null != b) 
				hfmArr.add(new HfmNode(a, b));
			
			// 排序
			sort(hfmArr);
		}
		
		this.hfmRoot = hfmArr.remove(0);
		
		// 2. 对构建好的树进行编码
		if(null == this.hfmRoot)
			return;

		Stack<HfmNode> hfmStk = new Stack<HfmNode>();
		HfmNode pNode;
		hfmStk.push(this.hfmRoot);
		
		// 非递归先序遍历基础上进行编码
		while(!hfmStk.empty()) {
			pNode = hfmStk.pop();
			if(pNode.hasContent()) {
				this.codeToScope.put(pNode.getCode(), pNode.getScopeName());
				this.scopeToCode.put(pNode.getScopeName(), pNode.getCode());
			}
			
			// 右子节点编码加1
			if(null != pNode.rchild) {
				hfmStk.push(pNode.rchild);
				pNode.rchild.setCode(pNode.getCode() + "1");
			}
			
			// 左子节点编码加0
			if(null != pNode.lchild) {
				hfmStk.push(pNode.lchild);
				pNode.lchild.setCode(pNode.getCode() + "0");
			}
		}
	}
	
	/**
	 * 前序遍历哈夫曼树
	 * 用于调试
	 * @param hfmNode 哈夫曼（根）节点
	 */
	public static void tripHfmTree(HfmNode hfmNode) {
		if(null == hfmNode)
			return;
		System.out.println(hfmNode.toString());
		tripHfmTree(hfmNode.lchild);
		tripHfmTree(hfmNode.rchild);
	}
	
	/**
	 * 将权限表编码成哈夫曼编码
	 * @param scopes
	 * @return 哈夫曼编码
	 */
	public String encodeHuffman(ArrayList<String> scopes) {
		StringBuffer codes = new StringBuffer();
		for(String scp : scopes) {
			codes.append(this.scopeToCode.get(scp));
		}
		return codes.toString();
	}
	
	/**
	 * 将哈夫曼编码解码成权限表
	 * @param codes 哈夫曼编码
	 * @return 权限表
	 */
	public ArrayList<String> decodeHuffman(String codes) {
		HfmNode pNode = this.hfmRoot;
		ArrayList<String> scopes = new ArrayList<String>();
		
		// 遍历哈夫曼编码
		for(int i = 0; i < codes.length(); i++) {
			
			// 0 则向左子树，1 则向右子树
			pNode = codes.charAt(i) == '0' ? pNode.lchild : pNode.rchild;
			
			// 越界则重来
			if(null == pNode)
				pNode = this.hfmRoot;
			
			// 如果本节点有相关内容，则列入权限表并重置指针
			if(pNode.hasContent()) {
				scopes.add(pNode.getScopeName());
				pNode = this.hfmRoot;
			}
		}
		
		return scopes;
	}
}
