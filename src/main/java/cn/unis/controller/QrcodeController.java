package cn.unis.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.unis.entity.Game;
import cn.unis.service.interfaces.IGameService;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 二维码
 * @author fanzz
 *
 */
@Controller
public class QrcodeController {
	private final static int _DEFAULT_HEIGHT = 250;
	private final static int _DEFAULT_WIDTH = 250;
	@Autowired
	private IGameService iGameService;

	/**
	 * wwc 报名二维码
	 *
	 */
	@RequestMapping(value = "/wwc/qrcode", method = RequestMethod.GET)
	public void wwcQrcode(HttpServletRequest req, HttpServletResponse resp, @RequestParam("content")String content, Model model) {
		// 设置相应类型,告诉浏览器输出的内容为图片
		resp.setContentType("image/png");
		// 不缓存此内容
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expire", 0);
		try {
			BitMatrix bitMatrix;
			Map<EncodeHintType,Integer> hints = Maps.newHashMap();
			hints.put(EncodeHintType.MARGIN, 1);
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, _DEFAULT_HEIGHT, _DEFAULT_WIDTH,hints);
			MatrixToImageWriter.writeToStream(bitMatrix, "png",resp.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 游戏图片的二维码
	 * @param req
	 * @param resp
	 * @param id 游戏id
	 * @param model
	 */
	@RequestMapping(value = "/frontEnd/game/description/qrcode/{id}", method = RequestMethod.GET)
	public void qrcode(HttpServletRequest req, HttpServletResponse resp,
			@PathVariable("id")Long id, Model model) {
		// 设置相应类型,告诉浏览器输出的内容为图片
		resp.setContentType("image/png");
		// 不缓存此内容
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expire", 0);
		try {
			Game game = null;
			if(id!=null && id>=0L){
				game = iGameService.findById(id);
			}
			String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort();
			String url = game == null ? "http://www.shiyugame.com" : basePath + "/frontEnd/game/description?id=" + game.getId();
			BitMatrix bitMatrix;
			Map<EncodeHintType,Integer> hints = Maps.newHashMap();
			hints.put(EncodeHintType.MARGIN, 1);
			bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, _DEFAULT_HEIGHT, _DEFAULT_WIDTH,hints);
			MatrixToImageWriter.writeToStream(bitMatrix, "png",resp.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
