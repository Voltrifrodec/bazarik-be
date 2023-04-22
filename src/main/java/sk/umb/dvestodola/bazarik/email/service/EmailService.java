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

	public void sendEmail(String toEmailAdress, String subject, String body) {
		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setFrom(this.fromEmail);
		mail.setTo(toEmailAdress);
		mail.setText(body);
		mail.setSubject(subject);

		// TODO: Zmeniť na Message message = new MimeMessage(session);
		// https://www.tutorialspoint.com/javamail_api/javamail_api_send_html_in_email.htm

		mailSender.send(mail);

		System.out.println("E-mail was sent successfully to " + toEmailAdress);
	}

	public void sendCode(String toEmail, String code) {
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
