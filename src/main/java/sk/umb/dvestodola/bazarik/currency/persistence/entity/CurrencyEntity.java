package sk.umb.dvestodola.bazarik.currency.persistence.entity;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@RedisHash("currency")
@Entity(name = "currency")
public class CurrencyEntity {
	@Id
	@GeneratedValue
	@Column(name = "id_currency", unique = true)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "symbol", unique = true)
	private String symbol;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
