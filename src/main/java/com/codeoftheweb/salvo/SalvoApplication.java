package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;


import java.util.Arrays;
import java.util.Date;

import static java.time.ZonedDateTime.now;


@SpringBootApplication
public class SalvoApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class);
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
			Player player1 = new Player("player1", "player1@example.com", "player1");
			Player player2 = new Player("player2", "player2@example.com", "player2");
			Player player3 = new Player("player3", "player3@example.com", "player3");
			Player player4 = new Player("player4", "player4@example.com", "player4");

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

			//saves the game players
			gamePlayerRepo.save(gamePlayer1);
			gamePlayerRepo.save(gamePlayer2);
			gamePlayerRepo.save(gamePlayer3);
			gamePlayerRepo.save(gamePlayer4);
			gamePlayerRepo.save(gamePlayer5);
			gamePlayerRepo.save(gamePlayer6);
			gamePlayerRepo.save(gamePlayer7);
			gamePlayerRepo.save(gamePlayer8);


			//////-------------creating + adding ships-----------------//////

			//saves + creates ships for gp1 -game1
			Ship ship1 = new Ship("CARRIER", Arrays.asList("H3", "H4", "H5", "H6", "H7"), gamePlayer1);
			Ship ship2 = new Ship("BATTLESHIP", Arrays.asList("E4", "E5", "E6", "E7"), gamePlayer1);
			Ship ship3 = new Ship("DESTROYER", Arrays.asList("H1", "I1", "J1"), gamePlayer1);
			Ship ship4 = new Ship("SUBMARINE", Arrays.asList("A1", "A2", "A3"), gamePlayer1);
			Ship ship5 = new Ship("PATROLBOAT", Arrays.asList("A8", "B8"), gamePlayer1);
			shipRepo.save(ship1);
			shipRepo.save(ship2);
			shipRepo.save(ship3);
			shipRepo.save(ship4);
			shipRepo.save(ship5);


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
			Shot shot1 = new Shot(1, Arrays.asList("H2", "H3", "H4"), gamePlayer1);
			Shot shot2 = new Shot(2, Arrays.asList("E4", "E5", "E6"), gamePlayer1);
			Shot shot3 = new Shot(3, Arrays.asList("H1", "I1", "J1"), gamePlayer1);
			Shot shot4 = new Shot(4, Arrays.asList("A1", "E4", "A3"), gamePlayer1);
			Shot shot5 = new Shot(5, Arrays.asList("A8", "B8", "G8"), gamePlayer1);
			shotRepo.save(shot1);
			shotRepo.save(shot2);
			shotRepo.save(shot3);
			shotRepo.save(shot4);
			shotRepo.save(shot5);

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

			Score score1 = new Score(new Date(),1.0);
			player1.addScore(score1);
			game1.addScore((score1));
			scoreRepo.save(score1);

			Score score2 = new Score(new Date(),0.5);
			player2.addScore(score2);
			game1.addScore((score2));
			scoreRepo.save(score2);

			Score score3 = new Score(new Date(),0.0);
			player1.addScore(score3);
			game1.addScore((score3));
			scoreRepo.save(score3);

		};
	}
}




