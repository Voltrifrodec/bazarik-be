package sk.umb.dvestodola.bazarik.authentication.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.umb.dvestodola.bazarik.authentication.persistence.entity.RoleEntity;
import sk.umb.dvestodola.bazarik.authentication.persistence.entity.TokenEntity;
import sk.umb.dvestodola.bazarik.authentication.persistence.entity.UserEntity;
import sk.umb.dvestodola.bazarik.authentication.persistence.repository.TokenRepository;
import sk.umb.dvestodola.bazarik.authentication.persistence.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
	private static final int TOKEN_VALIDITY_IN_MINUTES = 120;
	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthenticationService(UserRepository userRepository, TokenRepository tokenRepository) {
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@Cacheable(value = "authenticationCache", key = "{#username, #password}")
	@Transactional
	public String authenticate(String username, String password) {
		Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

		if (optionalUser.isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException("Username and/or password do not match!");
		}

		if (!passwordEncoder.matches(password, optionalUser.get().getPasswordHash())) {
			throw new AuthenticationCredentialsNotFoundException("Username and/or password do not match!");
		}

		TokenEntity token = new TokenEntity();
		String randomString = UUID.randomUUID().toString();
		token.setToken(randomString);
		token.setUser(optionalUser.get());
		token.setCreated(LocalDateTime.now());

		tokenRepository.save(token);

		return token.getToken();
	}

	@Cacheable(value = "tokenCache", key = "#token")
	@Transactional
	public UserRolesDto authenticate(String token) {
		Optional<TokenEntity> optionalToken = tokenRepository.findByToken(token);

		if (optionalToken.isEmpty()) {
			Set<String> roles = new HashSet<>();
			roles.add("ROLE_USER");
			return new UserRolesDto("user", roles);
			// throw new AuthenticationCredentialsNotFoundException("Authentication failed!");
		}

		validateTokenExpiration(optionalToken.get());

		Set<RoleEntity> roles = optionalToken.get().getUser().getRoles();
		Set<String> roleNames = roles.stream()
				.map(entry -> entry.getRoleName())
				.collect(Collectors.toSet());

		return new UserRolesDto(optionalToken.get().getUser().getUsername(), roleNames);
	}

	@Cacheable(value = "adminTokenValidation", key = "#token")
	@Transactional
	public boolean validateAdminToken(String token) {
		System.out.println("Ťahá to zo service metódy.");

		if (token.equals("null")) return false;

		String extractedToken = token.split("Bearer")[1].strip();
		return this.tokenRepository.findByToken(extractedToken).isPresent();
	}

	private void validateTokenExpiration(TokenEntity token) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime tokenExpiration = token.getCreated().plus(TOKEN_VALIDITY_IN_MINUTES, ChronoUnit.MINUTES);

		if (now.isAfter(tokenExpiration)) {
			tokenRepository.delete(token);
			throw new AuthenticationCredentialsNotFoundException("Authentication failed!");
		}
	}

	@CacheEvict(value = "tokenCache", key = "#token")
	@Transactional
	public void tokenRemove(String token) {
		tokenRepository.deleteByToken(token);
	}
}
