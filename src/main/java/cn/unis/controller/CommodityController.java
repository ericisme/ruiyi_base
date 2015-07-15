package cn.unis.controller;

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
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.entity.Mcode;
import cn.ruiyi.base.service.mcode.McodeService;
import cn.ruiyi.base.web.mvc.Paginate;
import cn.ruiyi.base.web.mvc.StringView;
import cn.unis.entity.Commodity;
import cn.unis.entity.CommodityCategory;
import cn.unis.service.impl.CommodityCategoryService;
import cn.unis.service.interfaces.ICommodityService;

/**
 * 商品管理
 * @author fanzz
 *
 */
@Controller
@RequestMapping(value = "/backEnd/commodity")
public class CommodityController {

	@Autowired
	private ICommodityService iCommodityService;
	@Autowired
	private McodeService mcodeService;
	@Autowired
	private CommodityCategoryService commodityCategoryService;

	@RequiresPermissions("commodity:view")
	@RequestMapping(value = "index")
	public String index(Model model) {
		model.addAttribute("type",mcodeService.getMbHtmlSelect("COMMODITY_TYPE", ""));
		return "unis/backEnd/commodity/index";
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
	@RequiresPermissions("commodity:view")
	@RequestMapping(value = "query")
	public String list(HttpServletRequest request,
			@RequestParam(value = "queryName") String queryName,
			@RequestParam(value = "_commodityCategory_id") Long _commodityCategory_id,
			@RequestParam(value = "queryStatus") Integer queryStatus,
			@RequestParam("page") Integer page, Model model) {
		Pageable pageable = new PageRequest(page - 1, Constants.PAGE_SIZE,
				Sort.Direction.ASC, "sortNumber");
		Page<Commodity> paginate = iCommodityService.findAll(_commodityCategory_id,queryName,queryStatus, null,pageable);
		
		for (Commodity commodity : paginate) {
			Mcode mcode = mcodeService.findByMtypeAndMvalue("COMMODITY_TYPE",commodity.getType());
			if(mcode!=null){
				commodity.setType(mcode.getMkey());
			}
		}
		
		String roll = Paginate.getPage(request, page, paginate, "base.roll",
				"list");
		model.addAttribute("page", paginate.getContent());
		model.addAttribute("pageSize", paginate.getContent().size());
		model.addAttribute("roll", roll);
		return "unis/backEnd/commodity/list";
	}
	/**
	 * 编辑
	 * @param id
	 * @param flag 0:修改 1:复制增加记录
	 * @param model
	 * @return
	 */
	@RequiresPermissions("commodity:edit")
	@RequestMapping(value = "/edit/{id}/{flag}")
	public String edit(@PathVariable Long id,@PathVariable int flag, Model model) {
		Commodity commodity;
		if(id==null||id==0){//新增的时候
			commodity = new Commodity();
			commodity.setId(0L);
		}else{
			commodity = iCommodityService.findById(id);
		}
		
		if(flag == 1){ //copy
			commodity.setId(0L);
		}
		
		model.addAttribute("commodity", commodity);
		model.addAttribute("type", mcodeService.getMbHtmlSelect("COMMODITY_TYPE",commodity == null ? "" : commodity.getType()));
		model.addAttribute("pictureType",mcodeService.getMbHtmlSelect("PICTURE_TYPE", ""));
		return "unis/backEnd/commodity/edit";
	}

	@RequiresPermissions("commodity:edit")
	@RequestMapping(value = "/save")
	@ResponseBody
	public ModelAndView save(HttpServletRequest request, Commodity commodity, Long commodityCategory_id) {
		StringView view = new StringView();
		//商品类别
		CommodityCategory commodityCategory = commodityCategoryService.findById(commodityCategory_id);
		commodity.setCommodityCategory(commodityCategory);
		//保存
		String flag = iCommodityService.saveForAdmin(commodity);
		view.setContent(flag);
		return new ModelAndView(view);
	}
	

	/**
	 * @param ids
	 *            (格式："1,2,3")
	 * @return
	 */
	@RequiresPermissions("commodity:edit")
	@RequestMapping(value = "/delete")
	public ModelAndView delete(HttpServletRequest request, String ids) {
		StringView view = new StringView();
		iCommodityService.delete(ids);
		view.setContent("success");
		return new ModelAndView(view);
	}

	/**
	 * 启用 / 禁用
	 */
	@RequiresPermissions("commodity:edit")
	@RequestMapping(value = "/setup")
	public ModelAndView setup(Long id, Integer status) {
		StringView view = new StringView();
		view.setContent(iCommodityService.changeStatus(id, status) ? "success"
				: "出现未知异常，操作失败");
		return new ModelAndView(view);
	}
}
