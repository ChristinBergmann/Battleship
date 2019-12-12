package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private PlayerRepository repository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;
    private Object Date;


    @RequestMapping("/games")
    public List<Object> getAll() {

        //bei Lists use .add and by maps use.put!!!
        List<Object> games = new ArrayList<>();
        System.out.println(games);

        gameRepository.findAll().forEach(oneGame -> {
            Map<String, Object> gameMap = new HashMap<>();
            gameMap.put("Game_created", oneGame.getCreationDate());
            gameMap.put("Game_Id", oneGame.getId());
            gameMap.put("GamePlayers", gamePlayerInfo(oneGame));
            games.add(gameMap);
        });
        return games;
    }

        List<Object> gamePlayerInfo(Game game){
            List<Object> gamePlayer  = new ArrayList();

            game.getGamePlayers().forEach(gp -> {
                Map<String, Object> bla = new HashMap<>();
                bla.put("GamePlayer_Id", gp.getId());
                bla.put("Player", playerInfo(gp));
                gamePlayer.add(bla);
            });
            return (gamePlayer);
    }

        List<Object> playerInfo(GamePlayer gameplayer){
            List<Object> player  = new ArrayList();

            Map<String, Object> blupp = new HashMap<>();
            blupp.put("Player_Id", gameplayer.getPlayer().getId());
            blupp.put("Player_Username", gameplayer.getPlayer().getUserName());
            player.add(blupp);

        return (player);
    }
}