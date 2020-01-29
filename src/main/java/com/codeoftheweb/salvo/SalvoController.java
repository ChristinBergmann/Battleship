package com.codeoftheweb.salvo;

import ch.qos.logback.core.net.SyslogOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.*;


@CrossOrigin(origins = "http://127.0.0.1:5500/")
@RestController

@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private PlayerRepository playerRepo;

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private GamePlayerRepository gamePlayerRepo;

    @Autowired
    private ShipRepository shipRepo;

    @Autowired
    private ScoreRepository scoreRepo;
    private java.lang.Object Object;

    @RequestMapping("/games")
    public List<Object> getAllGames(Authentication authentication) {
        List<Object> games = new ArrayList<>();

        gameRepo.findAll().forEach(oneGame -> {
            Map<String, Object> gameMap = new HashMap<>();
            gameMap.put("Game_created", oneGame.getCreationDate());
            gameMap.put("Game_Id", oneGame.getId());
            gameMap.put("GamePlayers", gamePlayersInfo(oneGame));

            games.add(gameMap);
        });
        return games;
    }

    public List<Object> gamePlayersInfo(Game game) {
        List<Object> gamePlayers = new ArrayList();

        game.getGamePlayers().forEach(gp -> {
            Map<String, Object> gp_info = new HashMap<>();
            gp_info.put("GamePlayer_Id", gp.getId());
            gp_info.put("Player", playerInfo(gp));
            gamePlayers.add(gp_info);
        });
        return gamePlayers;
    }

    public Object playerInfo(GamePlayer gameplayer) {

        Map<String, Object> pl_info = new HashMap<>();
        pl_info.put("Player_Id", gameplayer.getPlayer().getId());
        pl_info.put("Player_Username", gameplayer.getPlayer().getUserName());
        pl_info.put("Score", scoreInfo(gameplayer));
        return pl_info;

    }

    public List<Object> shipsInfo(GamePlayer gameplayer) {
        List<Object> ships = new ArrayList<>();

        gameplayer.getShips().forEach(ship -> {
            Map<String, Object> ship_info = new HashMap<>();
            ship_info.put("Ship", ship.getId());
            ship_info.put("Type", ship.getType());
            ship_info.put("Location", ship.getLocations());
            ships.add(ship_info);
        });
        return ships;
    }

    public List<Object> shotsInfo(GamePlayer gameplayer) {
        List<Object> shots = new ArrayList<>();

        gameplayer.getShots().forEach(sh -> {
            Map<String, Object> shot_info = new HashMap<>();
            shot_info.put("Shot", sh.getId());
            shot_info.put("Shot_fired", sh.getLocations());
            shot_info.put("Turn", sh.getTurn());
            shots.add(shot_info);
        });
        return shots;
    }

    public Object scoreInfo(GamePlayer gameplayer) {
        Map<String, Object> score_info = new HashMap<>();

        gameplayer.getGame().getScores().forEach(sc -> {
            if (sc.getPlayer().getUserName().equals(gameplayer.getPlayer().getUserName())) {
                score_info.put("Score_current", sc.getScore());
            } else {
                score_info.put("Score_opponent", sc.getScore());
            }
        });
        return score_info;

    }

    /*******______________________________________ GAME VIEW PAGE _____________________________________*******/

    @RequestMapping("/game_view/{gamePlayerId}")
    public Object findPlayerGame(@PathVariable Long gamePlayerId, Authentication authentication) {

        GamePlayer gameplayer = gamePlayerRepo.findById(gamePlayerId).get();
        System.out.println("authenticated name " + authentication.getName());
        System.out.println("gameplayer id from param " + gamePlayerId);
        System.out.println("player id from gameplayer param " + gameplayer.getPlayer().getId());

        long playerID = gameplayer.getPlayer().getId();
        long userID = playerRepo.findByUserName(authentication.getName()).getId();
        System.out.println("logged in user id " + userID);
        System.out.println("logged in player id " + playerID);

        if (playerID == userID) {
            //return new ResponseEntity<>(gamePlayerRepo.findById(gamePlayerId)}

            Game game = gameplayer.getGame();

            Map<String, Object> gameInfo = new HashMap<>();
            gameInfo.put("Game_Id", game.getId());
            gameInfo.put("Game_created", game.getCreationDate());
            //gameInfo.put("GamePlayers", gamePlayersInfo(game));
            gameInfo.put("Ships_mine", shipsInfo(gameplayer));
            gameInfo.put("Shots_mine", shotsInfo(gameplayer));
            gameInfo.put("Scores", scoreInfo(gameplayer));

            game.getGamePlayers().forEach(x -> {
                if (x != gameplayer) {
                    gameInfo.put("Hits_mine", shotsInfo(x));
                }
            });
            return gameInfo;
        }
        return null;
    }


    @RequestMapping("/leaderboard")
    public List<Object> getAllScores() {
        List<Object> rankings = new ArrayList<>();

        playerRepo.findAll().forEach(pl -> {
            Map<String, Object> pl_list = new HashMap<>();
            pl_list.put("Player", pl.getUserName());
            pl_list.put("Scores", sc_Info(pl));
            rankings.add(pl_list);
            //System.out.println(rankings);
        });
        return rankings;
    }

    public Object sc_Info(Player player) {

        Map<String, Object> score_info = new HashMap<>();
        Set<Score> scores = player.getScores();
        Double total = 0.0;

        for (Score sc : scores) {
            if (sc.getScore() == 1.0) {
                score_info.put("Win", sc.getScore());
            } else if (sc.getScore() == 0.5) {
                score_info.put("Tie", sc.getScore());
            } else {
                score_info.put("Lost", sc.getScore());
            }
            total += sc.getScore();
            score_info.put("Total", total);
        }
        return score_info;
    }

    /*********______________________________ Players logIn Auth (?)If Guests used __________________________*******/

    @RequestMapping("/players")
    public Player getAll(Authentication authentication) {
        if (isGuest(authentication)) return null;

        else {
            return playerRepo.findByUserName(authentication.getName());
        }
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null;
    }


    /*********____________________________________ Players SignUp Auth ________________________________*******/

    @Bean
    public PasswordEncoder passwordEncoder2() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(

            @RequestParam String userName, @RequestParam String email, @RequestParam String password) {

        if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Ooopsie Data is missing", HttpStatus.BAD_REQUEST);
        }
        if (playerRepo.findByUserName(userName) != null) {
            return new ResponseEntity<>("Username is already in use", HttpStatus.CONFLICT);
        }
        if (playerRepo.findByEmail(email) != null) {
            return new ResponseEntity<>("Email is already in use", HttpStatus.CONFLICT);
        }
        playerRepo.save(new Player(userName, email, passwordEncoder2().encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*********___________________________________ Add a GamePlayer to Game _________________________________*******/

    @RequestMapping(path = "/game/{gameId}/players", method = RequestMethod.POST)
    public ResponseEntity<Object> addPlayer(
            @PathVariable Long gameId, Authentication authentication) {

        //System.out.println(gameId);

        Game currentGame = gameRepo.findById(gameId).get();
        Player currentPlayer = playerRepo.findByUserName(authentication.getName());
        //System.out.println("current player id in join game api " + currentPlayer.getId());

        if (authentication == null) {
            return new ResponseEntity<>("You are not logged in!", HttpStatus.FORBIDDEN);
        }

        if (currentGame == null) {
            return new ResponseEntity<>("Game does not exist!", HttpStatus.FORBIDDEN);
        }

        if (currentGame.getGamePlayers().size() > 1) {
            return new ResponseEntity<>("There are already 2 Game Players!", HttpStatus.FORBIDDEN);
        }

            GamePlayer gamePlayerNew = new GamePlayer(new Date());
            currentPlayer.addGamePlayer(gamePlayerNew);
            currentGame.addGamePlayer(gamePlayerNew);

            Map<String, Object> addPlayer = new LinkedHashMap<>();

            addPlayer.put("Game_current", gamePlayerNew.getGame());
            addPlayer.put("GP_Id_current", gamePlayerNew.getId());

            gamePlayerRepo.save(gamePlayerNew);
            playerRepo.save(currentPlayer);
            gameRepo.save(currentGame);


            //System.out.println(gamePlayerNew.getPlayer().getUserName());
            //System.out.println(gamePlayerNew.getId());
            //System.out.println(gamePlayerNew.getGame().getId());

            //currentGame.getGamePlayers().forEach(gamePlayer -> System.out.println(gamePlayer.getPlayer().getUserName()));

        return new ResponseEntity<>(addPlayer, HttpStatus.CREATED);
    }


    /*********______________________________________ New Game Auth _______________________________________*******/

    @RequestMapping(method = RequestMethod.POST, value = "/games")
    public ResponseEntity<Map<String, java.lang.Object>> createGame(Authentication authentication) {


        Game game = new Game();
        System.out.println(authentication);
        if (authentication != null) {
            Player player = playerRepo.findByUserName(authentication.getName());

            Map<String, Object> newGame;
            if (player != null) {
                newGame = new LinkedHashMap<>();

                game.setCreationDate(new Date());
                gameRepo.save(game);

                GamePlayer gamePlayer = new GamePlayer();
                gamePlayer.setPlayer(player);
                gamePlayer.setGame(game);
                gamePlayerRepo.save(gamePlayer);

                newGame.put("Game_id", game.getId());
                newGame.put("Game_created", game.getCreationDate());
                newGame.put("Game_GPs", game.getGamePlayers());

                return new ResponseEntity<>(createMap("game", newGame), HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(createMap("error", "Log in"), HttpStatus.UNAUTHORIZED);
            }
        } else {
                return new ResponseEntity<>(createMap("error", "Log in"), HttpStatus.UNAUTHORIZED);
                }
    }

    public Map<String, Object> createMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        return map;
    }
}













