package cn.unis.dao;

import org.apache.ibatis.annotations.Param;

import cn.unis.utils.MyBatisRepository;

/**
 * 新闻myBatisDao
 * @author eric
 *
 */
@MyBatisRepository
public interface NewsMybatisDao {
	
	/**
	 * 上一条新闻
	 * @param sortNumber
	 * @param id
	 * @return
	 */
	public Long findPrewNewsId(@Param("p1") int sortNumber,@Param("p2") Long id);
	
	
	/**
	 * 下一条新闻
	 * @param sortNumber
	 * @param id
	 * @return
	 */
	public Long findNextNewsId(@Param("p1") int sortNumber,@Param("p2") Long id);
}
