package cn.unis.wwc.service.thread;

import cn.unis.utils.MailUtil;

/**
 * 发送email线程
 * @author Administrator
 *
 */
public class EmailThread implements Runnable {

	
	private String emailAddress;
	private String title;
	private String contant;
	
	public EmailThread(String emailAddress, String title, String contant){
		this.emailAddress = emailAddress;
		this.title = title;
		this.contant = contant;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//MailUtil.sendEmail(emailAddress, title, contant);
		MailUtil.sendEmailWithHtml(emailAddress, title, contant);
		System.out.println("contant:"+contant);
	}

}
