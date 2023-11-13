package sk.umb.dvestodola.bazarik;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sk.umb.dvestodola.bazarik.config.YAMLConfig;
import sk.umb.dvestodola.bazarik.currency.persistence.entity.CurrencyEntity;
import sk.umb.dvestodola.bazarik.currency.persistence.repository.CurrencyRepository;
import sk.umb.dvestodola.bazarik.email.service.EmailService;

@SpringBootApplication
public class BazarikApplication implements CommandLineRunner {

	public static final String topicExchangeName = "spring-boot-exchange";
  public static final String queueName = "spring-boot";
	
	@Autowired
	CurrencyRepository currencyRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	YAMLConfig yamlConfig;
	
	public static void main(String[] args) {
		SpringApplication.run(BazarikApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (currencyRepository.count() == 0) {
			insertToCurrencies("€", "Euro");
		}

		System.out.println("'" + this.yamlConfig.getEnvironment() + "'");
		if (this.yamlConfig.getEnvironment().equals("test-YAML")) {
			// String email = "avalastan@student.umb.sk";
			// System.out.println("Sending e-mail to:  " + email);
			// this.emailService.sendEmail(email, "68");
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

}
