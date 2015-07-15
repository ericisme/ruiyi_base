package cn.unis.utils;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传工具类
 *
 * @author unis
 *
 */
public class PictureUploader {

	/**
	 * 检查目录是否存在，否则生成
	 * @param directory
	 */
	private static void ckeckAndMkdirs(String directory) {
		File file = new File(directory);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	/**
	 * 图片文件移动
	 * @param original 完整来源路径 ex: /fanzz/a/1.jpg
	 * @param destination 完整目的路径 ex: /fanzz/b/2.jpg
	 * @return
	 */
	public static boolean move(String original, String destination) {
		File originalFile = new File(original);
		File destinationFile = new File(destination);
		boolean flag = originalFile.renameTo(destinationFile);
		return flag;
	}
	/**
	 * 图片保存（uuid for name）
	 * @param file 输入文件流
	 * @param directory 保存后得到的路径
	 * @return
	 */
	public static String save(MultipartFile file, String directory) {
		String picName = null;
		if (file != null && directory != null) {
			ckeckAndMkdirs(directory);
			picName = UUID.randomUUID().toString();
			String picPath = directory.endsWith(File.separator) ? directory + picName : directory + File.separator + picName;
			String suffix = StringUtils2.getFileSuffix(file.getOriginalFilename());
			picPath = picPath + suffix;
			picName = picName + suffix;
			try {
				FileOutputStream fos = new FileOutputStream(picPath);
				byte[] b = file.getBytes();
				fos.write(b);
				fos.close();
			} catch (Exception e) {
				picName = null;
				e.printStackTrace();
			}
		}
		return picName;
	}
	/**
	 * 创建图片预览图
	 * @param picDirectory 图片路径
	 * @param picName 图片名称
	 * @param previewDirectory 预览路径
	 * @param heigh 高度
	 * @param width 宽度
	 * @return
	 */
	public static String createPreview(String picDirectory,String picName,String previewDirectory,int heigh,int width){
		if(picDirectory == null || picName ==null || previewDirectory == null || heigh <=0 || width <= 0){
			return null;
		}
		String previewName  = UUID.randomUUID().toString() + StringUtils2.getFileSuffix(picName);
		CompressPic compressPic = new CompressPic();
		if(!picDirectory.endsWith(File.separator)){
			picDirectory  = picDirectory + File.separator;
		}
		if(!previewDirectory.endsWith(File.separator)){
			previewDirectory = previewDirectory + File.separator;
		}
		compressPic.compressPic(picDirectory ,previewDirectory, picName, previewName, heigh, width, false);
		return previewName;
	}

	public static boolean delete(String path){
		if(File.separator.equals("\\")){
			path = replace_backslash_2_slash(path);
		}
		File file = new File(path);
		return file.delete();
	}

	public static boolean isPictureStream(MultipartFile file){
		Image img = null;
		try {
			img = ImageIO.read(file.getInputStream());
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return img != null ;
	}

	public static String getPicName(String url){
		if(url != null){
			url = replace_slash_2_backslash(url);
			String [] urlArr  = url.split("/");
			if(urlArr!=null && urlArr.length > 0){
				return urlArr[urlArr.length - 1];
			}
		}
		return null;
	}
	public static String getContextPath(HttpServletRequest request){
		return  request.getSession().getServletContext().getRealPath("/");
	}


	/**
	 * \ ==> /
	 * @param str
	 * @return
	 */
	public static String replace_slash_2_backslash(String str){
		String temp = "";
//		if(StringUtils.isNotBlank(str)){
//			for(int i=0;i<str.length();i++){
//				if(str.charAt(i)=='\\'){
//					temp = temp + '/';
//				}else{
//					temp = temp + str.charAt(i);
//				}
//			}
//		}else{
//			temp = str;
//		}
		temp = str!=null ?  str.replaceAll("\\\\","/") : str;
		return temp;
	}
	/**
	 * / ==> \
	 * @param str
	 * @return
	 */
	public static String replace_backslash_2_slash(String str){
		String temp = "";
//		if(StringUtils.isNotBlank(str)){
//			for(int i=0;i<str.length();i++){
//				if(str.charAt(i)=='/'){
//					temp = temp + '\\';
//				}else{
//					temp = temp + str.charAt(i);
//				}
//			}
//		}else{
//			temp = str;
//		}
		temp = str!=null ? str.replaceAll("/", "\\\\") : str;
		return temp;
	}
	public static int calPicWidth(MultipartFile file,int heigh){
		Image img;
		int newWidth = 0;
		try {
			img = ImageIO.read(file.getInputStream());
			double heigh_pic = img.getHeight(null);
			double width_pic = img.getWidth(null);
			newWidth = (int)(heigh * width_pic / heigh_pic);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newWidth;
	}
	public static int calPicHeight(MultipartFile file,int width){
		Image img;
		int newHeigh = 0;
		try {
			img = ImageIO.read(file.getInputStream());
			double heigh_pic = img.getHeight(null);
			double width_pic = img.getWidth(null);
			newHeigh = (int)(width * heigh_pic / width_pic);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newHeigh;
	}
	public static void main(String[] args){

		//String dir = (System.getProperty("java.io.tmpdir"));
		//System.out.println(dir);
		//dir = replace_slash_2_backslash(dir);
		//System.out.println(dir.split("/")[0]);
		System.out.println((int)('/'));
		System.out.println((int)('\\'));

	}

}
