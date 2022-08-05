package me.thekloras.Entity;

import javax.persistence.*;

@Entity
@Table(name = "restaurants")

public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurantId")
    private int restaurantId;
    @Column(name = "restaurantName")
    private String restaurantName;
    @Column(name = "restaurantAddress")
    private String restaurantAddress;

}
