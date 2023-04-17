package sk.umb.dvestodola.bazarik.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;

	public void sendEmail(String toEmailAdress, String subject, String body) {
		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setFrom("bazarik.noreply@gmail.com");
		mail.setTo(toEmailAdress);
		mail.setText(body);
		mail.setSubject(subject);

		// TODO: Zmeniť na Message message = new MimeMessage(session);
		// https://www.tutorialspoint.com/javamail_api/javamail_api_send_html_in_email.htm

		mailSender.send(mail);

		System.out.println("E-mail was sent successfully to " + toEmailAdress);
	}
}
