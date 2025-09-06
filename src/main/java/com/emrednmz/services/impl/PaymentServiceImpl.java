package com.emrednmz.services.impl;

import com.emrednmz.dto.request.PaymentRequest;
import com.emrednmz.dto.response.DtoPayment;
import com.emrednmz.enums.PaymentStatus;
import com.emrednmz.exception.BaseException;
import com.emrednmz.exception.ErrorMessage;
import com.emrednmz.exception.MessageType;
import com.emrednmz.mapper.PaymentMapper;
import com.emrednmz.model.Payment;
import com.emrednmz.model.Reservation;
import com.emrednmz.model.UserEntity;
import com.emrednmz.repositories.PaymentRepository;
import com.emrednmz.repositories.ReservationRepository;
import com.emrednmz.services.IAutenticationService;
import com.emrednmz.services.IPaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final ReservationRepository reservationRepository;
    private final IAutenticationService authenticationService;


    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              PaymentMapper paymentMapper,
                              ReservationRepository reservationRepository,
                              IAutenticationService authenticationService) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.reservationRepository = reservationRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public Page<Payment> findAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    @Override
    public List<DtoPayment> getDtoPayments(List<Payment> payments) {
        return payments.stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoPayment getDtoById(Long id) {
        return paymentMapper.toDto(findPaymentById(id));
    }

    @Override
    public DtoPayment createPayment(PaymentRequest paymentRequest) {
        Payment entity = paymentMapper.toEntity(paymentRequest);
        entity.setCreateTime(new Date());
        entity.setPaymentStatus(PaymentStatus.PENDING);
        advantagePayment(paymentRequest, entity);
        return paymentMapper.toDto(paymentRepository.save(entity));
    }

    @Override
    public DtoPayment updatePayment(PaymentRequest paymentRequest, Long id) {
        Payment entity = findPaymentById(id);
        paymentMapper.updatePaymentFromRequest(paymentRequest, entity);
        entity.setUpdateTime(new Date());
        entity.setPaymentStatus(PaymentStatus.PENDING);
        advantagePayment(paymentRequest, entity);
        return paymentMapper.toDto(paymentRepository.save(entity));
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = findPaymentById(id);
        UserEntity currentUser = authenticationService.getCurrentUser();

       if(!payment.getReservation().getUser().getId().equals(currentUser.getId())) {
           throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED_TRANSACTION, currentUser.getId().toString()));
       }
        reservationRepository.deleteById(id);
    }


    private Payment findPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(()-> new BaseException(new ErrorMessage(MessageType.NOT_FOUND_AMENITY, id.toString())));
    }


    private Payment advantagePayment(PaymentRequest paymentRequest, Payment payment) {
        Reservation reservation = reservationRepository.findById(payment.getReservation().getId())
                .orElseThrow(() -> new BaseException
                        (new ErrorMessage(MessageType.NOT_FOUND_AMENITY, payment.getReservation().getId().toString())));
        payment.setReservation(reservation);
        return payment;
    }

}
