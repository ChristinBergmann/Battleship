package com.codeoftheweb.salvo;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.core.CollectionFactory.createMap;


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
    public List<Object> getAllGames() {
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

        gameplayer.getShots().forEach(x -> {
            Map<String, Object> shot_info = new HashMap<>();
            shot_info.put("Shot", x.getId());
            shot_info.put("Shot_fired", x.getLocations());
            shot_info.put("Turn", x.getTurn());
            shots.add(shot_info);
        });
        return shots;
    }

    public Object scoreInfo(GamePlayer gameplayer) {

        Map<String, Object> score_info = new HashMap<>();
        gameplayer.getGame().getScores().forEach(score -> {

            if (score.getPlayer().getUserName().equals(gameplayer.getPlayer().getUserName())) {
                score_info.put("Score_current", score.getScore());
            } else {
                score_info.put("Score_opponent", score.getScore());
            }
        });
        return score_info;

    }

    /*******______________________________________ GAME VIEW PAGE _____________________________________*******/

    @RequestMapping("/game_view/{gamePlayerId}")
    public Object findPlayerGame(@PathVariable Long gamePlayerId) {

        GamePlayer gameplayer = gamePlayerRepo.findById(gamePlayerId).get();
        Game game = gameplayer.getGame();

        Map<String, Object> gameInfo = new HashMap<>();
        gameInfo.put("Game_Id", game.getId());
        gameInfo.put("Game_created", game.getCreationDate());
        gameInfo.put("GamePlayers", gamePlayersInfo(game));
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

    @RequestMapping("/leaderboard")
    public List<Object> getAllScores() {
        List<Object> rankings = new ArrayList<>();

        playerRepo.findAll().forEach(pl -> {
            Map<String, Object> pl_list = new HashMap<>();
            pl_list.put("Player", pl.getUserName());
            pl_list.put("Scores", sc_Info(pl));
            rankings.add(pl_list);
            System.out.println(rankings);

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
        ;
        return score_info;
    }

    /*********___________ Players logIn Auth ________*******/

    @RequestMapping("/players")
    public Player getAll(Authentication authentication) {
        if (isGuest(authentication)) return null;

        else {
            return playerRepo.findByUserName(authentication.name());
        }
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null;
    }

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(

            @RequestParam String userName, @RequestParam String password) {

        if (userName.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Ooopsie Data is missing", HttpStatus.FORBIDDEN);
        }

        if (playerRepo.findByUserName(userName) != null) {
            return new ResponseEntity<>("Userame is already in use", HttpStatus.FORBIDDEN);
        }

        playerRepo.save(new Player(userName, password));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/games")
    public ResponseEntity<Map<String, java.lang.Object>> createGame(Authentication authentication) {


        Game game = new Game();

        if (authentication != null) {
            Player player = playerRepo.findByUserName(authentication.name());

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
                newGame.put("Game_GP", game.getGamePlayers());

                return new ResponseEntity<>(createMap("game", newGame), HttpStatus.CREATED);

//                return new ResponseEntity (Map<String,Object> createMap(String, Object),
//                        HttpStatus.CREATED);
//                }
            }
        }
        return null;
    }
    public Map<String, Object> createMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        return map;
    }
}







