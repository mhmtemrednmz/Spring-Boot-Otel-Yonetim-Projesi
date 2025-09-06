package com.emrednmz.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoFavorite extends DtoBase {
    private Long hotelId;
    private Long userId;
}
