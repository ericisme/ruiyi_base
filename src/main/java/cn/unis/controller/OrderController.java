package cn.unis.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.web.mvc.Paginate;
import cn.unis.entity.PPT;
import cn.unis.service.interfaces.IPPTService;

/**
 * 订单管理
 * @author fanzz
 * 
 */
@Controller
@RequestMapping(value = "/backEnd/order/")
public class OrderController {
	@Autowired
	private IPPTService ipptService;

	@RequestMapping(value = "index")
	public String index(Model model) {
		return "unis/backEnd/order/index";
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
	@RequestMapping(value = "query")
	public String list(HttpServletRequest request,
			@RequestParam(value = "queryName") String queryName,
			@RequestParam(value = "queryStatus") Integer queryStatus,
			@RequestParam("page") Integer page, Model model) {
		String rootPath = "";
		Pageable pageable = new PageRequest(page - 1, Constants.PAGE_SIZE,
				Sort.Direction.ASC, "sortNumber");
		Page<PPT> paginate = ipptService.findAll(queryName,queryStatus, pageable,rootPath);
		
		String roll = Paginate.getPage(request, page, paginate, "base.roll",
				"list");
		model.addAttribute("page", paginate.getContent());
		model.addAttribute("pageSize", paginate.getContent().size());
		model.addAttribute("roll", roll);
		return "unis/backEnd/order/list";
	}

}
