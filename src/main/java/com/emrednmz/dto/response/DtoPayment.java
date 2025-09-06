package com.emrednmz.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DtoPayment extends DtoBase {
    private Long reservationId;
    private String paymentMethod;
    private String paymentStatus;
    private BigDecimal amount;
}
