package cn.unis.wwc.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.service.mcode.McodeService;
import cn.ruiyi.base.service.mcode.McodeUtil;
import cn.ruiyi.base.util.FileManager;
import cn.ruiyi.base.util.PathUtil;
import cn.ruiyi.base.web.mvc.JsView;
import cn.ruiyi.base.web.mvc.Paginate;
import cn.ruiyi.base.web.mvc.StringView;
import cn.ruiyi.base.simplemvc.controller.BaseManagerController;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;
import cn.unis.utils.JsonUtils;
import cn.unis.wwc.entity.InvitationNews;
import cn.unis.wwc.service.InvitationNewsService;

import com.google.common.collect.Maps;


/**
 * 大会新闻管理 controller
 * @author eric
 *
 */
@Controller
@RequestMapping(value = "/backEnd/wwc/invitationNews")
public class InvitationNewsController extends BaseManagerController<InvitationNews>{

	@Autowired
	private InvitationNewsService invitationNewsService;
	@Autowired
	private McodeService mcodeService;
	@Override
	protected BaseManagerService<InvitationNews> getDomainService() {
		return invitationNewsService;
	}
	@PostConstruct//此注解功能是，让spring加载bean之后会调用一次的方法。
	public void controllerInit(){
		indexPage  = "unis/backEnd/wwc/invitationNews/index";
		listPage   = "unis/backEnd/wwc/invitationNews/list";
		editPage   = "unis/backEnd/wwc/invitationNews/edit";
		showPage   = "unis/backEnd/wwc/invitationNews/show";

		indexPermission   = "";
		queryPermission   = "";
		editPermission    = "invitationNews:edit";
		savePermission    = "invitationNews:edit";
		deletePermission  = "invitationNews:delete";
	}

	/**
	 * index
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		System.out.println("_tage_select_html");
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals(indexPermission) || SecurityUtils.getSubject().isPermitted(indexPermission))){
			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("_tage_select_html", mcodeService.getMbHtmlSelect(McodeUtil.INVITATION_NEWS_TYPE_CHINESE, null));
		return  new ModelAndView(indexPage, map);
	}

	/**
	 * edit
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable Long id){
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals(editPermission) || SecurityUtils.getSubject().isPermitted(editPermission))){
			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
		}
		Map<String,Object> map = new HashMap<String,Object>();
		InvitationNews doamin = getDomainService().findById(id);
		map.put("entity", doamin);				//编辑新闻，当为新增时，此变量为空
		map.put("key",id.equals(0L)); 			//区分用户的修改和新增(key为true时：新增；key为false时：修改。）
		map.put("tage_select_html", mcodeService.getMbHtmlSelect(McodeUtil.INVITATION_NEWS_TYPE_CHINESE, doamin==null ? null : doamin.getTag()));
		return new ModelAndView(editPage, map);
	}

	/**
	 * page query
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "query")
	public ModelAndView query(HttpServletRequest request) {
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals(queryPermission) || SecurityUtils.getSubject().isPermitted(queryPermission))){
			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
		}
		Map<String,Object> map = new HashMap<String,Object>();
		Page<InvitationNews> paginate = getDomainService().pageParamQuery(request);
		for(InvitationNews invitationNews : paginate){
			invitationNews.setTagChinese(mcodeService.findByMtypeAndMvalue(McodeUtil.INVITATION_NEWS_TYPE_CHINESE, invitationNews.getTag()).getMkey());
		}
		int page = Integer.parseInt(request.getParameter("page"));
		String roll = Paginate.getPage(request, page, paginate, "base.roll","list");
		map.put("page", paginate.getContent());
		map.put("pageSize", paginate.getContent().size());
		map.put("roll", roll);
		return new ModelAndView(listPage, map);
	}

	/**
	 * 标题图标上传动作
	 *
	 * @param num
	 * @return
	 */
	@RequestMapping(value = "/uploadTileImg")
	public String upload(HttpServletRequest request, @RequestParam(value = "qqfile", required = false) MultipartFile file,Model model){
		Map<String, String> map = Maps.newHashMap();
        //判断文件后缀名是否为图片格式
        String partRightType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).toLowerCase();
        if( !( 		".jpg".equals(partRightType)
        		||  ".gif".equals(partRightType)
        		||  ".jpeg".equals(partRightType)
        		||  ".png".equals(partRightType)
        		||  ".bmp".equals(partRightType))){
			map.put("filePath", "faild");
			model.addAttribute("success_json",JsonUtils.toJson(map));
			return "unis/backEnd/upload/upload_success";
        }

		String uploadDir = request.getSession().getServletContext().getRealPath("/") + Constants.InvitationNewsTitleImgPath ;

		try{
			File dirPath = new File(uploadDir);
			if (!dirPath.exists()) {
				FileUtils.forceMkdir(dirPath);
				//dirPath.mkdirs();
			}
			String randomFileName = PathUtil.createRandomFileName(file.getOriginalFilename());//生成的随机文件名
			String uploadFilePath = uploadDir + File.separator + randomFileName;
			File uploadedFile = new File(uploadFilePath);
			FileCopyUtils.copy(file.getBytes(), uploadedFile);
			map.put("filePath", Constants.InvitationNewsTitleImgPath + "/" + randomFileName);
			model.addAttribute("success_json",JsonUtils.toJson(map));
		}catch(Exception ex){
			ex.printStackTrace();
			map.put("filePath", "faild");
		}
		model.addAttribute("success_json",JsonUtils.toJson(map));
		return "unis/backEnd/upload/upload_success";
	}

	/**
	 * 文章内容图片上传动作
	 */
	@RequiresPermissions("invitationNews:edit")
	@RequestMapping(value = "/uploadImg")
	public ModelAndView uploadImg(HttpServletRequest request, String uploadFileName,String CKEditorFuncNum ){
		JsView view = new JsView();
       MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
       String fileDir = request.getSession().getServletContext().getRealPath("/") + Constants.InvitationNewsImgPath ;
       String randomFileName = "";
       Map<String,MultipartFile> files = multipartRequest.getFileMap();
       Iterator<String> fileNames = multipartRequest.getFileNames();
       boolean flag = false;
       for (; fileNames.hasNext();) {
           String filename = (String) fileNames.next();
           CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename);
           byte[] bytes = file.getBytes();
           if (bytes.length != 0) {
               if (!flag) {
                   File dirPath = new File(fileDir);
                   if (!dirPath.exists()) {
                       flag = dirPath.mkdirs();
                   }
               }
               //判断文件后缀名是否为图片格式
               String partRightType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).toLowerCase();
               if( !( 		".jpg".equals(partRightType)
               		||  ".gif".equals(partRightType)
               		||  ".jpeg".equals(partRightType)
               		||  ".png".equals(partRightType))){
               	String alt_msg = "Sorry! Image format selection is incorrect, please choose GIF, jpeg, PNG format JPG, picture!";
               	view.setContent("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ", '" + "/none" + "' , '" + alt_msg + "');</script>");
               	return new ModelAndView(view);
               }
               //生成的随机文件名
               randomFileName = PathUtil.createRandomFileName(file.getOriginalFilename());
               try{
					FileManager.saveFileInputStream(file.getInputStream(), randomFileName, fileDir);
				}catch (IOException e){
					e.printStackTrace();
				}
           }
       }
		view.setContent("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ", '" + Constants.InvitationNewsImgPath+"/"+randomFileName + "' , '" + "upload success" + "');</script>");
		return new ModelAndView(view);
	}

	/**
	 * 启用  / 禁用
	 */
	@RequiresPermissions("invitationNews:setup")
	@RequestMapping(value = "/setup")
	public ModelAndView setup(Long id,Integer status){
		StringView view = new StringView();
		try{
			InvitationNews invitationNews = getDomainService().findById(id);
			invitationNews.setStatus(status);
			getDomainService().save(invitationNews);
			view.setContent("success");
		}catch(Exception ex){
			view.setContent("出现未知异常，操作失败");
			ex.printStackTrace();
		}
		return new ModelAndView(view);
	}

	/**
	 * 排序号加1
	 */
	@RequiresPermissions("invitationNews:UpDown")
	@RequestMapping(value = "/up")
	public ModelAndView up(Long id){
		StringView view = new StringView();
		try{
			InvitationNews invitationNews = getDomainService().findById(id);
			invitationNews.setSortNumber(invitationNews.getSortNumber()+1);
			getDomainService().save(invitationNews);
			view.setContent("success");
		}catch(Exception ex){
			view.setContent("出现未知异常，操作失败");
			ex.printStackTrace();
		}
		return new ModelAndView(view);
	}
	/**
	 * 排序号减1
	 */
	@RequiresPermissions("invitationNews:UpDown")
	@RequestMapping(value = "/down")
	public ModelAndView down(Long id){
		StringView view = new StringView();
		try{
			InvitationNews invitationNews = getDomainService().findById(id);
			if(invitationNews.getSortNumber()>1){
				invitationNews.setSortNumber(invitationNews.getSortNumber()-1);
				getDomainService().save(invitationNews);
			}
			view.setContent("success");
		}catch(Exception ex){
			view.setContent("出现未知异常，操作失败");
			ex.printStackTrace();
		}
		return new ModelAndView(view);
	}





















}




