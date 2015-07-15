package cn.unis.service.interfaces;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import cn.unis.entity.Picture;
import cn.unis.service.impl.PicturePageable;

/**
 * 图片管理接口
 * @author fanzz
 *
 */
public interface IPictureService {

	 static String TMP = File.separator + "upload" + File.separator + "picture" + File.separator + "tmp" + File.separator;
	 static String ORIGINAL = File.separator + "upload" + File.separator + "picture" + File.separator + "original" + File.separator;
	 static String PREVIEW = File.separator + "upload" + File.separator + "picture" + File.separator + "preview" + File.separator;
	 static String PREVIEWLARGE = File.separator + "upload" + File.separator + "picture" + File.separator + "previewlarge" + File.separator;
	 static int ENABEL_STATUS = 1;

	 public void save(Picture picture);

	public boolean save(Picture entity,String rootPath);

	public boolean changeStatus(Long id,Integer status);

	public void delete(String ids,String rootPath);

	public Picture findById(Long id);
	/*根据 图片类型，图片名称，状态，图片id*/
	public Page<Picture> findAll(final String pictrueType,final String pictrueName,final Integer  queryStatus,final Long id ,Pageable pageable);
	/*根据 图片类型，图片名称，状态，图片id，图片编号排序*/
	public PicturePageable findAll(final String pictrueType,final String pictrueName, final Integer queryStatus, final Long id, String numStr,Pageable pageable);
	/*根据 图片类型，图片名称，状态*/
	public Page<Picture> findAll(final String pictrueType,final String pictrueName,final Integer  queryStatus,Pageable pageable);

	public String savePicture(MultipartFile file,String rootPath);

	public List<Picture> getAllWithEnabledStatus();

	public List<Picture> findByIds(List<Long> ids);
}
