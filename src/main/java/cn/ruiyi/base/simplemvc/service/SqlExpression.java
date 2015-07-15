package cn.ruiyi.base.simplemvc.service;

public class SqlExpression {
	
	/**
	 * 排序类型参数名称
	 * 客户端请求如： orders=orderNo__asc 指按照orderNo升序排序；多个排序用逗号隔开，如：orders=orderNo__asc,id_desc
	 */
	public static final String ORDERS = "orders";
	
	/**
	 * Sql的查询符号,将不断扩展
	 * 客户端请求如：
	 * msgTitle__like__string=测试   指msgTitle字段like “%测试%” 的限制；
	 * item.itemId__eq__int=5       指栏目ID即item.itemId=5的限制
	 */
	public enum Symbol {
		EQ, // =
		GT, // >
		GTE, // >=
		LT, // <
		LTE, //<=
		LIKE, // like
		ISNULL, // is null
	}
	
	/**
	 * 数据类型
	 */
	public enum DataType {
		INT,
		STRING,
		DATE
	}
	
	/**
	 * 排序类型
	 */
	public enum OrderType {
		ASC,
		DESC
	}
}
