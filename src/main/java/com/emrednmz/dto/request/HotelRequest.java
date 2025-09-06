package com.emrednmz.dto.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class HotelRequest {
    private String name;
    private String description;
    private String address;
    private String city;
    private String country;
    private String phone;
    private String email;
    private int stars;

}
