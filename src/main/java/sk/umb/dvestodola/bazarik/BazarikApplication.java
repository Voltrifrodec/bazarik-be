package sk.umb.dvestodola.bazarik;

import java.util.Random;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import sk.umb.dvestodola.bazarik.currency.persistence.entity.CurrencyEntity;
import sk.umb.dvestodola.bazarik.currency.persistence.repository.CurrencyRepository;
import sk.umb.dvestodola.bazarik.email.service.EmailService;

@SpringBootApplication
public class BazarikApplication implements CommandLineRunner {
	
	@Autowired
	CurrencyRepository currencyRepository;

	@Autowired
	EmailService emailService;
	
	public static void main(String[] args) {
		SpringApplication.run(BazarikApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (currencyRepository.count() == 0) {
			insertToCurrencies("€", "Euro");
		}
	}

	public void insertToCurrencies(String symbol, String name) {
		if (Strings.isBlank(name)) return;
		if (Strings.isBlank(symbol)) return;
		
		CurrencyEntity currencyEntity = new CurrencyEntity();
		currencyEntity.setName(name);
		currencyEntity.setSymbol(symbol);
		currencyRepository.save(currencyEntity);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void testSendEmail() {
		Random r = new Random();

		String toEmailAdress = "";
		String subject = "Bazarik - Overovací kód";
		String body = "Pre pridanie inzerátu zadajte overovací kód: \n\n" + r.nextInt(1000, 9999);

		this.emailService.sendEmail(toEmailAdress, subject, body);
	}
}
