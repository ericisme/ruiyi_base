package cn.ruiyi.base.simplemvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * 管理后台的dao基类，负责单个域对象CRUD操作的基类，子类只需继承此类便拥有CRUD操作功能
 * @author eric
 *
 * @param <T> CRUD对应的entity类, 只支持Long类型id
 */
public interface BaseManagerDao<T> extends JpaRepository<T, Long>,JpaSpecificationExecutor<T> {	

	
}
