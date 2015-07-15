package cn.unis.transit;

import java.util.Date;
import java.util.List;

/**
 * 游乐场，详细信息
 * @author Administrator
 *
 */
public class UserDetailInfo {
	
	private String key;
	private String username;
	private String handle_name;
	/**
	 * 	const USER_GRADE_PSEUDO = "p";
		const USER_GRADE_NON_VERIFIED = "n";
		const USER_GRADE_VERIFIED = "v";
		const USER_GRADE_VERIFIED_EMAIL = "e";
		const USER_GRADE_VERIFIED_MOBILE = "m";
		const USER_GRADE_VERIFIED_ID = "i";
	 */
	private String grade;
	private String email;
	private String phone;
	private String timezone;
	private String country;
	private String lang;
	private String phone_country_code;
	private String personal_status;
	private List<UserRegisteredArcade> registered_arcades;
	private Integer achievement_score;
	private Integer accumulated_ascore;
	
	private Date registered_at;
	private Date expire;
	
	/**
	 * 	'STATUS_NOT_HANDLED' => 0,
	 *	'STATUS_NORMAL'      => 1,
	 *	'STATUS_HIDDEN'      => 2,
	 *	'STATUS_DISABLED'    => 3, // or revoked
	 *	'STATUS_BANNED'      => 7,
	 *	'STATUS_DELETED'     => 9,
	 */
	private Integer status;
	public String getStatusChinese() {
		String statusChinese = "";
		if(status!=null){
			if(status.equals(0)){
				statusChinese = "未处理";
			}
			if(status.equals(1)){
				statusChinese = "正常";
			}
			if(status.equals(2)){
				statusChinese = "隐藏";
			}
			if(status.equals(3)){
				statusChinese = "停权";
			}
			if(status.equals(7)){
				statusChinese = "封查";
			}
			if(status.equals(9)){
				statusChinese = "已删除";
			}
		}
		return statusChinese;
	}
	
	

	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHandle_name() {
		return handle_name;
	}
	public void setHandle_name(String handle_name) {
		this.handle_name = handle_name;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getEmail() {
		//if(email!=null){
		//	if(email.indexOf("@qrcade.mobi")==-1){
				return email;
		//	}
		//}
		//return "";
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	public String getPhone_country_code() {
		return phone_country_code;
	}
	public void setPhone_country_code(String phone_country_code) {
		this.phone_country_code = phone_country_code;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getPersonal_status() {
		return personal_status;
	}
	public void setPersonal_status(String personal_status) {
		this.personal_status = personal_status;
	}
	public List<UserRegisteredArcade> getRegistered_arcades() {
		return registered_arcades;
	}
	public void setRegistered_arcades(List<UserRegisteredArcade> registered_arcades) {
		this.registered_arcades = registered_arcades;
	}
	public Integer getAchievement_score() {
		return achievement_score;
	}
	public void setAchievement_score(Integer achievement_score) {
		this.achievement_score = achievement_score;
	}
	public Integer getAccumulated_ascore() {
		return accumulated_ascore;
	}
	public void setAccumulated_ascore(Integer accumulated_ascore) {
		this.accumulated_ascore = accumulated_ascore;
	}
	public Date getRegistered_at() {
		return registered_at;
	}
	public void setRegistered_at(Date registered_at) {
		this.registered_at = registered_at;
	}
	public Date getExpire() {
		return expire;
	}
	public void setExpire(Date expire) {
		this.expire = expire;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

	
	
}
