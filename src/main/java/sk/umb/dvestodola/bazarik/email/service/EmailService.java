package sk.umb.dvestodola.bazarik.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;

	private String fromEmail = "bazarik.noreply@gmail.com";

	public void sendEmail(String toEmail, String code, String message) {
		SimpleMailMessage mail = new SimpleMailMessage();

		String body = message + "\n\n" + code;
		String subject = "Bazarik - Overovací kód";
		
		mail.setFrom(this.fromEmail);
		mail.setTo(toEmail);
		mail.setText(body);
		mail.setSubject(subject);

		mailSender.send(mail);
	}
}
