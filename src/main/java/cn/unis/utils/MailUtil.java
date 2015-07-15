package cn.unis.utils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.ruiyi.base.util.PathUtil;
/**
 * 邮件工具类
 * @author unis
 *
 */
public class MailUtil {
	public static Properties props = new Properties();
	static {
		InputStream inputStream = PathUtil.class.getClassLoader().getResourceAsStream("email.properties");
		if(inputStream!=null){
			try {
				props.load(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	static String port = props.getProperty("mail.smtp.port");

	static String host = props.getProperty("mail.smtp.host");

	static String from = props.getProperty("mail.from");

	static String user = props.getProperty("mail.user");

	static String password = props.getProperty("mail.password");

	static String subjectEncode = "UTF-8";

	static String textEncode = "UTF-8";

	static String InternetEncode = "UTF-8";
	/**
	 * 发送邮件
	 * @param email 目标邮件地址
	 * @param subject 主题
	 * @param body 邮件内容
	 * @return true成功，false失败
	 */
	public static boolean sendEmail(String email, String subject, String body) {
		boolean sendSuccess = true;
		try {
			Transport transport = null;
			Session session = Session.getDefaultInstance(props, null);
			transport = session.getTransport("smtp");
			transport.connect(host, user, password);
			MimeMessage msg = new MimeMessage(session);
			msg.setSentDate(new Date());
			InternetAddress fromAddress = new InternetAddress(user, from,
					InternetEncode);
			msg.setFrom(fromAddress);
			InternetAddress[] toAddress = new InternetAddress[1];
			toAddress[0] = new InternetAddress(email);
			msg.setRecipients(Message.RecipientType.TO, toAddress);
			msg.setSubject(subject, subjectEncode);
			msg.setText(body, textEncode);
			msg.saveChanges();
			transport.sendMessage(msg, msg.getAllRecipients());
		} catch (Exception e) {
			sendSuccess = false;
			e.printStackTrace();
		}
		return sendSuccess;
	}

	/**
	 * 发送邮件
	 * @param email 目标邮件地址
	 * @param subject 主题
	 * @param body 邮件内容
	 * @return true成功，false失败
	 */
	public static boolean sendEmailWithHtml(String email, String subject, String html) {
		boolean sendSuccess = true;
		try {
			Transport transport = null;
			Session session = Session.getDefaultInstance(props, null);
			transport = session.getTransport("smtp");
			transport.connect(host, user, password);
			MimeMessage msg = new MimeMessage(session);
			msg.setSentDate(new Date());
			InternetAddress fromAddress = new InternetAddress(user, from,
					InternetEncode);
			msg.setFrom(fromAddress);
			InternetAddress[] toAddress = new InternetAddress[1];
			toAddress[0] = new InternetAddress(email);
			msg.setRecipients(Message.RecipientType.TO, toAddress);
			msg.setSubject(subject, subjectEncode);
			//msg.setText(body, textEncode);
			msg.setContent(html, "text/html;charset=gb2312");
			msg.saveChanges();
			transport.sendMessage(msg, msg.getAllRecipients());
		} catch (Exception e) {
			sendSuccess = false;
			e.printStackTrace();
		}
		return sendSuccess;
	}
	
	public static void main(String args[]) throws UnsupportedEncodingException {
		// sendEmail("heyongwha@gmail.com","this is a test2 from zs shiyu","this is a test2 from zs shiyu");//收件人
		//sendEmail("453075727@qq.com", "这是个测试，代号1", "这是个测试，代号1");
		//send text
		//sendEmail("ericisme@126.com", "这是个测试，代号1", "这是个测试，代号1<img src='http://mimg.127.net/logo/126logo.gif'/>");
		//System.out.println("ok");
		//send html
		sendEmailWithHtml("675439886@qq.com", "这是个测试，代号1", "这是个测试，代号1<img src='http://mimg.127.net/logo/126logo.gif'/>");
		
	}
}