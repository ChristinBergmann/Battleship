package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date creationDate = new Date();


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;


    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    Set<Ship> ships = new HashSet<>();

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    private Set<Shot> shots = new HashSet<>();

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set <GamePlayer> gamePlayers = new HashSet<>();


    public GamePlayer() { }

    public void addShip(Ship ship) {
        ship.setGamePlayer(this);
        ships.add(ship);
    }
    public void addShot(Shot shot) {
        shot.setGamePlayer(this);
        shots.add(shot);
    }

    public GamePlayer(Date creationDate) {
        this.creationDate = creationDate;

    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }


    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }


    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public void plusSeconds(int seconds) {
        this.creationDate = Date.from(creationDate.toInstant().plusSeconds(seconds));
    }


    public Set<Ship> getShips() {
        return ships;
    }
    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }


    public Set<Shot> getShots() {
        return shots;
    }
    public void setShots(Set<Shot> shots) {
        this.shots = shots;
    }


    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }
    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public void getGamePlayer_id() {
    }
}
