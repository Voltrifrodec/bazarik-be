package sk.umb.dvestodola.bazarik.email.service;

import java.util.Properties;

import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import sk.umb.dvestodola.bazarik.config.YAMLConfig;

@Service
public class EmailService {
	
	private final Properties properties;
	private final Session session;
	
	private EmailFactory emailFactory;
	
	private final String fromEmail = "bazarik.noreply@gmail.com";
	private final String pathToHtml = "src/main/java/sk/umb/dvestodola/bazarik/email/design/index.html";

	public EmailService(
		YAMLConfig yamlConfig
	) {
		this.properties = new Properties();
		this.properties.put("mail.smtp.auth", "true");
		this.properties.put("mail.smtp.starttls.enable", "true");
		this.properties.put("mail.smtp.host", "smtp.gmail.com");
		this.properties.put("mail.smtp.port", "25");
		this.properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		this.session = Session.getInstance(
			this.properties,
			new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(
						yamlConfig.getEmail_username(),
						yamlConfig.getEmail_password()
					);
				}
			}
		);

		this.emailFactory = new EmailFactory(pathToHtml);
	}

	public void sendEmail(String toEmail, String code) {
		try {
			Message message = new MimeMessage(this.session);
			message.setFrom(new InternetAddress(this.fromEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject("Bazárik - Overovací kód");
			
			// https://stackoverflow.com/questions/5068827/how-do-i-send-an-html-email
			// https://developers.google.com/gmail/design/css
			// https://litmus.com/blog/gmail-to-support-responsive-email-design?utm_campaign=gmail_updates&utm_source=pardot&utm_medium=email
			// http://www.campaignmonitor.com/css/
			String messageBody = this.emailFactory.getEmailBody(code);
			
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(messageBody, "text/html; charset=utf-8");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			
			message.setContent(multipart);
		
			System.out.println("E-mail was sent to: " + toEmail);
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
