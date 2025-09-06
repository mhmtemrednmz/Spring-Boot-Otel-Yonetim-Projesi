package com.emrednmz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "hotels")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hotel extends BaseEntity {
    private String name;
    private String description;
    private String address;
    private String city;
    private String country;
    private String phone;
    private String email;
    private int stars;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private UserEntity manager;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Like> likes;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Favorite> favorites;



}
