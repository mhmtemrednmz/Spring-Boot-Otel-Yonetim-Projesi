package com.emrednmz.services;

import com.emrednmz.dto.request.ReservationRequest;
import com.emrednmz.dto.response.DtoReservation;
import com.emrednmz.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReservationService {
    Page<Reservation> findAllReservations(Pageable pageable);
    List<DtoReservation> getDtoReservations(List<Reservation> reservations);
    DtoReservation getReservationById(Long id);
    DtoReservation createReservation(ReservationRequest reservationRequest);
    DtoReservation updateReservation(ReservationRequest reservationRequest, Long id);
    void deleteReservation(Long id);
}
