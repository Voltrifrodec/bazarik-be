package sk.umb.dvestodola.bazarik.authentication.core;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sk.umb.dvestodola.bazarik.authentication.service.AuthenticationService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
	private final DemoAuthenticationEntryPoint demoAuthenticationEntryPoint;
	private final AuthenticationService authenticationService;

	public WebSecurityConfig(DemoAuthenticationEntryPoint demoAuthenticationEntryPoint,
			AuthenticationService authenticationService) {
		this.demoAuthenticationEntryPoint = demoAuthenticationEntryPoint;
		this.authenticationService = authenticationService;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable()
				.cors(withDefaults())
				.authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
				.exceptionHandling().authenticationEntryPoint(demoAuthenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilterBefore(new DemoAuthenticationFilter(authenticationService),
						UsernamePasswordAuthenticationFilter.class)
				.httpBasic(withDefaults())
				.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
				.requestMatchers(HttpMethod.POST, "/api/token")
				.requestMatchers("/swagger-ui/**",
						"/v3/api-docs/swagger-config",
						"/v3/api-docs",
						"/swagger-resources/**");
	}
}