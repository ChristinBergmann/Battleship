package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Date;


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


			//saves a few(4) games
			Game game1 = new Game(new Date(2019-12-5));
			Game game2 = new Game (new Date(2019-12-5));
			Game game3 = new Game(new Date(2019-12-5));
			Game game4 = new Game(new Date());

			game2.plusSeconds(3600);
			game3.plusSeconds(7200);
			//game4.plusSeconds(10800);

			gameRepository.save(game1);
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



			//saves + creates ships for gp1 -game1
			Ship ship1 = new Ship("CARRIER", Arrays.asList("H2", "H3", "H4", "H5", "H6"));
			shipRepository.save(ship1);
			gamePlayer1.addShip(ship1);

			Ship ship2 = new Ship("BATTLESHIP", Arrays.asList("E4", "E5", "E6","E7"));
			shipRepository.save(ship2);
			gamePlayer1.addShip(ship2);

			Ship ship3 = new Ship("DESTROYER", Arrays.asList("H1", "I1", "J1"));
			shipRepository.save(ship3);
			gamePlayer1.addShip(ship3);

			Ship ship4 = new Ship("SUBMARINE", Arrays.asList("A1", "A2", "A3"));
			shipRepository.save(ship4);
			gamePlayer1.addShip(ship4);

			Ship ship5 = new Ship("PATROLBOAT", Arrays.asList("A8", "B8"));
			shipRepository.save(ship5);
			gamePlayer1.addShip(ship5);

			//saves + creates ships for gp2 -game1
			Ship ship6 = new Ship("CARRIER", Arrays.asList("H1", "H2", "H3", "H4", "H5"));
			shipRepository.save(ship6);
			gamePlayer2.addShip(ship6);

			Ship ship7 = new Ship("BATTLESHIP", Arrays.asList("E1", "E2", "E3","E4"));
			shipRepository.save(ship7);
			gamePlayer2.addShip(ship7);

			Ship ship8 = new Ship("DESTROYER", Arrays.asList("H2", "I2", "J2"));
			shipRepository.save(ship8);
			gamePlayer2.addShip(ship8);

			Ship ship9 = new Ship("SUBMARINE", Arrays.asList("A5", "A6", "A7"));
			shipRepository.save(ship9);
			gamePlayer2.addShip(ship9);

			Ship ship10 = new Ship("PATROLBOAT", Arrays.asList("G3", "G4"));
			shipRepository.save(ship10);
			gamePlayer2.addShip(ship10);






			//saves + creates ships for gp3 -game2
			Ship ship11 = new Ship("CARRIER", Arrays.asList("H1", "H2", "H3", "H4", "H5"));
			shipRepository.save(ship11);
			gamePlayer3.addShip(ship11);

			Ship ship12 = new Ship("BATTLESHIP", Arrays.asList("E1", "E2", "E3","E4"));
			shipRepository.save(ship12);
			gamePlayer3.addShip(ship12);

			Ship ship13 = new Ship("DESTROYER", Arrays.asList("H2", "I2", "J2"));
			shipRepository.save(ship13);
			gamePlayer3.addShip(ship13);

			Ship ship14 = new Ship("SUBMARINE", Arrays.asList("A5", "A6", "A7"));
			shipRepository.save(ship14);
			gamePlayer3.addShip(ship14);

			Ship ship15 = new Ship("PATROLBOAT", Arrays.asList("G3", "G4"));
			shipRepository.save(ship15);
			gamePlayer3.addShip(ship15);

			//saves + creates ships for gp4 -game2
			Ship ship16 = new Ship("CARRIER", Arrays.asList("H2", "H3", "H4", "H5", "H6"));
			shipRepository.save(ship16);
			gamePlayer4.addShip(ship16);

			Ship ship17 = new Ship("BATTLESHIP", Arrays.asList("E4", "E5", "E6","E7"));
			shipRepository.save(ship17);
			gamePlayer4.addShip(ship17);

			Ship ship18 = new Ship("DESTROYER", Arrays.asList("H1", "I1", "J1"));
			shipRepository.save(ship18);
			gamePlayer4.addShip(ship18);

			Ship ship19 = new Ship("SUBMARINE", Arrays.asList("A1", "A2", "A3"));
			shipRepository.save(ship19);
			gamePlayer4.addShip(ship19);

			Ship ship20 = new Ship("PATROLBOAT", Arrays.asList("A8", "B8"));
			shipRepository.save(ship20);
			gamePlayer4.addShip(ship20);


			//saves the game players
			gamePlayerRepository.save(gamePlayer1);
			gamePlayerRepository.save(gamePlayer2);
			gamePlayerRepository.save(gamePlayer3);
			gamePlayerRepository.save(gamePlayer4);
			gamePlayerRepository.save(gamePlayer5);
			gamePlayerRepository.save(gamePlayer6);
			gamePlayerRepository.save(gamePlayer7);
			gamePlayerRepository.save(gamePlayer8);

			System.out.println(gamePlayer2.toString());
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

