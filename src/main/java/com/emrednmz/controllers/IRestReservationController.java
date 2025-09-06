package com.emrednmz.controllers;

import com.emrednmz.dto.request.ReservationRequest;
import com.emrednmz.dto.response.DtoReservation;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;

public interface IRestReservationController {
    RootEntity<RestPageableEntity<DtoReservation>> getAllReservations(RestPageableRequest request);
    RootEntity<DtoReservation> getReservationById(Long id);
    RootEntity<DtoReservation> createReservation(ReservationRequest reservationRequest);
    RootEntity<DtoReservation> updateReservation(ReservationRequest reservationRequest,Long id);
    void deleteReservation(Long id);
 }
