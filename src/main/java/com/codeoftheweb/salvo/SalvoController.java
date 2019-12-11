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


    @RequestMapping("/games")
    public Set<Long> getAll() {
        /*
        Set<Long> games = new HashSet<>();
        Map<String, Object> gameMap = new HashMap<>();
        System.out.println(gameMap);
        gameRepository.findAll().forEach(oneGame -> {
            Map<String, Object> test = new HashMap<>();
            oneGame.getGamePlayers().forEach(gp -> gp.getPlayer());
            games.add(oneGame.getId());
        });
        */
        Set <Long> games = new HashSet<>();
        Map<String, Object> gameMap = new HashMap<>();
        System.out.println(gameMap);
        return games;
    }

   /*
    @RequestMapping("/owners")
    public List<Object> getOwners( {
    ...
    }

    @RequestMapping("/pets")
    public List<Object> getPets() {
    ...
    }
    */
}