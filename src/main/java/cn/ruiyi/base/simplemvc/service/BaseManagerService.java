package cn.ruiyi.base.simplemvc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.util.DateUtil;
import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;

/**
 *  管理后台的service基类，负责单个域对象CRUD操作的基类，子类只需继承此类便拥有CRUD操作功能
 *  目前提供findById、delete、save、pageParamQuery四个基础方法，子类可以通过重载这几个方法实现特殊需求。
 * @author eric
 *
 * @param <T> CRUD对应的entity类, 只支持Long类型id
 */
@MappedSuperclass
public abstract class BaseManagerService<T> {	

	/**
	 * 获得domainDao的抽象方法，子类需要重写并返回已注入的doaminDao
	 * @return
	 */
	protected abstract BaseManagerDao<T> getDomainDao();
	
	/**
	 * 根据ID查找实体
	 * @param id
	 * @return
	 */
	public T findById(Long id) {
		return this.getDomainDao().findOne((Long) id);
	}
	
	/**
	 * 删除实体
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void delete(Long id) {
		this.getDomainDao().delete(id);
	}
	
	/**
	 *保存实体
	 *用于实体的增加、修改
	 * @param news
	 */			
	@Transactional(readOnly=false)
	public void save(T domain){
		this.getDomainDao().saveAndFlush(domain);
	}
	
	/**
	 *  分页查询方法
	 * @param request
	 * @return
	 */
	public Page<T> pageParamQuery(final HttpServletRequest request){		
		PageRequest pageable = this.getPageRequest(request);		
		Page<T> paginate = 
				this.getDomainDao().findAll(new Specification<T>(){
					@Override
					public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb){
						List<Predicate> predicateList = new ArrayList<Predicate>();						
						
						List<Predicate> requestPredicateList = BaseManagerService.this.getRequestPredicateList(request, root, cb);
						predicateList.addAll(requestPredicateList);					
						
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
	
	
	/**
	 * 工具方法，分页查询 pageable,组装器，主要用于order条件。
	 * @param request
	 * @return
	 */
	public PageRequest getPageRequest(HttpServletRequest request){
		Integer page = Integer.parseInt(request.getParameter("page"));
		Integer size_per_page = Integer.parseInt(request.getParameter("size_per_page")==null?(Constants.PAGE_SIZE+""):request.getParameter("size_per_page"));
		String orders = request.getParameter("orders");
		PageRequest pageable = new PageRequest(page - 1, size_per_page, Sort.Direction.DESC, "id");
		if(orders != null && orders.length() > 0){		
			String fieldAndType = orders;
			//for(String fieldAndType : orders.split(",")){
				if(SqlExpression.OrderType.ASC.toString().equalsIgnoreCase(fieldAndType.split("__")[1])){
					pageable = new PageRequest(page - 1, size_per_page, Sort.Direction.ASC,  fieldAndType.split("__")[0].split(","));
				}else if(SqlExpression.OrderType.DESC.toString().equalsIgnoreCase(fieldAndType.split("__")[1])){
					pageable = new PageRequest(page - 1, size_per_page, Sort.Direction.DESC, fieldAndType.split("__")[0].split(","));					
				}				
			//}
		}	
		return pageable;
	}
	
	/**
	 * 工具方法，分页查询 PredicateList 条件组装器。
	 * @param request
	 * @param root
	 * @param cb
	 * @return
	 */
	public List<Predicate> getRequestPredicateList(HttpServletRequest request, Root<T> root, CriteriaBuilder cb){
		List<Predicate> predicateList = new ArrayList<Predicate>();						
		
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String element = (String) params.nextElement();
			String[] nameAndType = element.split("__");
			if(nameAndType.length == 3){
				String v = request.getParameter(element);
				//System.out.println("element:"+element);
				//System.out.println("v:"+v);
				if(StringUtils.isBlank(v)){
					continue;
				}
				switch (SqlExpression.Symbol.valueOf(nameAndType[1].toUpperCase())) {
				case EQ:			
					switch(SqlExpression.DataType.valueOf(nameAndType[2].toUpperCase())){
					case STRING:
						Path<Object> ex_eq_str = root.get(nameAndType[0]);	
						predicateList.add(cb.equal(ex_eq_str, v));
						break;
					case INT:
						Path<Object> ex_eq_int = root.get(nameAndType[0]);
						if("null".equals(v)){
							predicateList.add(cb.isNull(ex_eq_int));
						}else{
							predicateList.add(cb.equal(ex_eq_int, Integer.parseInt(v)));
						}
						break;
					case DATE:
						Path<Object> ex_eq_dat = root.get(nameAndType[0]);
						predicateList.add(cb.equal(ex_eq_dat, DateUtil.parseSimpleDateTime(v)));
						break;
					default:
						break;
					}
					break;
				case GT:
					switch(SqlExpression.DataType.valueOf(nameAndType[2].toUpperCase())){
					case STRING:
						Path<String> ex_gt_str = root.get(nameAndType[0]);
						predicateList.add(cb.greaterThan(ex_gt_str, v));
						break;
					case INT:
						Path<Integer> ex_gt_int = root.get(nameAndType[0]);
						predicateList.add(cb.greaterThan(ex_gt_int, Integer.parseInt(v)));
						break;
					case DATE:
						Path<Date> ex_gt_dat = root.get(nameAndType[0]);
						predicateList.add(cb.greaterThan(ex_gt_dat, DateUtil.parseSimpleDateTime(v)));
						break;
					default:
						break;
					}
					break;
				case GTE:
					switch(SqlExpression.DataType.valueOf(nameAndType[2].toUpperCase())){
					case STRING:
						Path<String> ex_get_str = root.get(nameAndType[0]);
						predicateList.add(cb.greaterThanOrEqualTo(ex_get_str, v));
						break;
					case INT:
						Path<Integer> ex_get_int = root.get(nameAndType[0]);
						predicateList.add(cb.greaterThanOrEqualTo(ex_get_int, Integer.parseInt(v)));
						break;
					case DATE:
						System.out.println("get date");
						Path<Date> ex_get_dat = root.get(nameAndType[0]);
						predicateList.add(cb.greaterThanOrEqualTo(ex_get_dat, DateUtil.parseSimpleDateTime(v)));
						break;
					default:
						break;
					}
					break;
				case LT:
					switch(SqlExpression.DataType.valueOf(nameAndType[2].toUpperCase())){
					case STRING:
						Path<String> ex_lt_str = root.get(nameAndType[0]);
						predicateList.add(cb.lessThan(ex_lt_str, v));
						break;
					case INT:
						Path<Integer> ex_lt_int = root.get(nameAndType[0]);
						predicateList.add(cb.lessThan(ex_lt_int, Integer.parseInt(v)));
						break;
					case DATE:
						Path<Date> ex_lt_dat = root.get(nameAndType[0]);
						predicateList.add(cb.lessThan(ex_lt_dat, DateUtil.parseSimpleDateTime(v)));
						break;
					default:
						break;
					}
					break;
				case LTE:
					switch(SqlExpression.DataType.valueOf(nameAndType[2].toUpperCase())){
					case STRING:
						Path<String> ex_let_str = root.get(nameAndType[0]);
						predicateList.add(cb.lessThanOrEqualTo(ex_let_str, v));
						break;
					case INT:
						Path<Integer> ex_let_int = root.get(nameAndType[0]);
						predicateList.add(cb.lessThanOrEqualTo(ex_let_int, Integer.parseInt(v)));
						break;
					case DATE:
						Path<Date> ex_let_dat = root.get(nameAndType[0]);
						predicateList.add(cb.lessThanOrEqualTo(ex_let_dat, DateUtil.parseSimpleDateTime(v)));
						break;
					default:
						break;
					}
					break;
				case LIKE:
					switch(SqlExpression.DataType.valueOf(nameAndType[2].toUpperCase())){
					case STRING:
						Path<String> ex_like_str = root.get(nameAndType[0]);
						predicateList.add(cb.like(cb.lower(ex_like_str), "%" + v.toLowerCase() + "%"));
						break;
					default:
						break;
					}
					break;
				default:
					break;
				}
			}
		} 
		return predicateList;
	}

	
	
}
