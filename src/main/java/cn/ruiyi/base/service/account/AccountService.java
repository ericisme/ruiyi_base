package cn.ruiyi.base.service.account;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.ruiyi.base.dao.role.RoleDao;
//import cn.ruiyi.base.dao.school.SchoolDao;
//import cn.ruiyi.base.dao.unit.UnitDao;
import cn.ruiyi.base.dao.user.UserDao;
import cn.ruiyi.base.entity.Role;
import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.exception.ServiceException;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class AccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

	/**
	 * 获取所有用户
	 * @return
	 */
	public List<User> findAll(){
		return userDao.findAll();
	}

	/**
	 * 获取所有的已启用用户
	 * 	@param pageable
	 * @return
	 */
	public Page<User> getAllUser(Pageable  pageable) {
		return  userDao.findAllUser(pageable);
	}
	/**
	 * 获取所有用户（包括已启用和已停用的用户）
	 * @param pageable
	 * @return
	 */
	public List<User> getAllUsers(Pageable  pageable) {
		return (List<User>) userDao.findAll(pageable);
	}	
	
	/**
	 * 根据用户ID查找用户
	 * @param id
	 * @return
	 */
	public User getUser(Long id) {
		return userDao.findOne(id);
	}
	
	/**
	 * 根据登录名查找用户（登录名控制唯一）
	 * @param loginName
	 * @return
	 */
	public User findUserByLoginName(String loginName) {
		User user = userDao.findByLoginName(loginName);
		return user;
	}
	
	
	/**
	 * 列表查找
	 */
	public Page<User> findAll(final String qryName, final Long roleId, PageRequest pageable)
	{
		return this.userDao.findAll(new Specification<User>(){
			@Override
			public Predicate toPredicate(Root<User> user, CriteriaQuery<?> query, CriteriaBuilder cb)
			{
				List<Predicate> predicateList = new ArrayList<Predicate>();						
				//用户名
				if(StringUtils.isNotEmpty(qryName)){
					Path<String> expression = user.get("username");
					Predicate p = cb.like(cb.lower(expression), "%" + qryName.toLowerCase() + "%");
					predicateList.add(p);				
				}				
				//角色			
				if(roleId!=null){
					if(roleId!=0){
						System.out.println("roleName:"+roleDao.findOne(roleId).getName());
						List<Predicate> srOrList = new ArrayList<Predicate>();
						Path<List<Role>> expression = user.get("roleList");
						Predicate p = cb.isMember(roleDao.findOne(roleId), expression);
						predicateList.add(p);
					}
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
	}
	
	
	
	/**
	 * 返回当前用户
	 * 
	 * @param request
	 * @return
	 */
	public User getUser(HttpServletRequest request){
		String loginName = request.getRemoteUser();
		User user = findUserByLoginName(loginName);
		return user;
	}
	
	/**
	 * 用户真实姓名可能不唯一
	 * 根据用户的真实姓名查找用户
	 * @param loginName
	 * @return
	 */
	public List<User> findByUsername(String username){
		return userDao.findByUsername(username);
	}

	/**
	 *保存用户
	 *用于用户的增加、修改
	 * @param user
	 */			
	@Transactional(readOnly=false)
	public void saveUser(User user){
		this.userDao.save(user);
	}
	/**
	 * 删除用户
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}
	
//	/**
//	 * 返回子系统用户列表
//	 * 
//	 * @param subSys
//	 * @param list
//	 * @return
//	 */
//	public String parseJSONArray(List<User> list){
//		StringBuffer sbf = new StringBuffer();
//		
//		sbf.append("[");
//		for (int i = 0;i<list.size();i++){
//			User user = list.get(i);
//			sbf.append("{");
//			sbf.append("\"id\":").append("\""+user.getId()+"\",");
//			sbf.append("\"username\":").append("\""+user.getUsername()+"\",");
//			if(user.getSchool() == null){
//				sbf.append("\"schoolName\":").append("\"\",");
//			}else{
//				sbf.append("\"schoolName\":").append("\""+ user.getSchool().getSchoolName()+"\",");
//			}
//			if(user.getUnit() == null){
//				sbf.append("\"unitName\":").append("\"\",");
//			}else{
//				sbf.append("\"unitName\":").append("\""+ user.getUnit().getUnitName()+"\",");
//			}
//			sbf.append("\"IDCard\":").append("\""+user.getIDCard()+"\",");
//			sbf.append("\"status\":").append("\""+user.getStatus()+"\",");
//			sbf.append("\"identifyCode\":").append("\""+user.getIdentifyCode()+"\",");
//			sbf.append("\"userType\":").append("\""+user.getUsertype()+"\"");
//			if(list.size() - 1 == i){
//				sbf.append("}");
//			}else{
//				sbf.append("},");
//			}
//		}
//		sbf.append("]");
//		//StringUtil.debug(sbf.toString());
//		
//		return sbf.toString();
//	}
//	
//	/**
//	 * 返回子系统用户
//	 * 
//	 * @param subSys
//	 * @param list
//	 * @return
//	 */
//	public String parseJSONObject(User user){
//		StringBuffer sbf = new StringBuffer();
//		sbf.append("{");
//		sbf.append("\"id\":").append("\""+user.getId()+"\",");
//		sbf.append("\"loginName\":").append("\""+StringUtil.null2blank(user.getLoginName())+"\",");
//		sbf.append("\"password\":").append("\""+StringUtil.null2blank(user.getPassword())+"\",");
//		sbf.append("\"initPassword\":").append("\""+StringUtil.null2blank(user.getInitPassword())+"\",");
//		sbf.append("\"username\":").append("\""+StringUtil.null2blank(user.getUsername())+"\",");
//		sbf.append("\"mobile\":").append("\""+StringUtil.null2blank(user.getMobile())+"\",");
//		sbf.append("\"email\":").append("\""+StringUtil.null2blank(user.getEmail())+"\",");
//		sbf.append("\"birthday\":").append("\""+DateUtil.getDate(user.getBirthday())+"\",");
//		sbf.append("\"IDCard\":").append("\""+StringUtil.null2blank(user.getIDCard())+"\",");
//		sbf.append("\"typeId\":").append("\""+user.getTypeId()+"\",");
//		sbf.append("\"gender\":").append("\""+user.getGender()+"\",");
//		sbf.append("\"identifyCode\":").append("\""+StringUtil.null2blank(user.getIdentifyCode())+"\",");
//		if(user.getSchool() == null){
//			sbf.append("\"_xx_id\":").append("\"\",");
//		}else{
//			sbf.append("\"_xx_id\":").append("\""+user.getSchool().getId()+"\",");
//		}
//		if(user.getUnit() == null){
//			sbf.append("\"_dw_id\":").append("\"\",");
//		}else{
//			sbf.append("\"_dw_id\":").append("\""+user.getUnit().getId()+"\",");
//		}
//		sbf.append("\"usertype\":").append("\""+user.getUsertype()+"\"");
//		sbf.append("}");
//		//StringUtil.debug(sbf.toString());
//		
//		return sbf.toString();
//	}

}
