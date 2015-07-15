package cn.unis.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.entity.Mcode;
import cn.ruiyi.base.service.mcode.McodeService;
import cn.ruiyi.base.web.mvc.Paginate;
import cn.ruiyi.base.web.mvc.StringView;
import cn.unis.entity.Picture;
import cn.unis.service.impl.PicturePageable;
import cn.unis.service.interfaces.IPictureService;
import cn.unis.utils.JsonUtils;
import cn.unis.utils.PictureUploader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 图片管理
 * @author fanzz
 *
 */
@Controller
@RequestMapping(value = "/backEnd/picture/")
public class PictureController {
	/**
	 * 单个编辑时缺省图片URL
	 */
	public final static String DEFAULT_URL = "/upload/ppt/default/default.png";
	public final static String DEFAULT_PREVIREW_URL = "/upload/ppt/default/default.png";
	public final static String OTHER_PICTURE_TYPE = "99";
	public final static int ENABLE = 1;

	@Autowired
	private IPictureService iPictureService;
	@Autowired
	private McodeService mcodeService;

	@RequiresPermissions("picture:view")
	@RequestMapping(value = "index")
	public String index(Model model) {
		model.addAttribute("type",mcodeService.getMbHtmlSelect("PICTURE_TYPE", ""));
		return "unis/backEnd/picture/index";
	}

	@RequiresPermissions("picture:view")
	@RequestMapping(value = "query")
	public String list(HttpServletRequest request,
			@RequestParam(value = "queryName") String queryName,
			@RequestParam(value = "queryType") String queryType,
			@RequestParam(value = "queryStatus") Integer queryStatus,
			@RequestParam(value = "page") Integer page, Model model) {
		Pageable pageable = new PageRequest(page - 1, Constants.PAGE_SIZE, Sort.Direction.DESC, "id");
		Page<Picture> paginate = iPictureService.findAll(queryType,queryName,queryStatus,pageable);
		setPictureType(paginate);
		String roll = Paginate.getPage(request, page, paginate, "base.roll","list");
		model.addAttribute("page", paginate.getContent());
		model.addAttribute("pageSize", paginate.getContent().size());
		model.addAttribute("roll", roll);
		return "unis/backEnd/picture/list";
	}

	@RequiresPermissions("picture:edit")
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		Picture picture;
		if(id==null||id==0){//新增的时候，返回default图片路径
			picture = new Picture();
			picture.setId(0L);
			picture.setUrl(DEFAULT_URL);
			picture.setPreviewUrl(DEFAULT_PREVIREW_URL);
		}else{
			picture = iPictureService.findById(id);
		}
		model.addAttribute("picture", picture);
		model.addAttribute("type",mcodeService.getMbHtmlSelect("PICTURE_TYPE", picture.getType()));
		return "unis/backEnd/picture/edit";
	}

	@RequiresPermissions("picture:view")
	@RequestMapping(value = "/multiupload")
	public String multiupload(Model model,@RequestParam(value="type",required=false)String type){
		SecurityUtils.getSubject().getSession().setAttribute("MULTI_UPLOAD_PICTURE_TYPE", type);
		return "unis/backEnd/picture/multiupload";
	}



	@RequiresPermissions("picture:edit")
	@RequestMapping(value = "/save")
	@ResponseBody
	public ModelAndView save(HttpServletRequest request, Picture picture) {
		StringView view = new StringView();
		String rootPath = getRootPath(request);
		boolean flag = iPictureService.save(picture,rootPath);
		view.setContent( flag ? "success" : "error");
		return new ModelAndView(view);
	}

	@RequestMapping(value = "/multiupload/save")
	@ResponseBody
	public String multiupload(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request){
		String contextPath = PictureUploader.getContextPath(request);
		boolean flag = PictureUploader.isPictureStream(file);
		String resp = "";
		if(flag){
			String url = iPictureService.savePicture(file, contextPath + IPictureService.ORIGINAL);
			String previewUrl = PictureUploader.createPreview(contextPath + IPictureService.ORIGINAL, url, contextPath + IPictureService.PREVIEW, 80, 80);
			String previewLargeUrl = PictureUploader.createPreview(contextPath + IPictureService.ORIGINAL, url, contextPath + IPictureService.PREVIEWLARGE, 730, PictureUploader.calPicWidth(file, 730));
			String fileName = file.getOriginalFilename();
			if(fileName.toLowerCase().endsWith("png") || fileName.toLowerCase().endsWith("jpeg") || fileName.toLowerCase().endsWith("jpg") ||  fileName.toLowerCase().endsWith("gif")){
				Picture picture = new Picture();
				picture.setName(fileName);
				picture.setStatus(ENABLE);
				String pictureType = (String) SecurityUtils.getSubject().getSession().getAttribute("MULTI_UPLOAD_PICTURE_TYPE");
				if(pictureType == null){
					pictureType = OTHER_PICTURE_TYPE;
				}
				picture.setType(pictureType);
				picture.setUrl("/upload/picture/original/" + url);
				picture.setPreviewLargeUrl("/upload/picture/previewlarge/" + previewLargeUrl);
				picture.setPreviewUrl("/upload/picture/preview/" + previewUrl);
				iPictureService.save(picture);
				resp = "{\"jsonrpc\":\"2.0\",\"result\":null,\"id\":\"id\"}";
			}else{
				resp = "{\"jsonrpc\":\"2.0\",\"result\":\"not a picture\",\"id\":\"id\"}";
			}

		}else{
			resp = "{\"jsonrpc\":\"2.0\",\"result\":\"something error\",\"id\":\"id\"}";
		}

		return resp;
	}

	@RequiresPermissions("picture:edit")
	@RequestMapping(value = "/uploadPicture")
	public String uploadPicture(HttpServletRequest request,
			@RequestParam(value = "qqfile", required = false) MultipartFile file ,@RequestParam(value = "file", required = false) MultipartFile file2 ,Model model) {
		String contextPath = PictureUploader.getContextPath(request);
		boolean flag = PictureUploader.isPictureStream(file);
		Map<String,String> map = Maps.newHashMap();
		if(flag){
			String url = iPictureService.savePicture(file, contextPath + IPictureService.TMP);
			map.put("status", "success");
			map.put("url", IPictureService.TMP + url);
			String previewUrl = PictureUploader.createPreview(contextPath + IPictureService.TMP, url, contextPath + IPictureService.TMP, 80, 80);
			map.put("previewUrl", IPictureService.TMP + previewUrl);
			String previewLargeUrl = PictureUploader.createPreview(contextPath + IPictureService.TMP, url, contextPath + IPictureService.TMP, 730, PictureUploader.calPicWidth(file, 730));
			map.put("previewLargeUrl", IPictureService.TMP +previewLargeUrl);
		}else{
			map.put("status", "error");
		}
		model.addAttribute("success_json",JsonUtils.toJson(map));
		return "unis/backEnd/upload/upload_success";
	}

	@RequiresPermissions("picture:view")
	@RequestMapping(value = "/pickPicture")
	@ResponseBody
	public String pickPicture(HttpServletRequest request,
			@RequestParam(value = "sortByIds",required=false)String sortByIds,
			@RequestParam(value = "queryId",required=false) String queryId,
			@RequestParam(value = "queryName",required=false) String queryName,
			@RequestParam(value = "queryType",required=false) String queryType,
			@RequestParam(value = "queryStatus",required=false) Integer queryStatus,
			@RequestParam(value = "page",required=false) Integer page){

		int pageSize = 5;
		Long queryid = null;
		try {
			queryid = Long.parseLong(queryId);
		} catch (Exception e) {
		}

		Pageable pageable = new PageRequest(page - 1, pageSize, Sort.Direction.ASC, "id");
		PicturePageable pp = iPictureService.findAll(queryType, queryName, queryStatus, queryid, "'," + getReverseString(sortByIds) + ",'", pageable);
		int size = Integer.parseInt(pp.getSize().toString());
//		int maxPageNum = (int)(size % pageSize == 0 ?  size / pageSize : size / pageSize + 1);
		int maxPageNum = (size + pageSize - 1) / pageSize;
		Map<String,Object>  data = Maps.newHashMap();
		data.put("maxPageNum", maxPageNum);
		data.put("pageContent", pp.getPictureList());
		return JsonUtils.toJson(data);
	}
	/**
	 * @param ids
	 *            (格式："1,2,3")
	 * @return
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(HttpServletRequest request, String ids) {
		StringView view = new StringView();
		String rootPath = getRootPath(request);
		boolean flag = true;
		try {
			iPictureService.delete(ids,rootPath);
		} catch (Exception e) {
			flag = false;
			System.out.println("=================================>图片删除失败");
		}

		view.setContent(flag ? "success" : "error");
		return new ModelAndView(view);
	}

	/**
	 * 启用 / 禁用
	 */
	@RequiresPermissions("picture:edit")
	@RequestMapping(value = "/setup")
	public ModelAndView setup(Long id, Integer status) {
		StringView view = new StringView();
		view.setContent(iPictureService.changeStatus(id, status) ? "success" : "出现未知异常，操作失败");
		return new ModelAndView(view);
	}

	private String getRootPath(HttpServletRequest request){
		return  request.getSession().getServletContext().getRealPath("/");
	}

	private void setPictureType(Page<Picture> paginate){
		for (Picture picture : paginate) {
			Mcode mcode = mcodeService.findByMtypeAndMvalue("PICTURE_TYPE",picture.getType());
			if(mcode!=null){
				picture.setType(mcode.getMkey());
			}
		}
	}

	/**
	 * 倒转字符窜，并且去除一些非数字
	 * @param originalString
	 * @return 1，2，3，4，5，6， ===> 6，5，4，3，2，1
	 */
	private String getReverseString(String originalString){
		List<Long> ids = Lists.newArrayList();
		if(StringUtils.isNotBlank(originalString)){
			originalString = originalString.trim();
			String[] idsStrs = originalString.split(",|，");
			if(idsStrs!=null){
				for(String str :idsStrs){
					try {
						ids.add(Long.parseLong(str));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		String str = "";
		for(int i=ids.size() -1 ; i >-1 ;i--){
			str = str + (i!=0 ? ids.get(i)  + ",": ids.get(i));
		}
		return str;
	}

	public static void main(String[] args){
		System.out.println(new StringBuffer("1，2，3，4，5，6").reverse().toString());
	}
}
