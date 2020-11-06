package de.ostfalia.teamx.SnakeServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spielrunde {

    public String name;

    // TODO: implement missing

    public List<Spieler> spieler;

}
