package cn.unis.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ruiyi.base.util.DateUtil;
import cn.unis.entity.AlipayDiscount;
import cn.unis.service.interfaces.IAlipayDiscountService;

/**
 * 支付宝充值折扣
 * 
 * @author fanzz
 * 
 */
@Controller
@RequestMapping(value = "/backEnd/alipay/")
public class AlipayDiscountController {
	@Autowired
	private IAlipayDiscountService iAlipayDiscountService;

	@RequiresPermissions("alipaydiscount:view")
	@RequestMapping(value = "discountsetting", method = RequestMethod.GET)
	public String discountSetting(Model model) {
		model.addAttribute("alipayDiscount", iAlipayDiscountService.findOne());
		return "/unis/backEnd/alipay/discountsetting";
	}

	@RequiresPermissions("alipaydiscount:edit")
	@RequestMapping(value = "discountsave", method = RequestMethod.POST)
	@ResponseBody
	public String save(AlipayDiscount alipayDiscount,
			@RequestParam(value = "begin", required = true) String begin,
			@RequestParam(value = "end", required = true) String end) {
		String flag = "success";
		alipayDiscount.setEffectiveBegin(DateUtil.parseSimpleDateTime(begin));
		alipayDiscount.setEffectiveEnd(DateUtil.parseSimpleDateTime(end));
		if (checkValid(alipayDiscount)) {
			iAlipayDiscountService.save(alipayDiscount);
		} else {
			flag = "error";
		}
		return flag;
	}

	private boolean checkValid(AlipayDiscount alipayDiscount) {
		return alipayDiscount != null
				&& alipayDiscount.getEffectiveBegin()!=null
				&& alipayDiscount.getEffectiveEnd()!=null
				&& 0 < alipayDiscount.getDiscount()
				&& 1 >= alipayDiscount.getDiscount()
				&& alipayDiscount.getEffectiveBegin().getTime() < alipayDiscount
						.getEffectiveEnd().getTime();
	}

}
