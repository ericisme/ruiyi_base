package cn.unis.service.interfaces;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import cn.unis.entity.ADS;
import cn.unis.entity.ADSCount;


public interface IADSService {

    static String TMP = File.separator + "upload" + File.separator + "ads" + File.separator + "tmp" + File.separator;
	static String ORIGINAL = File.separator + "upload" + File.separator + "ads" + File.separator + "original" + File.separator;
	static String PREVIEW = File.separator + "upload" + File.separator + "ads" + File.separator + "preview" + File.separator;
	static int ENABEL_STATUS = 1;

	public boolean save(ADS ads,String rootPath);

	public boolean changeStatus(Long id,Integer status);

	public void delete(String ids,String rootPath);

	public ADS findById(Long id);

	public Page<ADS> findAll(final String pictrueName,final Integer  queryStatus ,Pageable pageable,String rootPath);

	public List<ADS> getAllADSWithEnabledStatus();

	public String savePicture(MultipartFile file, String directory);

	public List<ADS> getTopItems(Pageable pageable);

	public void updateCount(int count);

	public int getTheCount();
}
