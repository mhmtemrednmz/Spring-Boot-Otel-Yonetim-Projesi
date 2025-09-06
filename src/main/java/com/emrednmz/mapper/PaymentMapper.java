package com.emrednmz.mapper;

import com.emrednmz.dto.request.PaymentRequest;
import com.emrednmz.dto.response.DtoPayment;
import com.emrednmz.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface PaymentMapper {


    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    Payment toEntity(PaymentRequest request);

    @Mapping(source = "reservation.id", target = "reservationId")
    DtoPayment toDto(Payment payment);


    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    void updatePaymentFromRequest(PaymentRequest request, @MappingTarget Payment payment);
}
