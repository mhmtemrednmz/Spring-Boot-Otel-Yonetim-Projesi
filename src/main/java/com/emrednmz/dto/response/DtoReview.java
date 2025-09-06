package com.emrednmz.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoReview extends DtoBase{
    private int rating;
    private String comment;
    private Long hotelId;
    private Long userId;

}
