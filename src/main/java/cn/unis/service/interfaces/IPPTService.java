package cn.unis.service.interfaces;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import cn.unis.entity.PPT;

/**
 * 幻灯片管理接口
 * @author fanzz
 *
 */
public interface IPPTService {

    static String TMP = File.separator + "upload" + File.separator + "ppt" + File.separator + "tmp" + File.separator;
	static String ORIGINAL = File.separator + "upload" + File.separator + "ppt" + File.separator + "original" + File.separator;
	static String PREVIEW = File.separator + "upload" + File.separator + "ppt" + File.separator + "preview" + File.separator;
	static int ENABEL_STATUS = 1;
	/**
	 * 保存或更新
	 * @param ppt
	 * @return
	 */
	public boolean save(PPT ppt,String rootPath);
	/**
	 * 改变状态
	 * @param id
	 * @param status
	 * @return
	 */
	public boolean changeStatus(Long id,Integer status);
	/**
	 * 删除
	 * @param ids
	 */
	public void delete(String ids,String rootPath);
	/**
	 * 根据id查找记录
	 * @param id
	 * @return
	 */
	public PPT findById(Long id);
	/**
	 * 分页查询
	 * @param pictrueName 名字
	 * @param queryStatus 状态
	 * @param pageable
	 * @return
	 */
	public Page<PPT> findAll(final String pictrueName,final Integer  queryStatus ,Pageable pageable,String rootPath);
	/**
	 * 获取所有启用状态的ppt
	 * @return
	 */
	public List<PPT> getAllPPTWithEnabledStatus();

	public String savePicture(MultipartFile file, String directory);
}
