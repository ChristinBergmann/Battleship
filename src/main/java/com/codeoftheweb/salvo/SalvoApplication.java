package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
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
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.context.WebApplicationContext;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;



@SpringBootApplication
public class SalvoApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class);
	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SalvoApplication.class);
	}
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepo,
									  GameRepository gameRepo,
									  GamePlayerRepository gamePlayerRepo,
									  ShipRepository shipRepo,
									  ShotRepository shotRepo,
									  ScoreRepository scoreRepo) {


		return (args) -> {


			// saves a few players
			Player player1 = new Player("player1", "player1@example.com", passwordEncoder.encode("player1"));
			Player player2 = new Player("player2", "player2@example.com", passwordEncoder.encode("player2"));
			Player player3 = new Player("player3", "player3@example.com", passwordEncoder.encode("player3"));
			Player player4 = new Player("player4", "player4@example.com", passwordEncoder.encode("player4"));

			playerRepo.save(player1);
			playerRepo.save(player2);
			playerRepo.save(player3);
			playerRepo.save(player4);


			//saves a few(4) games
			Game game1 = new Game(new Date(2019 - 12 - 5));
			Game game2 = new Game(new Date(2019 - 12 - 5));
			Game game3 = new Game(new Date(2019 - 12 - 5));
			Game game4 = new Game(new Date());

			game2.plusSeconds(3600);
			game3.plusSeconds(7200);
			//game4.plusSeconds(10800);

			gameRepo.save(game1);
			gameRepo.save(game2);
			gameRepo.save(game3);
			gameRepo.save(game4);


			//Game 1
			//GamePlayer gamePlayer1 = new GamePlayer();
			GamePlayer gamePlayer2 = new GamePlayer();

			//game1.addGamePlayer(gamePlayer1);
			//player1.addGamePlayer(gamePlayer1);
			//gamePlayer1.setCreationDate(game1.getCreationDate());

			game1.addGamePlayer(gamePlayer2);
			player2.addGamePlayer(gamePlayer2);
			gamePlayer2.setCreationDate(game1.getCreationDate());


			//Game 2
			GamePlayer gamePlayer3 = new GamePlayer();
			GamePlayer gamePlayer4 = new GamePlayer();

			game2.addGamePlayer(gamePlayer3);
			player3.addGamePlayer(gamePlayer3);
			gamePlayer3.setCreationDate(game2.getCreationDate());

			game2.addGamePlayer(gamePlayer4);
			player4.addGamePlayer(gamePlayer4);
			gamePlayer4.setCreationDate(game2.getCreationDate());


			//Game 3
			GamePlayer gamePlayer5 = new GamePlayer();
			GamePlayer gamePlayer6 = new GamePlayer();

			game3.addGamePlayer(gamePlayer5);
			player3.addGamePlayer(gamePlayer5);
			gamePlayer5.setCreationDate(game3.getCreationDate());

			game3.addGamePlayer(gamePlayer6);
			player4.addGamePlayer(gamePlayer6);
			gamePlayer6.setCreationDate(game3.getCreationDate());


			//Game 4
			GamePlayer gamePlayer7 = new GamePlayer();
			GamePlayer gamePlayer8 = new GamePlayer();

			game4.addGamePlayer(gamePlayer7);
			player3.addGamePlayer(gamePlayer7);
			gamePlayer7.setCreationDate(game4.getCreationDate());

			game4.addGamePlayer(gamePlayer8);
			player4.addGamePlayer(gamePlayer8);
			gamePlayer8.setCreationDate(game4.getCreationDate());

			//saves the game players
			//gamePlayerRepo.save(gamePlayer1);
			gamePlayerRepo.save(gamePlayer2);
			gamePlayerRepo.save(gamePlayer3);
			gamePlayerRepo.save(gamePlayer4);
			gamePlayerRepo.save(gamePlayer5);
			gamePlayerRepo.save(gamePlayer6);
			gamePlayerRepo.save(gamePlayer7);
			gamePlayerRepo.save(gamePlayer8);


			//////-------------creating + adding ships-----------------//////

			//saves + creates ships for gp1 -game1
//			Ship ship1 = new Ship("CARRIER", Arrays.asList("H3", "H4", "H5", "H6", "H7"), gamePlayer1);
//			Ship ship2 = new Ship("BATTLESHIP", Arrays.asList("E4", "E5", "E6", "E7"), gamePlayer1);
//			Ship ship3 = new Ship("DESTROYER", Arrays.asList("H1", "I1", "J1"), gamePlayer1);
//			Ship ship4 = new Ship("SUBMARINE", Arrays.asList("A1", "A2", "A3"), gamePlayer1);
//			Ship ship5 = new Ship("PATROLBOAT", Arrays.asList("A8", "B8"), gamePlayer1);
//			shipRepo.save(ship1);
//			shipRepo.save(ship2);
//			shipRepo.save(ship3);
//			shipRepo.save(ship4);
//			shipRepo.save(ship5);


			//saves + creates ships for gp2 -game1
			Ship ship6 = new Ship("CARRIER", Arrays.asList("H1", "H2", "H3", "H4", "H5"), gamePlayer2);
			Ship ship7 = new Ship("BATTLESHIP", Arrays.asList("E1", "E2", "E3", "E4"), gamePlayer2);
			Ship ship8 = new Ship("DESTROYER", Arrays.asList("E8", "F8", "G8"), gamePlayer2);
			Ship ship9 = new Ship("SUBMARINE", Arrays.asList("A5", "A6", "A7"), gamePlayer2);
			Ship ship10 = new Ship("PATROLBOAT", Arrays.asList("G3", "G4"), gamePlayer2);
			shipRepo.save(ship6);
			shipRepo.save(ship7);
			shipRepo.save(ship8);
			shipRepo.save(ship9);
			shipRepo.save(ship10);


			//----------game 2------------//

			//saves + creates ships for gp3 -game2
			Ship ship11 = new Ship("CARRIER", Arrays.asList("H1", "H2", "H3", "H4", "H5"), gamePlayer3);
			Ship ship12 = new Ship("BATTLESHIP", Arrays.asList("E1", "E2", "E3", "E4"), gamePlayer3);
			Ship ship13 = new Ship("DESTROYER", Arrays.asList("H2", "I2", "J2"), gamePlayer3);
			Ship ship14 = new Ship("SUBMARINE", Arrays.asList("A5", "A6", "A7"), gamePlayer3);
			Ship ship15 = new Ship("PATROLBOAT", Arrays.asList("G3", "G4"), gamePlayer3);
			shipRepo.save(ship11);
			shipRepo.save(ship12);
			shipRepo.save(ship13);
			shipRepo.save(ship14);
			shipRepo.save(ship15);


			//saves + creates ships for gp4 -game2
			Ship ship16 = new Ship("CARRIER", Arrays.asList("H2", "H3", "H4", "H5", "H6"), gamePlayer4);
			Ship ship17 = new Ship("BATTLESHIP", Arrays.asList("E4", "E5", "E6", "E7"), gamePlayer4);
			Ship ship18 = new Ship("DESTROYER", Arrays.asList("H1", "I1", "J1"), gamePlayer4);
			Ship ship19 = new Ship("SUBMARINE", Arrays.asList("A1", "A2", "A3"), gamePlayer4);
			Ship ship20 = new Ship("PATROLBOAT", Arrays.asList("A8", "B8"), gamePlayer4);
			shipRepo.save(ship16);
			shipRepo.save(ship17);
			shipRepo.save(ship18);
			shipRepo.save(ship19);
			shipRepo.save(ship20);


			//////-------------creating + adding shot rounds-----------------//////

			//saves + creates shots for gp1 -game1
//			Shot shot1 = new Shot(1, Arrays.asList("H2", "H3", "H4"), gamePlayer1);
//			Shot shot2 = new Shot(2, Arrays.asList("E4", "E5", "E6"), gamePlayer1);
//			Shot shot3 = new Shot(3, Arrays.asList("H1", "I1", "J1"), gamePlayer1);
//			Shot shot4 = new Shot(4, Arrays.asList("A1", "E4", "A3"), gamePlayer1);
//			Shot shot5 = new Shot(5, Arrays.asList("A8", "B8", "G8"), gamePlayer1);
//			shotRepo.save(shot1);
//			shotRepo.save(shot2);
//			shotRepo.save(shot3);
//			shotRepo.save(shot4);
//			shotRepo.save(shot5);

			//saves + creates shots for gp2 -game1
			Shot shot1a = new Shot(1, Arrays.asList("C5", "I3", "H4"), gamePlayer2);
			Shot shot2a = new Shot(2, Arrays.asList("C7", "E7", "E8"), gamePlayer2);
			Shot shot3a = new Shot(3, Arrays.asList("H1", "I2", "J1"), gamePlayer2);
			Shot shot4a = new Shot(4, Arrays.asList("A1", "A2", "A3"), gamePlayer2);
			Shot shot5a = new Shot(5, Arrays.asList("F1", "B8", "G8"), gamePlayer2);
			shotRepo.save(shot1a);
			shotRepo.save(shot2a);
			shotRepo.save(shot3a);
			shotRepo.save(shot4a);
			shotRepo.save(shot5a);

			//////-------------creating + adding scores rounds-----------------//////
			//1.0-win//0.5-tie//0.0-lost//
			/*Score score1 = new Score(new Date(), 1.0);
			player1.addScore(score1);
			game1.addScore((score1));
			scoreRepo.save(score1);*/

			Score score2 = new Score(new Date(), 0.0);
			player2.addScore(score2);
			game1.addScore((score2));
			scoreRepo.save(score2);

			Score score3 = new Score(new Date(), 0.5);
			player3.addScore(score3);
			game2.addScore((score3));
			scoreRepo.save(score3);

			Score score4 = new Score(new Date(), 0.5);
			player4.addScore(score4);
			game2.addScore((score4));
			scoreRepo.save(score4);

			Score score5 = new Score(new Date(), 1.0);
			player2.addScore(score5);
			game2.addScore((score5));
			scoreRepo.save(score5);

//			Score score6 = new Score(new Date(), 0.5);
//			player1.addScore(score6);
//			game3.addScore((score6));
//			scoreRepo.save(score6);

		};

	}


	@Configuration
	static
	class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

		@Autowired
		private PlayerRepository playerRepo;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(username -> {

				Player player = playerRepo.findByUserName(username);
				//System.out.println(player.getUserName());
				User currentUser = new User(player.getUserName(), player.getPassword(), AuthorityUtils.createAuthorityList("USER"));
				//System.out.println(currentUser);
				return currentUser;
			});

		}
	}
}
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private WebApplicationContext applicationContext;
		private SalvoApplication.WebSecurityConfiguration webSecurityConfiguration;

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.csrf().disable()
					.authorizeRequests()
					.antMatchers("/web/main.css").permitAll()
					.antMatchers("/web/board.css").permitAll()
					.antMatchers("/web/manager*").permitAll()
					.antMatchers(	"/web/games*").hasAuthority("USER")
					.antMatchers("/web/game*").hasAuthority("USER")
					.antMatchers(	"/api/manager*").permitAll()
					.antMatchers("/api/register").permitAll()
					.antMatchers("/api/players*").permitAll()
					.antMatchers("/api/games*").permitAll()
					.antMatchers(	"/api/leaderboard*").permitAll()
					.antMatchers(	"/api/game*").permitAll()
					.antMatchers("/api/game_view*").permitAll()
					.anyRequest().fullyAuthenticated()
					.and()
					.formLogin()
					.usernameParameter("userName")
					.passwordParameter("password")
					.loginPage("/api/login")
					.and()
					.logout()
					.logoutUrl("/api/logout");


			// if user is not authenticated, just send an authentication failure response
			http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

			// if login is successful, just clear the flags asking for authentication
			http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

			// if login fails, just send an authentication failure response
			http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

			// if logout is successful, just send a success response
			http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

		}

		private void clearAuthenticationAttributes(HttpServletRequest request) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			}
		}
	}



