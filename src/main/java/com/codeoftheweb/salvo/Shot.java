package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity

public class Shot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    private Integer turn;

    @ElementCollection
    @Column(name = "locations")
    private List<String> locations = new ArrayList<>();



    public Shot() { }

    public Shot(Integer turn, List<String> locations, GamePlayer gamePlayer) {
        this.turn = turn;
        this.locations = locations;
        this.gamePlayer = gamePlayer;
    }


    public Integer getTurn() {
        return turn;
    }
    public void setTurn(Integer turn) {
        this.turn = turn;
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public List<String> getLocations() {
        return locations;
    }
    public void setLocations(List<String> locations) {
        this.locations = locations;
    }


    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public void add(Shot shot) {
    }
}
