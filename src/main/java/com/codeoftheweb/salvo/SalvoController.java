package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private ShotRepository shotRepo;

    @RequestMapping("/games")
    public List<Object> getAllGames(Authentication authentication) {

        Player Curr_Player = playerRepo.findByUserName(authentication.getName());
        System.out.println(Curr_Player);
        List<Object> games = new ArrayList<>();

        gameRepo.findAll().forEach(oneGame -> {
            Map<String, Object> gameMap = new HashMap<>();
            gameMap.put("Curr_PlayerName", Curr_Player.getUserName());
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
        return pl_info;
    }

    public List<Object> shipsInfo(GamePlayer gameplayer) {
        List<Object> ships = new ArrayList<>();

        gameplayer.getShips().forEach(ship -> {
            Map<String, Object> ship_info = new HashMap<>();
            ship_info.put("Ship_Id", ship.getId());
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
            for (int a = 0; a < sh.getTurn(); a++) {
                turns.add(sh.getTurn());
            }
        });
        return turns;
    }

    public List<Object> hitsInfo(GamePlayer gameplayer) {

        List<Object> hitsLocations = new ArrayList<>();
        List<Object> shotsLocations = new ArrayList<>();
        List<Object> shipsLocations = new ArrayList<>();

        if (gameplayer.getShips().size() != 0 && getOpponentGamePlayer(gameplayer).getShots().size() != 0) {
            for (Ship sh : gameplayer.getShips()) {
                shipsLocations.addAll(sh.getLocations());
            }

            for (Shot s : getOpponentGamePlayer(gameplayer).getShots()) {
                shotsLocations.addAll(s.getLocations());
            }

            shotsLocations.forEach(shot -> {
                if (shipsLocations.contains(shot)) {
                    hitsLocations.add(shot);
                }
            });
        }
        //System.out.println(hitsLocations);
        return hitsLocations;
    }

    public double scoreInfo(GamePlayer gameplayer) {
        double score = 0.0;
        for (Score sc : gameplayer.getPlayer().getScores()) {
            score += sc.getScore();
        }
        return score;
    }

    public GamePlayer getOpponentGamePlayer(GamePlayer gameplayer) {
        GamePlayer otherGP = null;

        for (GamePlayer gp : gameplayer.getGame().getGamePlayers()) {
            if (gp != gameplayer) {
                otherGP = gp;
            }
        }
        return otherGP;
    }

    public GamePlayer getMyGamePlayer_fromGame(Game game, Authentication authentication) {
        GamePlayer myGp = null;

        for (GamePlayer gp : game.getGamePlayers()) {

            if (gp.getPlayer().getUserName().equals(authentication.getName())) {
                myGp = gp;
            }
        }
        return myGp;
    }


    /********________________________________________ GAME VIEW PAGE _____________________________________********/

    @RequestMapping("/game_view/{gm_id}")
    public Map<String, Object> findPlayerGame(@PathVariable Long gm_id, Authentication authentication) {

        Game game = gameRepo.getOne(gm_id);
        GamePlayer myGameplayer = getMyGamePlayer_fromGame(game, authentication);

        if (authentication.getName() == null) {
            return null;
        }

        Long playerID = myGameplayer.getPlayer().getId();
        Long userID = playerRepo.findByUserName(authentication.getName()).getId();

        if (playerID == userID) {

            Map<String, Object> gameInfo = new HashMap<>();
            gameInfo.put("Game_Id", game.getId());
            gameInfo.put("Game_created", game.getCreationDate());

            GamePlayer enemyGamePlayer = getOpponentGamePlayer(myGameplayer);
        if (enemyGamePlayer != null) {
            gameInfo.put("Opp_name", enemyGamePlayer.getPlayer().getUserName());
            gameInfo.put("Opp_score", scoreInfo(enemyGamePlayer));
            gameInfo.put("Opp_shots", shotsInfo(enemyGamePlayer));
            gameInfo.put("Opp_turns", turnsInfo(enemyGamePlayer));
            }
            gameInfo.put("Curr_name", myGameplayer.getPlayer().getUserName());
            gameInfo.put("Curr_score", scoreInfo(myGameplayer));
            gameInfo.put("Curr_ships", shipsInfo(myGameplayer));
            gameInfo.put("Curr_shots", shotsInfo(myGameplayer));
            gameInfo.put("Curr_turns", turnsInfo(myGameplayer));
            gameInfo.put("Curr_hits", hitsInfo(myGameplayer));
            return gameInfo;
        }
        return null;
    }

    /***------------------------- LEADERBOARD ----------------------------***/
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
            return new ResponseEntity<>("Ooopsie you missed a field!", HttpStatus.BAD_REQUEST);
        }
        if (playerRepo.findByUserName(userName) != null) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.CONFLICT);
        }
        if (playerRepo.findByEmail(email) != null) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.CONFLICT);
        }
        playerRepo.save(new Player(userName, email, passwordEncoder2().encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    /*********______________________________________  create New Game _______________________________________*******/

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Object> createGame(Authentication authentication) {
        //System.out.println(authentication.getName());/*works*/

        Player currentPlayer = playerRepo.findByUserName(authentication.getName());

        if (currentPlayer != null) {

            Game gameNew = new Game();
            gameNew.setCreationDate(new Date());
            gameRepo.save(gameNew);

            GamePlayer gamePlayerNew = new GamePlayer(new Date());
            gamePlayerNew.setPlayer(currentPlayer);
            gamePlayerNew.setGame(gameNew);
            gamePlayerRepo.save(gamePlayerNew);

            return new ResponseEntity<>(createMap("gm_id", gameNew.getId()), HttpStatus.CREATED);

        } else {
            return new ResponseEntity<>(createMap("error", "Log In First!"), HttpStatus.UNAUTHORIZED);
        }
    }

    public Map<String, Object> createMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }


    /*********___________________________________ Join a Game _________________________________*******/

    @RequestMapping(path = "/game/{id}/players", method = RequestMethod.POST)
    public ResponseEntity<Object> joinGame(@PathVariable Long id, Authentication authentication) {
        //System.out.println(authentication.getName());/*works*/

        Game optionalGame = gameRepo.getOne(id);
        Player currentPlayer = playerRepo.findByUserName(authentication.getName());
        String opponentPlayerGame = optionalGame.getGamePlayers().stream().findFirst().get().getPlayer().getUserName();

        gameRepo.findById(id);
        if (!currentPlayer.getUserName().equals(opponentPlayerGame) && optionalGame.getGamePlayers().size() <= 1) {

            GamePlayer gamePlayerNew = new GamePlayer(new Date());

            gamePlayerNew.setPlayer(currentPlayer);
            gamePlayerNew.setGame(optionalGame);
            gamePlayerRepo.save(gamePlayerNew);

            return new ResponseEntity<>(createMap("gm_id", id), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(createMap("error", "It is not your Game!"), HttpStatus.FORBIDDEN);
        }
    }

    /*********_____________________________________ Place Ships ________________________________________*******/

    //@RequestMapping(path = "/players/{gm_id}/ships", method = RequestMethod.POST)
    //api/players/{gm_id}/ships
    @PostMapping(path = "/players/{id}/ships")
    public ResponseEntity<Object> addShips(@PathVariable Long id, @RequestBody List<Ship> ships, Authentication authentication) {

        Game game = gameRepo.getOne(id);
        Player currentPlayer = playerRepo.findByUserName(authentication.getName());
        GamePlayer myGameplayer = getMyGamePlayer_fromGame(game, authentication);
        System.out.println("HIIIIIIIIIIIIIIIIIIIIIII");
        System.out.println(myGameplayer);

        if (getMyGamePlayer_fromGame(game, authentication) == null){
            return new ResponseEntity<>(createMap("error", "GamePlayer does not exist!"), HttpStatus.UNAUTHORIZED);
        }
//        if (myGameplayer == null) {
//            return new ResponseEntity<>(createMap("error", "Log In First!"), HttpStatus.UNAUTHORIZED);
//        }
        if (myGameplayer.getShips().size() >= 5) {
            return new ResponseEntity<>(createMap("error", "Already placed ships!"), HttpStatus.FORBIDDEN);
        }
        if (myGameplayer.getPlayer().getId() != currentPlayer.getId()) {
            return new ResponseEntity<>(createMap("error", "This is not your Game!"), HttpStatus.UNAUTHORIZED);
        }
        else {
            for (Ship ship : ships) {
                myGameplayer.addShip(ship);
                gamePlayerRepo.save(myGameplayer);
                shipRepo.save(ship);

            }
            System.out.println("Your ships are placed!");
            return new ResponseEntity<>(createMap("success","Your ships are placed!"), HttpStatus.CREATED);
        }
    }

    /*********_____________________________________ Place Shots ________________________________________*******/

    @RequestMapping(path = "/players/{gm_id}/shots", method = RequestMethod.POST)
    public ResponseEntity<Object> addShots(@PathVariable Long gm_id,
                                                        @RequestBody List<Shot> shots, Authentication authentication) {

        Game game = gameRepo.getOne(gm_id);
        Player currentPlayer = playerRepo.findByUserName(authentication.getName());
        GamePlayer myGameplayer = getMyGamePlayer_fromGame(game, authentication);
        GamePlayer opponentGamePlayer = getOpponentGamePlayer(myGameplayer);
//        GamePlayer myGameplayer = gamePlayerRepo.getOne(gamePlayerId);
//        GamePlayer opponentGamePlayer = getOpponentGamePlayer(myGameplayer);
//        Player currentPlayer = playerRepo.findByUserName(authentication.getName());

        if (getMyGamePlayer_fromGame(game, authentication) == null){
        //if (gamePlayerRepo.findById(gm_id) == null) {
            return new ResponseEntity<>(createMap("error", "GamePlayer does not exist!"), HttpStatus.UNAUTHORIZED);
        }
        if (myGameplayer == null) {
            return new ResponseEntity<>(createMap("error", "Log In First!"), HttpStatus.UNAUTHORIZED);
        }
        if (myGameplayer.getPlayer().getId() != currentPlayer.getId()) {
            return new ResponseEntity<>(createMap("error", "This is not your Game!"), HttpStatus.UNAUTHORIZED);
        }
        if (opponentGamePlayer == null) {
            return new ResponseEntity<>(createMap("error", "There is no opponent Player!"), HttpStatus.UNAUTHORIZED);
        }else {
/*            if (checkIfShotsHasBeenAlreadyFired(myGamePlayer, shots)) {
                return new ResponseEntity<>(createMap("error", "The shots have already been added"), HttpStatus.FORBIDDEN);
            }*/
            if (opponentGamePlayer.getShips().size() < 5) {
                return new ResponseEntity<>(createMap("error", "You have to wait!"), HttpStatus.CONFLICT);
            }
            if (myGameplayer.getShips().size() >= 5) {
                return new ResponseEntity<>(createMap("error", "Already placed ships!"), HttpStatus.FORBIDDEN);
            }
            if (myGameplayer.getShots().size() >= 5) {
                return new ResponseEntity<>(createMap("error", "Already placed shots!"), HttpStatus.FORBIDDEN);
            }
/*

/*
            boolean methodCall;
            if (methodCall == true) {
                return new ResponseEntity<>(createMap("error", "The game is finished!"), HttpStatus.METHOD_NOT_ALLOWED);
            }
*/
        }
        for (Shot shot : shots) {
                myGameplayer.addShot(shot);
                gamePlayerRepo.save(myGameplayer);
                shotRepo.save(shot);
            }
            //System.out.println("Your shots are placed!");
            return new ResponseEntity<>(createMap("success", "Your shots are placed!"), HttpStatus.CREATED);
        }

/*    private boolean checkIfShotsHasBeenAlreadyFired (GamePlayer myGamePlayer, Shot shots){
        List<Integer> gamePlayerTurn = myGamePlayer.getShots().stream().filter(shot -> shots.getTurn());
        if (gamePlayerTurn.contains(shots.getTurn())) {
            return true;
        }
        return false;
    }*/
}

















