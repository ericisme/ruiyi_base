package cn.unis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.unis.utils.MyBatisRepository;

@MyBatisRepository
public interface testMybatisDao {
	public Long testGet(@Param("p1") int sortNumber,@Param("p2") Long id);
	public List<Map<String,String>> getAll();
	public Integer testMinus(@Param("delta") int delta);
	public Integer testAdd(@Param("delta") int delta);
	
	public List<Map<String,Object>> testSqlInject(@Param("s") String s);
}
