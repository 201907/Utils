

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

public class MailUtil implements Serializable {
	private Properties mailConfig;
    public MailUtil() {
		super();
		mailConfig = new Properties();
		try {
			mailConfig.load(new FileInputStream("mail.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
     * @param recipient      目标地址
     * @param subject        邮件主题
     * @param fileAttachment 附件
     * @param content        内容
     * @throws Exception
     */
    public void sendEmail(String recipient, String subject, String fileAttachment, String content) throws Exception {
        String ttId = new Date().toString();
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailConfig.getProperty("SMTP_SERVER"));
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.transport.protocol", "smtp");
        InternetAddress[] receiveAddresses = new InternetAddress[1];
        try {
            receiveAddresses[0] = new InternetAddress(recipient);
        } catch (AddressException e) {
            e.printStackTrace();
        }
        Session session = Session.getInstance(properties, new SmtpAuth());
        MimeMessage message = new MimeMessage(session);
        MimeMultipart multipart = new MimeMultipart();
        multipart.setSubType("alternative");

        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(content, "text/html; charset=utf-8");
        multipart.addBodyPart(bodyPart);
        if (fileAttachment != null) {
            DataSource dataSource = new FileDataSource(fileAttachment);
            String name = dataSource.getName();
            bodyPart = new MimeBodyPart();
            bodyPart.setDataHandler(new DataHandler(dataSource));
            bodyPart.setFileName(MimeUtility.encodeText(name));
            multipart.addBodyPart(bodyPart);
        }
        message.setSubject(subject);
        message.setContent(multipart);
        message.setFrom(new InternetAddress(mailConfig.getProperty("EmailFrom")));
        message.setRecipients(Message.RecipientType.TO, receiveAddresses);
        InternetAddress[] addresses = new InternetAddress[1];
        addresses[0] = new InternetAddress(recipient);
        message.setReplyTo(addresses);
        message.addHeader("X-Campaign", MimeUtility.encodeText(ttId));
        message.addHeader("X-Qos", "transactional");
        Transport.send(message);


    }

    class SmtpAuth extends Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(mailConfig.getProperty("APIKey"), mailConfig.getProperty("APISecret"));
        }
    }

}