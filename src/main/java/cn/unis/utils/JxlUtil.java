package cn.unis.utils;

import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

/**
 * jxl工具类
 * @author Administrator
 *
 */
public class JxlUtil {

	
	/**
	 * 粗红字，浅蓝底。
	 * @return
	 * @throws WriteException
	 */
	public static WritableCellFormat getWritableCellFormatTitleRed() throws WriteException{
		/**
		   * 构造格式：ARIAL字体、10号、粗体、非斜体、无下划线、红色
		   */
		    WritableFont fmtx2TotalCaption = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,
		        false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.RED); 
		    WritableCellFormat totalx2Format = new WritableCellFormat(fmtx2TotalCaption);
		    	//文字垂直居中对齐
		    	totalx2Format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
		    	//文字水平居中对齐 
		    	totalx2Format.setAlignment(jxl.format.Alignment.CENTRE);   
		    	//边框深蓝色
		    	totalx2Format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN,
		        jxl.format.Colour.DARK_BLUE);
		    	//设置底色为冰蓝
		    	totalx2Format.setBackground(jxl.format.Colour.ICE_BLUE);		    	
		    	return totalx2Format;
	}
	/**
	 * 粗黑字，浅蓝底。
	 * @return
	 * @throws WriteException
	 */
	public static  WritableCellFormat getWritableCellFormatTitleBlack() throws WriteException{
		/**
		   * 构造格式：ARIAL字体、10号、粗体、非斜体、无下划线、红色
		   */
		WritableFont fmtx2TotalCaption = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,
															false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); 
		WritableCellFormat totalx2Format = new WritableCellFormat(fmtx2TotalCaption);
		//文字垂直居中对齐
		totalx2Format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
		//文字水平居中对齐 
		totalx2Format.setAlignment(jxl.format.Alignment.CENTRE);   
		//边框深蓝色
		totalx2Format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN,
								jxl.format.Colour.DARK_BLUE);
		//设置底色为冰蓝
		totalx2Format.setBackground(jxl.format.Colour.ICE_BLUE);		    	
		return totalx2Format;
	}
	/**
	 * 粗黑字，白底，居中。
	 * @return
	 * @throws WriteException
	 */
	public static  WritableCellFormat getWritableCellFormatTitleWrite() throws WriteException{
		/**
		   * 构造格式：ARIAL字体、10号、粗体、非斜体、无下划线、红色
		   */
		    WritableFont fmtx2TotalCaption = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,
		        false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); 
		    WritableCellFormat totalx2Format = new WritableCellFormat(fmtx2TotalCaption);
		    	//文字垂直居中对齐
		    	totalx2Format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
		    	//文字水平居中对齐 
		    	totalx2Format.setAlignment(jxl.format.Alignment.CENTRE);   
		    	//边框深蓝色
//		    	totalx2Format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN,
//		        jxl.format.Colour.WHITE);
		    	//设置底色为冰蓝
//		    	totalx2Format.setBackground(jxl.format.Colour.ICE_BLUE);		    	
		    	return totalx2Format;
	}
	/**
	 * 黑字，白底，居中。
	 * @return
	 * @throws WriteException
	 */
	public static  WritableCellFormat getWritableCellFormatTitleWrite2() throws WriteException{
		/**
		   * 构造格式：ARIAL字体、10号、粗体、非斜体、无下划线、红色
		   */
		    WritableFont fmtx2TotalCaption = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,
		        false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); 
		    WritableCellFormat totalx2Format = new WritableCellFormat(fmtx2TotalCaption);
		    	//文字垂直居中对齐
		    	totalx2Format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
		    	//文字水平居中对齐 
		    	totalx2Format.setAlignment(jxl.format.Alignment.CENTRE);   
		    	//边框深蓝色
//		    	totalx2Format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN,
//		        jxl.format.Colour.WHITE);
		    	//设置底色为冰蓝
//		    	totalx2Format.setBackground(jxl.format.Colour.ICE_BLUE);		    	
		    	return totalx2Format;
	}
}
