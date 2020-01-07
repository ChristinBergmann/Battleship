package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;



@CrossOrigin(origins = "http://127.0.0.1:5500/")
@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    GamePlayerRepository gamePlayerRepository;
    @Autowired
    ShipRepository shipRepository;
    @Autowired
    ScoreRepository scoreRepository;

    @RequestMapping("/games")
    public List<Object> getAll() {

        //bei Lists use .add and by maps use.put!!!
        List<Object> games = new ArrayList<>();

        gameRepository.findAll().forEach(oneGame -> {
            Map<String, Object> gameMap = new HashMap<>();
            gameMap.put("Game_created", oneGame.getCreationDate());
            gameMap.put("Game_Id", oneGame.getId());
            gameMap.put("GamePlayers", gamePlayerInfo(oneGame));
            games.add(gameMap);
        });
        return games;
    }

    List<Object> gamePlayerInfo(Game game) {
        List<Object> gamePlayer = new ArrayList();

        game.getGamePlayers().forEach(gp -> {
            Map<String, Object> gp_info = new HashMap<>();
            gp_info.put("GamePlayer_Id", gp.getId());
            gp_info.put("Player", playerInfo(gp));
            gamePlayer.add(gp_info);
        });
        return (gamePlayer);
    }

    Object playerInfo(GamePlayer gameplayer) {

        Map<String, Object> pl_info = new HashMap<>();
        pl_info.put("Player_Id", gameplayer.getPlayer().getId());
        pl_info.put("Player_Username", gameplayer.getPlayer().getUserName());

        return pl_info;

    }

    List<Object> shipsInfo(GamePlayer gameplayer) {
        List<Object> ships = new ArrayList<>();

        gameplayer.getShips().forEach(ship -> {
            Map<String, Object> ship_info = new HashMap<>();
            ship_info.put("Type", ship.getType());
            ship_info.put("Location", ship.getLocations());
            ships.add(ship_info);
            System.out.println(ships);
        });
        return (ships);
    }

    @RequestMapping("/game_view/{gamePlayerId}")
    public Object findPlayerGame(@PathVariable Long gamePlayerId) {
        System.out.println(gamePlayerId);
        System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");

        System.out.println(gamePlayerRepository.findGamePlayerById(gamePlayerId));
        GamePlayer gameplayer = gamePlayerRepository.findGamePlayerById(gamePlayerId);
        Game game = gameplayer.getGame();

        Map<String, Object> gameInfo = new HashMap<>();
        gameInfo.put("Game_Id", game.getId());
        gameInfo.put("Game_created", game.getCreationDate());
        gameInfo.put("GamePlayers", gamePlayerInfo(game));
        gameInfo.put("Ships", shipsInfo(gameplayer));
        return gameInfo;
    }
}


