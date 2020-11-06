package de.ostfalia.teamx.SnakeServer.model;

import de.ostfalia.teamx.shared.Spieler;

import javax.persistence.*;

@Entity(name = "spieler")
public class SpielerDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, length = 255)
    public String name;

    @Column(nullable = false, length = 255)
    public String pass;

    public SpielerDTO() {
    }

    public SpielerDTO(Long id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public SpielerDTO(Spieler spieler){
        map(spieler);
    }

    public void map(Spieler spieler){
        this.id = spieler.id;
        this.name = spieler.name;
        this.pass = spieler.pass;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
