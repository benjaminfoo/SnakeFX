package de.ostfalia.teamx.model;

public class Spielregel {

    public String name;
    public boolean regelErfuellt;

    public Spielregel() {
    }

    public Spielregel(String name, boolean regelErfuellt) {
        this.name = name;
        this.regelErfuellt = regelErfuellt;
    }

    @Override
    public String toString() {
        return "Spielregel{" +
                "name='" + name + '\'' +
                ", regelErfuellt=" + regelErfuellt +
                '}';
    }
}
