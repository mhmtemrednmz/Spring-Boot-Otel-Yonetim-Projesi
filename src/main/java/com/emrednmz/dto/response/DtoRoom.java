package com.emrednmz.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DtoRoom extends DtoBase {
    private String roomNumber;
    private String type;
    private BigDecimal pricePerNight;
    private Integer capacity;
    private Boolean available;
    private Long hotelId;
}
