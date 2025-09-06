package com.emrednmz.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "likes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Like extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
