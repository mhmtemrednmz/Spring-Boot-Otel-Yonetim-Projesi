package com.emrednmz.mapper;

import com.emrednmz.dto.request.ReservationRequest;
import com.emrednmz.dto.response.DtoReservation;
import com.emrednmz.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    // Request -> Entity

    @Mapping(target = "room", ignore = true)
    @Mapping(target = "user", ignore = true)
    Reservation toEntity(ReservationRequest reservationRequest);

    // Entity -> DTO
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "user.id", target = "userId")
    DtoReservation toDto(Reservation reservation);

    // Update

    @Mapping(target = "room", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateReservationFromRequest(ReservationRequest request, @MappingTarget Reservation reservation);
}
