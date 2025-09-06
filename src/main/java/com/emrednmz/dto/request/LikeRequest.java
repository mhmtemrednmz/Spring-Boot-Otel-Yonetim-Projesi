package com.emrednmz.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRequest {
    @NotNull
    private Long hotelId;
}
