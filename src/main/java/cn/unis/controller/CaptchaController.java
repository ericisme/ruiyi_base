package cn.unis.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ruiyi.base.constants.Constants;
import cn.unis.utils.CaptchaUtil;
import cn.unis.utils.captcha.Captcha;
import cn.unis.utils.captcha.GifCaptcha;

/**
 * 验证码
 *
 * @author fanzz
 *
 */
@Controller
@RequestMapping(value = "/static/captchaCode")
public class CaptchaController {
	private static int width = 150;
	private static int heigh = 40;
	private static int len = 5;
	@RequestMapping(value = "alipay", method = RequestMethod.GET)
	public void alipayCaptcha(HttpServletRequest req, HttpServletResponse resp) {
//		generateCaptcha(req, resp, Constants.ALIPAY_KEY_CAPTCHA);
		generateGifCaptcha(req, resp, Constants.ALIPAY_KEY_CAPTCHA);

	}
	@RequestMapping(value = "wwc", method = RequestMethod.GET)
	public void wwcIndexCaptcha(HttpServletRequest req, HttpServletResponse resp){
		//generateGifCaptcha(req, resp, Constants.WWC_KEY_CAPTCHA);
		generateCaptcha(req, resp, Constants.WWC_KEY_CAPTCHA);
	}

	@RequestMapping(value = "/wwc/applycode", method = RequestMethod.GET)
	public void applyCodeCaptcha(HttpServletRequest req, HttpServletResponse resp){
		generateGifCaptcha(req, resp, Constants.WWC_APPLY_INVITATION_CAPTCHA);
	}

	private void generateGifCaptcha(HttpServletRequest req,
			HttpServletResponse resp, String name) {
		// 设置相应类型,告诉浏览器输出的内容为图片
		resp.setContentType("image/jpeg");
		// 不缓存此内容
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expire", 0);
		GifCaptcha captcha = new GifCaptcha(width,heigh,len);//   gif格式动画验证码
        try {
			captcha.out(resp.getOutputStream());
			req.getSession().removeAttribute(name);
			req.getSession().setAttribute(name, captcha.text());
			System.out.println(captcha.text());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void generateCaptcha(HttpServletRequest req,
			HttpServletResponse resp, String name) {
		// 设置相应类型,告诉浏览器输出的内容为图片
		resp.setContentType("image/jpeg");
		// 不缓存此内容
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expire", 0);
		try {
			HttpSession session = req.getSession();
			CaptchaUtil tool = new CaptchaUtil(width,heigh,len);
			StringBuffer code = new StringBuffer();
			BufferedImage image = tool.genRandomCodeImage(code);
			session.removeAttribute(name);
			session.setAttribute(name, code.toString());
			// 将内存中的图片通过流动形式输出到客户端
			ImageIO.write(image, "JPEG", resp.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
