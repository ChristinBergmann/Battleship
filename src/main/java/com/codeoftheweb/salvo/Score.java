package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;


    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    Set<Score> scores = new HashSet<Score>();
    private Double score;
    private Date finishDate;


    public Score() {
    }

    public Score(Date finishDate, Double score) {
        this.finishDate = finishDate;
        this.score = score;

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


    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }


    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
    public Date getFinishDate() {
        return finishDate;
    }
}


