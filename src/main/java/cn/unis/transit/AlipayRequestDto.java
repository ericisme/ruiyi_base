package cn.unis.transit;

public class AlipayRequestDto {
	/**
	 * 验证码
	 */
	private String captcha;
	/**
	 * 充值号码
	 */
	private String username;
	/**
	 * 确认充值号码
	 */
	private String confirmusername;
	/**
	 * 验证码对错标记
	 */
	private boolean captchaFlag;
	/**
	 * 充值号码错误标记
	 */
	private boolean usernameFlag;
	/**
	 * 标记
	 */
	private float discount;
	/**
	 * 充值数量中的radio是否选中
	 * 0.没有选中
	 * 1.第一个
	 * 2.第二个
	 * 。。。
	 */
	private int isChecked;
	/**
	 * 同意
	 */
	private int isAgree;
	/**
	 * 选中的银行
	 */
	private String bankselect;
	/**
	 * 世宇大钱包
	 */
	private int sycoin;
	/**
	 * 游戏钱包
	 */
	private int token;
	/**
	 * 充值类型：游戏币 世宇币
	 * 改成 游币(sycoin) 和 实体币(token)
	 */
	private String rechargetype;
	/**
	 * 游戏选择HTML
	 */
	private String gameOptionHtml;
	/**
	 * 选中的游戏
	 */
	private String gameSelect;
	/**
	 * 游乐场选择HTML
	 */
	private String gameCenterOptionHtml;
	/**
	 * 选中的游乐场
	 */
	private String gameCenterSelect;
	
	private String gameSelectTmpDataJson;
	
	private String rate;
	
	private int userErrorReturnFlag;
	

	public int getUserErrorReturnFlag() {
		return userErrorReturnFlag;
	}

	public void setUserErrorReturnFlag(int userErrorReturnFlag) {
		this.userErrorReturnFlag = userErrorReturnFlag;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getConfirmusername() {
		return confirmusername;
	}

	public void setConfirmusername(String confirmusername) {
		this.confirmusername = confirmusername;
	}

	public boolean isCaptchaFlag() {
		return captchaFlag;
	}

	public void setCaptchaFlag(boolean captchaFlag) {
		this.captchaFlag = captchaFlag;
	}

	public boolean isUsernameFlag() {
		return usernameFlag;
	}

	public void setUsernameFlag(boolean usernameFlag) {
		this.usernameFlag = usernameFlag;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public int getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}

	public int getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(int isAgree) {
		this.isAgree = isAgree;
	}

	public String getBankselect() {
		return bankselect;
	}

	public void setBankselect(String bankselect) {
		this.bankselect = bankselect;
	}

	public int getSycoin() {
		return sycoin;
	}

	public void setSycoin(int sycoin) {
		this.sycoin = sycoin;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

	public String getRechargetype() {
		return rechargetype;
	}

	public void setRechargetype(String rechargetype) {
		this.rechargetype = rechargetype;
	}

	public String getGameOptionHtml() {
		return gameOptionHtml;
	}

	public void setGameOptionHtml(String gameOptionHtml) {
		this.gameOptionHtml = gameOptionHtml;
	}

	public String getGameSelect() {
		return gameSelect;
	}

	public void setGameSelect(String gameSelect) {
		this.gameSelect = gameSelect;
	}

	public String getGameCenterOptionHtml() {
		return gameCenterOptionHtml;
	}

	public void setGameCenterOptionHtml(String gameCenterOptionHtml) {
		this.gameCenterOptionHtml = gameCenterOptionHtml;
	}

	public String getGameCenterSelect() {
		return gameCenterSelect;
	}

	public void setGameCenterSelect(String gameCenterSelect) {
		this.gameCenterSelect = gameCenterSelect;
	}

	public String getGameSelectTmpDataJson() {
		return gameSelectTmpDataJson;
	}

	public void setGameSelectTmpDataJson(String gameSelectTmpDataJson) {
		this.gameSelectTmpDataJson = gameSelectTmpDataJson;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
}	
