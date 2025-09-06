package com.emrednmz.controllers;

import com.emrednmz.dto.request.PaymentRequest;
import com.emrednmz.dto.response.DtoPayment;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;

public interface IRestPaymentController {
    RootEntity<RestPageableEntity<DtoPayment>> getAllPayments(RestPageableRequest request);
    RootEntity<DtoPayment> getPaymentById(Long id);
    RootEntity<DtoPayment> createPayment(PaymentRequest paymentRequest);
    RootEntity<DtoPayment> updatePayment(PaymentRequest paymentRequest, Long id);
    void deletePayment(Long id);

}
