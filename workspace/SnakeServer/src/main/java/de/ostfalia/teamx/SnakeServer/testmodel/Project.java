package de.ostfalia.teamx.SnakeServer.testmodel;

import javax.persistence.*;

@Entity
public class Project {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    public Employee employee;

}
