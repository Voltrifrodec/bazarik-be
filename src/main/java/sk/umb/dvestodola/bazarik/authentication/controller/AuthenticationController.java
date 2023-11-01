package sk.umb.dvestodola.bazarik.authentication.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import sk.umb.dvestodola.bazarik.authentication.service.AuthenticationService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@RestController
public class AuthenticationController {
	private final String AUTHORIZATION_HEADER = "Authorization";
	private final AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/api/token")
	public void login(
		@RequestHeader(value = AUTHORIZATION_HEADER, required = false) Optional<String> authentication,
		HttpServletResponse response
	) {
		if (authentication.isEmpty()) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return;
		}

		String[] credentials = credentialsDecode(authentication.get());

		String token = authenticationService.authenticate(credentials[0], credentials[1]);

		response.addHeader("Access-Control-Expose-Headers", AUTHORIZATION_HEADER);
		response.addHeader(AUTHORIZATION_HEADER, "Bearer " + token);
		response.setStatus(HttpStatus.OK.value());
	}

	@PostMapping("/api/token/check")
	public boolean checkToken(
		@RequestHeader(value = AUTHORIZATION_HEADER, required = false) Optional<String> auth,
		HttpServletResponse response
	) {
		if (auth.isPresent()) {
			System.out.println("Volá validate admin token.");
			
			if (this.authenticationService.validateAdminToken(auth.get())) {
				response.setStatus(HttpStatus.OK.value());
				return true;
			}
		}
		response.setStatus(HttpStatus.FORBIDDEN.value());
		return false;
	}

	private static String[] credentialsDecode(String authorization) {
		String base64Credentials = authorization.substring("Basic".length()).trim();
		byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
		String credentials = new String(credDecoded, StandardCharsets.UTF_8);

		return credentials.split(":", 2);
	}

	@DeleteMapping("/api/token")
	public void logoff(@RequestHeader(value = AUTHORIZATION_HEADER, required = true) Optional<String> authentication) {
		String token = authentication.get().substring("Bearer".length()).trim();
		authenticationService.tokenRemove(token);
	}
}
