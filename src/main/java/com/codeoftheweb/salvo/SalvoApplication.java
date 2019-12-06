package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository repository,
									  GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository) {

		return (args) -> {

			// saves a couple of players
			Player player1 = new Player("player1@example.com", "player1");
			Player player2 = new Player("player2@example.com", "player2");
			Player player3 = new Player("player3@example.com","player3");

			repository.save(player1);
			repository.save(player2);
			repository.save(player3);


			//saves a couple of games
			Game game = new Game(new Date());
			gameRepository.save(game);

			Game game1 = new Game(new Date(2019-12- 5));

			Game game2 = new Game (new Date(2019-12-5));
			game2.plusSeconds(3600);

			Game game3 = new Game(new Date(2019-12-5));
			game3.plusSeconds(7200);

			gameRepository.save(game1);
			gameRepository.save(game2);
			gameRepository.save(game3);


			//GamePlayer gamePlayer1 = new GamePlayer();

		};
	}

}
