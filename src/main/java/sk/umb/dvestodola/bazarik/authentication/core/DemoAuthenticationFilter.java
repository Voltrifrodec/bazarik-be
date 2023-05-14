package sk.umb.dvestodola.bazarik.authentication.core;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import sk.umb.dvestodola.bazarik.authentication.service.AuthenticationService;
import sk.umb.dvestodola.bazarik.authentication.service.UserRolesDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DemoAuthenticationFilter extends OncePerRequestFilter {
	private final AuthenticationService authenticationService;

	public DemoAuthenticationFilter(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		String authorizationHeader = request.getHeader("authorization");

		if (!StringUtils.hasLength(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
			SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_USER");
			List<SimpleGrantedAuthority> roles = new ArrayList<>();
			roles.add(role);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("user", null, roles);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
			// throw new AuthenticationCredentialsNotFoundException("Authentication failed!");
			return;
		}

		String token = tokenExtract(authorizationHeader);
		UserRolesDto userRoles = authenticationService.authenticate(token);

		List<SimpleGrantedAuthority> roles = userRoles.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userRoles.getUserName(), null, roles);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}

	private static String tokenExtract(String bearerToken) {
		return bearerToken.substring("Bearer".length()).trim();
	}

}
