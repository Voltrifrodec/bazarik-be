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

	public void sendCodeUpdate(String toEmail, String code) {
		SimpleMailMessage mail = new SimpleMailMessage();

		String body = "Pre úpravu inzerátu zadajte overovací kód: \n\n" + code;
		String subject = "Bazarik - Overovací kód";
		
		mail.setFrom(this.fromEmail);
		mail.setTo(toEmail);
		mail.setText(body);
		mail.setSubject(subject);

		mailSender.send(mail);
	}

	public void sendCodeCreate(String toEmail, String code) {
		SimpleMailMessage mail = new SimpleMailMessage();

		String body = "Pre pridanie inzerátu zadajte overovací kód: \n\n" + code;
		String subject = "Bazarik - Overovací kód";
		
		mail.setFrom(this.fromEmail);
		mail.setTo(toEmail);
		mail.setText(body);
		mail.setSubject(subject);

		mailSender.send(mail);
	}

	
	/* @EventListener(ApplicationReadyEvent.class)
	public void testSendEmail() {
		Random r = new Random();

		String toEmailAdress = "";
		String subject = "Bazarik - Overovací kód";
		String body = "Pre pridanie inzerátu zadajte overovací kód: \n\n" + r.nextInt(1000, 9999);

		this.emailService.sendEmail(toEmailAdress, subject, body);
	} */
	
}
