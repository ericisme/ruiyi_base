package cn.unis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.service.account.AccountService;
import cn.unis.dao.GameDao;
import cn.unis.dao.PictureDao;
import cn.unis.dao.PlatformAndMarketDao;
import cn.unis.dao.TempUrlDao;
import cn.unis.dao.testMybatisDao;
import cn.unis.entity.Game;
import cn.unis.entity.Picture;
import cn.unis.entity.PlatformAndMarket;
import cn.unis.service.interfaces.IGameService;
import cn.unis.utils.FileUtils;
import cn.unis.utils.ListUtils;
import cn.unis.utils.PictureUploader;
import cn.unis.utils.StringUtils2;

import com.google.common.collect.Lists;

/**
 * 游戏管理接口实现
 *
 * @author fanzz
 *
 */
@Component
 @Transactional(readOnly = true)
public class GameService implements IGameService {
	@Autowired
	private GameDao gameDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private TempUrlDao tempUrlDao;
	@Autowired
	private PlatformAndMarketDao platformAndMarketDao;
	@Autowired
	private PictureDao pictureDao;
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private testMybatisDao testMybatisDao;


	@Transactional(readOnly = false)
	public boolean save(Game game,String contextPath) {
		boolean flag = true;
		if (game != null) {
			Long id = game.getId();
			List<Long> ids = game.getPictureNumberList();
			List<Picture> pictureList = null;
			if(ids!=null && ids.size() > 0){
				pictureList= (List<Picture>)pictureDao.findAll(game.getPictureNumberList());
			}
			game.setPictureList(pictureList);//设置图片
			if (id == null || id == 0) { //新增游戏
				movePicture(contextPath,game);
			} else { //编辑游戏
				Game oldGame = findById(id);
				if (oldGame != null && !oldGame.getIconPath().equals(game.getIconPath())) {
					PictureUploader.delete(contextPath + oldGame.getIconPath());
					movePicture(contextPath, game);
				}
			}
			game.setIconPath(FileUtils.replace(game.getIconPath()));
			gameDao.save(game); //部分platformAndMarket记录的外键丢失

		} else {
			flag = false;
		}
		return flag;
	}

	private void movePicture(String contextPath,Game game){
		String tmp = contextPath + game.getIconPath();
		String picName = PictureUploader.getPicName(game.getIconPath());
		String original = contextPath + ORIGINAL + picName;
		if(PictureUploader.move(tmp, original)){
			game.setIconPath(ORIGINAL + picName);
		}
	}

	@Transactional(readOnly = false)
	private List<Long> deletePlatformAndMarket(Game oldGame, Game newGame) {
		List<Long> oldIds = Lists.newArrayList();
		List<Long> newIds = Lists.newArrayList();
		for (PlatformAndMarket platformAndMarket : oldGame
				.getPlatformAndMarkets()) {
			oldIds.add(platformAndMarket.getId());
		}
		for (PlatformAndMarket platformAndMarket : newGame
				.getPlatformAndMarkets()) {
			newIds.add(platformAndMarket.getId());
		}
		ListUtils<Long> compare = new ListUtils<Long>();
		List<Long> diff = compare.diff(oldIds, newIds);
		return diff;
	}

	/**
	 * 启用or停用
	 *
	 * @param id
	 * @param status
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean changeStatus(Long id, Integer status) {
		boolean flag = true;
		Game game = findById(id);
		if (game == null) {
			flag = false;
		} else {
			game.setStatus(status);
			gameDao.save(game);
		}
		return flag;
	}

	@Transactional(readOnly = false)
	public void delete(Long id,String contextPath) {
		Game game = findById(id);
		if (game != null) {
			PictureUploader.delete(contextPath + game.getIconPath());
		}
		gameDao.delete(id);
	}

	@Transactional(readOnly = false)
	public void delete(String ids , String rootPath) {
		List<Long> idList = StringUtils2.getTFromString(ids, ",|，", Long.class, false);
		for(Long id : idList ){
			delete(id, rootPath);
		}
	}

	@Transactional(readOnly = true)
	public Game findById(Long id) {
		return gameDao.findOne(id);
	}

	/**
	 * 列表查找
	 */
	@Transactional(readOnly = false)
	public Page<Game> findAll(final String rootPath , final Integer queryStatus,
			final String queryType, final String queryName,
			final Integer queryIsNew, final Integer queryIsHot,
			Pageable pageable) {

		return this.gameDao.findAll(new Specification<Game>() {
			@Override
			public Predicate toPredicate(Root<Game> game,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(queryName)) {
					Path<String> expression1 = game.get("nameForCH");
					Predicate p1 = cb.like(cb.upper(expression1), "%" + queryName.toUpperCase() + "%");
					Path<String> expression2 = game.get("nameForEN");
					Predicate p2 = cb.like(cb.upper(expression2), "%" + queryName.toUpperCase() + "%");
					predicateList.add(cb.or(p1,p2));
					
				}
				if (queryIsNew != null && queryIsNew != -1) {
					Path<Integer> expression = game.get("isNew");
					Predicate p = cb.equal(expression, queryIsNew);
					predicateList.add(p);
				}
				if (queryIsHot != null && queryIsHot != -1) {
					Path<Integer> expression = game.get("isHot");
					Predicate p = cb.equal(expression, queryIsHot);
					predicateList.add(p);
				}

				if (StringUtils.isNotBlank(queryType)
						&& !queryType.equals("all")) {
					Path<String> expression = game.get("type");
					Predicate p = cb.equal(expression, queryType);
					predicateList.add(p);
				}
				if (queryStatus != null && queryStatus != -1) {
					Path<Integer> expression = game.get("status");
					Predicate p = cb.equal(expression, queryStatus);
					predicateList.add(p);
				}

				Predicate[] predicates = new Predicate[predicateList.size()];
				predicateList.toArray(predicates);

				if (predicates.length > 0) {
					return cb.and(predicates);
				} else {
					return cb.conjunction();
				}
			}
		}, pageable);
	}


	@Override
	public List<Game> getAllWithEnabledStatus() {
		return gameDao.findByStatusOrderBySortNumberAsc(ENABEL_STATUS);
	}

	@Override
	public Page<Game> findAll(final Integer queryIsNew, final Integer queryIsHot,
			final String queryType, Pageable pageable) {
		return this.gameDao.findAll(new Specification<Game>() {
			@Override
			public Predicate toPredicate(Root<Game> game,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();

				if (queryIsNew != null && queryIsNew != -1) {
					Path<Integer> expression = game.get("isNew");
					Predicate p = cb.equal(expression, queryIsNew);
					predicateList.add(p);
				}
				if (queryIsHot != null && queryIsHot != -1) {
					Path<Integer> expression = game.get("isHot");
					Predicate p = cb.equal(expression, queryIsHot);
					predicateList.add(p);
				}

				if (StringUtils.isNotBlank(queryType) && !queryType.equals("-1")) {
					Path<String> expression = game.get("type");
					Predicate p = cb.equal(expression, queryType);
					predicateList.add(p);
				}

				Path<Integer> expression = game.get("status");
				Predicate p = cb.equal(expression, 1);
				predicateList.add(p);

				Predicate[] predicates = new Predicate[predicateList.size()];
				predicateList.toArray(predicates);

				if (predicates.length > 0) {
					return cb.and(predicates);
				} else {
					return cb.conjunction();
				}
			}
		}, pageable);
	}
	
	/**
	 * 按条件 分页 查询 
	 * @param queryType null || "-1" 不作为筛选条件
	 * @param queryName null 不作为筛选条件
	 * @param isHot 是否热门游戏; 非必填，1为是，0为不是
	 * @param pageNum 第几页, 不填写默认第1页
	 * @param pageable
	 * @return
	 */
	public Page<Game> findAll(
						final String queryType,
						final String queryName,
						final Integer isNew,
						final Integer isHot,	
						final Integer isLinked,
						final Pageable pageable
						) {
		return this.gameDao.findAll(new Specification<Game>() {
			@Override
			public Predicate toPredicate(Root<Game> game,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				
				//游戏类型
				if (StringUtils.isNotBlank(queryType) && !queryType.equals("-1")) {
					Path<String> expression = game.get("type");
					Predicate p = cb.equal(expression, queryType);
					predicateList.add(p);
				}
				//游戏名称
				if(StringUtils.isNotBlank(queryName)){
					Path<String> expression1 = game.get("nameForCH");
					Path<String> expression2 = game.get("nameForEN");
					Predicate p1 = cb.like(expression1, "%" + queryName + "%") ;
					Predicate p2 = cb.like(expression2, "%" + queryName + "%");
					Predicate p3[] = new Predicate[]{p1,p2};
					Predicate p4 = cb.or(p3);
					predicateList.add(p4);
				}
				//是否新游戏
				if(isNew!=null){
					Path<Integer> expression = game.get("isNew");
					Predicate p = cb.equal(expression, isNew);
					predicateList.add(p);					
				}
				//是否热热门游戏
				if(isHot!=null){
					Path<Integer> expression = game.get("isHot");
					Predicate p = cb.equal(expression, isHot);
					predicateList.add(p);		
				}
				//是否联动游戏
				if(isLinked!=null){
					Path<Integer> expression = game.get("isLinked");
					Predicate p = cb.equal(expression, isLinked);
					predicateList.add(p);		
				}
				//状态，固定为启用
				Path<Integer> expression = game.get("status");
				Predicate p = cb.equal(expression, 1);
				predicateList.add(p);

				Predicate[] predicates = new Predicate[predicateList.size()];
				predicateList.toArray(predicates);

				if (predicates.length > 0) {
					return cb.and(predicates);
				} else {
					return cb.conjunction();
				}
			}
		},pageable);
	}
	
	public List<Game> getIndexGameList(int limit,String order){
		Pageable pageable = new PageRequest(0, limit,Sort.Direction.DESC, order);
		return gameDao.findByStatus(ENABEL_STATUS, pageable).getContent();
	}

	@Override
	public String savePicture(MultipartFile file, String directory) {
		 String  url = PictureUploader.save(file,directory);
		return url;
	}
	/**
	 * 游戏统计下载量
	 * @param platformAndMarket_id
	 */
	public PlatformAndMarket countGameDownloadTimes(Long platformAndMarket_id){
		final PlatformAndMarket platFormAndMarket = platformAndMarketDao.findOne(platformAndMarket_id);
		if(platFormAndMarket!=null){
			Game game = this.gameDao.findOne(new Specification<Game>() {
				@Override
				public Predicate toPredicate(Root<Game> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					Path<List<PlatformAndMarket>> expression = root.get("platformAndMarkets");
					return cb.isMember(platFormAndMarket, expression);
				}
			});
			//platFormAndMarket downloadTimes +1;
			String hql1 = "update PlatformAndMarket pam set pam.downloadTimes=(pam.downloadTimes+1) where pam.id=:platFormAndMarket_id ";
			Query query1 = entityManager.createQuery(hql1);
			query1.setParameter("platFormAndMarket_id", platFormAndMarket.getId());
			query1.executeUpdate();
			//game downloadTimes +1;
			String hql2 = "update Game g set g.downloadTimes=(g.downloadTimes+1) where g.id=:game_id";
			Query query2 = entityManager.createQuery(hql2);
			query2.setParameter("game_id", game.getId());
			query2.executeUpdate();
			return platFormAndMarket;
		}else{
			return null;
		}
	}
	/**
	 * 游戏统计点击量(人气)
	 * @param platformAndMarket_id
	 */
	public void countGamePopularity(Long game_id){
		String hql = "update Game g set g.popularity=(g.popularity+1) where g.id=:game_id";
		Query query = entityManager.createQuery(hql);
		query.setParameter("game_id", game_id);
		query.executeUpdate();
	}
}
