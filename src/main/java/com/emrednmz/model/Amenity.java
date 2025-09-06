package com.emrednmz.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "amenities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Amenity extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
}
