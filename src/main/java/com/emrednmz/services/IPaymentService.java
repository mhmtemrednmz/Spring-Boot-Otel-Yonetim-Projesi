package com.emrednmz.services;

import com.emrednmz.dto.request.PaymentRequest;
import com.emrednmz.dto.response.DtoPayment;
import com.emrednmz.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPaymentService {
    Page<Payment> findAllPayments(Pageable pageable);
    List<DtoPayment> getDtoPayments(List<Payment> payments);
    DtoPayment getDtoById(Long id);
    DtoPayment createPayment(PaymentRequest paymentRequest);
    DtoPayment updatePayment(PaymentRequest paymentRequest, Long id);
    void deletePayment(Long id);

}
