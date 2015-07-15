package cn.unis.transit;

/**
 * 积分商城主页左边栏用户名&&积分
 * @author fanzz
 *
 */
public class GiftCenterUsernameAndScore {
	/**
	 * 是否显示
	 */
	private int isShow;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 彩票，（2014-06-25后不在是积分）
	 */
	private int score;
	/**
	 * 是否显示积分不够
	 * 0.未登录 or 登录够积分
	 * 1.登录未够积分（最低兑换积分商品）
	 */
	private int enoughScore;
	
	/**
	 * 分类
	 */
	private int sort;
	
	/**
	 * 排序
	 */
	private int order;
	/**
	 * 我们为你挑选了以下礼品
	 */
	private int showWeChooseFor;
	
	public int getShowWeChooseFor() {
		return showWeChooseFor;
	}
	public void setShowWeChooseFor(int showWeChooseFor) {
		this.showWeChooseFor = showWeChooseFor;
	}
	public int getIsShow() {
		return isShow;
	}
	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getEnoughScore() {
		return enoughScore;
	}
	public void setEnoughScore(int enoughScore) {
		this.enoughScore = enoughScore;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
}
