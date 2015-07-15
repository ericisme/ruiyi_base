package cn.ruiyi.base.dao.user;
//package cn.ruiyi.base.dao.user;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Repository;
//
//import cn.ruiyi.base.constants.Constants;
//import cn.ruiyi.base.entity.Unit;
//import cn.ruiyi.base.entity.User;
//import cn.ruiyi.base.util.CodeUnit;
//import cn.ruiyi.base.util.DateUtil;
//import cn.ruiyi.base.util.StringUtil;
//
//
//@Repository
//public class UserDaoImpl implements UserDaoCustom{
//	
//	@PersistenceContext
//	private EntityManager em;
//
//	/**
//	 * 组合条件用户查找——单位、学校、用户姓名（模糊查询）、角色
//	 */
//	public Page<User> findBySubject(String units, String schools, String keyName,String roles,Pageable pageable){		
//
//		StringBuffer HQL=new StringBuffer("select * from "+Constants.YPT_BASE+".ypt_user u");
//		
//		if(schools !=null && !"".equals(schools) ){
//			HQL.append(" left join "+Constants.YPT_BASE+".ypt_school  s on s.school_id=u.school_id");
//		}
//		HQL.append(" left join "+Constants.YPT_BASE+".ypt_user_role ur on ur.user_id=u.user_id");
//		HQL.append(" left join "+Constants.YPT_BASE+".ypt_role r on r.role_id=ur.role_id");
//		HQL.append(" where u.unit_id in ("+units+") and u.userName like '%"+keyName+"%' and r.role_id in ("+roles+")");
//		
//		List<User> listUser=(List<User>)em.createNativeQuery(HQL.toString()).getResultList();
//		
//		for (User user : listUser)
//		{
//			StringUtil.debug(user.getLoginName());
//		}
//		
//		
//		//查询记录改页记录
//		Query query=em.createNativeQuery(HQL.toString());
//		query.setFirstResult(pageable.getPageNumber()*pageable.getPageSize());
//		query.setMaxResults(pageable.getPageSize());
//		List<Map<String, Object>> results =query.getResultList();
//		
//		//查询记录总数
//		Object obj = em.createNativeQuery("select count(*) from ( "+HQL.toString()+" )").getSingleResult();
//		long totalNum = new Long(obj.toString());
//				
//		//Page<User> page = new PageImpl<User>(results, pageable, totalNum);
//		
//		
//		return null;
//	}
//
//	@Override
//	public Page<User> getNeedUser(String qryName, long roleId, long typeId, int userType, Pageable pageable)
//	{
//		StringBuffer sql = new StringBuffer();
//		StringBuffer countSql = new StringBuffer();
//		sql.append("SELECT A.user_id,A.idcard,A.add_time,A.birthday,A.email,A.gender, ");
//		sql.append("A.identify_code,A.last_loginip,A.last_login_time,A.login_name,A.login_times, ");
//		sql.append("A.mobile,A.password,A.status,A.super_manager,A.system_ids,A.type_id, ");
//		sql.append("A.update_time,A.username,A.usertype ");
//		sql.append(" from "+Constants.YPT_BASE+".ypt_user A ");
//		if (roleId !=0) {
//			sql.append(" inner join "+Constants.YPT_BASE+".ypt_user_role C on C.user_Id=A.user_id and C.role_id="+roleId);
//		}
//		sql.append(" where A.super_manager=0 ");
//		if (!StringUtil.isNullAndBlank(qryName)) {
//			sql.append(" AND A.username LIKE '%").append(qryName).append("%'");
//		}
//		if (typeId != 0) {
//			if (userType == User.XX) {
//				sql.append(" AND (A.TYPE_ID = ").append(typeId).append(")");
//			} else if (userType == User.JG) { //查询其下所属机构下的所有机构用户和学校用户
//				sql.append(" AND A.TYPE_ID IN (");
//
//				Unit entity= this.getUnitById(typeId);
//				String dqm = entity.getDq_m();
//
//				sql.append("SELECT B.unit_ID AS TYPE_ID FROM "+Constants.YPT_BASE+".ypt_unit B WHERE ");
//				sql.append("(B.DQ_M LIKE '").append(dqm.substring(0, CodeUnit.getGldwdmSubLength(dqm)))
//						.append("%')");
//				sql.append(" AND (B.JB_M BETWEEN 1 AND 4)");
//				sql.append(" UNION ");
//				sql.append("SELECT c.school_id AS TYPE_ID FROM "+Constants.YPT_BASE+".ypt_school C WHERE ");
//				sql.append("C.DQ_M IN (");
//				sql.append("SELECT D.unit_ID FROM "+Constants.YPT_BASE+".ypt_unit D WHERE ");
//				sql.append("(D.DQ_M LIKE '").append(dqm.substring(0, CodeUnit.getGldwdmSubLength(dqm)))
//						.append("%')");
//				sql.append(" AND (D.JB_M BETWEEN 1 AND 4)");
//				sql.append(")");
//				sql.append(")");
//			}
//		}
//		sql.append(" ORDER BY A.user_id");
//		StringUtil.debug("用户查询SQL语句为:"+sql.toString());
//		countSql.append("SELECT COUNT(*) FROM (").append(sql).append(")");
//
//	Query query = em.createNativeQuery(sql.toString());
//	query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
//	List<Object[]> result = query.getResultList();
//	Iterator<Object[]> it = (Iterator<Object[]>) result.iterator();
//	List<User> list = new ArrayList<User>();
//	while(it.hasNext()){
//		Object[] arr = it.next();
//		User user = new User();
//		
//		String date1 = DateUtil.getDate(DateUtil.parseSimpleDate(String.valueOf(arr[2])));
//		String date2 = DateUtil.getDate(DateUtil.parseSimpleDate(String.valueOf(arr[3])));
//		String date3 = DateUtil.getDate(DateUtil.parseSimpleDate(String.valueOf(arr[8])));
//		String date4 = DateUtil.getDate(DateUtil.parseSimpleDate(String.valueOf(arr[17])));
//		
//		user.setId(Long.valueOf(String.valueOf(arr[0])));
//		user.setIDCard(StringUtil.null2blank(String.valueOf(arr[1])));
//		user.setAddTime(DateUtil.parseSimpleDate(date1));
//		user.setBirthday(DateUtil.parseSimpleDate(date2));
//		user.setBdayStr(date2);
//		user.setEmail(StringUtil.null2blank(String.valueOf(arr[4])));
//		user.setGender(Integer.parseInt(String.valueOf(arr[5])));
//		user.setIdentifyCode(StringUtil.null2blank(String.valueOf(arr[6])));
//		user.setLastLoginIP(StringUtil.null2blank(String.valueOf(arr[7])));
//		user.setLastLoginTime(DateUtil.parseSimpleDate(date3));	
//		user.setLoginName(StringUtil.null2blank(String.valueOf(arr[9])));
//		user.setLoginTimes(Long.valueOf(String.valueOf(arr[10])));
//		user.setMobile(StringUtil.null2blank(String.valueOf(arr[11])));
//		user.setPassword(StringUtil.null2blank(String.valueOf(arr[12])));
//		user.setStatus(Integer.parseInt(String.valueOf(arr[13])));
//		user.setSuperManager(Integer.parseInt(String.valueOf(arr[14])));
//		user.setSystemIds(StringUtil.null2blank(String.valueOf(arr[15])));
//		user.setTypeId(Long.valueOf(String.valueOf(arr[16])));
//		user.setUpdateTime(DateUtil.parseSimpleDate(date4));
//		user.setUsername(StringUtil.null2blank(String.valueOf(arr[18])));
//		user.setUsertype(Integer.parseInt(String.valueOf(arr[19])));
//		
//		list.add(user);
//	}
//	//查询记录总数
//	Object obj = em.createNativeQuery(countSql.toString()).getSingleResult();
//	long totalNum = new Long(obj.toString());
//	//StringUtil.debug(totalNum);	
//	Page<User> page = new PageImpl<User>(list, pageable, totalNum);
//	
//	return page;
//	}
//
//	
//	public Unit getUnitById(Long id)
//	{
//		Unit unit = em.find(Unit.class, id);
//		return unit;
//	}
//
//}
