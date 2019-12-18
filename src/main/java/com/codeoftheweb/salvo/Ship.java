package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity

public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;
    private String type;


    @ElementCollection
    @Column(name = "locations")
    private List<String> locations = new ArrayList<>();



    public Ship() {}

    public Ship (String type, List<String> locations) {
        this.type = type;
        this.locations = locations;
    }


    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }


    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
    public List<String> getLocations() {
        return locations;
    }


    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }


    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
}
