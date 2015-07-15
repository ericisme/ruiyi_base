package cn.unis.utils;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;


public class FileUtils {
	private  String TMP;
	private  String ORIGINAL;
	private  String PREVIEW;
	private String PREVIEWLARGE;
	public FileUtils(){
		
	}
	
	public FileUtils(String tmp, String original, String preview) {
		super();
		TMP = tmp;
		ORIGINAL = original;
		PREVIEW = preview;
	}
	
	public FileUtils(String tmp, String original, String preview,String previewlarge) {
		super();
		TMP = tmp;
		ORIGINAL = original;
		PREVIEW = preview;
		PREVIEWLARGE = previewlarge;
	}

	/**
	 * 检查并创建文件夹
	 */
	public void checkAndMkdirs(String rootPath) {
		File file = null;
		if(StringUtils.isNotBlank(TMP)){
			file = new File(rootPath + TMP);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		if(StringUtils.isNotBlank(ORIGINAL)){
			file = new File(rootPath + ORIGINAL);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		if(StringUtils.isNotBlank(PREVIEW)){
			file = new File(rootPath + PREVIEW);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		if(StringUtils.isNotBlank(PREVIEWLARGE)){
			file = new File(rootPath + PREVIEWLARGE);
			if(!file.exists()){
				file.mkdirs();
			}
		}
	}
	
	/**
	 * 将临时的图片移到正式的位置,返回正式路径地址
	 * @param rootPath web路径 
	 * @param originalUrl 还在临时路径的图片地址 /upload/ppt/tmp/aaaa.jpeg
	 * @param previewUrl 还在临时路径的预览图片途径 /upload/ppt/tmp/aaaa80x80.jpeg
	 * @return /upload/ppt/original/aaaa.jpeg && /upload/ppt/preview/aaaa80x80.jpeg
	 */
	public  String[] movePicture(String rootPath , String originalUrl, String previewUrl) {

		String original = rootPath + originalUrl;
		String tmpOriginalUrl = originalUrl.replace(TMP, ORIGINAL);
		String destOriginal = rootPath + tmpOriginalUrl;
		File fileOriginal = new File(original);
		fileOriginal.renameTo(new File(destOriginal));
		fileOriginal.delete();

		String preview = rootPath + previewUrl;
		String tmpPreviewUrl = previewUrl.replace(TMP, PREVIEW);
		String destPreviewUrl = rootPath + tmpPreviewUrl;
		File filePreview = new File(preview);
		filePreview.renameTo(new File(destPreviewUrl));
		filePreview.delete();

		return new String[] { tmpOriginalUrl, tmpPreviewUrl };
	}
	
	public  String[] movePicture(String rootPath , String originalUrl, String previewUrl,String previewLargeUrl) {

		String original = rootPath + originalUrl;
		String tmpOriginalUrl = originalUrl.replace(TMP, ORIGINAL);
		String destOriginal = rootPath + tmpOriginalUrl;
		File fileOriginal = new File(original);
		fileOriginal.renameTo(new File(destOriginal));
		fileOriginal.delete();

		String preview = rootPath + previewUrl;
		String tmpPreviewUrl = previewUrl.replace(TMP, PREVIEW);
		String destPreviewUrl = rootPath + tmpPreviewUrl;
		File filePreview = new File(preview);
		filePreview.renameTo(new File(destPreviewUrl));
		filePreview.delete();
		
		String previewLarge = rootPath + previewLargeUrl;
		String tmpPreviewLargeUrl = previewLargeUrl.replace(TMP, PREVIEWLARGE);
		String destPreviewLargeUrl = rootPath + tmpPreviewLargeUrl;
		File filePreviewLarge = new File(previewLarge);
		filePreviewLarge.renameTo(new File(destPreviewLargeUrl));
		filePreviewLarge.delete();

		return new String[] { tmpOriginalUrl, tmpPreviewUrl,tmpPreviewLargeUrl };
	}
	
	/**
	 * 将临时的图片移到正式的位置,返回正式路径地址
	 */
	public  String movePicture(String url,String rootPath) {
		String original = rootPath + url;
		String tmpOriginalUrl = url.replace(TMP, ORIGINAL);
		String destOriginal = rootPath + tmpOriginalUrl;
		File fileOriginal = new File(original);
		fileOriginal.renameTo(new File(destOriginal));
		fileOriginal.delete();
		return tmpOriginalUrl;
	}

	
	/**
	 * 删除图片
	 * 
	 * @param url
	 */
	public  void deletePicture(String url) {
		System.out.println("delete url replace before====================>" + url);
		if(File.separator.equals("\\")){
			url = replace2(url);
		}
		System.out.println("delete url replace  after====================>" + url);
		File file = new File(url);
		file.delete();
	}
	
	public String[] savePicture(MultipartFile file, String rootPath ,boolean createPreview,boolean createPreviewLarge) {
		checkAndMkdirs(rootPath);
		String fileName = "" , previewFileName = "",previewLargeName = "";
		String path;
		try {
			/**
			 * 正式文件名
			 */
			fileName = Base64Coder.encode((new Date() + "").getBytes()) + StringUtils2.getFileSuffix(file.getOriginalFilename());
			/**
			 * 预览图片文件名
			 */
			if(createPreview){
				previewFileName = Base64Coder.encode((new Date() + "").getBytes()) + "80x80.jpeg";
			}
			if(createPreviewLarge){
				previewLargeName = Base64Coder.encode((new Date() + "").getBytes()) + "730xauto.jpeg";
			}
			/**
			 * 将文件保存在临时路径
			 */
			path = rootPath + TMP + fileName;
			FileOutputStream fos = new FileOutputStream(path);
			byte[] b = file.getBytes();
			fos.write(b);
			fos.close();
			/**
			 * 将缩略图保存在临时路径
			 */
			if(createPreview){
				CompressPic compressPic = new CompressPic();
				compressPic.compressPic(rootPath + TMP, rootPath + TMP, fileName, previewFileName, 80, 80, false);		
			}
			
			if(createPreviewLarge){
				File tmpfile = new File(rootPath + TMP + fileName);
				if (tmpfile.exists()) {
					Image img = ImageIO.read(tmpfile);
					double height = img.getHeight(null);
					double width = img.getWidth(null);
					int newWidth = (int)(730 * height / width);
					CompressPic compressPic = new CompressPic();
					
					//newWidth = 411;
					compressPic.compressPic(rootPath + TMP, rootPath + TMP, fileName, previewLargeName,730,newWidth,false);
				}
				
			}
			/**
			 * 返回预览路径和正式路径到前台
			 */
			fileName = TMP + fileName;
			if(createPreview){
				previewFileName = TMP + previewFileName;
			}
			
			if(createPreviewLarge){
				previewLargeName = TMP + previewLargeName;
			}

		} catch (Exception e) {
			fileName = "error";
			if(createPreview){
				previewFileName = "error";
			}
			if(createPreviewLarge){
				previewLargeName = "error";
			}
			e.printStackTrace();
		}
		String [] urls;
		if(createPreview && !createPreviewLarge){
			urls = new String[]{fileName,previewFileName};
		}else if(!createPreview && createPreviewLarge ){
			urls = new String[]{fileName,previewLargeName};
		}else if(createPreview && createPreviewLarge ){
			urls = new String[]{fileName,previewFileName,previewLargeName};
		}else{
			urls = new String[] { fileName };
		}
		return urls;
	}

	/**
	 * \ ==> /
	 * @param str
	 * @return
	 */
	public static String replace(String str){
		String temp = "";
		if(StringUtils.isNotBlank(str)){
			for(int i=0;i<str.length();i++){
				if(str.charAt(i)==92){
					temp = temp + '/';
				}else{
					temp = temp + str.charAt(i);
				}
			}
		}else{
			temp = str;
		}
		return temp;
	}
	/**
	 * / ==> \
	 * @param str 
	 * @return
	 */
	public static String replace2(String str){
		String temp = "";
		if(StringUtils.isNotBlank(str)){
			for(int i=0;i<str.length();i++){
				if(str.charAt(i)=='/'){
					temp = temp + '\\';
				}else{
					temp = temp + str.charAt(i);
				}
			}
		}else{
			temp = str;
		}
		return temp;
	}
	
}
