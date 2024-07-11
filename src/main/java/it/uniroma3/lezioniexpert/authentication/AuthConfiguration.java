package it.uniroma3.lezioniexpert.authentication;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import static it.uniroma3.lezioniexpert.model.Credentials.ADMIN_ROLE;
import static it.uniroma3.lezioniexpert.model.Credentials.DEFAULT_ROLE;
import static it.uniroma3.lezioniexpert.model.Credentials.PROFESSOR_ROLE;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

	@Autowired
	private DataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
		.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}

//	@Bean
//	protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception{
//		httpSecurity
//		.csrf().and().cors().disable()
//		.authorizeHttpRequests()
//		//                .requestMatchers("/**").permitAll()
//		// chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini
//		.requestMatchers(HttpMethod.GET,"/","/login","/registerProfessor",  "/register","/css/**", "/images/**").permitAll()
//		// chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register 
//		.requestMatchers(HttpMethod.POST,"/registerProfessor", "/register", "/login").permitAll()
//		.requestMatchers(HttpMethod.GET,"/announcements","/subjects","/announcement/**","/announcementsOfSubject/**","/success","/professors","/professor/**","/profilePage").hasAnyAuthority(PROFESSOR_ROLE,ADMIN_ROLE,DEFAULT_ROLE)
//		.requestMatchers(HttpMethod.POST,"").hasAnyAuthority(PROFESSOR_ROLE,ADMIN_ROLE,DEFAULT_ROLE)
//		.requestMatchers(HttpMethod.GET,"/formNewAnnouncement","/removeAnnouncement/**","/formNewEducation/**","/removeEducation/**").hasAnyAuthority(PROFESSOR_ROLE,ADMIN_ROLE)
//		.requestMatchers(HttpMethod.POST,"/announcement","/newEducation/**").hasAnyAuthority(PROFESSOR_ROLE,ADMIN_ROLE)
//		.requestMatchers(HttpMethod.GET,"/formNewProfessor","/removeProfessor/**","/setProfessor/**","/removeReview/**").hasAnyAuthority(ADMIN_ROLE)
//		.requestMatchers(HttpMethod.POST,"/professor").hasAnyAuthority(ADMIN_ROLE)
//		.requestMatchers(HttpMethod.GET,"/formNewReview/**").hasAnyAuthority(DEFAULT_ROLE,ADMIN_ROLE)
//		.requestMatchers(HttpMethod.POST,"/newReview/**").hasAnyAuthority(DEFAULT_ROLE,ADMIN_ROLE)
//		// tutti gli utenti autenticati possono accere alle pagine rimanenti 
//		.anyRequest().authenticated()
//		// LOGIN: qui definiamo il login
//		.and().formLogin()
//		.loginPage("/login")
//		.permitAll()
//		.defaultSuccessUrl("/success", true)
//		.failureUrl("/login?error=true")
//		// LOGOUT: qui definiamo il logout
//		.and()
//		.logout()
//		// il logout è attivato con una richiesta GET a "/logout"
//		.logoutUrl("/logout")
//		// in caso di successo, si viene reindirizzati alla home
//		.logoutSuccessUrl("/")
//		.invalidateHttpSession(true)
//		.deleteCookies("JSESSIONID")
//		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//		.clearAuthentication(true).permitAll();
//		return httpSecurity.build();
//	}
	@Bean
	protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception{
		httpSecurity
		.csrf(withDefaults()).cors(cors -> cors.disable())
		.authorizeHttpRequests(requests -> requests
				//                .requestMatchers("/**").permitAll()
				.requestMatchers(HttpMethod.GET,"/","/login","/registerProfessor",  "/register","/css/**", "/images/**").permitAll()
				// chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register 
				.requestMatchers(HttpMethod.POST,"/registerProfessor", "/register", "/login").permitAll()
				.requestMatchers(HttpMethod.GET,"/announcements","/subjects","/announcement/**","/announcementsOfSubject/**","/success","/professors","/professor/**","/profilePage").hasAnyAuthority(PROFESSOR_ROLE,ADMIN_ROLE,DEFAULT_ROLE)
				.requestMatchers(HttpMethod.POST,"").hasAnyAuthority(PROFESSOR_ROLE,ADMIN_ROLE,DEFAULT_ROLE)
				.requestMatchers(HttpMethod.GET,"/formNewAnnouncement","/removeAnnouncement/**","/formNewEducation/**","/removeEducation/**").hasAnyAuthority(PROFESSOR_ROLE,ADMIN_ROLE)
				.requestMatchers(HttpMethod.POST,"/announcement","/newEducation/**").hasAnyAuthority(PROFESSOR_ROLE,ADMIN_ROLE)
				.requestMatchers(HttpMethod.GET,"/formNewProfessor","/removeProfessor/**","/setProfessor/**","/removeReview/**").hasAnyAuthority(ADMIN_ROLE)
				.requestMatchers(HttpMethod.POST,"/professor").hasAnyAuthority(ADMIN_ROLE)
				.requestMatchers(HttpMethod.GET,"/formNewReview/**").hasAnyAuthority(DEFAULT_ROLE,ADMIN_ROLE)
				.requestMatchers(HttpMethod.POST,"/newReview/**").hasAnyAuthority(DEFAULT_ROLE,ADMIN_ROLE)
				// tutti gli utenti autenticati possono accere alle pagine rimanenti 
				.anyRequest().authenticated()).formLogin(login -> login
						.loginPage("/login")
						.permitAll()
						.defaultSuccessUrl("/success", true)
						.failureUrl("/login?error=true"))
		.logout(logout -> logout
				// il logout è attivato con una richiesta GET a "/logout"
				.logoutUrl("/logout")
				// in caso di successo, si viene reindirizzati alla home
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.clearAuthentication(true).permitAll())
		.exceptionHandling(exceptionHandling -> exceptionHandling
				.accessDeniedHandler((request, response, accessDeniedException) -> {
					// Reindirizza all'utente alla pagina di login in caso di AccessDeniedException
					response.sendRedirect("/login");
				})
				);

		return httpSecurity.build();
	}


}
