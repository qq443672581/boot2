package cn.dlj1.cms.dao.condition;

/**
 * 服务于Condition<br>
 * 查询条件父级接口<br>
 * 
 * 拥有两个实现 {@link Condition} {@link Conditions} <br>
 * 
 * @author fivewords(443672581@qq.com)
 * @date 2018年8月29日
 */
public interface Cnd {
	
	/**
	 * 获取条件的类型
	 * 
	 * @return
	 */
	QueryType getType();
	
}
