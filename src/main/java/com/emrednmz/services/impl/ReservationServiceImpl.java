package com.emrednmz.services.impl;

import com.emrednmz.dto.request.ReservationRequest;
import com.emrednmz.dto.response.DtoReservation;
import com.emrednmz.exception.BaseException;
import com.emrednmz.exception.ErrorMessage;
import com.emrednmz.exception.MessageType;
import com.emrednmz.mapper.ReservationMapper;
import com.emrednmz.model.Reservation;
import com.emrednmz.model.Room;
import com.emrednmz.model.UserEntity;
import com.emrednmz.repositories.ReservationRepository;
import com.emrednmz.repositories.RoomRepository;
import com.emrednmz.repositories.UserRepository;
import com.emrednmz.services.IAutenticationService;
import com.emrednmz.services.IReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final RoomRepository roomRepository;
    private final IAutenticationService authenticationService;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  ReservationMapper reservationMapper,
                                  RoomRepository roomRepository,
                                  IAutenticationService authenticationService) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.roomRepository = roomRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public Page<Reservation> findAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable);
    }

    @Override
    public List<DtoReservation> getDtoReservations(List<Reservation> reservations) {
        return reservations.stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoReservation getReservationById(Long id) {
        return reservationMapper.toDto(findReservationById(id));
    }

    @Override
    public DtoReservation createReservation(ReservationRequest reservationRequest) {
        UserEntity currentUser = authenticationService.getCurrentUser();

        Reservation entity = reservationMapper.toEntity(reservationRequest);
        entity.setCreateTime(new Date());
        entity.setUser(currentUser);
        arrangeReservation(entity,reservationRequest);
        return reservationMapper.toDto(reservationRepository.save(entity));
    }

    @Override
    public DtoReservation updateReservation(ReservationRequest reservationRequest, Long id) {
        Reservation reservation = findReservationById(id);

        UserEntity currentUser = authenticationService.getCurrentUser();

        if (!reservation.getUser().getId().equals(currentUser.getId())) {
            throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED_TRANSACTION, currentUser.getId().toString()));
        }


        reservationMapper.updateReservationFromRequest(reservationRequest, reservation);
        reservation.setUpdateTime(new Date());
        arrangeReservation(reservation,reservationRequest);
        return reservationMapper.toDto(reservationRepository.save(reservation));
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = findReservationById(id);

        UserEntity currentUser = authenticationService.getCurrentUser();

        if (!reservation.getUser().getId().equals(currentUser.getId())) {
            throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED_TRANSACTION, currentUser.getId().toString()));
        }
        reservationRepository.deleteById(id);
    }

    private Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NOT_FOUND_RESERVATION, id.toString())));
    }

    private Reservation arrangeReservation(Reservation reservation, ReservationRequest reservationRequest) {

        Room room = roomRepository.findById(reservationRequest.getRoomId())
                .orElseThrow(() -> new BaseException
                        (new ErrorMessage(MessageType.NOT_FOUND_ROOM, reservationRequest.getRoomId().toString())));

        reservation.setRoom(room);
        return reservation;
    }
}
