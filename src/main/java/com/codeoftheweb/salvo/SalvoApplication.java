package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;



@SpringBootApplication
public class SalvoApplication {

	private long id;

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
									  GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository) {

		return (args) -> {

			// saves a few players
			Player player1 = new Player("player1@example.com", "player1");
			Player player2 = new Player("player2@example.com", "player2");
			Player player3 = new Player("player3@example.com","player3");
			Player player4 = new Player("player4@example.com","player4");

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

		};
	}

}
