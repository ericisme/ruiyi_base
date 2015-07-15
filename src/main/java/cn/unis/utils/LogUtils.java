package cn.unis.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LogUtils {

	public static int getFileLines(String filePath) {
		int count = 0;
		File file = new File(filePath);
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			while (bufferedReader.readLine() != null) {
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (Exception e2) {
			}
			try {
				bufferedReader.close();
			} catch (Exception e2) {
			}
		}

		return count;
	}

	/**
	 * 写文件
	 * 
	 * @param content
	 *            （需要换行 + "\n" ）
	 * @param filePath
	 *            （文件路径）
	 * @param dirPath
	 *            （文件夹路径）
	 * @return
	 */
	public static boolean writeIntoFile(String content, String filePath,boolean append) {
		boolean isSuccess = true;
		createNewFile(filePath);

		File file = new File(filePath);
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file, append);
			fileWriter.write(content);
			fileWriter.flush();
		} catch (IOException e) {
			isSuccess = false;
			e.printStackTrace();
		} finally {
			try {
				if(fileWriter!=null)
					fileWriter.close();
			} catch (IOException e) {
				isSuccess = false;
				e.printStackTrace();
			}
		}
		return isSuccess;
	}

	public static boolean createNewFile(String filePath) {
		boolean isSuccess = true;
		File tmpFile = new File(filePath);
		File dir = new File(tmpFile.getParent());
		isSuccess = dir.mkdirs();
		File file = new File(filePath);
		try {
			isSuccess = file.createNewFile();
		} catch (IOException e) {
			isSuccess = false;
			e.printStackTrace();
		}
		return isSuccess;
	}
	


}
