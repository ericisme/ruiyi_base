package cn.unis.wwc.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.ruiyi.base.util.RandomUtil;
import cn.ruiyi.base.util.StringUtil;
import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;
import cn.unis.wwc.dao.InvitationFormApplicantDao;
import cn.unis.wwc.entity.InvitationFormApplicant;
import cn.unis.wwc.entity.InvitationFormApplicantCheckIn;

/**
 * 邀请 详细信息表单 与会者 .service层
 *
 * @author eric
 */
@Component
@Transactional(readOnly = false)
public class InvitationFormApplicantService extends
		BaseManagerService<InvitationFormApplicant> {

	@Autowired
	private InvitationFormApplicantDao invitationFormApplicantDao;

	@Override
	protected BaseManagerDao<InvitationFormApplicant> getDomainDao() {
		return invitationFormApplicantDao;
	}
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * 获得总人数
	 */
	public Long getTotalCount(){
		return invitationFormApplicantDao.count();
	}
	/**
	 * 获得已签到人数
	 */
	public Long getCheckInCount(){
		String hql = "select count(ifa.id) from InvitationFormApplicant ifa where exists(select ifaci.id from InvitationFormApplicantCheckIn ifaci where ifa.id=ifaci.invitationFormApplicant.id)";
		Query query = entityManager.createQuery(hql);
		return (Long)query.getSingleResult();
	}
	
	/**
	 * 根据InvitationFormId查找InvitationFormApplicant列表
	 */
	public List<InvitationFormApplicant> getListByInvitationFormId(	Long invitationFormId, String invitationCode) {
		return invitationFormApplicantDao.getListByInvitationFormId(invitationFormId, invitationCode);
	}


	/**
	 * 根据identityCode查找InvitationFormApplicant
	 */
	public InvitationFormApplicant findByIdentityCode(String identityCode){
		return invitationFormApplicantDao.findByIdentityCode(identityCode);
	}
	
	/**
	 * 生成一个身份二维码
	 */
	public String generateAIdentityCode(int length) {
		boolean flag = true;
		String code = "";
		while (flag) {
			String newCode = RandomUtil.getInstance().randNumberAndAlpha(length);
			InvitationFormApplicant ifa = invitationFormApplicantDao.findByIdentityCode(newCode);
			if (ifa == null) {
				code = newCode;
				flag = false;
			}
		}
		return code;
	}
	

	/**
	 * 分页查询
	 */
	public Page<InvitationFormApplicant> pageParamQuery(PageRequest pageable, 
			final Integer _checkOrNot, final String _districtCode, final String _companyName, final String _name, final String _firstName, final String _lastName, final String _mobileNumber, final String _email){
		Page<InvitationFormApplicant> paginate =
				invitationFormApplicantDao.findAll(new Specification<InvitationFormApplicant>(){
					@Override
					public Predicate toPredicate(Root<InvitationFormApplicant> root, CriteriaQuery<?> query, CriteriaBuilder cb){
						List<Predicate> predicateList = new ArrayList<Predicate>();
						/*
						 * 签到与否
						 */
						if(_checkOrNot!=null){
							Path<List<InvitationFormApplicantCheckIn>> expression = root.get("invitationFormApplicantCheckInList");
							//Predicate p = cb.isMember(sr, expression);
							if(_checkOrNot==1){								
								predicateList.add(cb.ge(cb.size(expression), 1));
							}
							if(_checkOrNot==0){
								predicateList.add(cb.equal(cb.size(expression), 0));
							}
						}
						
						/*
						 * 国家或地区
						 */
						if(!StringUtil.isNullAndBlank(_districtCode)){
							//Path<String> ex_like_str = root.get("invitationForm").get("invitationCode").get("districtCode");
							//predicateList.add(cb.equal(ex_like_str, _districtCode));
							Path<String> ex_like_str = root.get("invitationForm").get("districtCode");
							predicateList.add(cb.equal(ex_like_str, _districtCode));
						}
						/*
						 * 公司名
						 */
						if(!StringUtil.isNullAndBlank(_companyName)){
							System.out.println("_companyName:"+_companyName);
							Path<String> ex_like_str = root.get("invitationForm").get("companyName");
							predicateList.add(cb.like(cb.lower(ex_like_str), "%" + _companyName.toLowerCase() + "%"));
						}
						/*
						 * 中文姓名
						 */
						if(!StringUtil.isNullAndBlank(_name)){
							Path<String> ex_like_str = root.get("name");
							predicateList.add(cb.like(cb.lower(ex_like_str), "%" + _name.toLowerCase() + "%"));
						}	
						/*
						 * 英文firstName
						 */
						if(!StringUtil.isNullAndBlank(_firstName)){
							Path<String> ex_like_str = root.get("firstName");
							predicateList.add(cb.like(cb.lower(ex_like_str), "%" + _firstName.toLowerCase() + "%"));
						}	
						/*
						 * 英文lastName
						 */
						if(!StringUtil.isNullAndBlank(_lastName)){
							Path<String> ex_like_str = root.get("lastName");
							predicateList.add(cb.like(cb.lower(ex_like_str), "%" + _lastName.toLowerCase() + "%"));
						}							
						/*
						 * 手机号
						 */
						if(!StringUtil.isNullAndBlank(_mobileNumber)){
							Path<String> ex_like_str = root.get("mobileNumber");
							predicateList.add(cb.like(cb.lower(ex_like_str), "%" + _mobileNumber.toLowerCase() + "%"));
						}
						/*
						 * email
						 */
						if(!StringUtil.isNullAndBlank(_email)){
							Path<String> ex_like_str = root.get("email");
							predicateList.add(cb.like(cb.lower(ex_like_str), "%" + _email.toLowerCase() + "%"));
						}
						
						Predicate[] predicates = new Predicate[predicateList.size()];
						predicateList.toArray(predicates);
						if(predicates.length > 0){
							return cb.and(predicates);
						}else{
							return cb.conjunction();
						}
					}
				}, pageable);
		return paginate;
	}
	
	
}
