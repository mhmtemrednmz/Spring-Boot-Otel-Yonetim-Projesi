package com.emrednmz.mapper;

import com.emrednmz.dto.request.RoomRequest;
import com.emrednmz.dto.response.DtoRoom;
import com.emrednmz.model.Room;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {HotelMapper.class})
public interface RoomMapper {

    // Entity -> DTO
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "type", ignore = true)
    Room toEntity(RoomRequest roomRequest);

    @Mapping(source = "hotel.id", target = "hotelId")
    @Mapping(source = "type", target = "type")
    DtoRoom toDto(Room room);


    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "type", ignore = true)
    void updateRoomFromRequest(RoomRequest roomRequest,@MappingTarget Room room);
}
