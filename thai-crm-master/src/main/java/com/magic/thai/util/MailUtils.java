package com.magic.thai.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtils {

	private static Properties properties;

	static {
		InputStream inputStream = MailUtils.class.getClassLoader().getResourceAsStream("system.properties");
		if (inputStream == null) {
			throw new RuntimeException("can not read  file system.properties");
		}
		try {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 发mail的一个通用方法
	 * 
	 * @param fromEmail mail的发送方地址
	 * @param fromEmailPWD mail发送方登陆mail的密码
	 * @param userName mail发送方登陆mail的用户名
	 * @param reveiveMailAddress 接收方的mail地址，如果有多个接收方地址间用“,”分隔
	 * @param mailTitle 邮件标题
	 * @param mailHtmlContent 邮件内容，支持html格式
	 * @param file 附件内容
	 * @throws Exception 异常
	 */
	public static void sendEmail(String reveiveMailAddress, String mailTitle, String mailHtmlContent, File file) throws Exception {
		if (mailHtmlContent == null) {
			return;
		}
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		// host 发送mail的host
		sender.setHost(properties.get("mail.host").toString());
		sender.setPassword(properties.get("mail.from.password").toString());
		sender.setUsername(properties.get("mail.from.username").toString());
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", properties.get("mail.transport.protocol"));
		props.put("mail.smtp.host", properties.get("mail.smtp.host"));// "smtp.qq.com"
		props.put("mail.smtp.auth", properties.get("mail.smtp.auth"));// true
		props.put("mail.smtp.localhost", properties.get("mail.smtp.localhost"));// "smtp.exmail.qq.com"
		sender.setJavaMailProperties(props);
		javax.mail.internet.MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setFrom(properties.get("mail.from").toString());
		if (file != null) {
			helper.addAttachment("file", file);
		}
		helper.setTo(reveiveMailAddress.split(","));
		helper.setText(mailHtmlContent, true);
		helper.setSentDate(new Date());
		helper.setSubject(mailTitle);
		sender.send(message);
	}
	
	public static void main(String[] args) {
		try {
			sendEmail("jiayanchang@yeah.net", "testtitle", "testcontent", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
