package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
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


        Player Curr_Player = playerRepo.findByUserName(authentication.getName());
        System.out.println(Curr_Player);
        List<Object> games = new ArrayList<>();

        gameRepo.findAll().forEach(oneGame -> {
            Map<String, Object> gameMap = new HashMap<>();
            gameMap.put("Curr_PlayerName",Curr_Player.getUserName());
            gameMap.put("Game_created", oneGame.getCreationDate());
            gameMap.put("Game_Id", oneGame.getId());
            gameMap.put("GamePlayers", gamePlayersInfo(oneGame));
            games.add(gameMap);
        });
        return games;
    }

    public List<Object> gamePlayersInfo(Game game) {
        List<Object> gamePlayers = new ArrayList<>();

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
       // pl_info.put("Score", scoreInfo(gameplayer));
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

        for (Shot sh : gameplayer.getShots()) {
            shots.addAll(sh.getLocations());
        }
        return shots;
    }
    public List<Object> turnsInfo(GamePlayer gameplayer) {
        List<Object> turns = new ArrayList<>();

        gameplayer.getShots().forEach(sh -> {

            for(int a = 0; a < sh.getTurn(); a++){
                turns.add(sh.getTurn());
            }
        });
        return turns;
    }

    public double scoreInfo(GamePlayer gameplayer) {
        double score = 0.0;
        for(Score sc: gameplayer.getPlayer().getScores()){
            score += sc.getScore();
        }
        return score;
    }

    public List<Object> hitsInfo(GamePlayer gameplayer) {

        List<Object> hitsLocations = new ArrayList<>();
        List<Object> shotsLocations = new ArrayList<>();
        List<Object> shipsLocations = new ArrayList<>();

        if (gameplayer.getShips().size() != 0 && getOpponent(gameplayer).getShots().size() != 0){
            for (Ship sh : gameplayer.getShips()) {
                shipsLocations.addAll(sh.getLocations());
            }

            for (Shot s : getOpponent(gameplayer).getShots()) {
                shotsLocations.addAll(s.getLocations());
            }

            shotsLocations.forEach(shot -> {
                if(shipsLocations.contains(shot)){
                    hitsLocations.add(shot);
                }
            });
        }
        System.out.println(hitsLocations);
        return hitsLocations;
    }

    public GamePlayer getOpponent (GamePlayer gameplayer){
        GamePlayer otherGP = null;

        for(GamePlayer gp: gameplayer.getGame().getGamePlayers()) {
        if (gp != gameplayer) {
            otherGP = gp;
            }
        }
        return otherGP;
    }


    /********________________________________________ GAME VIEW PAGE _____________________________________********/

    @RequestMapping("/game_view/{gamePlayerId}")
    public Map<String,Object> findPlayerGame(@PathVariable Long gamePlayerId, Authentication authentication) {

        GamePlayer gameplayer = gamePlayerRepo.getOne(gamePlayerId);

        if (authentication.getName() != null) {

            long playerID = gameplayer.getPlayer().getId();
            long userID = playerRepo.findByUserName(authentication.getName()).getId();

            if (playerID == userID) {

                Game game = gameplayer.getGame();

                Map<String, Object> gameInfo = new HashMap<>();
                gameInfo.put("Game_Id", game.getId());
                gameInfo.put("Game_created", game.getCreationDate());
                    game.getGamePlayers().forEach(x -> {
                        if (x != gameplayer) {
                            gameInfo.put("Opp_name", x.getPlayer().getUserName());
                            gameInfo.put("Opp_score", scoreInfo(x));
                            gameInfo.put("Opp_shots", shotsInfo(x));
                            gameInfo.put("Opp_turns", turnsInfo(x));

                        }else {
                            gameInfo.put("Curr_name", gameplayer.getPlayer().getUserName());
                            gameInfo.put("Curr_score", scoreInfo(gameplayer));
                            gameInfo.put("Curr_ships", shipsInfo(gameplayer));
                            gameInfo.put("Curr_shots", shotsInfo(gameplayer));
                            gameInfo.put("Curr_turns", turnsInfo(gameplayer));
                            gameInfo.put("Curr_hits", hitsInfo(gameplayer));
                        }
                    });
                return gameInfo;
            }
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
    public ResponseEntity<Object> addPlayer(Authentication authentication) {
        System.out.println(authentication.getName());/*works*/

        Player currentPlayer = playerRepo.findByUserName(authentication.getName());

        if (currentPlayer != null) {

            Game gameNew = new Game();

            gameNew.setCreationDate(new Date());
            gameRepo.save(gameNew);

            GamePlayer gamePlayerNew = new GamePlayer(new Date());

            gamePlayerNew.setPlayer(currentPlayer);
            gamePlayerNew.setGame(gameNew);
            gamePlayerRepo.save(gamePlayerNew);


           return new ResponseEntity<>(createMap("GP_Id", gamePlayerNew.getId()), HttpStatus.CREATED);

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

    @RequestMapping(path = "/game/{id}/players", method = RequestMethod.POST)
    public ResponseEntity<Object> addPlayer(@PathVariable Long id, Authentication authentication) {
        //System.out.println(authentication.getName());/*works*/

        Game optionalGame = gameRepo.getOne(id);
        Player currentPlayer = playerRepo.findByUserName(authentication.getName());
        String opponentPlayerGame = optionalGame.getGamePlayers().stream().findFirst().get().getPlayer().getUserName();

        if (gameRepo.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (!currentPlayer.getUserName().equals(opponentPlayerGame) && optionalGame.getGamePlayers().size() <= 1) {

            GamePlayer gamePlayerNew = new GamePlayer(new Date());

            gamePlayerNew.setPlayer(currentPlayer);
            gamePlayerNew.setGame(optionalGame);
            gamePlayerRepo.save(gamePlayerNew);

            return new ResponseEntity<>(createMap("GP_Id", gamePlayerNew.getId()), HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(createMap("error","It is not your Game"), HttpStatus.FORBIDDEN);
        }
    }
}

















