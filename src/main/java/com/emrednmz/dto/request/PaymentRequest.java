package com.emrednmz.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequest {
    private Long reservationId;
    private String paymentMethod;
    private BigDecimal amount;
}
