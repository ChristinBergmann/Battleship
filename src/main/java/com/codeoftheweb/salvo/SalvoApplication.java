package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;

import static org.hibernate.criterion.Restrictions.and;


@SpringBootApplication
public class SalvoApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
									  GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository,
									  ShipRepository shipRepository,
									  ScoreRepository scoreRepository) {


		return (args) -> {


			// saves a few players
			Player player1 = new Player("player1","player1@example.com", passwordEncoder().encode("player1"));
			Player player2 = new Player("player2","player2@example.com", passwordEncoder().encode("player2"));
			Player player3 = new Player("player3","player3@example.com",passwordEncoder().encode("player3"));
			Player player4 = new Player("player4","player4@example.com",passwordEncoder().encode("player4"));

			playerRepository.save(player1);
			playerRepository.save(player2);
			playerRepository.save(player3);
			playerRepository.save(player4);


			//saves a few games
			Game game1 = new Game(new Date(2019-12-5));
			Game game2 = new Game (new Date(2019-12-5));
			Game game3 = new Game(new Date(2019-12-5));
			Game game4 = new Game(new Date());

			game2.plusSeconds(3600);
			game3.plusSeconds(7200);

			gameRepository.save(game1);
			System.out.println(game1);
			gameRepository.save(game2);
			gameRepository.save(game3);
			gameRepository.save(game4);



			//Game(round) 1
			GamePlayer gamePlayer1 = new GamePlayer();
			GamePlayer gamePlayer2 = new GamePlayer();

			game1.addGamePlayer(gamePlayer1);
			player1.addGamePlayer(gamePlayer1);
			gamePlayer1.setCreationDate(game1.getCreationDate());

			game1.addGamePlayer(gamePlayer2);
			player2.addGamePlayer(gamePlayer2);
			gamePlayer2.setCreationDate(game1.getCreationDate());


			//Game(round) 2
			GamePlayer gamePlayer3 = new GamePlayer();
			GamePlayer gamePlayer4 = new GamePlayer();

			game2.addGamePlayer(gamePlayer3);
			player1.addGamePlayer(gamePlayer3);
			gamePlayer3.setCreationDate(game2.getCreationDate());

			game2.addGamePlayer(gamePlayer4);
			player2.addGamePlayer(gamePlayer4);
			gamePlayer4.setCreationDate(game2.getCreationDate());


			//Game(round) 3
			GamePlayer gamePlayer5 = new GamePlayer();
			GamePlayer gamePlayer6 = new GamePlayer();

			game3.addGamePlayer(gamePlayer5);
			player3.addGamePlayer(gamePlayer5);
			gamePlayer5.setCreationDate(game3.getCreationDate());

			game3.addGamePlayer(gamePlayer6);
			player4.addGamePlayer(gamePlayer6);
			gamePlayer6.setCreationDate(game3.getCreationDate());


			//Game(round) 4
			GamePlayer gamePlayer7 = new GamePlayer();
			GamePlayer gamePlayer8 = new GamePlayer();

			game4.addGamePlayer(gamePlayer7);
			player3.addGamePlayer(gamePlayer7);
			gamePlayer7.setCreationDate(game4.getCreationDate());

			game4.addGamePlayer(gamePlayer8);
			player4.addGamePlayer(gamePlayer8);
			gamePlayer8.setCreationDate(game4.getCreationDate());

			gamePlayerRepository.save(gamePlayer1);
			gamePlayerRepository.save(gamePlayer2);
			gamePlayerRepository.save(gamePlayer3);
			gamePlayerRepository.save(gamePlayer4);
			gamePlayerRepository.save(gamePlayer5);
			gamePlayerRepository.save(gamePlayer6);
			gamePlayerRepository.save(gamePlayer7);
			gamePlayerRepository.save(gamePlayer8);


			//saves + creates ships
			final String CARRIER = "carrier";
			final String BATTLESHIP = "battleship";
			final String SUBMARINE = "submarine";
			final String DESTROYER = "destroyer";
			final String PATROLBOAT = "patrolboat";

			Ship ship1 = new Ship(DESTROYER, Arrays.asList("H2", "H3", "H4"));
			gamePlayer1.addShip(ship1);
			shipRepository.save(ship1);

			Ship ship2 = new Ship(DESTROYER, Arrays.asList("H2", "H3", "H4"));
			gamePlayer1.addShip(ship1);
			shipRepository.save(ship1);


		};
	}

}


// security login ----authentication
/*@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Player player = playerRepository.findByUserEmail(inputName);
			if (player != null) {
				return new User(player.getUserEmail(),player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}
}*/

//Authority ----login
/*@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				//defines which page the user is allowed to see: add paths of url`s needed
				.antMatchers("/api/login").hasAuthority("USER");


		*//*@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/fonts/**").permitAll().anyRequest().authenticated()
					.and().formLogin().loginPage("/login.jsp").permitAll();*//*


		//how to login
		http.formLogin()
				.usernameParameter("userName")
				.passwordParameter("password")
				.loginPage("/api/login")
				.permitAll();

		http.logout().logoutUrl("/api/logout");


		// turns off checking for CSRF tokens
        http.csrf().disable();

		// if user is not authenticated   --- it sends an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful  --- it clears the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails  --- it sends an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful ---it sends a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
		}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}*/

