package com.emrednmz.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DtoHotel extends DtoBase{
    private String name;
    private String description;
    private String address;
    private String city;
    private String country;
    private String phone;
    private String email;
    private int stars;
    private Long managerId;
    private List<DtoRoom> rooms;
    private List<DtoReview> reviews;
    private List<DtoLike> likes;
    private List<DtoFavorite> favorites;
}
