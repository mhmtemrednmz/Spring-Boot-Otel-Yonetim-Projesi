package com.emrednmz.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorites")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Favorite extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
