package cn.unis.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import cn.ruiyi.base.web.mvc.Paginate;
import cn.ruiyi.base.web.mvc.StringView;
import cn.unis.entity.PPT;
import cn.unis.service.interfaces.IPPTService;
import cn.unis.utils.JsonUtils;
import cn.unis.utils.PictureUploader;

import com.google.common.collect.Maps;

/**
 * 幻灯片管理
 * @author fanzz
 *
 */
@Controller
@RequestMapping(value = "/backEnd/ppt/")
public class PPTController {
	@Autowired
	private IPPTService ipptService;

	@RequestMapping(value = "index")
	public String index(Model model) {
		return "unis/backEnd/ppt/index";
	}
	/**
	 * 分页查询
	 * @param request
	 * @param queryName 图片名字
	 * @param queryStatus 状态
	 * @param page
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ppt:query")
	@RequestMapping(value = "query")
	public String list(HttpServletRequest request,
			@RequestParam(value = "queryName") String queryName,
			@RequestParam(value = "queryStatus") Integer queryStatus,
			@RequestParam("page") Integer page, Model model) {
		String rootPath = getRootPath(request);
		Pageable pageable = new PageRequest(page - 1, Constants.PAGE_SIZE,
				Sort.Direction.ASC, "sortNumber");
		Page<PPT> paginate = ipptService.findAll(queryName,queryStatus, pageable,rootPath);

		String roll = Paginate.getPage(request, page, paginate, "base.roll",
				"list");
		model.addAttribute("page", paginate.getContent());
		model.addAttribute("pageSize", paginate.getContent().size());
		model.addAttribute("roll", roll);
		return "unis/backEnd/ppt/list";
	}
	/**
	 * 编辑
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ppt:edit")
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		PPT ppt;
		if(id==null||id==0){//新增的时候，返回default图片路径
			ppt = new PPT();
			ppt.setId(0L);
			ppt.setPreviewUrl("/upload/ppt/default/default.png");
			ppt.setUrl("/upload/ppt/default/default.png");
		}else{
			ppt = ipptService.findById(id);
		}

		model.addAttribute("ppt", ppt);
		return "unis/backEnd/ppt/edit";
	}

	@RequiresPermissions("ppt:edit")
	@RequestMapping(value = "/save")
	@ResponseBody
	public ModelAndView save(HttpServletRequest request, PPT ppt) {
		StringView view = new StringView();
		String rootPath = getRootPath(request);
		boolean flag = ipptService.save(ppt,rootPath);
		view.setContent(flag ?  "success" : "error");
		return new ModelAndView(view);
	}


	@RequiresPermissions("ppt:edit")
	@RequestMapping(value = "/uploadPicture2")
	public String uploadPicture2(Model model, HttpServletRequest request,
			@RequestParam(value = "qqfile", required = false) MultipartFile file) {
		String contextPath = PictureUploader.getContextPath(request);
		boolean isPicture = PictureUploader.isPictureStream(file);
		Map<String,String> map = Maps.newHashMap();
		if(isPicture){
			String url = ipptService.savePicture(file,contextPath + IPPTService.TMP);
			String previewUrl = PictureUploader.createPreview(contextPath + IPPTService.TMP, url, contextPath + IPPTService.TMP, 80, 80);
			if(url!=null && previewUrl!=null){
				map.put("status", "success");
				map.put("url", IPPTService.TMP + url);
				map.put("previewUrl",  IPPTService.TMP + previewUrl);
			}else{
				map.put("status", "error");
			}
		}else{
			map.put("status", "error");
		}
		model.addAttribute("success_json",JsonUtils.toJson(map));
		return "unis/backEnd/upload/upload_success";
	}

	/**
	 * @param ids
	 *            (格式："1,2,3")
	 * @return
	 */
	@RequiresPermissions("ppt:delete")
	@RequestMapping(value = "/delete")
	public ModelAndView delete(HttpServletRequest request, String ids) {
		StringView view = new StringView();
		String rootPath = getRootPath(request);
		ipptService.delete(ids,rootPath);
		view.setContent("success");
		return new ModelAndView(view);
	}

	/**
	 * 启用 / 禁用
	 */
	@RequiresPermissions("ppt:setup")
	@RequestMapping(value = "/setup")
	public ModelAndView setup(Long id, Integer status) {
		StringView view = new StringView();
		view.setContent(ipptService.changeStatus(id, status) ? "success": "出现未知异常，操作失败");
		return new ModelAndView(view);
	}

	private String getRootPath(HttpServletRequest request){
		return  request.getSession().getServletContext().getRealPath("/");
	}

}
