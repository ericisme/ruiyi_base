package cn.unis.wwc.dto;

public class ApplyCodeDto {

	private int apply_type;
	private String companyname;
	private String fullname;
	private String position;
	private String email;
	private String captcha;
	private String phone;

	public int getApply_type() {
		return apply_type;
	}
	public void setApply_type(int apply_type) {
		this.apply_type = apply_type;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}


}
