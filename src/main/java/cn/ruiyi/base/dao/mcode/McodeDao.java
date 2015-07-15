package cn.ruiyi.base.dao.mcode;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.ruiyi.base.entity.Mcode;
import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;

/**
 * 码表的管理实现，dao层
 * @author chen
 * @version 1.0, 2012-11-13
 */
public interface McodeDao extends BaseManagerDao<Mcode>{
	
	/**
	 * 根据码类型查找
	 */
	@Query(" from Mcode m where m.mtype=?1 and m.datelevel=1 order by m.orderNum")
	List<Mcode> findByMtype(String mtype);
	
	/**
	 * 根据码类型和码名查找
	 */
	@Query(" from Mcode m where m.mtype=?1 and m.mkey=?2 and m.datelevel=1 order by m.orderNum")
	List<Mcode> findByMtypeAndMkey(String mtype,String mkey);
	
	/**
	 * 根据码类型和码值得到一条码记录
	 */
	@Query(" from Mcode m where m.mtype=?1 and m.mvalue=?2 and m.datelevel=1 order by m.orderNum")
	Mcode findByMtypeAndMvalue(String mtype,String mvalue);
	
	
}
