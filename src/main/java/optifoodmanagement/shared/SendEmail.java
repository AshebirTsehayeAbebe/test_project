package optifoodmanagement.shared;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import optifoodmanagement.ui.model.request.SendEmailRequestModel;

@Component
public class SendEmail {
	
	@Value("${gmail.Username}")
	private String username;
	
	@Value("${gmail.Password}")
	private String password;
	
	@Value("${mail.HostDomain}")
	private String host;

	@Value("${smtp.port}")
	private String port;
	
	/*
	 * @Value("${mail.HostDomain}") private String hostDomain;
	 */
	
	public String sendMail(SendEmailRequestModel sendMail) throws AddressException, MessagingException, IOException {
		
		String returnValue = "";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		
		Session session = Session.getInstance(props, 
		 new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(username, false));
			
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendMail.getToAddress()));
			msg.setSubject(sendMail.getSubject());
			msg.setContent(sendMail.getBody(),"text/html");
			msg.setSentDate(new Date());
			
			MimeBodyPart messaBodyPart = new MimeBodyPart();
			messaBodyPart.setContent(sendMail.getBody(), "text/html");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messaBodyPart);
			new MimeBodyPart();
			
			
			/*TO SEND ATTACHEMENT
			 * 
			 * attachment.attachFile("C:\\epharmacy\\logo.jpg");
			 * multipart.addBodyPart(attachment); msg.setContent(multipart);
			 */
			
			// Send the email			
			Transport.send(msg);
			returnValue = "Mail Sent";
			
		}catch (MessagingException  e) {
			returnValue = "Mail Not Sent";
		}
		
		return returnValue;
	}
}
