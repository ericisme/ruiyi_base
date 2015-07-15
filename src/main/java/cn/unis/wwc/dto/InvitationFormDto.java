package cn.unis.wwc.dto;



public class InvitationFormDto {
	/**
	 * 记录id
	 */
	private Long id;
	/**
	 * 邀请码
	 */
	private String code;
	/**
	 * 公司名
	 */
	private String companyname;
	/**
	 * 国家或地区
	 */
	private String country;
	/**
	 * 到达时间
	 */
	private String arrvialdate;
	/**
	 * 接机
	 */
	private boolean pickup;
	/**
	 * 航班号
	 */
	private String flight_no;

	/**
	 * 参加人信息
	 */
	private Long[] app_id;

	private String[] firstName;

	private String[] lastName;

	private String[] app_fullname;

	private String[] app_position;

	private String[] app_mobilenumber;

	private String[] app_gender;

	private String[] app_email;

	private String[] app_identityCode;

	/**
	 * 入住酒店
	 */
	private int hotel;
	/**
	 * 中西餐
	 */
	private int food;
	/**
	 * 特殊饮食习惯
	 */
	private String special_diet;
	/**
	 * 活动
	 */
	private boolean activity1;
	private boolean activity2;
	private boolean activity3;
	private boolean activity4;

	private String message;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getArrvialdate() {
		return arrvialdate;
	}

	public void setArrvialdate(String arrvialdate) {
		this.arrvialdate = arrvialdate;
	}

	public boolean isPickup() {
		return pickup;
	}

	public void setPickup(boolean pickup) {
		this.pickup = pickup;
	}

	public String getFlight_no() {
		return flight_no;
	}

	public void setFlight_no(String flight_no) {
		this.flight_no = flight_no;
	}

	public String[] getApp_fullname() {
		return app_fullname;
	}

	public void setApp_fullname(String[] app_fullname) {
		this.app_fullname = app_fullname;
	}

	public String[] getApp_position() {
		return app_position;
	}

	public void setApp_position(String[] app_position) {
		this.app_position = app_position;
	}

	public String[] getApp_mobilenumber() {
		return app_mobilenumber;
	}

	public void setApp_mobilenumber(String[] app_mobilenumber) {
		this.app_mobilenumber = app_mobilenumber;
	}

	public String[] getApp_gender() {
		return app_gender;
	}

	public void setApp_gender(String[] app_gender) {
		this.app_gender = app_gender;
	}



	public String[] getApp_email() {
		return app_email;
	}

	public void setApp_email(String[] app_email) {
		this.app_email = app_email;
	}

	public int getHotel() {
		return hotel;
	}


	public void setHotel(int hotel) {
		this.hotel = hotel;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public String getSpecial_diet() {
		return special_diet;
	}

	public void setSpecial_diet(String special_diet) {
		this.special_diet = special_diet;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getActivity1() {
		return activity1;
	}

	public void setActivity1(boolean activity1) {
		this.activity1 = activity1;
	}

	public boolean getActivity2() {
		return activity2;
	}

	public void setActivity2(boolean activity2) {
		this.activity2 = activity2;
	}

	public boolean getActivity3() {
		return activity3;
	}

	public void setActivity3(boolean activity3) {
		this.activity3 = activity3;
	}

	public boolean getActivity4() {
		return activity4;
	}

	public void setActivity4(boolean activity4) {
		this.activity4 = activity4;
	}

	public Long[] getApp_id() {
		return app_id;
	}

	public void setApp_id(Long[] app_id) {
		this.app_id = app_id;
	}

	public String[] getApp_identityCode() {
		return app_identityCode;
	}

	public void setApp_identityCode(String[] app_identityCode) {
		this.app_identityCode = app_identityCode;
	}

	public String[] getFirstName() {
		return firstName;
	}

	public void setFirstName(String[] firstName) {
		this.firstName = firstName;
	}

	public String[] getLastName() {
		return lastName;
	}

	public void setLastName(String[] lastName) {
		this.lastName = lastName;
	}
}
