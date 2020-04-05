package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.stream.Collectors.toList;


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

    @Autowired
    private ShotRepository shotRepo;

    private Object Object;

    private boolean methodCall = false;


    public SalvoController() { }

    @RequestMapping("/games")
    public List<Object> getAllGames(Authentication authentication) {

        Player Curr_Player = playerRepo.findByUserName(authentication.getName());
        System.out.println("1 THIS IS ME" + Curr_Player);
        List<Object> games = new ArrayList<>();

        gameRepo.findAll().forEach(oneGame -> {
            Map<String, Object> gameMap = new HashMap<>();
            gameMap.put("Curr_PlayerName", Curr_Player.getUserName());
            gameMap.put("Game_created", oneGame.getCreationDate());
            gameMap.put("Game_Id", oneGame.getId());
            gameMap.put("Game_finish", getScoreDate(oneGame.getScores()));
            gameMap.put("GamePlayers", gamePlayersInfo(oneGame));
            games.add(gameMap);
        });
        return games;
    }

    private Date getScoreDate(Set<Score> scores) {
        return scores.stream()
                .findFirst()
                .map(score -> score.getFinishDate())
                .orElse(null);
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
            shots.add(sh.getLocations());
            //shots.add(sh.getTurn());
        }
        return shots;
    }


    public List<Object> turnsInfo(GamePlayer gameplayer) {
        List<Object> turns = new ArrayList<>();
        Integer turn = 0;
        for (Shot sh : gameplayer.getShots()) {
            //if (!gameplayer.getShots().isEmpty())
            for (int a = 0; a < sh.getTurn(); a++)
               turns.add(sh.getTurn());
            System.out.println("HERE IS MY TURN" + sh.getTurn());

        }
//            turn += sh.getTurn();
            turns.add(turn);
            System.out.println("HERE IS MY TURN" + turns);

       // }
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

    public double scoresInfo(GamePlayer gameplayer) {
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
        GamePlayer opponentGamePlayer = getOpponentGamePlayer(myGameplayer);

        if (authentication.getName() == null) {
            return null;
        }

        Long playerID = myGameplayer.getPlayer().getId();
        Long userID = playerRepo.findByUserName(authentication.getName()).getId();

        if (playerID.equals(userID)) {

            Map<String, Object> gameInfo = new HashMap<>();
            gameInfo.put("Game_Id", game.getId());
            gameInfo.put("Game_created", game.getCreationDate());

        if (opponentGamePlayer != null) {
            gameInfo.put("Opp_name", opponentGamePlayer.getPlayer().getUserName());
            gameInfo.put("Opp_score", scoresInfo(opponentGamePlayer));
            gameInfo.put("Opp_shots", shotsInfo(opponentGamePlayer));
            gameInfo.put("Opp_turns", turnsInfo(opponentGamePlayer));
            }
            gameInfo.put("Curr_name", myGameplayer.getPlayer().getUserName());
            gameInfo.put("Curr_score", scoresInfo(myGameplayer));
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
            pl_list.put("Scores", score_Info(pl));
            rankings.add(pl_list);
        });
        return rankings;
    }

    public Object score_Info(Player player) {

        Map<String, Object> sc_info = new HashMap<>();
        Set<Score> scores = player.getScores();
        Double total = 0.0;

        for (Score sc : scores) {
            if (sc.getScore() == 1.0) {
                sc_info.put("Win", sc.getScore());
            } else if (sc.getScore() == 0.5) {
                sc_info.put("Tie", sc.getScore());
            } else {
                sc_info.put("Lost", sc.getScore());
            }
            total += sc.getScore();
            sc_info.put("Total", total);
        }
        return sc_info;
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
            return new ResponseEntity<>("Ooopsie missing data!", HttpStatus.BAD_REQUEST);
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

    /*********___________________________________ Join a Game _________________________________*******/

    @RequestMapping(path = "/game/{id}/players", method = RequestMethod.POST)
    public ResponseEntity<Object> joinGame(@PathVariable Long id, Authentication authentication) {

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

    @PostMapping(path = "/players/{id}/ships")
    public ResponseEntity<Object> addShips(@PathVariable Long id, @RequestBody List<Ship> ships, Authentication authentication) {

        Game game = gameRepo.getOne(id);
        Player currentPlayer = playerRepo.findByUserName(authentication.getName());
        GamePlayer myGameplayer = getMyGamePlayer_fromGame(game, authentication);
        System.out.println("2 MY GP in place ships" + myGameplayer);
        System.out.println("2 THIS IS MY GAME ID" + game);

        gameRepo.findById(id);
        if (currentPlayer == null) {
            return new ResponseEntity<>(createMap("error", "LOG IN first!"), HttpStatus.UNAUTHORIZED);
        }
        if (myGameplayer == null) {
            return new ResponseEntity<>(createMap("error", "GamePlayer does not exist!"), HttpStatus.UNAUTHORIZED);
        }
        if (!myGameplayer.getPlayer().getId().equals(currentPlayer.getId())) {
            return new ResponseEntity<>(createMap("error", "This is not your Game!"), HttpStatus.UNAUTHORIZED);
        }
        if (myGameplayer.getShips().size() >= 5) {
            return new ResponseEntity<>(createMap("error", "Already placed ships!"), HttpStatus.FORBIDDEN);
        }
        else {

            for (Ship ship : ships) {
                myGameplayer.addShip(ship);
                shipRepo.save(ship);
            }
            System.out.println("Your ships are placed!");
            return new ResponseEntity<>(createMap("success","Your ships are placed!"), HttpStatus.CREATED);
        }
    }

    /*********_____________________________________ Place Shots ________________________________________*******/
    @PostMapping(path = "/players/{id}/shots")
    public  ResponseEntity<Object> addShots(@PathVariable Long id,
                                           @RequestBody List<Shot> shots, Authentication authentication) {
        Game game = gameRepo.getOne(id);
        Player currentPlayer = playerRepo.findByUserName(authentication.getName());
        GamePlayer myGameplayer = getMyGamePlayer_fromGame(game, authentication);
        GamePlayer opponentGameplayer = getOpponentGamePlayer(myGameplayer);

//        System.out.println("3 ME Player" + currentPlayer.getUserName());
//        System.out.println("4 ME GP ID" + myGameplayer.getId());

    gameRepo.findById(id);
        System.out.println("HHHHEEEELLLOOOOOOOOOOO");
        if (myGameplayer == null){
            return new ResponseEntity<>(createMap("error", "GamePlayer does not exist!"), HttpStatus.UNAUTHORIZED);
        }
        if (!myGameplayer.getPlayer().getId().equals(currentPlayer.getId())) {
            return new ResponseEntity<>(createMap("error", "This is not your Game!"), HttpStatus.UNAUTHORIZED);
        }
        if(opponentGameplayer == null){
            return new ResponseEntity<>(createMap("error", "You have no opponent Player"), HttpStatus.SERVICE_UNAVAILABLE);
        }
        else {


//            if (checkIfShotsHasBeenAlreadyFired(myGameplayer, shots)) {
//                return new ResponseEntity<>(createMap("error", "The salvos have already been added"), HttpStatus.FORBIDDEN);
//            }


            if (opponentGameplayer.getShips().size() < 5) {
                return new ResponseEntity<>(createMap("error", "You have to wait!"), HttpStatus.CONFLICT);
            }
            if (myGameplayer.getShips().size() > 5) {
                return new ResponseEntity<>(createMap("error", "Already placed ships!"), HttpStatus.FORBIDDEN);
            }
            if (myGameplayer.getShots().size() > 3) {
                return new ResponseEntity<>(createMap("error", "Already placed shots!"), HttpStatus.FORBIDDEN);
            }
            if (myGameplayer.getShots().size() > opponentGameplayer.getShots().size()) {
                return new ResponseEntity<>(createMap("error", "You have to wait!"), HttpStatus.NOT_ACCEPTABLE);
            }
            if(methodCall){
                return new ResponseEntity<>(createMap("error", "The game is finished"), HttpStatus.METHOD_NOT_ALLOWED);
            }

        }
            for (Shot shot : shots) {
                    myGameplayer.addShot(shot);
                    shotRepo.save(shot);
                System.out.println("5 HERE are MY shots" + shots);
            }

//            myGameplayer.addShot(shots);
//            shotRepo.save(shots);

            System.out.println("6 Your shots are placed!");
        return new ResponseEntity<>(createMap("success", "Your shots are placed!"), HttpStatus.CREATED);
        }




//    private boolean checkIfShotsHasBeenAlreadyFired(GamePlayer myGameplayer, Shot shots) {
//        List<Integer> gamePlayerTurn = myGameplayer.getShots().stream().map(Shot::getTurn).collect(toList());
//        return gamePlayerTurn.contains(shots.getTurn());
//    }



    public Map<String, Object> createMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

}

















