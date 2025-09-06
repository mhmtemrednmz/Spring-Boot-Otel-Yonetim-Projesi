package com.emrednmz.controllers.impl;

import com.emrednmz.controllers.IRestReservationController;
import com.emrednmz.controllers.RestBaseController;
import com.emrednmz.controllers.RootEntity;
import com.emrednmz.dto.request.ReservationRequest;
import com.emrednmz.dto.response.DtoReservation;
import com.emrednmz.model.Reservation;
import com.emrednmz.services.IReservationService;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservation")
public class RestReservationControllerImpl extends RestBaseController implements IRestReservationController {
    private final IReservationService reservationService;

    public RestReservationControllerImpl(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    @GetMapping("list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RootEntity<RestPageableEntity<DtoReservation>> getAllReservations(RestPageableRequest request) {
        Page<Reservation> page = reservationService.findAllReservations(getPageable(request));
        List<DtoReservation> dtoReservations = reservationService.getDtoReservations(page.getContent());
        return ok(toRestPageableResponse(page, dtoReservations));
    }

    @Override
    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RootEntity<DtoReservation> getReservationById(@PathVariable Long id) {
        return ok(reservationService.getReservationById(id));
    }

    @Override
    @PostMapping("save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER','ROLE_USER')")
    public RootEntity<DtoReservation> createReservation(@RequestBody @Valid ReservationRequest reservationRequest) {
        return ok(reservationService.createReservation(reservationRequest));
    }

    @Override
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER','ROLE_USER')")
    public RootEntity<DtoReservation> updateReservation(@RequestBody @Valid ReservationRequest reservationRequest,
                                                        @PathVariable Long id) {
        return ok(reservationService.updateReservation(reservationRequest, id));
    }

    @Override
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER','ROLE_USER')")
    public void deleteReservation(Long id) {
        reservationService.deleteReservation(id);
    }
}
