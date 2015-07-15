
package cn.unis.service.interfaces;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import cn.unis.entity.Game;
import cn.unis.entity.PlatformAndMarket;

/**
 * 游戏
 * @author fanzz
 *
 */
public interface IGameService {

	static String TMP = File.separator + "upload" + File.separator + "game" + File.separator + "tmp" + File.separator;
	static String ORIGINAL = File.separator + "upload" + File.separator + "game" + File.separator + "original" + File.separator;
	static int ENABEL_STATUS = 1;

	/**
	 * 保存或更新
	 * @param ppt
	 * @return
	 */
	public boolean save(Game game,String rootPath);
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
	public Game findById(Long id);
	/**
	 * 分页查询
	 * @param pictrueName 名字
	 * @param queryStatus 状态
	 * @param pageable
	 * @return
	 */
	public Page<Game> findAll(final String rootPath , final Integer queryStatus,final String queryType,final String queryName,final Integer  queryIsNew ,final Integer  queryIsHot,Pageable pageable);

	public Page<Game> findAll(final Integer queryIsNew,final Integer queryIsHot,final String queryType,Pageable pageable);

	public List<Game> getAllWithEnabledStatus();

	public List<Game> getIndexGameList(int limit,String order);

	public String savePicture(MultipartFile file, String directory);

	/**
	 * 游戏统计下载量
	 * @param platformAndMarket_id
	 */
	public PlatformAndMarket countGameDownloadTimes(Long platformAndMarket_id);
	/**
	 * 游戏统计点击量(人气)
	 * @param platformAndMarket_id
	 */
	public void countGamePopularity(Long game_id);

}
