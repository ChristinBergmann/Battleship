package com.codeoftheweb.salvo;

import net.minidev.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    private Object findPlayerGame(@PathVariable Long gamePlayerId, Authentication authentication) {

        GamePlayer gameplayer = gamePlayerRepo.getOne(gamePlayerId);
            System.out.println("authenticated name " + authentication.getName());
            System.out.println("gameplayer id from param " + gamePlayerId);
            System.out.println("player id from gameplayer param " + gameplayer.getPlayer().getId());

        if (authentication.getName() != null) {
            long playerID = gameplayer.getPlayer().getId();
            long userID = playerRepo.findByUserName(authentication.getName()).getId();
                System.out.println("logged in user id " + userID);
                System.out.println("logged in player id " + playerID);
            if (playerID == userID)
                return gamePlayerRepo.findById(gamePlayerId);

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

    /*********_________________________________ Players logIn Auth  _____________________________________*******/

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


    /*********____________________________________ Players SignUp Auth _________________________________*******/

    @Bean
    public PasswordEncoder passwordEncoder2() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @RequestMapping(path = "/players", method = RequestMethod.POST)
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


    /*********______________________________________  create New Game _______________________________________*******/

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    //public ResponseEntity<Map<String, java.lang.Object>> createGame(Authentication authentication) {
    public ResponseEntity<Object> addPlayer(Authentication authentication) {
        System.out.println(authentication.getName());/*works*/

        if (authentication != null) {
            Player playerNew = playerRepo.findByUserName(authentication.getName());

           // Map<String, Object> newGame  = new LinkedHashMap<>();
            if (playerNew != null) {

                Game gameNew = new Game();
                //System.out.println(authentication);
                gameNew.setCreationDate(new Date());
                gameRepo.save(gameNew);

                GamePlayer gamePlayerNew = new GamePlayer(new Date());

                gamePlayerNew.setPlayer(playerNew);
                gamePlayerNew.setGame(gameNew);
                gamePlayerRepo.save(gamePlayerNew);

                System.out.println(gamePlayerNew.getId());

               // newGame.put("Game_id", gameNew.getId());
                //newGame.put("Game_created", gameNew.getCreationDate());
               // newGame.put("Game_GPs", gameNew.getGamePlayers());

                System.out.println(gameNew);
                return new ResponseEntity<>(createMap("GP_Id", gamePlayerNew.getId()), HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(createMap("error", "Log In First"), HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>(createMap("error", "Log In First"), HttpStatus.UNAUTHORIZED);
                }
    }

    public Map<String, Object> createMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        return map;
    }


    /*********___________________________________ Add a GamePlayer to Game _________________________________*******/

    @RequestMapping(path = "/game/{gameId}/players", method = RequestMethod.POST)
    public ResponseEntity<Object> addPlayer(@PathVariable Long gameId, Authentication authentication) {
        System.out.println(authentication.getName());/*works*/

    Optional <Game> optionalGame = gameRepo.findById(gameId);

    Player playerNew = playerRepo.findByUserName(authentication.getName());
    GamePlayer gamePlayerNew = new GamePlayer(new Date());

        gamePlayerNew.setPlayer(playerNew);
        gamePlayerNew.setGame(optionalGame.get());
        gamePlayerRepo.save(gamePlayerNew);

        return new ResponseEntity<>(createMap("GP_Id", gamePlayerNew.getId()), HttpStatus.CREATED);
    }

}



















