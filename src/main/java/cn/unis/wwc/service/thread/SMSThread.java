package cn.unis.wwc.service.thread;

import cn.unis.utils.SMSUtil;

/**
 * 发送短信线程
 * @author Administrator
 *
 */
public class SMSThread implements Runnable{

	
	private String phoneNumber;
	private String contant;
	
	public SMSThread(String phoneNumber, String contant){
		this.phoneNumber = phoneNumber;
		this.contant = contant;
	}
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			SMSUtil.send(phoneNumber, contant, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}

}
