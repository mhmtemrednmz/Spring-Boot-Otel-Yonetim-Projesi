package com.emrednmz.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class RoomRequest {
    private String roomNumber;
    private String type;
    private BigDecimal pricePerNight;
    private Integer capacity;
    private Boolean available;
    private Long hotelId;
}
