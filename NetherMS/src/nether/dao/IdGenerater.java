package nether.dao;

/**
 * 用于生成ID
 * @author TGB
 *
 */
public class IdGenerater {
	private int id;
	
	public IdGenerater(int initNum) {
		this.id = initNum;
	}
	
	public IdGenerater() {
		this(100000);	// 从 1 开始， 0 为阎王
	}
	
	/**
	 * 生成ID
	 * @return 递增的ID
	 */
	public Integer genID() {
		return Integer.valueOf(this.id++);
	}
	
	/**
	 * 设置初始化的ID
	 * @param initNum
	 */
	public void setInitialID(int initNum) {
		this.id = initNum;
	}
}
